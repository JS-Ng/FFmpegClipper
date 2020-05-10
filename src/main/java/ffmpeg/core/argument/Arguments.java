package ffmpeg.core.argument;

import java.util.LinkedList;
import java.util.List;
/**
 * Parse the command line arguments
 * */
public class Arguments <T> {
    List<T> paramList;
    private String arg;
    public Arguments(String arg) {
        this.arg = arg;
        this.paramList = new LinkedList<>();
    }

    public Arguments(Arguments<T> arg) {
        this(arg.arg);
        for (T e : arg.paramList) {
            this.addParam(e);
        }
    }

    public Arguments<T> addParam(T param) {
        this.paramList.add(param);
        return this;
    }

    @Override
    public String toString() {

        if (paramList.size() == 0) return this.arg.trim();

        return this.arg.trim() + " " + String.join(" ", paramList.stream().
                                                    map(e -> e.toString().
                                                            trim()).
                                                    toArray(String[]::new));
    }
}
