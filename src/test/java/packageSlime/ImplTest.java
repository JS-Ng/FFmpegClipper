package packageSlime;

import ffmpeg.util.FFmpegReflectionUtils;
import org.junit.Assert;
import org.junit.Test;
import packageSlime.ClipperExample;

public class ImplTest {
    @Test
    public void testDefault() {
        String result = FFmpegReflectionUtils.getCurrentPackage();
        String expected = "packageSlime";

        Assert.assertEquals(expected, result);
    }
}
