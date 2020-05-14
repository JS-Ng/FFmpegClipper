package ffmpeg.core.scanner;

import ffmpeg.core.annotations.Clippable;
import org.reflections.Reflections;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
/**
 * Scan classes in a parallel way
 * */
public enum ClassPathScanner {
    INSTANCE;
    /**
     * maintain the method dependency
     * */

    Set<Class<?>> classSet;
    private ClassPathScanner() {
        this.classSet = new HashSet<>();
    }

    public void init() {
        this.classSet.clear();
    }
    public ClassPathScanner scanClippable(String packageName) {
        Reflections ref = new Reflections(packageName);
        classSet.addAll(ref.getTypesAnnotatedWith(Clippable.class));
        return this;
    }

    public int size() {
        return this.classSet.size();
    }

    public List<Class<?>> filter(Function<Class<?>, Boolean> checker) {
        return classSet.parallelStream().filter(checker::apply).collect(Collectors.toList());
    }
}
