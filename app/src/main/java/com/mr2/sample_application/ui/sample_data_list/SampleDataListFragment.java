package com.mr2.sample_application.ui.sample_data_list;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.DialogInterface;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mr2.my_genelic_ui_library.dialog.PromptDialogFragment;
import com.mr2.sample_app_infra.room_database.sample_list_data.SampleListData;
import com.mr2.sample_application.R;
import com.mr2.sample_application.databinding.SampleDataListFragmentBinding;
import com.mr2.sample_application.ui.live_dialog.LiveDialogFragment;
import com.mr2.sample_application.ui.live_dialog.LiveDialogState;
import com.mr2.sample_application.ui.live_dialog.LiveDialogViewModel;

public class SampleDataListFragment extends Fragment {
    private SampleDataListViewModel viewModel;
    private LiveDialogViewModel dialogViewModel;

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
        dialogViewModel = new ViewModelProvider(this, getDefaultViewModelProviderFactory())
                .get(LiveDialogViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        com.mr2.sample_application.databinding.SampleDataListFragmentBinding binding
                = DataBindingUtil.inflate(inflater, R.layout.sample_data_list_fragment, container, false);
        binding.setLifecycleOwner(this);
        binding.setVm(viewModel);
        binding.sampleListFab.setOnClickListener(view -> {
            LiveDialogFragment dialog = LiveDialogFragment.newInstance("test title", "test message");
            dialog.show(getChildFragmentManager(), "");
        });
//        viewModel.listLiveData.observe(getViewLifecycleOwner(), sampleListData -> {
//            if (null != sampleListData)
//            viewModel.liveListSize.postValue(sampleListData.getLoadedCount());
//        });　//listの変更タイミングの関係でうまく動かない
        dialogViewModel.state.observe(getViewLifecycleOwner(), liveDialogState -> {
            switch (liveDialogState){
                case OK:
                    String s = dialogViewModel.editText.getValue();
                    viewModel.addItem(s);
                    dialogViewModel.editText.postValue("");
                    break;
                case CANCEL:
                    break;
            }
        });
//        dialogViewModel.editText.observe(getViewLifecycleOwner(), str ->{
//            System.out.println(str); //入力ルールの表示とか強制とかできるよ
//        });
        return binding.getRoot();
    }

//    private void showAddItemDialog(View view){
//        String title = "Create item";
//        String positive = "Create";
//        PromptDialogFragment dialog = new PromptDialogFragment.Builder(title, positive)
//                .setHint("ex) additional item")
//                .setMessage("Please input new item name.")
//                .setNegativeButton("Cancel")
//                .setListener((dialog1, which, input) -> {
//                    if (DialogInterface.BUTTON_POSITIVE == which){
//                        viewModel.addItem(input);
//                    }
//                    if (null != dialog1) dialog1.dismiss();
//                }).create();
//        dialog.show(getChildFragmentManager(), "ADD_ITEM_DIALOG");
//    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        binding.sampleListFab.setOnTouchListener(new ViewDiffMover(ViewDiffMover.TYPE_DOWN_ONLY));
//    }

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

    public static class ViewDiffMover implements View.OnTouchListener{
        public static final int TYPE_DOWN_ONLY = 0;
        public static final int TYPE_UP_ONLY = 1;
        public static final int ORIENTATION_HORIZONTAL = 2;
        static final float MOVE_BOUNDARY = 500;

        final float up_limit;
        final float down_limit;
        final float left_limit;
        final float right_limit;

        public ViewDiffMover(int type){
            switch (type){
                case TYPE_DOWN_ONLY:
                    up_limit = 0;
                    down_limit = Float.MAX_VALUE;
                    left_limit = 0;
                    right_limit = 0;
                    break;
                default:
                    up_limit = Float.MIN_VALUE;
                    down_limit = Float.MAX_VALUE;
                    left_limit = Float.MIN_VALUE;
                    right_limit = Float.MAX_VALUE;
                    break;
            }
        }

        public ViewDiffMover(float up_limit, float down_limit, float left_limit, float right_limit) {
            this.up_limit = up_limit;
            this.down_limit = down_limit;
            this.left_limit = left_limit;
            this.right_limit = right_limit;
        }

        float down_x = 0;
        float down_y = 0;
        float diff_x = 0;
        float diff_y = 0;
        float trans_x = 0;
        float trans_y = 0;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
//                    System.out.println("MotionEvent.ACTION_DOWN:");
                    down_x = event.getRawX();
                    down_y = event.getRawY();
                    trans_x = v.getTranslationX();
                    trans_y = v.getTranslationY();
//                    System.out.println("downx:" + down_x + " ,downy:" + down_y + " ,diffx:" + diff_x + " ,diffy:" + diff_y + " ,transx:" + trans_x + " ,transy:" + trans_y);
                    break;
                case MotionEvent.ACTION_MOVE:
//                        System.out.println("MotionEvent.ACTION_MOVE:");
                    diff_x = event.getRawX() - down_x;
                    diff_y = event.getRawY() - down_y;
                    float move_x = (right_limit < diff_x ? right_limit : Math.max(left_limit, diff_x));
                    float move_y = (down_limit < diff_y ? down_limit : Math.max(up_limit, diff_y));
                    v.setTranslationX(trans_x + move_x);
                    v.setTranslationY(trans_y + move_y);
                    break;
                case MotionEvent.ACTION_UP:
//                    System.out.println("MotionEvent.ACTION_UP:");
                    if ((-MOVE_BOUNDARY <= diff_x && diff_x <= MOVE_BOUNDARY) && (-MOVE_BOUNDARY <= diff_y && diff_y <= MOVE_BOUNDARY))
                    {
                        PropertyValuesHolder holderX = PropertyValuesHolder.ofFloat("translationX", trans_x);
                        PropertyValuesHolder holderY = PropertyValuesHolder.ofFloat("translationY", trans_y);
                        ObjectAnimator anim = ObjectAnimator.ofPropertyValuesHolder(v, holderX, holderY);
                        anim.setDuration(400);
                        anim.start();
                    }
//                    System.out.println("downx:" + down_x + " ,downy:" + down_y + " ,diffx:" + diff_x + " ,diffy:" + diff_y + " ,transx:" + trans_x + " ,transy:" + trans_y);
                    break;
            }
            return false;
        }
    }
}
