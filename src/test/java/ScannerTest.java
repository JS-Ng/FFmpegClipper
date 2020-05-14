import ffmpeg.core.scanner.ClassPathScanner;
import org.junit.Assert;
import org.junit.Test;
import packageSlime.FFtest;

public class ScannerTest {
    @Test
    public void testScanner() {
        ClassPathScanner scanner = ClassPathScanner.INSTANCE;
        scanner.init();
        scanner.scanClippable("packageSlime");
        Assert.assertEquals(1, scanner.size());
    }

    @Test
    public void testFilter() {
        ClassPathScanner scanner = ClassPathScanner.INSTANCE;
        scanner.init();
        scanner.scanClippable("packageSlime");
        Assert.assertEquals(FFtest.class, scanner.filter(e->e.equals(FFtest.class)).get(0));
    }
}
