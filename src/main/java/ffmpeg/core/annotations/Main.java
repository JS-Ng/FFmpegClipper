package ffmpeg.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This decides the exact functionality of this framework
 * The framework consists of 3 parts: probe class, ffmpeg class, and this main, this is where the user defines
 * its behavior with the custom ffmpeg class and probe class, sequence: probe/ffmpeg -> main -> inputstream
 * */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Main {
}
