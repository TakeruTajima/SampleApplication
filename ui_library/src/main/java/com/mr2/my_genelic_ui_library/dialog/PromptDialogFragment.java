package com.mr2.my_genelic_ui_library.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class PromptDialogFragment extends DialogFragment {
    private static final String KEY_TITLE = "title";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_HINT = "hint";
    private static final String KEY_DEFAULT = "default";
    private static final String KEY_POSITIVE = "positive";
    private static final String KEY_NEUTRAL = "neutral";
    private static final String KEY_NEGATIVE = "negative";
    private EditText editText;
    private OnDialogResultListener listener;

    public interface OnDialogResultListener {
        void onDialogResult(DialogInterface dialog, int which, String input);
    }

    private PromptDialogFragment() {
    }

    private static PromptDialogFragment newInstance(
            @NonNull String title,
            @Nullable String message,
            @Nullable String hint,
            @Nullable String defaultText,
            @NonNull String positive,
            @Nullable String neutral,
            @Nullable String negative) {
        PromptDialogFragment fragment = new PromptDialogFragment();
        Bundle arg = new Bundle();
        arg.putString(KEY_TITLE, title);
        if (null != message) arg.putString(KEY_MESSAGE, message);
        if (null != hint) arg.putString(KEY_HINT, hint);
        if (null != defaultText) arg.putString(KEY_DEFAULT, defaultText);
        arg.putString(KEY_POSITIVE, positive);
        if (null != neutral) arg.putString(KEY_NEUTRAL, neutral);
        if (null != negative) arg.putString(KEY_NEGATIVE, negative);
        fragment.setArguments(arg);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        if (null == savedInstanceState) return ??
        Bundle arg = getArguments();
        assert null != arg;
        String title = arg.getString(KEY_TITLE);
        String message = arg.getString(KEY_MESSAGE);
        String hint = arg.getString(KEY_HINT);
        String defaultText = arg.getString(KEY_DEFAULT);
        String positive = arg.getString(KEY_POSITIVE);
        String neutral = arg.getString(KEY_NEUTRAL);
        String negative = arg.getString(KEY_NEGATIVE);
        editText = new EditText(getContext());
        editText.setSingleLine();
        /* Builder */
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);
        if (null != message) builder.setMessage(message);
        if (null != hint) editText.setHint(hint);
        if (null !=  defaultText) editText.setText(defaultText);
        builder.setView(editText);
        if (null != negative) builder.setNegativeButton(negative, (dialog, which) ->
                listener.onDialogResult(dialog, which, editText.getText().toString()));
        if (null != neutral) builder.setNeutralButton(neutral, (dialog, which) ->
                listener.onDialogResult(dialog, which, editText.getText().toString()));
        builder.setPositiveButton(positive, (dialog, which) ->
                listener.onDialogResult(dialog, which, editText.getText().toString()));
        return builder.create();
    }

    public void setOnDialogResultListener(OnDialogResultListener listener) {
        this.listener = listener;
    }

    /**
     *
     * //clients example:
     * String title = "caution";
     * String message = "This operation cannot be undone. Do you want to delete it?";
     * PromptDialogFragment d = new PromptDialogFragment.Builder(title, "Delete")
     *    .setMessage(message)
     *    .setNegativeButton("cancel")
     *    .setListener((dialog1, which, input) -> {
     *        if (which == DialogInterface.BUTTON_POSITIVE) onDialogResult(input);
     *        dialog1.dismiss();
     *    })
     *    .create();
     * d.show(getChildFragmentManager(), "tag");
     */
    public static class Builder{
        private String title;
        private String message;
        private String hint;
        private String defaultText;
        private String positive;
        private String neutral;
        private String negative;
        private OnDialogResultListener listener;

        public Builder(String title, String positive) {
            if (null == title || null == positive) throw new IllegalArgumentException();
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

        public Builder setHint(String hint) {
            this.hint = hint;
            return this;
        }

        public Builder setDefaultText(String defaultText) {
            this.defaultText = defaultText;
            return this;
        }

        public Builder setPositiveButton(String positive) {
            this.positive = positive;
            return this;
        }

        public Builder setNeutralButton(String neutral) {
            this.neutral = neutral;
            return this;
        }

        public Builder setNegativeButton(String negative) {
            this.negative = negative;
            return this;
        }

        public Builder setListener(OnDialogResultListener listener) {
            this.listener = listener;
            return this;
        }

        public PromptDialogFragment create(){
            PromptDialogFragment dialog = PromptDialogFragment.newInstance(title,message,hint,defaultText,positive,neutral,negative);
            if (null != listener) dialog.setOnDialogResultListener(listener);
            return dialog;
        }
    }
}