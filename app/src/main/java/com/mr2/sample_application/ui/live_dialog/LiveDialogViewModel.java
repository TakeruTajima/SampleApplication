package com.mr2.sample_application.ui.live_dialog;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.UUID;

public class LiveDialogViewModel extends ViewModel {
    public MutableLiveData<LiveDialogState> state = new MutableLiveData<>();
    public MutableLiveData<String> editText = new MutableLiveData<>();
//    public final String unique_id = UUID.randomUUID().toString();
}
