package main.java.db;

public class DBConfig {
    private static String DB_NAME = "c_store_db";
    private static String USERNAME = "";
    private static String PASSWORD = "";

    private static String getDBName() {
        return DB_NAME;
    }

    private static String getDBUsername() {
        return USERNAME;
    }

    private static String getDBPassword() {
        return PASSWORD;
    }
}
