import ffmpeg.fix.HelloWorld;
import ffmpeg.job.CommandLineJob;
import ffmpeg.job.Job;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.logging.Logger;

public class JobTest {
    static Logger logger = Logger.getLogger("test");

    @Test
    public void testHelloWorld() throws IOException {
        logger.info("current dir: " + System.getProperty("user.dir"));
        String command = String.format("cat %s", HelloWorld.HELLO_WORLD.toString());
        logger.info("command: " + command);
        File file = new File(HelloWorld.HELLO_WORLD.toString());
        Job<InputStream> curJob = new CommandLineJob(command);
        byte[] buffer = new byte[100];
        byte[] bufferExpected = new byte[100];
        curJob.runJob().getResult().read(buffer, 0, (int) file.length());
        new FileInputStream(file).read(bufferExpected, 0, (int) file.length());
        String result = new String(buffer, "UTF-8");
        String expected = new String(bufferExpected, "UTF-8");
        Assert.assertEquals(expected, result);
    }
}
