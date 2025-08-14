package com.example.lms;

import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
    private static Properties props = new Properties();

    static {
        try (InputStream input = AppConfig.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("Config file not found");
            }
            props.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Error loading config", e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}
