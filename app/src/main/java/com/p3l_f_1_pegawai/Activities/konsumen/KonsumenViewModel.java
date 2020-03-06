package com.p3l_f_1_pegawai.Activities.konsumen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class KonsumenViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public KonsumenViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is konsumen fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
