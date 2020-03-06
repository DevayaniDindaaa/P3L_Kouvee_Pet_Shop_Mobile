package com.p3l_f_1_pegawai.Activities.supplier;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SupplierViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SupplierViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is supplier fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}