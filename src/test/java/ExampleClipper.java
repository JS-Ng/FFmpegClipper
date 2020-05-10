import ffmpeg.Clipper;
import ffmpeg.core.annotations.Main;
import ffmpeg.impl.DefaultFFmpeg;

import java.io.InputStream;

public class ExampleClipper extends Clipper <InputStream>{
    private InputStream stream;
    @Override
    public InputStream getResult() {
        return stream;
    }

    @Main
    public void haha(DefaultFFmpeg ffmpeg) {
        stream = (InputStream) ffmpeg.enableCopy().startTime(0).runJob().getJobResult().get();
    }
}
