package main.java.utils;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {

    public static String setLength(String str, int length) {
        if (str != null) {
            return str.length() > length ? str.substring(0, length).trim() : str.trim();
        }
        return null;
    }

    public static String trimSafely(String input) {
        if (input != null) {
            input = input.trim();
        }

        return input;
    }

    public static String trimSafely(String input, int maxLength) {
        if (input != null) {
            input = input.trim();
        }

        return setLength(input, maxLength);
    }

    public static String checkNullString(String input) {
        return input == null ? "" : input.trim();
    }

    public static String replaceStringDoubleQuotesWithSingle(String str) {
        return str.replaceAll("\"", "'");
    }

    public static String toUpperCase(String value) {
        return value == null ? null : value.trim().toUpperCase();
    }

    public static String toLowerCase(String value) {
        return value == null ? null : value.trim().toLowerCase();
    }

    public static List<String> getListFromCommaSeparatedString(String str) {
        List<String> result = new ArrayList<>();
        String text = str;
        while (true) {
            if (!text.contains(",")) {
                result.add(text);
                break;
            }
            String currency = text.substring(0, text.indexOf(","));
            text = text.substring(text.indexOf(",") + 1);
            result.add(currency);
        }
        return result;
    }
}
