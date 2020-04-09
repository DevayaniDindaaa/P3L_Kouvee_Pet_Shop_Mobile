package com.p3l_f_1_pegawai.Activities.pengadaan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.p3l_f_1_pegawai.R;
import com.p3l_f_1_pegawai.dao.detail_pengadaanDAO;
import com.p3l_f_1_pegawai.dao.pengadaanDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class activity_detail_pengadaan extends AppCompatActivity {
    Activity context;
    private List<detail_pengadaanDAO> DetailPengadaanList;
    private RecyclerView recyclerView;
    private DetailPengadaanAdapter recycleAdapter;
    private TextView nomor_pemesanan, tanggal_pesan, tanggal_cetak, nama_supplier, alamat_supplier, telepon_supplier;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pengadaan);
        setAtribut();

        DetailPengadaanList = new ArrayList<>();

        recyclerView = findViewById(R.id.recycle_tampil_detail_pengadaan);
        recycleAdapter = new DetailPengadaanAdapter(DetailPengadaanList, this);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recycleAdapter);

        getDetailPengadaan();
    }

    private void getDetailPengadaan() {
        try {
            String detailPengadaan = getIntent().getStringExtra("details");
            JSONArray detail = new JSONArray(detailPengadaan);
            for (int j = 0; j < detail.length(); j++) {
                JSONObject objectDetail = detail.getJSONObject(j);
                detail_pengadaanDAO d = new detail_pengadaanDAO(objectDetail.getString("id_detail_pengadaan"),
                        objectDetail.getString("nama_produk"),
                        objectDetail.getString("satuan_produk"),
                        objectDetail.getString("status_data"),
                        objectDetail.getString("time_stamp"),
                        objectDetail.getString("keterangan"),
                        objectDetail.getInt("jumlah_produk_dipesan"));
                System.out.println(objectDetail);
                DetailPengadaanList.add(d);
            }
            recycleAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setAtribut(){
        nomor_pemesanan = findViewById(R.id.nomor_pemesanan);
        nomor_pemesanan.setText(getIntent().getStringExtra("nomor_pemesanan"));
        tanggal_pesan = findViewById(R.id.tanggal_pemesanan);
        tanggal_pesan.setText(getIntent().getStringExtra("tanggal_pemesanan"));
        tanggal_cetak = findViewById(R.id.tanggal_cetak_surat_detail);
        tanggal_cetak.setText(getIntent().getStringExtra("tanggal_cetak"));
        nama_supplier = findViewById(R.id.nama_supplier_detail);
        nama_supplier.setText(getIntent().getStringExtra("nama_supplier"));
        alamat_supplier = findViewById(R.id.alamat_supplier_detail);
        alamat_supplier.setText(getIntent().getStringExtra("alamat_supplier"));
        telepon_supplier = findViewById(R.id.telepon_supplier_detail);
        telepon_supplier.setText(getIntent().getStringExtra("telepon_supplier"));
    }
}
