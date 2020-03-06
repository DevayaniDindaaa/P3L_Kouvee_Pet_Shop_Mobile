package com.p3l_f_1_pegawai.Activities.pengadaan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PengadaanViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PengadaanViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is pengadaan fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}