import ffmpeg.core.argument.Arguments;
import ffmpeg.core.argument.Constants;
import ffmpeg.core.argument.FFmpegArgument;
import ffmpeg.util.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CoreTest {
    @Test
    public void testClipStatement() {
        String expected = "ffmpeg -ss 00:00:10 -i input.wmv -c copy -t 00:00:10 test.out";
        List<Arguments<?>> listing = new ArrayList<>();
        listing.add(new Arguments<>("ffmpeg"));
        listing.add(new Arguments<>("-ss").addParam("00:00:10"));
        listing.add(new Arguments<>("-i").addParam("input.wmv"));
        listing.add(new Arguments<>("-c copy"));
        listing.add(new Arguments<>("-t").addParam("00:00:10"));
        listing.add(new Arguments<>("").addParam("test.out"));

        String result = ArrayUtils.toCommandLineArgs(listing);

        Assert.assertEquals(expected, result);
    }

    @Test
    public void testConstants() {
        String expected = "ffmpeg -ss 00:00:10 -i input.wmv -c copy -t 00:00:10 test.out";
        List<Arguments<?>> listing = new ArrayList<>();
        Constants.FFMPEG.getArg().addParam("disruptor");
        listing.add(Constants.FFMPEG.getArg());
        listing.add(Constants.CLIP_START.getArg().addParam("00:00:10"));
        listing.add(Constants.INPUT_FILE.getArg().addParam("input.wmv"));
        listing.add(Constants.CLIP_COPY.getArg());
        listing.add(Constants.CLIP_END.getArg().addParam("00:00:10"));
        listing.add(Constants.PURE_ARGS.getArg().addParam("test.out"));
        String result = ArrayUtils.toCommandLineArgs(listing);
        Assert.assertEquals(expected, result);

    }

    @Test
    public void testFFArguments() {
        String expected = "ffmpeg -i input.wmv -f ismv -c copy -t 00:00:10 pipe:1";
        String result = new FFmpegArgument().
                            src("input.wmv").
                            setEncoding("ismv").
                            enableCopy().
                            setEndTime("00:00:10").
                            toStdin().
                            build();
        Assert.assertEquals(expected, result);
    }
}
