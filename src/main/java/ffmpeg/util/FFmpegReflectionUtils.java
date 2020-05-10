package ffmpeg.util;

public class FFmpegReflectionUtils {
    public static String getCurrentPackage() {
        String callerKlass = new Throwable().getStackTrace()[1].getClassName();
        try {
            Class<?> curKlass = Class.forName(callerKlass);
            return curKlass.getPackageName();
        }

        catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't launch the ffmpeg clipper because: " + e);
        }
    }
}
