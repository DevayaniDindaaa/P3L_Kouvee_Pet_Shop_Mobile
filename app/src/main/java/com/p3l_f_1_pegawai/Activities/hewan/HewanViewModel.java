package com.p3l_f_1_pegawai.Activities.hewan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HewanViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HewanViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is hewan fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}