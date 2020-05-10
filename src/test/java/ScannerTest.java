import ffmpeg.core.scanner.ClasspathScanner;
import org.junit.Assert;
import org.junit.Test;
import packageSlime.FFtest;

public class ScannerTest {
    @Test
    public void testScanner() {
        ClasspathScanner scanner = ClasspathScanner.INSTANCE;
        scanner.init();
        scanner.scanClippable("packageSlime");
        Assert.assertEquals(1, scanner.size());
    }

    @Test
    public void testFilter() {
        ClasspathScanner scanner = ClasspathScanner.INSTANCE;
        scanner.init();
        scanner.scanClippable("packageSlime");
        Assert.assertEquals(FFtest.class, scanner.filter(e->e.equals(FFtest.class)).get(0));
    }
}
