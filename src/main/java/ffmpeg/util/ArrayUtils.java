package ffmpeg.util;

import ffmpeg.core.argument.Arguments;

import java.util.Arrays;
import java.util.List;

public class ArrayUtils {
    public static <T> T[] concat(T[] a1, T[] a2) {
        T[] extended = Arrays.copyOf(a1, a1.length + a2.length);
        System.arraycopy(a2, 0, extended, a1.length, a2.length);
        return extended;
    }

    public static String toCommandLineArgs(List<Arguments<?>> argList) {
        return String.join(" ", argList.stream().
                map(e -> e.toString().
                        trim()).
                toArray(String[]::new));
    }

    public static String convertSecToTimeFormat(long time) {
        return "00:00:00";
    }
}
