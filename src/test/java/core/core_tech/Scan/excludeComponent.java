package core.core_tech.Scan;

import java.lang.annotation.*;

@Target(ElementType.TYPE) // class level
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface excludeComponent {
}
