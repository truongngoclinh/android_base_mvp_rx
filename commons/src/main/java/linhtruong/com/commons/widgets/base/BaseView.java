package linhtruong.com.commons.widgets.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Base view
 *
 * @author linhtruong
 * @date 10/2/18 - 11:42.
 * @organization VED
 */
public abstract class BaseView extends FrameLayout implements IScreenView {
    public BaseView(Context context) {
        super(context);
    }

    public BaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
