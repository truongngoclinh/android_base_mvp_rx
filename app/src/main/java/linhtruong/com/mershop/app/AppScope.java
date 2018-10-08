package linhtruong.com.mershop.app;

import javax.inject.Scope;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * App singleton scope
 *
 * @author linhtruong
 * @date 10/1/18 - 14:33.
 * @organization VED
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface AppScope {
}
