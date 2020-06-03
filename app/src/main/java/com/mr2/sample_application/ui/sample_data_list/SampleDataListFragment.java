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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mr2.sample_app_infra.room_database.sample_list_data.SampleListData;
import com.mr2.sample_application.R;
import com.mr2.sample_application.databinding.SampleDataListFragmentBinding;

public class SampleDataListFragment extends Fragment {
    private SampleDataListViewModel viewModel;
    private SampleDataListFragmentBinding binding;
    private Context context;

    public static SampleDataListFragment newInstance() {

        Bundle args = new Bundle();

        SampleDataListFragment fragment = new SampleDataListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        assert null != getActivity();
        viewModel = new ViewModelProvider
                .AndroidViewModelFactory(getActivity().getApplication())
                .create(SampleDataListViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.sample_data_list_fragment, container,false);
        binding.setVm(viewModel);
        if (null == binding.sampleRecycler.getAdapter()) {
            binding.sampleRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            binding.sampleRecycler.setAdapter(new SampleDataListAdapter(context));
            binding.sampleRecycler.setHasFixedSize(true);
        }
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        Observer<PagedList<SampleListData>> observer = new Observer<PagedList<SampleListData>>() {
            @Override
            public void onChanged(PagedList<SampleListData> sampleListData) {
                System.out.println("viewModel.listLiveData.observer:: on Change SampleListData. ");
                if (null == sampleListData) return;
                System.out.println("list size: " + sampleListData.size());
            }
        };
        viewModel.fetchList(getViewLifecycleOwner(), observer);
    }
}
