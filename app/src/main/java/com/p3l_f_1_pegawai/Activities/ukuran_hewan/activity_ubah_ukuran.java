package com.p3l_f_1_pegawai.Activities.ukuran_hewan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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
import com.p3l_f_1_pegawai.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class activity_ubah_ukuran extends AppCompatActivity {
    private String URLline = "http://192.168.8.102/CI_Mobile_P3L_1F/index.php/ukuranhewan/";
    private TextInputEditText nama_ukuran_hewan;
    private Button simpan_ukuran, batal_simpan;
    private TextView show_calendar, show_person;
    private String data = "-";
    private String message = "-";
    private String id_ukuran_hewan = "-";
    ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_ukuran);
        setAtribut();

        simpan_ukuran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nama_ukuran, nama_pengguna;
                nama_ukuran = nama_ukuran_hewan.getText().toString();
                nama_pengguna = show_person.getText().toString();

                formValidation(nama_ukuran);
                ubahUkuran(nama_ukuran, nama_pengguna);
                System.out.println(message);
                progDialog();
                waitingResponse();
            }
        });

        batal_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama_ukuran_hewan.getText().clear();
                Toast.makeText(activity_ubah_ukuran.this, "Batal Ubah Ukuran!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(activity_ubah_ukuran.this, UkuranHewanFragment.class);
                startActivity(intent);
            }
        });
    }

    private void setAtribut(){
        nama_ukuran_hewan = (TextInputEditText) findViewById(R.id.tambah_ukuran_hewan);
        nama_ukuran_hewan.setText(getIntent().getStringExtra("nama_ukuran_hewan"));
        simpan_ukuran = (Button) findViewById(R.id.button_simpan);
        batal_simpan = (Button) findViewById(R.id.button_batal);
        show_calendar = (TextView) findViewById(R.id.show_calendar);
        show_person = (TextView) findViewById(R.id.show_person);
        show_person.setText(getIntent().getStringExtra("keterangan"));
        id_ukuran_hewan = getIntent().getStringExtra("id_ukuran_hewan");
        dialog = new ProgressDialog(this);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        String format = simpleDateFormat.format(new Date());
        show_calendar.setText(format);
    }

    private void formValidation(String nama_ukuran) {
        if (TextUtils.isEmpty(nama_ukuran)) {
            nama_ukuran_hewan.setError("Field Tidak Boleh Kosong!");
            return;
        }
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
                    Toast.makeText(activity_ubah_ukuran.this, "Data Ukuran Hewan Berhasil Dihapus!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(activity_ubah_ukuran.this, UkuranHewanFragment.class);
                    startActivity(intent);
                }
                else if(message.equalsIgnoreCase("Gagal")) {
                    Toast.makeText(activity_ubah_ukuran.this, "Kesalahan Koneksi", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(activity_ubah_ukuran.this, UkuranHewanFragment.class);
                    startActivity(intent);
                }
                dialog.dismiss();
            }
        }, 2000);
    }

    private void ubahUkuran(final String string_nama, final String nama_pengguna){
        final String url = URLline + id_ukuran_hewan;
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
                        Toast.makeText(activity_ubah_ukuran.this, "Koneksi Terputus",
                                Toast.LENGTH_SHORT).show();
                    }
                }){

            //datayangdiinput
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String,String>();
                params.put("NAMA_UKURAN_HEWAN", string_nama);
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
