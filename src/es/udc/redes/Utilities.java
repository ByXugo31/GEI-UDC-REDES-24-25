package es.udc.redes;

import java.util.Arrays;

public class Utilities {
    public static boolean verifyArgs(String[] args, int numArgs) {
        return args.length != numArgs;
    }

    public static <T> String printArray(T[] array) {
        return String.join(" ", Arrays.stream(array)
                .map(String::valueOf)
                .toArray(String[]::new));
    }


}
