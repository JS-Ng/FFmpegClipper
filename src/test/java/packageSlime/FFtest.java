package packageSlime;

import ffmpeg.core.FFmpeg;
import ffmpeg.core.annotations.Clippable;
import ffmpeg.job.JobPool;
import ffmpeg.job.JobResult;
import ffmpeg.probe.AbstractProbe;

@Clippable(classType = FFtest.class)
public class FFtest extends FFmpeg {

    @Override
    public FFmpeg src(String fileName) {
        return null;
    }

    @Override
    public FFmpeg probe(AbstractProbe probe) {
        return null;
    }

    @Override
    public FFmpeg runJob() {
        return null;
    }

    @Override
    public FFmpeg runJob(JobPool pool) {
        return null;
    }

    @Override
    public JobResult getJobResult() {
        return null;
    }
}
