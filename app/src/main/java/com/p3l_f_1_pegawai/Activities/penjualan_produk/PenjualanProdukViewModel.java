package com.p3l_f_1_pegawai.Activities.penjualan_produk;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PenjualanProdukViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PenjualanProdukViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is penjualan produk fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}