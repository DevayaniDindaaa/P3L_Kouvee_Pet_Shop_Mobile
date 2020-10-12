package com.p3l_f_1_pegawai.Activities.pengadaan;

import android.app.Activity;
import android.content.Intent;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.p3l_f_1_pegawai.R;
import com.p3l_f_1_pegawai.dao.detail_pengadaanDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class activity_detail_pengadaan extends AppCompatActivity {
    private String URLline = "http://192.168.0.200/CI_Mobile_P3L_1F/index.php/tambahproduk/ubahstatus";
    private String URL = "http://192.168.0.200/CI_Mobile_P3L_1F/index.php/tambahproduk/addstok";
    Activity context;
    private List<detail_pengadaanDAO> DetailPengadaanList;
    private RecyclerView recyclerView;
    private DetailPengadaanAdapter recycleAdapter;
    private TextView nomor_pemesanan, tanggal_pesan, tanggal_cetak, nama_supplier, alamat_supplier, telepon_supplier;
    private String tanggal_cetak_surat, status_kedatangan;
    private Button ubah_detail, terima_produk;
    String pesan, message, data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pengadaan);
        setAtribut();

        DetailPengadaanList = new ArrayList<>();

        ubah_detail = findViewById(R.id.ubah_detail_pengadaan);
        terima_produk = findViewById(R.id.button_terima_stok);
        recyclerView = findViewById(R.id.recycle_tampil_detail_pengadaan);
        recycleAdapter = new DetailPengadaanAdapter(DetailPengadaanList, this);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recycleAdapter);

        getDetailPengadaan();

        ubah_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tanggal_cetak_surat.equalsIgnoreCase("null")) {
                    Intent i = new Intent(activity_detail_pengadaan.this, activity_ubah_pengadaan.class);
                    i.putExtra("nomor_pemesanan", nomor_pemesanan.getText().toString());
                    i.putExtra("tanggal_pemesanan", tanggal_pesan.getText().toString());
                    i.putExtra("tanggal_cetak", tanggal_cetak_surat);
                    i.putExtra("nama_supplier", nama_supplier.getText().toString());
                    i.putExtra("id_supplier", getIntent().getStringExtra("id_supplier"));
                    i.putExtra("details", getIntent().getStringExtra("details"));
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Tidak dapat Ubah Detail, Surat Pemesanan Sudah Dicetak!", Toast.LENGTH_LONG).show();
                }

            }});

        terima_produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tanggal_cetak_surat.equalsIgnoreCase("null")){
                    Toast.makeText(getApplicationContext(), "Pengadaan Produk Belum Selesai Diproses!", Toast.LENGTH_LONG).show();
                }
                else{
                    if(status_kedatangan.equalsIgnoreCase("belum datang")){
                        terimaPengadaan(nomor_pemesanan.getText().toString());
                        for (int i = 0; i<DetailPengadaanList.size(); i++)
                        {
                            String id_detail = DetailPengadaanList.get(i).getId_detail_pengadaan();
                            System.out.println(id_detail);
                            tambahDetailPengadaan(DetailPengadaanList.get(i).getId_detail_pengadaan(), DetailPengadaanList.get(i).getId_produk(), DetailPengadaanList.get(i).getJumlah_produk_dipesan());
                        }
                        Toast.makeText(getApplicationContext(), "Stok Produk Berhasil Diperbarui!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Produk dari Pengadaan ini, Sudah Diterima!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    private void getDetailPengadaan() {
        try {
            String detailPengadaan = getIntent().getStringExtra("details");
            JSONArray detail = new JSONArray(detailPengadaan);
            for (int j = 0; j < detail.length(); j++) {
                JSONObject objectDetail = detail.getJSONObject(j);
                detail_pengadaanDAO d = new detail_pengadaanDAO(objectDetail.getString("id_detail_pengadaan"),
                        objectDetail.getString("id_produk"),
                        objectDetail.getString("nama_produk"),
                        objectDetail.getString("satuan_produk"),
                        objectDetail.getString("status_data"),
                        objectDetail.getString("time_stamp"),
                        objectDetail.getString("keterangan"),
                        objectDetail.getInt("jumlah_produk_dipesan"));
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
        tanggal_cetak_surat = getIntent().getStringExtra("tanggal_cetak");
        if(tanggal_cetak_surat.equalsIgnoreCase("null")){
            tanggal_cetak.setText("-");
        }
        else{
            tanggal_cetak.setText(tanggal_cetak_surat);
        }
        status_kedatangan = getIntent().getStringExtra("status_kedatangan");
        nama_supplier = findViewById(R.id.nama_supplier_detail);
        nama_supplier.setText(getIntent().getStringExtra("nama_supplier"));
        alamat_supplier = findViewById(R.id.alamat_supplier_detail);
        alamat_supplier.setText(getIntent().getStringExtra("alamat_supplier"));
        telepon_supplier = findViewById(R.id.telepon_supplier_detail);
        telepon_supplier.setText(getIntent().getStringExtra("telepon_supplier"));
    }

    private void terimaPengadaan(final String nomor_pemesanan){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            pesan = jsonObject.getString("message");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity_detail_pengadaan.this, "Koneksi Terputus",
                                Toast.LENGTH_SHORT).show();
                    }
                }){

            //datayangdiinput
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("NOMOR_PEMESANAN", nomor_pemesanan);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void tambahDetailPengadaan(final String id_detail, final String id_produk, final Integer jumlah_produk_dipesan){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);

                            message = json.getString("message");
                            data = json.getString("data");
                            System.out.println(message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity_detail_pengadaan.this, "Koneksi Terputus",
                                Toast.LENGTH_SHORT).show();
                    }
                }){

            //datayangdiinput
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("ID_DETAIL_PENGADAAN", id_detail);
                params.put("ID_PRODUK", id_produk);
                params.put("JUMLAH_PRODUK_DIPESAN", String.valueOf(jumlah_produk_dipesan));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
