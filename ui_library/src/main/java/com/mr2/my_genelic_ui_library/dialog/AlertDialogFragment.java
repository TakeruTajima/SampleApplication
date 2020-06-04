package com.mr2.my_genelic_ui_library.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AlertDialogFragment extends DialogFragment {
    private static final String KEY_TITLE = "title";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_POSITIVE = "positive";
    private static final String KEY_NEUTRAL = "neutral";
    private static final String KEY_NEGATIVE = "negative";
    private OnDialogResultListener listener;

    public interface OnDialogResultListener{
        void onDialogResult(DialogInterface dialog, int which);
    }

    private AlertDialogFragment(){
    }

    private static AlertDialogFragment newInstance(@NonNull Builder b) {
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, b.title);
        if (null != b.message) args.putString(KEY_MESSAGE, b.message);
        args.putString(KEY_POSITIVE, b.positive);
        if (null != b.neutral) args.putString(KEY_NEUTRAL, b.neutral);
        if (null != b.negative) args.putString(KEY_NEGATIVE, b.negative);
        AlertDialogFragment fragment = new AlertDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setOnDialogListener(OnDialogResultListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        assert null != args;
        String title = args.getString(KEY_TITLE);
        String message = args.getString(KEY_MESSAGE);
        String positive = args.getString(KEY_POSITIVE);
        String neutral = args.getString(KEY_NEUTRAL);
        String negative = args.getString(KEY_NEGATIVE);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);
        if (null != message) builder.setMessage(message);
        if (null != negative) builder.setNegativeButton(negative, listener::onDialogResult);
        if (null != neutral) builder.setNeutralButton(neutral, listener::onDialogResult);
        builder.setPositiveButton(positive, listener::onDialogResult);
        return builder.create();
    }

    public static class Builder{
        private String title;
        private String message;
        private String positive;
        private String neutral;
        private String negative;
        private OnDialogResultListener listener;

        public Builder(@NonNull String title, @NonNull String positive) {
//            if (null == title || null == positive) throw new IllegalArgumentException();
            this.title = title;
            this.positive = positive;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setPositive(String positive) {
            this.positive = positive;
            return this;
        }

        public Builder setNeutral(String neutral) {
            this.neutral = neutral;
            return this;
        }

        public Builder setNegative(String negative) {
            this.negative = negative;
            return this;
        }

        public Builder setListener(OnDialogResultListener listener) {
            this.listener = listener;
            return this;
        }

        public AlertDialogFragment create(){
            AlertDialogFragment dialog = AlertDialogFragment.newInstance(this);
            if (null != listener) dialog.setOnDialogListener(listener);
            return dialog;
        }
    }
}
