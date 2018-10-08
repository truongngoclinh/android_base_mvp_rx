package linhtruong.com.commons.widgets;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * App recycler decoration
 *
 * @author linhtruong
 * @date 10/8/18 - 00:22.
 * @organization VED
 */
public class MSItemOffSetDecoration extends RecyclerView.ItemDecoration {

    private int mItemOffset;

    public MSItemOffSetDecoration(int itemOffset) {
        mItemOffset = itemOffset;
    }

    public MSItemOffSetDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
        this(context.getResources().getDimensionPixelSize(itemOffsetId));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
    }
}
