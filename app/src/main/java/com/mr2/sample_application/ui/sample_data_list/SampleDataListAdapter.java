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
    @NonNull
    private final Context context;

    protected SampleDataListAdapter(@NonNull Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_data_list_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SampleListData item = getItem(position);
        assert null != item;
        holder.textHead.setText(item.id());
        holder.textBody.setText(item.name());
        //https://qiita.com/nagasakulllo/items/88c7857c4b542825b06b
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
    }

    @BindingAdapter({"pagedList"})
    public static void setPagedList(RecyclerView rv, PagedList<SampleListData> list){
        SampleDataListAdapter adapter = (SampleDataListAdapter) rv.getAdapter();
        if (null == adapter){
            //アダプター作ってList入れて？
            adapter = new SampleDataListAdapter(rv.getContext());
        }
        adapter.submitList(list);
    }
}
