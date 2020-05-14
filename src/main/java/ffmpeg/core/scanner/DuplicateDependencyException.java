package ffmpeg.core.scanner;

public class DuplicateDependencyException extends RuntimeException{
    public DuplicateDependencyException(String errorMsg) {
        super(errorMsg);
    }
}
