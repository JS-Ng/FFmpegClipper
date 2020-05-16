package ffmpeg.core.argument;

import ffmpeg.util.ArrayUtils;

import java.util.LinkedList;
import java.util.List;
/**
 * Returns the command line argument for video clip
 * */
public final class CommonFFmpegArgument {
    private final List<Arguments<?>> arguments;
    public CommonFFmpegArgument() {
        this.arguments = new LinkedList<>();
        arguments.add(Constants.FFMPEG.getArg());
    }

    public CommonFFmpegArgument enableCopy() {
        arguments.add(Constants.CLIP_COPY.getArg());
        return this;
    }

    public CommonFFmpegArgument setStartTime(String startFormat) {
        arguments.add(Constants.CLIP_START.getArg().addParam(startFormat));
        return this;
    }

    public CommonFFmpegArgument setEndTime(String endFormat) {
        arguments.add(Constants.CLIP_END.getArg().addParam(endFormat));
        return this;
    }

    public CommonFFmpegArgument src(String fileName) {
        arguments.add(Constants.INPUT_FILE.getArg().addParam(fileName));
        return this;
    }
    public CommonFFmpegArgument setEncoding(String encoding) {
        arguments.add(Constants.CLIP_ENCODING.getArg().addParam(encoding));
        return this;
    }
    /**
     * Destination file
     * */
    public CommonFFmpegArgument dest(String fileName) {
        arguments.add(Constants.PURE_ARGS.getArg().addParam(fileName));
        return this;
    }
    public CommonFFmpegArgument toStdin() {
        arguments.add(Constants.PIPE_STDIN.getArg());
        return this;
    }
    public String build() {
        return ArrayUtils.toCommandLineArgs(this.arguments);
    }
}
