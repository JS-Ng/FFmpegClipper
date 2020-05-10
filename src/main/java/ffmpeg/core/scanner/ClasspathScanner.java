package ffmpeg.core.scanner;

import ffmpeg.core.annotations.Clippable;
import org.reflections.Reflections;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
/**
 * Scan classes in a parallel way
 * */
public enum ClasspathScanner {
    INSTANCE;
    Set<Class<?>> classSet;
    
    private ClasspathScanner() {
        this.classSet = new HashSet<>();
    }

    public void init() {
        this.classSet.clear();
    }
    public ClasspathScanner scanClippable(String packageName) {
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
