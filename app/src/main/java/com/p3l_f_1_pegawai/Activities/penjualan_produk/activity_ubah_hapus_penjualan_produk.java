package com.p3l_f_1_pegawai.Activities.penjualan_produk;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.p3l_f_1_pegawai.Activities.penjualan_layanan.DetailUbahPenjualanLayananAdapter;
import com.p3l_f_1_pegawai.Activities.penjualan_layanan.activity_ubah_hapus_penjualan_layanan;
import com.p3l_f_1_pegawai.R;
import com.p3l_f_1_pegawai.dao.detail_penjualan_layananDAO;
import com.p3l_f_1_pegawai.dao.detail_penjualan_produkDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class activity_ubah_hapus_penjualan_produk extends AppCompatActivity {
    Activity context;
    private List<detail_penjualan_produkDAO> DetailPenjualanProdukList;
    private RecyclerView recyclerView;
    private DetailUbahPenjualanProdukAdapter recycleAdapter;
    private TextView no_transaksi, tgl_transaksi, nama_konsumen, status_member, nama_hewan, jenis_hewan, nama_cs, nama_kasir, sub_total, diskon, total_bayar, status_bayar;
    String nama_user;
    private Button button_ubah, button_batal, tambah_produk;
    private String status_pembayaran = "-";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_hapus_penjualan_produk);
        setAtribut();

        DetailPenjualanProdukList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycle_tampil_detail_penjualan_produk);
        recycleAdapter = new DetailUbahPenjualanProdukAdapter(DetailPenjualanProdukList, this);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recycleAdapter);

        getDetailPenjualanProduk();

        button_ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity_ubah_hapus_penjualan_produk.this, "Berhasil Ubah Detail Penjualan Layanan!", Toast.LENGTH_SHORT).show();
            }});
    }

    private void getDetailPenjualanProduk() {
        try {
            String detailJualProduk = getIntent().getStringExtra("details");
            JSONArray detail = new JSONArray(detailJualProduk);
            for (int j = 0; j < detail.length(); j++) {
                JSONObject objectDetail = detail.getJSONObject(j);
                detail_penjualan_produkDAO d = new detail_penjualan_produkDAO(objectDetail.getString("id_detail_trans_produk"),
                        objectDetail.getString("id_produk"),
                        objectDetail.getString("nama_produk"),
                        objectDetail.getString("nama_jenis_hewan"),
                        objectDetail.getString("satuan_produk"),
                        objectDetail.getString("status_data"),
                        objectDetail.getString("time_stamp"),
                        objectDetail.getString("keterangan"),
                        objectDetail.getInt("harga_satuan_produk"),
                        objectDetail.getInt("jumlah_produk"),
                        objectDetail.getInt("jumlah_harga_produk"));
                DetailPenjualanProdukList.add(d);
            }
            recycleAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setAtribut(){
        SharedPreferences mSettings = getSharedPreferences("Login", Context.MODE_PRIVATE);
        nama_user = mSettings.getString("nama_user_login", "user_tidak_terdeteksi");

        no_transaksi = findViewById(R.id.nomor_transaksi);
        no_transaksi.setText(getIntent().getStringExtra("no_transaksi"));
        tgl_transaksi = findViewById(R.id.tanggal_transaksi);
        tgl_transaksi.setText(getIntent().getStringExtra("tgl_transaksi"));
        nama_konsumen = findViewById(R.id.nama_konsumen_produk);
        nama_konsumen.setText(getIntent().getStringExtra("nama_konsumen"));
        nama_hewan = findViewById(R.id.nama_hewan_produk);
        nama_hewan.setText(getIntent().getStringExtra("nama_hewan"));

        button_ubah = findViewById(R.id.button_simpan);
        button_batal = findViewById(R.id.button_batal);
        tambah_produk = (Button) findViewById(R.id.button_tambah_produk);
    }
}