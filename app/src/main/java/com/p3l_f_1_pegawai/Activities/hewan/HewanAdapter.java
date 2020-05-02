package com.p3l_f_1_pegawai.Activities.hewan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HewanAdapter extends ViewModel {

    private MutableLiveData<String> mText;

    public HewanAdapter() {
        mText = new MutableLiveData<>();
        mText.setValue("This is hewan fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}