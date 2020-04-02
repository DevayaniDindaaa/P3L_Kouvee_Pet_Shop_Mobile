package com.p3l_f_1_pegawai.Activities.akun;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.p3l_f_1_pegawai.R;

public class AkunFragment extends Fragment {
    private TextView id_pegawai, role, nama_pegawai, username, tgl_lahir_pegawai, alamat_pegawai, no_telp;
    Activity context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        context = getActivity();
        return inflater.inflate(R.layout.fragment_akun, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        id_pegawai = view.findViewById(R.id.id_pegawai);
        role = view.findViewById(R.id.role);
        nama_pegawai = view.findViewById(R.id.nama_pegawai);
        username = view.findViewById(R.id.username);
        tgl_lahir_pegawai = view.findViewById(R.id.tgl_lahir);
        alamat_pegawai = view.findViewById(R.id.alamat);
        no_telp = view.findViewById(R.id.telepon);

        id_pegawai.setText(getActivity().getIntent().getExtras().getString("ID_PEGAWAI"));
        role.setText(getActivity().getIntent().getExtras().getString("ID_ROLE"));
        nama_pegawai.setText(getActivity().getIntent().getExtras().getString("NAMA_PEGAWAI"));
        username.setText(getActivity().getIntent().getExtras().getString("USERNAME"));
        alamat_pegawai.setText(getActivity().getIntent().getExtras().getString("ALAMAT_PEGAWAI"));
        tgl_lahir_pegawai.setText(getActivity().getIntent().getExtras().getString("TGL_LAHIR_PEGAWAI"));
        no_telp.setText(getActivity().getIntent().getExtras().getString("NO_TELP"));
    }
}
