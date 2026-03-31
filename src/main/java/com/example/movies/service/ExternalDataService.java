package com.example.movies.service;

import com.example.movies.dto.MovieDTO;
import com.example.movies.model.*;
import com.example.movies.repository.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.List;

@Service
public class ExternalDataService {

    private static final Logger log = LoggerFactory.getLogger(ExternalDataService.class);

    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final GenreRepository genreRepository;
    private final DirectorRepository directorRepository;

    private static final String SIMKL_API_KEY = "d5bbb687eafb0ad0565de52972ccbee56338acb698946b55a579fca01a87d92a";

    public ExternalDataService(MovieRepository movieRepository,
                               ActorRepository actorRepository,
                               GenreRepository genreRepository,
                               DirectorRepository directorRepository) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
        this.genreRepository = genreRepository;
        this.directorRepository = directorRepository;
    }

    // ─── Fetch trending movies from Simkl JSON API ───────────────────────────────
    public void fetchMoviesFromApi() throws IOException {
        String urlString = "https://data.simkl.in/discover/trending/movies/today_100.json";

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(15000);

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("Failed to fetch trending movies. HTTP code: " + responseCode);
        }

        try (var inputStream = connection.getInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(inputStream);

            if (root.isArray()) {
                log.info("Отримано {} фільмів з API", root.size());
                int saved = 0;

                for (JsonNode node : root) {
                    String title = node.path("title").asText(null);
                    if (title == null) continue;

                    Movie movie = new Movie();
                    movie.setTitle(title);
                    movie.setYear(node.path("year").asText(null));
                    movie.setOverview(node.path("overview").asText(null));

                    // Формування повного URL постера
                    String posterId = node.path("poster").asText(null);
                    if (posterId != null && !posterId.isBlank()) {
                        String posterUrl = "https://simkl.in/posters/" + posterId + "_m.jpg";
                        movie.setPosterUrl(posterUrl);
                    }

                    movie.setDirector(parseDirector(node));
                    movie.setActors(parseActors(node));
                    movie.setGenres(parseGenres(node));

                    movieRepository.save(movie);
                    saved++;
                    log.debug("Збережено фільм: title='{}', year='{}'", title, movie.getYear());
                }

                log.info("Збережено {} фільмів до БД", saved);
            } else {
                log.warn("Відповідь API не є масивом. Тип: {}, Вміст: {}", root.getNodeType(), root);
            }
        } finally {
            connection.disconnect();
        }
    }

    // ─── Fetch from HTML page using Jsoup ────────────────────────────────────────
    /**
     * @param htmlUrl      URL сторінки для парсингу
     * @param containerSel CSS-селектор контейнера фільму  (default: "div.movie")
     * @param titleSel     CSS-селектор назви             (default: "h2.title")
     * @param overviewSel  CSS-селектор опису             (default: "p.overview")
     * @param directorSel  CSS-селектор режисера          (default: "span.director")
     * @param posterSel    CSS-селектор постера (атрибут src) (default: "img.poster")
     * @param yearSel      CSS-селектор року              (default: "span.year")
     * @return кількість збережених фільмів
     */
    public int fetchMoviesFromHtml(String htmlUrl,
                                   String containerSel,
                                   String titleSel,
                                   String overviewSel,
                                   String directorSel,
                                   String posterSel,
                                   String yearSel) throws IOException {
        // fallback до дефолтних селекторів
        String cSel = blank(containerSel) ? "div.movie"      : containerSel;
        String tSel = blank(titleSel)     ? "h2.title"        : titleSel;
        String oSel = blank(overviewSel)  ? "p.overview"      : overviewSel;
        String dSel = blank(directorSel)  ? "span.director"   : directorSel;
        String pSel = blank(posterSel)    ? "img.poster"      : posterSel;
        String ySel = blank(yearSel)      ? "span.year"       : yearSel;

        log.info("HTML парсинг: url='{}' | container='{}' title='{}' overview='{}' director='{}' poster='{}' year='{}'",
                htmlUrl, cSel, tSel, oSel, dSel, pSel, ySel);

        try {
            var doc = Jsoup.connect(htmlUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .timeout(15000)
                    .get();

            var containers = doc.select(cSel);
            log.info("Знайдено {} контейнерів за селектором '{}'", containers.size(), cSel);

            final int[] count = {0};

            containers.forEach(element -> {
                String title = element.select(tSel).text();
                if (title.isBlank()) return; // пропускаємо без назви

                Movie movie = new Movie();
                movie.setTitle(title);
                movie.setOverview(element.select(oSel).text());
                movie.setYear(element.select(ySel).text());

                // Постер — src атрибут тега img
                var posterEl = element.select(pSel).first();
                if (posterEl != null) {
                    String src = posterEl.attr("abs:src");
                    if (src.isBlank()) src = posterEl.attr("src");
                    movie.setPosterUrl(src.isBlank() ? null : src);
                }

                String directorName = element.select(dSel).text();
                if (!directorName.isBlank()) {
                    Director director = directorRepository.findByName(directorName)
                            .orElseGet(() -> directorRepository.save(
                                    Director.builder().name(directorName).build()));
                    movie.setDirector(director);
                }

                movie.setActors(new HashSet<>());
                movie.setGenres(new HashSet<>());

                movieRepository.save(movie);
                count[0]++;
                log.debug("Збережено фільм з HTML: title='{}', year='{}', director='{}'",
                        title, movie.getYear(), directorName);
            });

            log.info("HTML парсинг завершено. Збережено {} фільмів", count[0]);
            return count[0];

        } catch (org.jsoup.HttpStatusException e) {
            log.error("HTTP помилка при парсингу HTML: status={}, url={}", e.getStatusCode(), htmlUrl);
            throw new IOException("Failed to fetch HTML. Status=" + e.getStatusCode() + ", URL=" + htmlUrl, e);
        }
    }

    private boolean blank(String s) { return s == null || s.isBlank(); }

    /**
     * Парсить HTML але НЕ зберігає — лише повертає список MovieDTO для попереднього перегляду.
     */
    public List<MovieDTO> previewMoviesFromHtml(String htmlUrl,
                                                String containerSel,
                                                String titleSel,
                                                String overviewSel,
                                                String directorSel,
                                                String posterSel,
                                                String yearSel) throws IOException {
        String cSel = blank(containerSel) ? "div.movie"    : containerSel;
        String tSel = blank(titleSel)     ? "h2.title"     : titleSel;
        String oSel = blank(overviewSel)  ? "p.overview"   : overviewSel;
        String dSel = blank(directorSel)  ? "span.director": directorSel;
        String pSel = blank(posterSel)    ? "img.poster"   : posterSel;
        String ySel = blank(yearSel)      ? "span.year"    : yearSel;

        log.info("HTML preview: url='{}' | container='{}'", htmlUrl, cSel);

        try {
            var doc = Jsoup.connect(htmlUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .timeout(15000)
                    .get();

            List<com.example.movies.dto.MovieDTO> result = new java.util.ArrayList<>();
            for (var el : doc.select(cSel)) {
                String title = el.select(tSel).text();
                if (title.isBlank()) continue;

                var posterEl = el.select(pSel).first();
                String posterUrl = null;
                if (posterEl != null) {
                    posterUrl = posterEl.attr("abs:src");
                    if (posterUrl.isBlank()) posterUrl = posterEl.attr("src");
                    if (posterUrl.isBlank()) posterUrl = null;
                }

                result.add(com.example.movies.dto.MovieDTO.builder()
                        .title(title)
                        .year(el.select(ySel).text())
                        .overview(el.select(oSel).text())
                        .director(el.select(dSel).text())
                        .posterUrl(posterUrl)
                        .build());
            }
            log.info("Preview знайдено {} фільмів", result.size());
            return result;
        } catch (org.jsoup.HttpStatusException e) {
            throw new IOException("Failed to fetch HTML. Status=" + e.getStatusCode(), e);
        }
    }

    // ─── Helpers ─────────────────────────────────────────────────────────────────

    private Director parseDirector(JsonNode node) {
        JsonNode dirNode = node.path("director");
        String name = null;
        if (dirNode.isObject()) {
            name = dirNode.path("name").asText(null);
        } else if (dirNode.isTextual()) {
            name = dirNode.asText(null);
        }
        if (name != null && !name.isBlank()) {
            final String directorName = name;
            return directorRepository.findByName(directorName)
                    .orElseGet(() -> directorRepository.save(
                            Director.builder().name(directorName).build()));
        }
        return null;
    }

    private HashSet<Actor> parseActors(JsonNode node) {
        HashSet<Actor> actorsSet = new HashSet<>();
        if (node.has("actors")) {
            for (JsonNode a : node.get("actors")) {
                String name = a.path("name").asText(null);
                if (name != null && !name.isBlank()) {
                    Actor actor = actorRepository.findByName(name)
                            .orElseGet(() -> actorRepository.save(
                                    Actor.builder().name(name).build()));
                    actorsSet.add(actor);
                }
            }
        }
        return actorsSet;
    }

    private HashSet<Genre> parseGenres(JsonNode node) {
        HashSet<Genre> genresSet = new HashSet<>();
        if (node.has("genres")) {
            for (JsonNode g : node.get("genres")) {
                String name = g.asText();
                if (name != null && !name.isBlank()) {
                    Genre genre = genreRepository.findByName(name)
                            .orElseGet(() -> genreRepository.save(
                                    Genre.builder().name(name).build()));
                    genresSet.add(genre);
                }
            }
        }
        return genresSet;
    }
}