package com.p3l_f_1_pegawai.Activities.hewan;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.p3l_f_1_pegawai.R;

public class activity_detail_hewan extends AppCompatActivity {
    Activity context;
    private TextView nama_konsumen, alamat_konsumen, tgl_lahir_konsumen, nomor_telepon, status_member, status_data, nama_hewan, jenis_hewan, ukuran_hewan, tgl_lahir_hewan;
    String nama_user;
    private Button ubah_hewan, hapus_hewan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_hewan);
        setAtribut();

        ubah_hewan = findViewById(R.id.ubah_hewan);
        hapus_hewan = findViewById(R.id.hapus_hewan);

        ubah_hewan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nama_user.equalsIgnoreCase("owner")){
                    Toast.makeText(context, "Owner tidak bisa mengubah data hewan!", Toast.LENGTH_SHORT).show();
                }
                else{
                    //DISINI INTENT KE HALAMAN UBAH DATA HEWAN
                }
            }});

        hapus_hewan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nama_user.equalsIgnoreCase("owner")){
                    Toast.makeText(context, "Owner tidak bisa menghapus data hewan!", Toast.LENGTH_SHORT).show();
                }
                else{
                    //DISINI INTENT KE HALAMAN HAPUS DATA HEWAN
                }
            }
        });

    }

    private void setAtribut(){
        //nama_user = context.getIntent().getExtras().getString("USERNAME");
        nama_hewan = findViewById(R.id.nama_hewan_detail);
        nama_hewan.setText(getIntent().getStringExtra("nama_hewan"));
        jenis_hewan = findViewById(R.id.jenis_hewan_detail);
        jenis_hewan.setText(getIntent().getStringExtra("jenis_hewan"));
        ukuran_hewan = findViewById(R.id.ukuran_hewan_detail);
        ukuran_hewan.setText(getIntent().getStringExtra("ukuran_hewan"));
        tgl_lahir_hewan = findViewById(R.id.tgl_lahir_hewan_detail);
        tgl_lahir_hewan.setText(getIntent().getStringExtra("tgl_lahir_hewan"));
        status_data = findViewById(R.id.log_aktivitas);
        status_data.setText(getIntent().getStringExtra("status_data"));

        nama_konsumen = findViewById(R.id.nama_konsumen_detail);
        nama_konsumen.setText(getIntent().getStringExtra("nama_konsumen"));
        alamat_konsumen = findViewById(R.id.alamat_konsumen_detail);
        alamat_konsumen.setText(getIntent().getStringExtra("alamat_konsumen"));
        tgl_lahir_konsumen = findViewById(R.id.tgl_lahir_konsumen_detail);
        tgl_lahir_konsumen.setText(getIntent().getStringExtra("tgl_lahir_konsumen"));
        nomor_telepon = findViewById(R.id.no_tlp_konsumen_detail);
        nomor_telepon.setText(getIntent().getStringExtra("no_tlp_konsumen"));
        status_member = findViewById(R.id.status_member_konsumen_detail);
        status_member.setText(getIntent().getStringExtra("status_member"));
    }
}