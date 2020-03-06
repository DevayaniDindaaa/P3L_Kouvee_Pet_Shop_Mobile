package com.p3l_f_1_pegawai.Activities.layanan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LayananViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LayananViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is layanan fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}