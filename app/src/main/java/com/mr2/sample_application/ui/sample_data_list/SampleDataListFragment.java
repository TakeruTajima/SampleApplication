package com.mr2.sample_application.ui.sample_data_list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mr2.sample_app_infra.room_database.sample_list_data.SampleListData;
import com.mr2.sample_application.R;
import com.mr2.sample_application.databinding.SampleDataListFragmentBinding;

public class SampleDataListFragment extends Fragment {
    private SampleDataListViewModel viewModel;
    private SampleDataListFragmentBinding binding;

    public static SampleDataListFragment newInstance() {

        Bundle args = new Bundle();

        SampleDataListFragment fragment = new SampleDataListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        assert null != getActivity();
        viewModel = new ViewModelProvider
                .AndroidViewModelFactory(getActivity().getApplication())
                .create(SampleDataListViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.sample_data_list_fragment, container, false);

        binding.setLifecycleOwner(this);
        binding.setVm(viewModel);
        binding.sampleListFab.setOnClickListener(v -> viewModel.addItem());
        viewModel.listLiveData.observe(getViewLifecycleOwner(), sampleListData -> {
            if (null != sampleListData)
            viewModel.liveListSize.postValue(sampleListData.getLoadedCount());
        });
        return binding.getRoot();
    }


    /**
     * カスタムセッター
     *
     * @param rv Tagを設定したViewのインスタンス
     * @param list Tagに放り込まれた設定値
     */
    @BindingAdapter({"pagedList"})
    public static void setPagedList(RecyclerView rv, PagedList<SampleListData> list){
        RecyclerView.Adapter adapter = rv.getAdapter();
        if (null == adapter){
            DiffUtil.ItemCallback<SampleListData> DIFF_CALLBACK = new DiffUtil.ItemCallback<SampleListData>() {
                @Override
                public boolean areItemsTheSame(@NonNull SampleListData oldItem, @NonNull SampleListData newItem) {
                    return oldItem.id == newItem.id;
                }
                @Override
                public boolean areContentsTheSame(@NonNull SampleListData oldItem, @NonNull SampleListData newItem) {
                    return oldItem.equals(newItem);
                }
            };

            rv.setLayoutManager(new LinearLayoutManager(rv.getContext(), LinearLayoutManager.VERTICAL, false));
            rv.setHasFixedSize(true);
            adapter = new SamplePagedListAdapter<>(DIFF_CALLBACK, R.layout.sample_data_list_row, BR.item);
            rv.setAdapter(adapter);
            rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));
        }
        if (adapter instanceof SamplePagedListAdapter) {
            SamplePagedListAdapter<SampleListData> adapter1 = SamplePagedListAdapter.avoidingUntestedCasts(adapter);
            adapter1.submitList(list);
        }
    }
}
