package com.mr2.sample_application.ui.item_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.mr2.sample_app_infra.observer.ItemHeadlineDto;
import com.mr2.sample_application.R;

public class ItemListAdapter extends PagedListAdapter<ItemHeadlineDto, ItemListAdapter.ViewHolder> {
    private Context context;

    protected ItemListAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemHeadlineDto item = getItem(position);
//        if (null == item){
//            holder.clear();
//            return;
//        }
        assert item != null;
        holder.textItemHead.setText(item.name);
        String body = item.quantity + item.unit_name;
        holder.textItemBody.setText(body);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textItemHead;
        private TextView textItemBody;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textItemHead = itemView.findViewById(R.id.itemListRowHead);
            textItemBody = itemView.findViewById(R.id.itemListRowBody);
        }
    }

    private static DiffUtil.ItemCallback<ItemHeadlineDto> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<ItemHeadlineDto>() {
                //IDが一致するか
                @Override
                public boolean areItemsTheSame(@NonNull ItemHeadlineDto oldItem, @NonNull ItemHeadlineDto newItem) {
                    return oldItem.item_id.equals(newItem.item_id);
                }

                //変化していないか
                @Override
                public boolean areContentsTheSame(@NonNull ItemHeadlineDto oldItem, @NonNull ItemHeadlineDto newItem) {
                    return oldItem.equals(newItem);
                }
            };
}
