package com.p3l_f_1_pegawai.Activities.jenis_hewan;

import android.app.Activity;
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
import androidx.core.app.NavUtils;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.p3l_f_1_pegawai.R;
import com.p3l_f_1_pegawai.drawer_activity_owner;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class activity_tambah_jenis extends AppCompatActivity {
    private String URLline = "http://192.168.8.102/CI_Mobile_P3L_1F/index.php/jenishewan";
    private TextInputEditText nama_jenis_hewan;
    private Button simpan_jenis, batal_simpan;
    private TextView show_calendar, show_person;
    private String data = "-";
    private String message = "-";
    ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_jenis);
        setAtribut();

        simpan_jenis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nama_jenis, nama_pengguna;
                nama_jenis = nama_jenis_hewan.getText().toString();
                nama_pengguna = show_person.getText().toString();

                formValidation(nama_jenis);
                tambahJenis(nama_jenis, nama_pengguna);
                System.out.println(message);
                progDialog();
                waitingResponse();
            }
        });

        batal_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama_jenis_hewan.getText().clear();
                JenisHewanFragment jenisHewanFragment = new JenisHewanFragment();
                Toast.makeText(activity_tambah_jenis.this, "Batal Tambah Jenis!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(activity_tambah_jenis.this, jenisHewanFragment.getClass());
                startActivity(intent);
//                Intent parentIntent = NavUtils.getParentActivityIntent(activity_tambah_jenis.this);
//                parentIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                startActivity(parentIntent);
//                finish();
            }
        });
    }

    private void setAtribut(){
        nama_jenis_hewan = (TextInputEditText) findViewById(R.id.tambah_jenis_hewan);
        simpan_jenis = (Button) findViewById(R.id.button_simpan);
        batal_simpan = (Button) findViewById(R.id.button_batal);
        show_calendar = (TextView) findViewById(R.id.show_calendar);
        show_person = (TextView) findViewById(R.id.show_person);
        show_person.setText(getIntent().getStringExtra("USERNAME"));
        dialog = new ProgressDialog(this);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        String format = simpleDateFormat.format(new Date());
        show_calendar.setText(format);
    }

    private void formValidation(String nama_jenis) {
        if (TextUtils.isEmpty(nama_jenis)) {
            nama_jenis_hewan.setError("Field Tidak Boleh Kosong!");
            return;
        }
    }

    private void progDialog() {
        dialog.setMessage("Menyimpan Data ...");
        dialog.show();
    }

    private void waitingResponse() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(message.equalsIgnoreCase("Berhasil")) {
                    Toast.makeText(activity_tambah_jenis.this, "Data Jenis Hewan Berhasil Disimpan!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(activity_tambah_jenis.this, JenisHewanFragment.class);
                    startActivity(intent);
                }
                else if(message.equalsIgnoreCase("Gagal")) {
                    Toast.makeText(activity_tambah_jenis.this, "Kesalahan Koneksi", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(activity_tambah_jenis.this, JenisHewanFragment.class);
                    startActivity(intent);
                }
                dialog.dismiss();
            }
        }, 2000);
    }

    private void tambahJenis(final String string_nama, final String nama_pengguna){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
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
                        Toast.makeText(activity_tambah_jenis.this, "Koneksi Terputus",
                                Toast.LENGTH_SHORT).show();
                    }
                }){

            //datayangdiinput
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String,String>();
                params.put("NAMA_JENIS_HEWAN", string_nama);
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
