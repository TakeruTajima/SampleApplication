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

    protected SampleDataListAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_data_list_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        System.out.println("onBindViewHolder is called. position:" + position);
        SampleListData item = getItem(position);
        holder.bindTo(item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private View view;
        private TextView textHead;
        private TextView textBody;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            this.textHead = view.findViewById(R.id.sampleListRowHead);
            this.textBody = view.findViewById(R.id.sampleListRowBody);
        }
        public void bindTo(SampleListData item){
            if (null == textHead || null == textBody) System.out.println("bindTo(): view is null.");
            if (null != textHead) textHead.setText(item.id());
            if (null != textBody) textBody.setText(item.name());
        }
    }

    @BindingAdapter({"pagedList"})
    public static void setPagedList(RecyclerView rv, PagedList<SampleListData> list){
        System.out.println("setPagedList called.");
        if (null != list) System.out.println("list.size():" + list.size());
        if (null == list) System.out.println("list isNull.");
        SampleDataListAdapter adapter = (SampleDataListAdapter) rv.getAdapter();
        if (null == adapter){
            //アダプター作ってList入れて？
            rv.setLayoutManager(new LinearLayoutManager(rv.getContext(), LinearLayoutManager.VERTICAL, false));
            adapter = new SampleDataListAdapter();
        }
        adapter.submitList(list);
        rv.setAdapter(adapter);
    }
}
