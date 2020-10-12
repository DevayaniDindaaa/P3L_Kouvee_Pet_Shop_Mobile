package com.p3l_f_1_pegawai.Activities.penjualan_layanan;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
import com.p3l_f_1_pegawai.R;
import com.p3l_f_1_pegawai.dao.detail_penjualan_layananDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class activity_detail_penjualan_layanan extends AppCompatActivity {
    Activity context;
    private String URL = "http://192.168.0.200/CI_Mobile_P3L_1F/index.php/transaksilayanan/sendsms";
    private List<detail_penjualan_layananDAO> DetailPenjualanLayananList;
    private RecyclerView recyclerView;
    private DetailPenjualanLayananAdapter recycleAdapter;
    private TextView no_transaksi, tgl_transaksi, nama_konsumen, status_member, nama_hewan, jenis_hewan, ukuran_hewan, nama_cs, nama_kasir, sub_total, diskon, total_bayar, status_bayar, status_kerja;
    String nama_user, nomor_telepon;
    private Button ubah_penjualan_layanan, kirim_sms;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_penjualan_layanan);
        setAtribut();

        DetailPenjualanLayananList = new ArrayList<>();

        ubah_penjualan_layanan = findViewById(R.id.ubah_detail_layanan);
        kirim_sms = findViewById(R.id.button_kirim_sms);
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
                else if(status_kerja.getText().toString().equalsIgnoreCase("Selesai")){
                    Toast.makeText(activity_detail_penjualan_layanan.this, "Transaksi ini Sudah Lunas, tidak dapat diubah lagi!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent i = new Intent(activity_detail_penjualan_layanan.this, activity_ubah_hapus_penjualan_layanan.class);
                    i.putExtra("no_transaksi", no_transaksi.getText().toString());
                    i.putExtra("tgl_transaksi", tgl_transaksi.getText().toString());
                    i.putExtra("nama_konsumen", nama_konsumen.getText().toString());
                    i.putExtra("nama_hewan", nama_hewan.getText().toString());
                    i.putExtra("nama_jenis_hewan", jenis_hewan.getText().toString());
                    i.putExtra("ukuran_hewan", ukuran_hewan.getText().toString());
                    i.putExtra("details", getIntent().getStringExtra("details"));
                    startActivity(i);
                }
            }});

        kirim_sms.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("IntentReset")
            @Override
            public void onClick(View v) {
                if(nama_user.equalsIgnoreCase("owner")){
                    Toast.makeText(activity_detail_penjualan_layanan.this, "Owner tidak bisa mengirim SMS ke nomor pelanggan!", Toast.LENGTH_SHORT).show();
                }
                else if(status_kerja.getText().toString().equalsIgnoreCase("Selesai")){
                    Toast.makeText(activity_detail_penjualan_layanan.this, "Transaksi ini Sudah Selesai di kerjakan!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent smsIntent = new Intent(Intent.ACTION_VIEW);

                    smsIntent.setData(Uri.parse("smsto:"));
                    smsIntent.setType("vnd.android-dir/mms-sms");
                    smsIntent.putExtra("address"  , nomor_telepon);
                    smsIntent.putExtra("sms_body"  , "Hallo Pelanggan, " + nama_konsumen.getText().toString() + "! \n" + "Pengerjaan Layanan untuk Nomor Transaksi : " + no_transaksi.getText().toString() + "\n---SUDAH SELESAI---" +
                            "\n\nSilahkan Melakukan Pembayaran di Kouvee Pet Shop dan Mengambil Hewan Anda!" +
                            "\n \n \nBest Regards, \nKouvee Pet Shop");

                    try {
                        startActivity(smsIntent);
                        finish();
                        updateStatus(no_transaksi.getText().toString());
                        Log.i("SMS Berhasil Dikirim ke Nomor Telepon Pelanggan...", "");
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(activity_detail_penjualan_layanan.this,
                                "Kirim SMS Gagal... Silahkan Cek Kembali Nomor Telepon Pelanggan!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
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

        nomor_telepon = getIntent().getStringExtra("telepon_konsumen");
    }

    private void updateStatus(final String no_transaksi_layanan){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(activity_detail_penjualan_layanan.this, "SMS Berhasil Dikirim ke Nomor Telepon Pengguna!", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity_detail_penjualan_layanan.this, "Koneksi Terputus",
                                Toast.LENGTH_SHORT).show();
                    }
                }){

            //datayangdiinput
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("NO_TRANSAKSI_LAYANAN", no_transaksi_layanan);
                return params;
            }
        };
        stringRequest.setRetryPolicy(
                new DefaultRetryPolicy(
                        50000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
        );
        requestQueue.add(stringRequest);
    }
}