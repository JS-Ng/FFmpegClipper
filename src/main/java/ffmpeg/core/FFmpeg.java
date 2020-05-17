package ffmpeg.core;

import ffmpeg.job.JobPool;
import ffmpeg.probe.AbstractProbe;

/**
 * Core class. the java interface of ffmpeg
 * */
public abstract class FFmpeg {

    public abstract FFmpeg src(String fileName);

    public abstract FFmpeg probe(AbstractProbe probe);

    public abstract FFmpeg runJob();

    public abstract FFmpeg runJob(JobPool pool);

    public abstract JobResult getJobResult();

}
