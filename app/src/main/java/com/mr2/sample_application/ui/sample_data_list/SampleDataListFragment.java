package com.mr2.sample_application.ui.sample_data_list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mr2.sample_app_infra.room_database.MyDatabase;
import com.mr2.sample_app_infra.room_database.sample_list_data.SampleListData;
import com.mr2.sample_application.Executors;
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
        binding.sampleListFab.setOnClickListener(v -> {
            if (null != binding.sampleRecycler.getAdapter()) {
                System.out.println("RecyclerView.Adapter.itemCount is -> ");
                System.out.println("" + ((SampleDataListAdapter) binding.sampleRecycler.getAdapter()).getItemCount());
                Executors.ioThread(()->{
                    MyDatabase.getInstance(getContext()).sampleDao().insert(
                            new SampleListData("additional item")
                    );
                });
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.isLoadFinished.observe(getViewLifecycleOwner(), this::loadingObserver);
        viewModel.fetchList();
    }

    private void loadingObserver(Boolean isFinished){
        SampleDataListAdapter adapter = (SampleDataListAdapter) binding.sampleRecycler.getAdapter();
        if (isFinished && null != adapter){
            viewModel.listLiveData.observe(getViewLifecycleOwner(), adapter::submitList);
            //submitListが呼ばれた時点でPagedListの読み込みが開始される
        }
    }
}
