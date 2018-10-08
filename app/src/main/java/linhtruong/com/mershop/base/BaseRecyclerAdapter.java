package linhtruong.com.mershop.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Base recycler view adapter, support multi category defined with type
 *
 * @author linhtruong
 * @date 10/1/18 - 15:46.
 * @organization VED
 */
@SuppressWarnings("unchecked")
public abstract class BaseRecyclerAdapter<T, H extends BaseRecyclerAdapter.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<T> mData = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return createHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        T data = mData.get(position);
        ((ViewHolder) holder).bindData(data);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<? extends T> data) {
        mData.clear();
        if (data != null) {
            mData.addAll(data);
        }

        notifyDataSetChanged();
    }

    public abstract H createHolder(ViewGroup parent, int viewType);

    public static abstract class ViewHolder<T> extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void bindData(T data);
    }
}
