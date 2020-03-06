package com.p3l_f_1_pegawai.Activities.penjualan_layanan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.p3l_f_1_pegawai.R;

public class PenjualanLayananFragment extends Fragment {

    private PenjualanLayananViewModel penjualanLayananViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        penjualanLayananViewModel =
                ViewModelProviders.of(this).get(PenjualanLayananViewModel.class);
        View root = inflater.inflate(R.layout.fragment_penjualan_layanan, container, false);
        final TextView textView = root.findViewById(R.id.text_share);
        penjualanLayananViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}