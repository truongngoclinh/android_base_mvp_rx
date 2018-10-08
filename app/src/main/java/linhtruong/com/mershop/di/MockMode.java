package linhtruong.com.mershop.di;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Mock mode for fake rest data
 *
 * @author linhtruong
 * @date 10/3/18 - 14:54.
 * @organization VED
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface MockMode {
    String value() default ""; // true / false
}
