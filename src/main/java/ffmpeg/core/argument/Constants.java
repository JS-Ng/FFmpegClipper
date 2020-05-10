package ffmpeg.core.argument;

import ffmpeg.core.argument.Arguments;

public enum Constants {
    FFMPEG(new Arguments<String>("ffmpeg")),
    CLIP_START(new Arguments<String>("-ss")),
    CLIP_END(new Arguments<String>("-t")),
    INPUT_FILE(new Arguments<String>("-i")),
    CLIP_COPY(new Arguments<String>("-c copy")),
    PURE_ARGS(new Arguments<>("")),
    PIPE_STDIN(new Arguments<>("pipe:1")),
    CLIP_ENCODING(new Arguments<>("-f")); // this is pure object for only adding the arguments
    Constants(Arguments arg) {
        this.arg = arg;
    }

    private Arguments arg;
    public Arguments getArg() {
        // extremely thread-safe
        return new Arguments<>(this.arg);
    }


}
