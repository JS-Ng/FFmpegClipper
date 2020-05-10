import ffmpeg.util.ArrayUtils;
import ffmpeg.util.FFmpegReflectionUtils;
import org.junit.Assert;
import org.junit.Test;

public class UtilTest {
    @Test
    public void testConcat() {
        String[] a1 = {"1", "2"};
        String[] a2 = {"1", "3"};

        String[] r = ArrayUtils.concat(a1, a2);
        Assert.assertArrayEquals(r, new String[]{"1", "2", "1", "3"});
    }
    @Test
    public void testReflection() {
        String expected = "";
        String result = FFmpegReflectionUtils.getCurrentPackage();
        Assert.assertEquals(expected, result);
    }
}
