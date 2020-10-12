package com.p3l_f_1_pegawai.Activities.penjualan_produk;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.p3l_f_1_pegawai.R;
import java.util.HashMap;
import java.util.Map;

public class activity_ubah_detail_penjualan_produk extends AppCompatActivity {
    private String URLline = "http://192.168.0.200/CI_Mobile_P3L_1F/index.php/transaksiproduk/detail/";
    private EditText jumlah_produk_pesan;
    private TextView nama_produk;
    private Button simpan_produk, batal_simpan;
    private String data = "-";
    private String message = "-";
    private String id_detail_pengadaan;
    String nama_user, id_produk;
    ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_detail_pengadaan);
        setAtribut();

        simpan_produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String jumlah_produk = jumlah_produk_pesan.getText().toString();
                ubahDetail(jumlah_produk, nama_user, id_produk);
                System.out.println(message);
                progDialog();
            }
        });

        batal_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity_ubah_detail_penjualan_produk.this, "Batal Ubah Detail Pembelian Produk!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(activity_ubah_detail_penjualan_produk.this, activity_ubah_hapus_penjualan_produk.class);
                startActivity(intent);
            }
        });
    }

    private void setAtribut(){
        SharedPreferences mSettings = getSharedPreferences("Login", Context.MODE_PRIVATE);
        nama_user = mSettings.getString("nama_user_login", "user_tidak_terdeteksi");

        nama_produk = (TextView) findViewById(R.id.nama_produk_pengadaan_ubah);
        nama_produk.setText(getIntent().getStringExtra("nama_produk"));
        jumlah_produk_pesan = (EditText) findViewById(R.id.jumlah_produk_pengadaan_ubah);
        jumlah_produk_pesan.setText(getIntent().getStringExtra("jumlah_produk_pesan"));
        simpan_produk = (Button) findViewById(R.id.button_ubah_produk);
        batal_simpan = (Button) findViewById(R.id.button_batal_ubah);
        id_detail_pengadaan = getIntent().getStringExtra("id_detail_penjualan_produk");
        id_produk = getIntent().getStringExtra("id_produk");
        dialog = new ProgressDialog(this);
    }

    private void progDialog() {
        dialog.setMessage("Mengubah Data ...");
        dialog.show();
    }

    private void ubahDetail(final String jumlah_produk, final String nama_user, final String id_produk){
        final String url = URLline + id_detail_pengadaan;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity_ubah_detail_penjualan_produk.this, "Data Detail Pembelian Produk Berhasil Diubah!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(activity_ubah_detail_penjualan_produk.this, activity_ubah_hapus_penjualan_produk.class);
                                startActivity(intent);
                            }
                        }, 2000);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity_ubah_detail_penjualan_produk.this, "Koneksi Terputus",
                                Toast.LENGTH_SHORT).show();
                    }
                }){

            //datayangdiinput
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String,String>();
                params.put("JUMLAH_PRODUK", jumlah_produk);
                params.put("KETERANGAN", nama_user);
                params.put("ID_PRODUK", id_produk);
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