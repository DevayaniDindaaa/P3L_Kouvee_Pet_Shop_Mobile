package com.p3l_f_1_pegawai.Activities.pengadaan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
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
import com.google.android.material.textfield.TextInputEditText;
import com.p3l_f_1_pegawai.Activities.ukuran_hewan.UkuranHewanFragment;
import com.p3l_f_1_pegawai.Activities.ukuran_hewan.activity_ubah_ukuran;
import com.p3l_f_1_pegawai.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class activity_ubah_detail_pengadaan extends AppCompatActivity {
    private String URLline = "http://192.168.8.100/CI_Mobile_P3L_1F/index.php/transaksipengadaan/detail/";
    private EditText jumlah_produk_pesan;
    private TextView nama_produk;
    private Button simpan_produk, batal_simpan;
    private String data = "-";
    private String message = "-";
    private String id_detail_pengadaan;
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
                ubahDetail(jumlah_produk);
                System.out.println(message);
                progDialog();
                waitingResponse();
            }
        });

        batal_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity_ubah_detail_pengadaan.this, "Batal Ubah Detail Pengadaan!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(activity_ubah_detail_pengadaan.this, activity_ubah_pengadaan.class);
                startActivity(intent);
            }
        });
    }

    private void setAtribut(){
        nama_produk = (TextView) findViewById(R.id.nama_produk_pengadaan_ubah);
        nama_produk.setText(getIntent().getStringExtra("nama_produk"));
        jumlah_produk_pesan = (EditText) findViewById(R.id.jumlah_produk_pengadaan_ubah);
        jumlah_produk_pesan.setText(getIntent().getStringExtra("jumlah_produk_pesan"));
        simpan_produk = (Button) findViewById(R.id.button_ubah_produk);
        batal_simpan = (Button) findViewById(R.id.button_batal_ubah);
        id_detail_pengadaan = getIntent().getStringExtra("id_detail_pengadaan");
        dialog = new ProgressDialog(this);
    }

    private void progDialog() {
        dialog.setMessage("Mengubah Data ...");
        dialog.show();
    }

    private void waitingResponse() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(message.equalsIgnoreCase("Berhasil")) {
                    Toast.makeText(activity_ubah_detail_pengadaan.this, "Data Detail Pengadaan Berhasil Diubah!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(activity_ubah_detail_pengadaan.this, activity_ubah_pengadaan.class);
                    startActivity(intent);
                }
                else if(message.equalsIgnoreCase("Gagal")) {
                    Toast.makeText(activity_ubah_detail_pengadaan.this, "Kesalahan Koneksi", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(activity_ubah_detail_pengadaan.this, activity_ubah_pengadaan.class);
                    startActivity(intent);
                }
                dialog.dismiss();
            }
        }, 2000);
    }

    private void ubahDetail(final String jumlah_produk){
        final String url = URLline + id_detail_pengadaan;
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
                        Toast.makeText(activity_ubah_detail_pengadaan.this, "Koneksi Terputus",
                                Toast.LENGTH_SHORT).show();
                    }
                }){

            //datayangdiinput
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String,String>();
                params.put("JUMLAH_PRODUK_DIPESAN", jumlah_produk);
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
