package dev.ngb.util;

public final class BitmaskOperationUtils {

    private BitmaskOperationUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean isSet(int value, int flag) {
        return (value & flag) != 0;
    }

    public static int set(int value, int flag) {
        return value | flag;
    }

    public static int clear(int value, int flag) {
        return value & ~flag;
    }

    public static int toggle(int value, int flag) {
        return value ^ flag;
    }

    public static boolean hasAll(int value, int flags) {
        return (value & flags) == flags;
    }

    public static boolean hasAny(int value, int flags) {
        return (value & flags) != 0;
    }

    public static boolean isSet(long value, long flag) {
        return (value & flag) != 0L;
    }

    public static long set(long value, long flag) {
        return value | flag;
    }

    public static long clear(long value, long flag) {
        return value & ~flag;
    }

    public static long toggle(long value, long flag) {
        return value ^ flag;
    }

    public static boolean hasAll(long value, long flags) {
        return (value & flags) == flags;
    }

    public static boolean hasAny(long value, long flags) {
        return (value & flags) != 0L;
    }
}

