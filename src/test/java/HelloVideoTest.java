import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class HelloVideoTest {
    private static Logger logger = LogManager.getLogger(HelloVideoTest.class);

    static {
        logger.info("The root dir is: " + System.getProperty("user.dir"));
    }
    
    @Test
    public void testHello() {
        Assert.assertEquals(1, 1);
    }
}
