package com.mr2.sample_application.ui.sample_data_list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class SamplePagedListAdapter<T> extends PagedListAdapter<T, SamplePagedListAdapter.BindingHolder> {
    private final int LAYOUT_ID;
    private final int VARIABLE_ID;
    /* Example */
//    private static DiffUtil.ItemCallback<T> DIFF_CALLBACK =
//            new DiffUtil.ItemCallback<T>() {
//                @Override // 同一:　同じ参照先/オブジェクトか
//                public boolean areItemsTheSame(@NonNull T oldItem, @NonNull T newItem) {
//                    return (oldItem == newItem || oldItem.id == newItem.id);
//                }
//                @Override // 同値:　参照先は不問として等価か
//                public boolean areContentsTheSame(@NonNull T oldItem, @T Object newItem) {
//                    return oldItem.equals(newItem);
//                }
//            };

    SamplePagedListAdapter(DiffUtil.ItemCallback<T> callback, int layoutId, int variableId) {
        super(callback);
        LAYOUT_ID = layoutId;
        VARIABLE_ID = variableId;
    }

    @NonNull
    @Override
    public BindingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding b = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                LAYOUT_ID,
                parent,
                false
        );
//        binding.setLifecycleOwner(getViewLifecycleOwner()); // いるの？
        return new BindingHolder(b);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingHolder holder, int position) {
        final T item = getItem(position);
        holder.binding.setVariable(VARIABLE_ID, item);
    }

    static class BindingHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        BindingHolder(ViewDataBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    /**
     * "総称型の未検査キャスト警告は避けられない"
     */
    @SuppressWarnings("unchecked")
    static <T> T avoidingUntestedCasts(Object o){
        return (T)o;
    }
}
