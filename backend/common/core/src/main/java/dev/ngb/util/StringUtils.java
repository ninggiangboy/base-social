package dev.ngb.util;

import java.util.UUID;

public class StringUtils {

    private StringUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean equals(String s1, String s2) {
        if (s1 == null && s2 == null) return true;
        if (s1 == null || s2 == null) return false;
        return s1.equals(s2);
    }

    public static String generateUniqueStr() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
