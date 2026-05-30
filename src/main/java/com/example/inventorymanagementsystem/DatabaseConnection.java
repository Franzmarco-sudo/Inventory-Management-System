package com.example.inventorymanagementsystem;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

public class DatabaseConnection {

    private static final Map<String, String> env = new HashMap<>();

    static {
        loadEnv();
    }

    private static void loadEnv() {
        String[] paths = { ".env", "../.env", "../../.env" };
        for (String path : paths) {
            try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("#")) continue;
                    int idx = line.indexOf('=');
                    if (idx != -1) {
                        env.put(line.substring(0, idx).trim(), line.substring(idx + 1).trim());
                    }
                }
                return;
            } catch (Exception ignored) {}
        }
        System.err.println("WARNING: .env file not found. Copy .env.example to .env and fill in your credentials.");
    }

    public static Connection connect() throws Exception {
        String url      = env.getOrDefault("DB_URL",      System.getenv("DB_URL"));
        String user     = env.getOrDefault("DB_USER",     System.getenv("DB_USER"));
        String password = env.getOrDefault("DB_PASSWORD", System.getenv("DB_PASSWORD"));

        if (url == null || user == null || password == null) {
            throw new IllegalStateException(
                    "Database credentials not found. Please create a .env file based on .env.example."
            );
        }

        return DriverManager.getConnection(url, user, password);
    }
}