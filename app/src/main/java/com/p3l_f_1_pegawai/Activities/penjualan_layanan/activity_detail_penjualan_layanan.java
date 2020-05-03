package com.p3l_f_1_pegawai.Activities.penjualan_layanan;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.p3l_f_1_pegawai.Activities.hewan.activity_detail_hapus_hewan;
import com.p3l_f_1_pegawai.Activities.konsumen.DetailKonsumenAdapter;
import com.p3l_f_1_pegawai.R;
import com.p3l_f_1_pegawai.dao.detail_penjualan_layananDAO;
import com.p3l_f_1_pegawai.dao.hewanDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class activity_detail_penjualan_layanan extends AppCompatActivity {
    Activity context;
    private List<detail_penjualan_layananDAO> DetailPenjualanLayananList;
    private RecyclerView recyclerView;
    private DetailPenjualanLayananAdapter recycleAdapter;
    private TextView no_transaksi, tgl_transaksi, nama_konsumen, status_member, nama_hewan, jenis_hewan, ukuran_hewan, nama_cs, nama_kasir, sub_total, diskon, total_bayar, status_bayar, status_kerja;
    String nama_user;
    private Button ubah_penjualan_layanan;
    private String status_pembayaran = "-";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_penjualan_layanan);
        setAtribut();

        DetailPenjualanLayananList = new ArrayList<>();

        ubah_penjualan_layanan = findViewById(R.id.ubah_detail_layanan);
        recyclerView = findViewById(R.id.recycle_tampil_detail_penjualan_layanan);
        recycleAdapter = new DetailPenjualanLayananAdapter(DetailPenjualanLayananList, this);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recycleAdapter);

        getDetailPenjualanLayanan();

        ubah_penjualan_layanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(nama_user);
                if(nama_user.equalsIgnoreCase("owner")){
                    Toast.makeText(activity_detail_penjualan_layanan.this, "Owner tidak bisa mengubah data penjualan layanan!", Toast.LENGTH_SHORT).show();
                }
                else if(status_pembayaran.equalsIgnoreCase("Lunas")){
                    Toast.makeText(activity_detail_penjualan_layanan.this, "Transaksi ini Sudah Lunas, tidak dapat diubah lagi!", Toast.LENGTH_SHORT).show();
                }
                else{
                    //DISINI INTENT KE HALAMAN UBAH DATA HEWAN
                }
            }});
    }

    private void getDetailPenjualanLayanan() {
        try {
            String detailJualLayanan = getIntent().getStringExtra("details");
            JSONArray detail = new JSONArray(detailJualLayanan);
            for (int j = 0; j < detail.length(); j++) {
                JSONObject objectDetail = detail.getJSONObject(j);
                detail_penjualan_layananDAO d = new detail_penjualan_layananDAO(objectDetail.getString("id_detail_trans_layanan"),
                        objectDetail.getString("id_layanan"),
                        objectDetail.getString("nama_layanan"),
                        objectDetail.getString("nama_jenis_hewan"),
                        objectDetail.getString("nama_ukuran_hewan"),
                        objectDetail.getString("status_data"),
                        objectDetail.getString("time_stamp"),
                        objectDetail.getString("keterangan"),
                        objectDetail.getInt("harga_satuan_layanan"),
                        objectDetail.getInt("jumlah_layanan"),
                        objectDetail.getInt("jumlah_harga_layanan"));
                DetailPenjualanLayananList.add(d);
            }
            recycleAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setAtribut(){
        SharedPreferences mSettings = getSharedPreferences("Login", Context.MODE_PRIVATE);
        nama_user = mSettings.getString("nama_user_login", "user_tidak_terdeteksi");

        no_transaksi = findViewById(R.id.no_transaksi_layanan_detail);
        no_transaksi.setText(getIntent().getStringExtra("no_transaksi"));
        tgl_transaksi = findViewById(R.id.tgl_transaksi_layanan_detail);
        tgl_transaksi.setText(getIntent().getStringExtra("tgl_transaksi"));
        nama_konsumen = findViewById(R.id.nama_konsumen_layanan_detail);
        nama_konsumen.setText(getIntent().getStringExtra("nama_konsumen"));
        status_member = findViewById(R.id.status_konsumen_layanan_detail);
        status_member.setText(getIntent().getStringExtra("status_member"));

        nama_hewan = findViewById(R.id.nama_hewan_layanan_detail);
        nama_hewan.setText(getIntent().getStringExtra("nama_hewan"));
        jenis_hewan = findViewById(R.id.jenis_hewan_layanan_detail);
        jenis_hewan.setText(getIntent().getStringExtra("jenis_hewan"));
        ukuran_hewan = findViewById(R.id.ukuran_hewan_layanan_detail);
        ukuran_hewan.setText(getIntent().getStringExtra("ukuran_hewan"));

        nama_cs = findViewById(R.id.nama_cs_layanan_detail);
        nama_cs.setText(getIntent().getStringExtra("nama_cs"));
        nama_kasir = findViewById(R.id.nama_kasir_layanan_detail);
        nama_kasir.setText(getIntent().getStringExtra("nama_kasir"));
        sub_total = findViewById(R.id.sub_total_layanan_detail);
        sub_total.setText("Rp " + getIntent().getStringExtra("sub_total"));
        diskon = findViewById(R.id.diskon_layanan_detail);
        diskon.setText("Rp " + getIntent().getStringExtra("diskon"));
        total_bayar = findViewById(R.id.total_bayar_layanan_detail);
        total_bayar.setText("Rp " + getIntent().getStringExtra("total_bayar"));
        status_bayar = findViewById(R.id.status_bayar_layanan_detail);
        status_bayar.setText(getIntent().getStringExtra("status_bayar"));

        status_kerja = findViewById(R.id.status_kerja_layanan_detail);
        status_kerja.setText(getIntent().getStringExtra("status_kerja"));

        status_pembayaran = getIntent().getStringExtra("status_bayar");
    }
}