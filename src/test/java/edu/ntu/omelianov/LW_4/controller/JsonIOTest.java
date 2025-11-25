package edu.ntu.omelianov.LW_4.controller;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonIOTest {

    @Test
    public void testWriteReadJsonProducesEqualUniversity() throws Exception {
        Class<edu.ntu.omelianov.LW_4.model.University> cls = edu.ntu.omelianov.LW_4.model.University.class;
        edu.ntu.omelianov.LW_4.model.University oldUniversity = cls.getDeclaredConstructor().newInstance();
        initializeSampleObject(oldUniversity, 2);

        Path tmp = Files.createTempFile("university_test", ".json");
        JsonIO.writeJsonToFile(oldUniversity, tmp.toString());

        edu.ntu.omelianov.LW_4.model.University newUniversity = JsonIO.readJsonFromFile(tmp.toString());

        assertEquals(oldUniversity, newUniversity);

        Files.deleteIfExists(tmp);
    }

    private void initializeSampleObject(Object obj, int depth) {
        if (obj == null || depth < 0) return;
        Class<?> cls = obj.getClass();
        try {
            for (Class<?> c = cls; c != null; c = c.getSuperclass()) {
                Field[] fields = c.getDeclaredFields();
                for (Field f : fields) {
                    if (Modifier.isStatic(f.getModifiers())) continue;
                    f.setAccessible(true);
                    Class<?> t = f.getType();
                    try {
                        if (t == String.class) {
                            f.set(obj, "test");
                        } else if (t == int.class || t == Integer.class) {
                            f.set(obj, 1);
                        } else if (t == long.class || t == Long.class) {
                            f.set(obj, 1L);
                        } else if (t == double.class || t == Double.class) {
                            f.set(obj, 1.0);
                        } else if (t == boolean.class || t == Boolean.class) {
                            f.set(obj, true);
                        } else if (t.isEnum()) {
                            Object[] consts = t.getEnumConstants();
                            if (consts != null && consts.length > 0) f.set(obj, consts[0]);
                        } else if (java.util.List.class.isAssignableFrom(t)) {
                            f.set(obj, new ArrayList<>());
                        } else if (java.util.Set.class.isAssignableFrom(t)) {
                            f.set(obj, new HashSet<>());
                        } else if (java.util.Map.class.isAssignableFrom(t)) {
                            f.set(obj, new HashMap<>());
                        } else if (!t.isPrimitive() && !t.getName().startsWith("java.")) {
                            try {
                                Constructor<?> ctor = t.getDeclaredConstructor();
                                ctor.setAccessible(true);
                                Object nested = ctor.newInstance();
                                f.set(obj, nested);
                                initializeSampleObject(nested, depth - 1);
                            } catch (NoSuchMethodException ns) {
                                // can't instantiate, leave null
                            }
                        }
                    } catch (IllegalAccessException ignored) {
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
