package ffmpeg.fix;
/**
 * fix package contains sample data for this video clipper
 * */
public enum HelloWorld {

    HELLO_WORLD("testFile/HelloWorld.txt");

    private HelloWorld(String path) {
        this.ctx = path;
    }
    private String ctx;

    @Override
    public String toString() {
        return this.ctx;
    }
}
