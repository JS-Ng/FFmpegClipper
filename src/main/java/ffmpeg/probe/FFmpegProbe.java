package ffmpeg.probe;

import ffmpeg.core.annotations.Probable;

/**
 * This is the default probe
 * */
@Probable
public class FFmpegProbe extends AbstractProbe {

    @Override
    public double getDuration() {
        return 0;
    }
}
