/**
 * Annotations are functionalities for users to build their custom pipeline of ffmpeg, instead of directly use
 * our default provided pipeline of video clipping
 * */

package ffmpeg.core.annotations;
// TODO: enable the clippable ffmpeg injection, and extensible component development
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Clippable {
    boolean joint() default false;
    Class<?> classType();
}
