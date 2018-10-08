package linhtruong.com.mershop.di;

import javax.inject.Scope;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Scope for home flow, features, activity
 *
 * @author linhtruong
 * @date 10/3/18 - 13:42.
 * @organization VED
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface MSHomeScope {
}
