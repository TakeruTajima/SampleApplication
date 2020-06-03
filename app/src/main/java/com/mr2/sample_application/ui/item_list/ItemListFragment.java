package com.mr2.sample_application.ui.item_list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mr2.sample_app_infra.observer.ItemHeadlineDto;
import com.mr2.sample_application.R;

import java.util.List;

public class ItemListFragment extends Fragment {
    private ItemListViewModel mViewModel;
    private Context context;

    public static ItemListFragment newInstance() {

        Bundle args = new Bundle();

        ItemListFragment fragment = new ItemListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_list_fragment, container);
        RecyclerView rv = view.findViewById(R.id.itemListRecycler);
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
//        rv.setHasFixedSize(true);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        assert null != getActivity();
        mViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getActivity().getApplication())
                .create(ItemListViewModel.class);
    }

    @BindingAdapter("item_list")
    public void loadData(RecyclerView recyclerView, PagedList<ItemHeadlineDto> list){
        ItemListAdapter adapter = (ItemListAdapter) recyclerView.getAdapter();
        if (null == adapter){
            adapter = new ItemListAdapter(getContext());
            adapter.submitList(list);
            recyclerView.setAdapter(adapter);
            return;
        }
        adapter.submitList(list);
        recyclerView.setAdapter(adapter);
    }
}
