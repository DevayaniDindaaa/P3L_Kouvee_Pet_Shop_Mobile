package com.p3l_f_1_pegawai.Activities.jenis_hewan;

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
import com.p3l_f_1_pegawai.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class activity_hapus_jenis extends AppCompatActivity {
    private String URLline = "http://192.168.8.102/CI_Mobile_P3L_1F/index.php/jenishewan/delete/";
    private Button hapus_jenis, batal_hapus;
    private TextView show_calendar, show_person, nama_jenis_hewan;
    private String data = "-";
    private String message = "-";
    private String id_jenis_hewan = "-";
    ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hapus_jenis);
        setAtribut();

        hapus_jenis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nama_pengguna;
                nama_pengguna = show_person.getText().toString();
                hapusJenis(nama_pengguna);
                System.out.println(message);
                progDialog();
                waitingResponse();
            }
        });

        batal_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity_hapus_jenis.this, "Batal Hapus Jenis!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(activity_hapus_jenis.this, JenisHewanFragment.class);
                startActivity(intent);
            }
        });
    }

    private void setAtribut(){
        nama_jenis_hewan = (TextView) findViewById(R.id.jenis_hewan);
        nama_jenis_hewan.setText(getIntent().getStringExtra("nama_jenis_hewan"));
        hapus_jenis = (Button) findViewById(R.id.button_simpan);
        batal_hapus = (Button) findViewById(R.id.button_batal);
        show_calendar = (TextView) findViewById(R.id.show_calendar);
        show_person = (TextView) findViewById(R.id.show_person);
        show_person.setText(getIntent().getStringExtra("keterangan"));
        id_jenis_hewan = getIntent().getStringExtra("id_jenis_hewan");
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
                    Toast.makeText(activity_hapus_jenis.this, "Data Jenis Hewan Berhasil Dihapus!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(activity_hapus_jenis.this, JenisHewanFragment.class);
                    startActivity(intent);
                }
                else if(message.equalsIgnoreCase("Gagal")) {
                    Toast.makeText(activity_hapus_jenis.this, "Kesalahan Koneksi", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(activity_hapus_jenis.this, JenisHewanFragment.class);
                    startActivity(intent);
                }
                dialog.dismiss();
            }
        }, 2000);
    }

    private void hapusJenis(final String nama_pengguna){
        final String url = URLline + id_jenis_hewan;
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
                        Toast.makeText(activity_hapus_jenis.this, "Koneksi Terputus",
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
