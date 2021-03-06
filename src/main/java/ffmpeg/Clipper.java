package ffmpeg;

import ffmpeg.core.scanner.ClassPathScanner;
import ffmpeg.core.scanner.FunctionPool;
import ffmpeg.util.FFmpegReflectionUtils;

import java.util.logging.Logger;

/**
 * Utility class used to clip the video
 * Entry class for the whole ffmpeg tool
 * example: someclass extends Clipper
 * @Main, @Main, @Main
 * start() -> getResult()
 * */
public abstract class Clipper <T>{
    static Logger logger = Logger.getLogger("cur");
    private static String FFMPEG_PACKAGE = "ffmpeg";
    protected static ClassPathScanner scanner = ClassPathScanner.INSTANCE; // ffmpeg resource
    FunctionPool pool = new FunctionPool(this); // each clipper maintains a new instance of execution
    // initialize the whole clipper, load the whole classes to memory for later reference
    // TODO: get initialize process
    public static void init() {
        scanner.init();
    }
    public static void initClipper() {
        init();
        String currentPackage = FFmpegReflectionUtils.getCurrentPackage();
        loadFFmpegPackage(currentPackage);
    }

    private static void loadFFmpegPackage() {
        loadFFmpegPackage(FFMPEG_PACKAGE);
    }

    private static void loadFFmpegPackage(String packageName) {
        loadFFmpegPackage(); // load default package
    }
    /**
     * get Result after we click start()
     * */
    public abstract T getResult();
    /**
     * Entry function, this basically performs behavior defined in @main annotated function
     * */
    public Clipper<T> start() {
        Class<?> curChildKlass = this.getClass();// this will retrieve the child process name
        return this;
    }
}
