package com.p3l_f_1_pegawai.Activities.jenis_hewan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class JenisHewanViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public JenisHewanViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is jenis hewan fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}