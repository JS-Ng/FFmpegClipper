package ffmpeg.core.scanner.errors;

public class DuplicateDependencyException extends RuntimeException{
    public DuplicateDependencyException(String errorMsg) {
        super(errorMsg);
    }
}
