package ffmpeg.job;

import java.io.IOException;
import java.io.InputStream;
/**
 * Job classes are created for retrieving the information from the command line
 *
 * */
public abstract class Job <T> {
    public abstract Job<T> runJob () throws IOException;
    public abstract T getResult();
    public abstract T getError();
}
