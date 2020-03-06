package com.p3l_f_1_pegawai.Activities.produk;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProdukViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ProdukViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is produk fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}