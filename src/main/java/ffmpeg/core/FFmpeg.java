package ffmpeg.core;

import ffmpeg.impl.DefaultFFmpeg;
import ffmpeg.job.CommandLineJob;
import ffmpeg.job.Job;
import ffmpeg.job.JobPool;
import ffmpeg.job.JobResult;
import ffmpeg.probe.AbstractProbe;
import ffmpeg.probe.FFmpegProbe;
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
