package com.greenmiststudios.pitch;

import java.util.Arrays;

/**
 * Created by geoffpowell on 10/28/15.
 */
public class Utils {

    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}
