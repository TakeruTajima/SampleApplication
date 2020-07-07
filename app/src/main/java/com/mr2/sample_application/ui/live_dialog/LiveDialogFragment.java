package com.mr2.sample_application.ui.live_dialog;

//import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.mr2.sample_application.R;
import com.mr2.sample_application.databinding.FragmentLiveDialogBinding;

public class LiveDialogFragment extends DialogFragment {
    private static final String KEY_TITLE = "title";
    private static final String KEY_MESSAGE = "message";
    private LiveDialogViewModel vm;
    private FragmentLiveDialogBinding binding;

    public static LiveDialogFragment newInstance(String title, String message) {
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        args.putString(KEY_MESSAGE, message);
        LiveDialogFragment fragment = new LiveDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        /* view model & binding */
        binding = FragmentLiveDialogBinding.inflate(LayoutInflater.from(getContext()));
        ViewModelStoreOwner parent = getParentFragment();
        if (null == parent) throw new IllegalStateException("LiveDialogFragmentのParentFragmentが取得できませんでした。");
        vm = new ViewModelProvider(parent, getDefaultViewModelProviderFactory())
                    .get(LiveDialogViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setVm(vm);
        /* get arguments */
        Bundle args = getArguments();
        if (null == args) throw new IllegalArgumentException();
        String title = args.getString(KEY_TITLE);
        String message = args.getString(KEY_MESSAGE);
        /* building dialog */
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.ThemeOverlay_Sample_Dialog_Alert);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setView(binding.getRoot());
        binding.liveDialogEditText.setHint("hint: xxxx");
        builder.setPositiveButton("OK", LISTENER); //TODO: ボタンのBackgroundColorが@color/colorPrimary
        builder.setNegativeButton("cancel", LISTENER);
        return builder.create();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Dialog d = getDialog();
        Window w = null;
        if (null != d) w = d.getWindow();
        if (null != w) w.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        binding.liveDialogEditText.requestFocus();
    }

    DialogInterface.OnClickListener LISTENER = (dialog, which) -> {
        switch (which){
            case DialogInterface.BUTTON_POSITIVE:
                vm.state.postValue(LiveDialogState.OK);
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                vm.state.postValue(LiveDialogState.CANCEL);
                break;
        }
        dialog.dismiss();
    };
}
