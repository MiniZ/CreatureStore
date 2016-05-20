package main.java.db;

public class DBConfig {
    private static String DB_NAME = "c_store_db";
    private static String USERNAME = "";
    private static String PASSWORD = "";

    public static String getDBName() {
        return DB_NAME;
    }

    public static String getDBUsername() {
        return USERNAME;
    }

    public static String getDBPassword() {
        return PASSWORD;
    }
}
