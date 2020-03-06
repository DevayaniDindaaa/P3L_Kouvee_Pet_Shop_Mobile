package com.p3l_f_1_pegawai;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class logout_activity extends Fragment {
    Activity context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        alertsignout();
        return inflater.inflate(R.layout.fragment_keluar, container, false);
    }

    public void alertsignout()
    {
        AlertDialog.Builder alertDialog2 = new
                AlertDialog.Builder(context);

        alertDialog2.setTitle("Konfirmasi");
        alertDialog2.setMessage("Apakah Anda Yakin Ingin Keluar?");

        alertDialog2.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(context,
                               login_activity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        Toast.makeText(context,
                                "Terimakasih!", Toast.LENGTH_SHORT)
                                .show();
                    }
                });

        alertDialog2.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context,
                                "Selamat Datang Kembali!", Toast.LENGTH_SHORT)
                                .show();
                        dialog.cancel();
                    }
                });

        alertDialog2.show();
    }
}
