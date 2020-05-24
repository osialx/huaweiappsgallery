package com.github.osialx;

public class ArrayUtils {

    public static <T> T getLast(T[] array) {
        if (isEmpty(array)) {
            return null;
        } else {
            return array[array.length - 1];
        }
    }

    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }
}
