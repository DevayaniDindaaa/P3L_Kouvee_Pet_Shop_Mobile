package com.p3l_f_1_pegawai.Activities.penjualan_produk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import com.p3l_f_1_pegawai.Activities.penjualan_layanan.DetailPenjualanLayananAdapter;
import com.p3l_f_1_pegawai.Activities.penjualan_layanan.activity_detail_penjualan_layanan;
import com.p3l_f_1_pegawai.R;
import com.p3l_f_1_pegawai.dao.detail_penjualan_layananDAO;
import com.p3l_f_1_pegawai.dao.detail_penjualan_produkDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class activity_detail_penjualan_produk extends AppCompatActivity {
    Activity context;
    private List<detail_penjualan_produkDAO> DetailPenjualanProdukList;
    private RecyclerView recyclerView;
    private DetailPenjualanProdukAdapter recycleAdapter;
    private TextView no_transaksi, tgl_transaksi, nama_konsumen, status_member, nama_hewan, jenis_hewan, nama_cs, nama_kasir, sub_total, diskon, total_bayar, status_bayar;
    String nama_user;
    private Button ubah_penjualan_produk;
    private String status_pembayaran = "-";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_penjualan_produk);
        setAtribut();

        DetailPenjualanProdukList = new ArrayList<>();

        ubah_penjualan_produk = findViewById(R.id.ubah_detail_produk);
        recyclerView = findViewById(R.id.recycle_tampil_detail_penjualan_produk);
        recycleAdapter = new DetailPenjualanProdukAdapter(DetailPenjualanProdukList, this);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recycleAdapter);

        getDetailPenjualanProduk();

        ubah_penjualan_produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(nama_user);
                if(nama_user.equalsIgnoreCase("owner")){
                    Toast.makeText(activity_detail_penjualan_produk.this, "Owner tidak bisa mengubah data penjualan produk!", Toast.LENGTH_SHORT).show();
                }
                else if(status_pembayaran.equalsIgnoreCase("Lunas")){
                    Toast.makeText(activity_detail_penjualan_produk.this, "Transaksi ini Sudah Lunas, tidak dapat diubah lagi!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent i = new Intent(activity_detail_penjualan_produk.this, activity_ubah_hapus_penjualan_produk.class);
                    i.putExtra("no_transaksi", no_transaksi.getText().toString());
                    i.putExtra("tgl_transaksi", tgl_transaksi.getText().toString());
                    i.putExtra("nama_konsumen", nama_konsumen.getText().toString());
                    i.putExtra("nama_hewan", nama_hewan.getText().toString());
                    i.putExtra("details", getIntent().getStringExtra("details"));
                    startActivity(i);
                }
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

        no_transaksi = findViewById(R.id.no_transaksi_produk_detail);
        no_transaksi.setText(getIntent().getStringExtra("no_transaksi"));
        tgl_transaksi = findViewById(R.id.tgl_transaksi_produk_detail);
        tgl_transaksi.setText(getIntent().getStringExtra("tgl_transaksi"));
        nama_konsumen = findViewById(R.id.nama_konsumen_produk_detail);
        nama_konsumen.setText(getIntent().getStringExtra("nama_konsumen"));
        status_member = findViewById(R.id.status_konsumen_produk_detail);
        status_member.setText(getIntent().getStringExtra("status_member"));

        nama_hewan = findViewById(R.id.nama_hewan_produk_detail);
        nama_hewan.setText(getIntent().getStringExtra("nama_hewan"));
        jenis_hewan = findViewById(R.id.jenis_hewan_produk_detail);
        jenis_hewan.setText(getIntent().getStringExtra("jenis_hewan"));

        nama_cs = findViewById(R.id.nama_cs_produk_detail);
        nama_cs.setText(getIntent().getStringExtra("nama_cs"));
        nama_kasir = findViewById(R.id.nama_kasir_produk_detail);
        nama_kasir.setText(getIntent().getStringExtra("nama_kasir"));
        sub_total = findViewById(R.id.sub_total_produk_detail);
        sub_total.setText("Rp " + getIntent().getStringExtra("sub_total"));
        diskon = findViewById(R.id.diskon_produk_detail);
        diskon.setText("Rp " + getIntent().getStringExtra("diskon"));
        total_bayar = findViewById(R.id.total_bayar_produk_detail);
        total_bayar.setText("Rp " + getIntent().getStringExtra("total_bayar"));
        status_bayar = findViewById(R.id.status_bayar_produk_detail);
        status_bayar.setText(getIntent().getStringExtra("status_bayar"));

        status_pembayaran = getIntent().getStringExtra("status_bayar");
    }
}