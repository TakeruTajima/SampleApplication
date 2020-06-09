package com.mr2.sample_application.ui.sample_data_list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mr2.sample_app_infra.room_database.sample_list_data.SampleListData;
import com.mr2.sample_application.databinding.SampleDataListRowBinding;

public class SampleDataListAdapter extends PagedListAdapter<SampleListData, SampleDataListAdapter.BindingHolder> {
    private static DiffUtil.ItemCallback<SampleListData> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<SampleListData>() {
                @Override
                public boolean areItemsTheSame(@NonNull SampleListData oldItem, @NonNull SampleListData newItem) {
                    return oldItem.id == newItem.id;
                }

                @Override
                public boolean areContentsTheSame(@NonNull SampleListData oldItem, @NonNull SampleListData newItem) {
                    return oldItem.equals(newItem);
                }
            };

    private SampleDataListAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public BindingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SampleDataListRowBinding binding =
                SampleDataListRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingHolder holder, int position) {
        SampleListData item = getItem(position);
//        holder.binding.
        holder.binding.setItem(item);
    }

    static class BindingHolder extends RecyclerView.ViewHolder {
        private final com.mr2.sample_application.databinding.SampleDataListRowBinding binding;

        BindingHolder(SampleDataListRowBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    /**
     * カスタムセッター
     *
     * @param rv Tagを設定したViewのインスタンス
     * @param list Tagに放り込まれた設定値
     */
    @BindingAdapter({"pagedList"})
    public static void setPagedList(RecyclerView rv, PagedList<SampleListData> list){
        SampleDataListAdapter adapter = (SampleDataListAdapter) rv.getAdapter();
        if (null == adapter){
            rv.setLayoutManager(new LinearLayoutManager(rv.getContext(), LinearLayoutManager.VERTICAL, false));
            rv.setHasFixedSize(true);
            adapter = new SampleDataListAdapter();
            rv.setAdapter(adapter);
            rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));
        }
        adapter.submitList(list);
    }
}
