package main.java.db;

public class DBConfig {
    private static String DB_NAME = "cstore_db";
    private static String USERNAME = "root";
    private static String PASSWORD = "your_mysql_password";

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
