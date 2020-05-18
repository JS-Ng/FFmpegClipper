package ffmpeg.core;

public class JobResult {
    private Object result;
    public Object get() {
        return result;
    }
    public JobResult() {}
    public JobResult(Object item) {
        set(item);
    }
    public void set(Object result) {
        this.result = result;
    }
    public <T> T get(Class<T> classType) {
        return classType.cast(result);
    }
}
