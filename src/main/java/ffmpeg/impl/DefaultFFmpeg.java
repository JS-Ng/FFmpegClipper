package ffmpeg.impl;

import ffmpeg.core.FFmpeg;
import ffmpeg.core.argument.FFmpegArgument;
import ffmpeg.core.annotations.Clippable;
import ffmpeg.job.CommandLineJob;
import ffmpeg.job.JobPool;
import ffmpeg.job.JobResult;
import ffmpeg.probe.AbstractProbe;
import ffmpeg.util.ArrayUtils;

@Clippable(classType = DefaultFFmpeg.class)
public class DefaultFFmpeg extends FFmpeg {
    private static String defaultFormat = "ismv";
    private AbstractProbe probe;
    private JobResult result;
    FFmpegArgument arg = new FFmpegArgument();

    @Override
    public DefaultFFmpeg src(String fileName) {
        arg.src(fileName);
        return this;
    }
    @Override
    public DefaultFFmpeg probe(AbstractProbe probe) {
        this.probe = probe;
        return this;
    }

    @Override
    public DefaultFFmpeg runJob() {
        this.result = new JobResult();
        result.set(new CommandLineJob(this.arg.setEncoding(defaultFormat).toStdin().build()));
        return this;
    }
    // TODO: implement this with a job pool if wanted
    @Override
    public DefaultFFmpeg runJob(JobPool pool) {
        return this;
    }

    @Override
    public JobResult getJobResult() {
        return this.result;
    }

    public DefaultFFmpeg enableCopy() {
        arg.enableCopy();
        return this;
    }

    public DefaultFFmpeg endTime(long sec) {
        arg.setEndTime(ArrayUtils.convertSecToTimeFormat(sec));
        return this;
    }

    public DefaultFFmpeg startTime(long sec) {
        arg.setStartTime(ArrayUtils.convertSecToTimeFormat(sec));
        return this;
    }
}
