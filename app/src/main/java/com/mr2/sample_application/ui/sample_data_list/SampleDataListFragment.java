package com.mr2.sample_application.ui.sample_data_list;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
        binding.sampleListFab.setOnClickListener(v -> {
//            viewModel.addItem();
        });
        viewModel.listLiveData.observe(getViewLifecycleOwner(), sampleListData -> {
            if (null != sampleListData)
            viewModel.liveListSize.postValue(sampleListData.getLoadedCount());
        });
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),
                R.animator.escape_up);
        set.setTarget(binding.sampleListFab);
        set.start();
        binding.sampleListFab.setOnTouchListener(new View.OnTouchListener() {
            float start_x = 0;
            float old_x = 0;
            float new_x = 0;
            float start_y;
            float old_y;
            float new_y;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (start_x == 0 && start_y == 0) {
                    start_x = event.getRawX();
                    start_y = event.getRawY();
                }
                new_x = event.getRawX();
                new_y = event.getRawY();
                float diff_x = new_x - old_x;
                float total_x = new_x - start_x;
                float total_y = new_y - start_y;
                System.out.println("start=" + start_x + ", new=" + new_x + ", total=" + total_x);
                v.setTranslationX(total_x);
                v.setTranslationY(total_y);
                return true;
            }
        });
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
