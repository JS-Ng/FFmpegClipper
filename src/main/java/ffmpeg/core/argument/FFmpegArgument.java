package ffmpeg.core.argument;

import ffmpeg.util.ArrayUtils;

import java.util.LinkedList;
import java.util.List;
/**
 * Returns the command line argument for video clip
 * */
public final class FFmpegArgument {
    private final List<Arguments<?>> arguments;
    public FFmpegArgument() {
        this.arguments = new LinkedList<>();
        arguments.add(Constants.FFMPEG.getArg());
    }

    public FFmpegArgument enableCopy() {
        arguments.add(Constants.CLIP_COPY.getArg());
        return this;
    }

    public FFmpegArgument setStartTime(String startFormat) {
        arguments.add(Constants.CLIP_START.getArg().addParam(startFormat));
        return this;
    }

    public FFmpegArgument setEndTime(String endFormat) {
        arguments.add(Constants.CLIP_END.getArg().addParam(endFormat));
        return this;
    }

    public FFmpegArgument src(String fileName) {
        arguments.add(Constants.INPUT_FILE.getArg().addParam(fileName));
        return this;
    }
    public FFmpegArgument setEncoding(String encoding) {
        arguments.add(Constants.CLIP_ENCODING.getArg().addParam(encoding));
        return this;
    }
    /**
     * Destination file
     * */
    public FFmpegArgument dest(String fileName) {
        arguments.add(Constants.PURE_ARGS.getArg().addParam(fileName));
        return this;
    }
    public FFmpegArgument toStdin() {
        arguments.add(Constants.PIPE_STDIN.getArg());
        return this;
    }
    public String build() {
        return ArrayUtils.toCommandLineArgs(this.arguments);
    }
}
