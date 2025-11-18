package edu.ntu.omelianov.LW_4.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.ntu.omelianov.LW_4.model.University;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JSONController {

    private final Gson gson;

    public JSONController() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    public String toJson(University university) {
        return gson.toJson(university);
    }

    public void writeToFile(University university, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(university, writer);
        }
    }

    public University readFromFile(String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, University.class);
        }
    }

    public University fromJson(String json) {
        return gson.fromJson(json, University.class);
    }
}
