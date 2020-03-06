package com.p3l_f_1_pegawai.Activities.penjualan_layanan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PenjualanLayananViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PenjualanLayananViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is penjualan layanan fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
