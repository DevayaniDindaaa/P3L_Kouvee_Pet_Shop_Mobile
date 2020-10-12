package com.p3l_f_1_pegawai.Activities.produk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.bumptech.glide.Glide;
import com.p3l_f_1_pegawai.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class activity_hapus_produk extends AppCompatActivity {
    private String URLline = "http://192.168.0.200/CI_Mobile_P3L_1F/index.php/produk/delete/";
    private Button hapus_produk, batal_hapus;
    private TextView show_calendar, show_person, nama_produk, harga_produk, jenis_hewan_produk, stok_produk, stok_minimal_produk;
    private ImageView foto_produk;
    private String data = "-";
    private String message = "-";
    private String id_produk = "-";
    ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hapus_produk);
        setAtribut();

        hapus_produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nama_pengguna;
                nama_pengguna = show_person.getText().toString();
                hapusProduk(nama_pengguna);
                System.out.println(message);
                progDialog();
                waitingResponse();
            }
        });

        batal_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity_hapus_produk.this, "Batal Hapus Produk!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(activity_hapus_produk.this, ProdukFragment.class);
                startActivity(intent);
            }
        });
    }

    private void setAtribut(){
        foto_produk = (ImageView) findViewById(R.id.foto_produk_hapus);
        Glide.with(getApplicationContext()).load(getIntent().getStringExtra("foto_produk")).into(foto_produk);
        nama_produk = (TextView) findViewById(R.id.nama_produk_hapus);
        nama_produk.setText(getIntent().getStringExtra("nama_produk"));
        jenis_hewan_produk = (TextView) findViewById(R.id.jenis_hewan_hapus);
        jenis_hewan_produk.setText(getIntent().getStringExtra("jenis_hewan"));
        harga_produk = (TextView) findViewById(R.id.harga_produk_hapus);
        harga_produk.setText(getIntent().getStringExtra("harga_produk"));
        stok_produk = (TextView) findViewById(R.id.stok_produk_hapus);
        stok_produk.setText(getIntent().getStringExtra("stok_produk"));
        stok_minimal_produk = (TextView) findViewById(R.id.stok_minimal_hapus);
        stok_minimal_produk.setText(getIntent().getStringExtra("stok_minimal_produk"));
        hapus_produk = (Button) findViewById(R.id.button_simpan);
        batal_hapus = (Button) findViewById(R.id.button_batal);
        show_calendar = (TextView) findViewById(R.id.show_calendar);
        show_person = (TextView) findViewById(R.id.show_person);
        show_person.setText(getIntent().getStringExtra("keterangan"));
        id_produk = getIntent().getStringExtra("id_produk");
        dialog = new ProgressDialog(this);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        String format = simpleDateFormat.format(new Date());
        show_calendar.setText(format);
    }

    private void progDialog() {
        dialog.setMessage("Menghapus Data ...");
        dialog.show();
    }

    private void waitingResponse() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(message.equalsIgnoreCase("Berhasil")) {
                    Toast.makeText(activity_hapus_produk.this, "Data Produk Berhasil Dihapus!", Toast.LENGTH_LONG).show();
                }
                else if(message.equalsIgnoreCase("Gagal")) {
                    Toast.makeText(activity_hapus_produk.this, "Kesalahan Koneksi", Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
        }, 2000);
    }

    private void hapusProduk(final String nama_pengguna){
        final String url = URLline + id_produk;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            message = jsonObject.getString("message");
                            data = jsonObject.getString("data");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity_hapus_produk.this, "Koneksi Terputus",
                                Toast.LENGTH_SHORT).show();
                    }
                }){

            //datayangdiinput
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String,String>();
                params.put("KETERANGAN", nama_pengguna);
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
