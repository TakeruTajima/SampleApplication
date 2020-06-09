package com.mr2.sample_application.ui.sample_data_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mr2.sample_app_infra.room_database.sample_list_data.SampleListData;
import com.mr2.sample_application.R;

public class SampleDataListAdapter extends PagedListAdapter<SampleListData, SampleDataListAdapter.ViewHolder> {
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_data_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SampleListData item = getItem(position);
        holder.bindTo(item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textHead;
        private TextView textBody;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textHead = itemView.findViewById(R.id.sampleListRowHeader);
            this.textBody = itemView.findViewById(R.id.sampleListRowBody);
        }
        void bindTo(SampleListData item){
            if (null == item) return;
            textHead.setText(item.id());
            textBody.setText(item.name());
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
