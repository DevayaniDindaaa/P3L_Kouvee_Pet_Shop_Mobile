package com.p3l_f_1_pegawai.Activities.ukuran_hewan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UkuranHewanViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public UkuranHewanViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is ukuran hewan fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}