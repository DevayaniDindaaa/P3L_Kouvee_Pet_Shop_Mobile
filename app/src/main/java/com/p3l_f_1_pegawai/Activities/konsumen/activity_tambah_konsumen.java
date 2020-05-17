package com.p3l_f_1_pegawai.Activities.konsumen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.regex.Pattern;

public class activity_tambah_konsumen  extends AppCompatActivity {
    private String URLline = "http://192.168.8.100/CI_Mobile_P3L_1F/index.php/konsumen";
    private TextInputEditText nama_konsumen, alamat_konsumen, tgl_lahir, telepon_konsumen;
    String MobilePattern = "\\+?([ -]?\\d+)+|\\(\\d+\\)([ -]\\d+)";
    String DatePattern = "(\\d{4})-(\\d{1,2})-(\\d{1,2})";
    private Button simpan_konsumen, batal_simpan;
    private TextView show_calendar, show_person;
    private RadioGroup mRadioGroup;
    private RadioButton status_member;
    private String data = "-";
    private String message = "-";
    ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_konsumen);
        setAtribut();

        simpan_konsumen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nama, alamat, tgl_lahir_konsumen, telepon, nama_pengguna, member;
                nama = nama_konsumen.getText().toString();
                alamat = alamat_konsumen.getText().toString();
                tgl_lahir_konsumen = tgl_lahir.getText().toString();
                telepon = telepon_konsumen.getText().toString();
                nama_pengguna = show_person.getText().toString();
                int selectedId = mRadioGroup.getCheckedRadioButtonId();
                status_member = findViewById(selectedId);
                member = status_member.getText().toString();

                formValidation(nama, alamat, tgl_lahir_konsumen, telepon);
                tambahKonsumen(nama, alamat, tgl_lahir_konsumen, telepon, nama_pengguna, member);
                System.out.println(message);
                progDialog();
                waitingResponse();
            }
        });

        batal_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama_konsumen.getText().clear();
                alamat_konsumen.getText().clear();
                tgl_lahir.getText().clear();
                telepon_konsumen.getText().clear();
                Toast.makeText(activity_tambah_konsumen.this, "Batal Tambah Konsumen!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setAtribut(){
        nama_konsumen = (TextInputEditText) findViewById(R.id.tambah_konsumen);
        alamat_konsumen = (TextInputEditText) findViewById(R.id.alamat_konsumen);
        tgl_lahir = (TextInputEditText) findViewById(R.id.tgl_lahir_konsumen);
        telepon_konsumen = (TextInputEditText) findViewById(R.id.telepon_konsumen);
        simpan_konsumen = (Button) findViewById(R.id.button_simpan);
        batal_simpan = (Button) findViewById(R.id.button_batal);
        show_calendar = (TextView) findViewById(R.id.show_calendar);
        show_person = (TextView) findViewById(R.id.show_person);
        show_person.setText(getIntent().getStringExtra("USERNAME"));
        dialog = new ProgressDialog(this);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        String format = simpleDateFormat.format(new Date());
        show_calendar.setText(format);

        mRadioGroup = findViewById(R.id.status_member);
        int selectedId = mRadioGroup.getCheckedRadioButtonId();
        status_member = findViewById(selectedId);
    }

    private void formValidation(String nama, String alamat, String tgl_lahir_konsumen, String telepon) {
        if (TextUtils.isEmpty(nama)) {
            nama_konsumen.setError("Field Tidak Boleh Kosong!");
            return;
        }

        if (TextUtils.isEmpty(alamat)) {
            alamat_konsumen.setError("Field Tidak Boleh Kosong!");
            return;
        }

        if (TextUtils.isEmpty(tgl_lahir_konsumen)) {
            tgl_lahir.setError("Field Tidak Boleh Kosong!");
            return;
        }

        if (!Pattern.matches(DatePattern, tgl_lahir_konsumen)){
            tgl_lahir.setError("Format Tgl Lahir adalah yyyy-mm-dd");
            return;
        }

        if (TextUtils.isEmpty(telepon)) {
            telepon_konsumen.setError("Field Tidak Boleh Kosong!");
            return;
        }

        if (!Pattern.matches(MobilePattern, telepon)){
            telepon_konsumen.setError("Masukkan Nomor Telepon yang Valid!");
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
                    nama_konsumen.getText().clear();
                    alamat_konsumen.getText().clear();
                    tgl_lahir.getText().clear();
                    telepon_konsumen.getText().clear();
                    Toast.makeText(activity_tambah_konsumen.this, "Data Konsumen Berhasil Disimpan!", Toast.LENGTH_LONG).show();
                }
                else if(message.equalsIgnoreCase("Gagal")) {
                    Toast.makeText(activity_tambah_konsumen.this, "Kesalahan Koneksi", Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
        }, 2000);
    }

    private void tambahKonsumen(final String nama, final String alamat, final String tgl_lahir, final String telepon, final String nama_pengguna, final String status_member){
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
                        Toast.makeText(activity_tambah_konsumen.this, "Koneksi Terputus",
                                Toast.LENGTH_SHORT).show();
                    }
                }){

            //datayangdiinput
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String,String>();
                params.put("NAMA_KONSUMEN", nama);
                params.put("ALAMAT_KONSUMEN", alamat);
                params.put("TGL_LAHIR_KONSUMEN", tgl_lahir);
                params.put("NO_TLP_KONSUMEN", telepon);
                params.put("STATUS_MEMBER", status_member);
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
