package com.p3l_f_1_pegawai.Activities.supplier;

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
import java.util.regex.Pattern;

public class activity_ubah_supplier extends AppCompatActivity {
    private String URLline = "http://192.168.8.102/CI_Mobile_P3L_1F/index.php/supplier/";
    private Button ubah_supplier, batal_ubah;
    private TextView show_calendar, show_person;
    private TextInputEditText  nama_supplier, alamat_supplier, kota_supplier, telepon_supplier;
    String MobilePattern = "\\+?([ -]?\\d+)+|\\(\\d+\\)([ -]\\d+)";
    private String data = "-";
    private String message = "-";
    private String id_supplier = "-";
    ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_supplier);
        setAtribut();

        ubah_supplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nama, alamat, kota, telepon, nama_pengguna;
                nama = nama_supplier.getText().toString();
                alamat = alamat_supplier.getText().toString();
                kota = kota_supplier.getText().toString();
                telepon = telepon_supplier.getText().toString();
                nama_pengguna = show_person.getText().toString();

                formValidation(nama, alamat, kota, telepon);
                ubahSupplier(nama, alamat, kota, telepon, nama_pengguna);
                System.out.println(message);
                progDialog();
                waitingResponse();
            }
        });

        batal_ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity_ubah_supplier.this, "Batal Ubah Supplier!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(activity_ubah_supplier.this, SupplierFragment.class);
                startActivity(intent);
            }
        });
    }

    private void setAtribut(){
        nama_supplier = (TextInputEditText) findViewById(R.id.nama_supplier_ubah);
        nama_supplier.setText(getIntent().getStringExtra("nama_supplier"));
        alamat_supplier = (TextInputEditText) findViewById(R.id.alamat_supplier_ubah);
        alamat_supplier.setText(getIntent().getStringExtra("alamat_supplier"));
        kota_supplier = (TextInputEditText) findViewById(R.id.kota_supplier_ubah);
        kota_supplier.setText(getIntent().getStringExtra("kota_supplier"));
        telepon_supplier = (TextInputEditText) findViewById(R.id.telepon_supplier_ubah);
        telepon_supplier.setText(getIntent().getStringExtra("no_tlp_supplier"));
        ubah_supplier = (Button) findViewById(R.id.button_simpan);
        batal_ubah = (Button) findViewById(R.id.button_batal);
        show_calendar = (TextView) findViewById(R.id.show_calendar);
        show_person = (TextView) findViewById(R.id.show_person);
        show_person.setText(getIntent().getStringExtra("keterangan"));
        id_supplier = getIntent().getStringExtra("id_supplier");
        dialog = new ProgressDialog(this);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        String format = simpleDateFormat.format(new Date());
        show_calendar.setText(format);
    }

    private void formValidation(String nama, String alamat, String kota, String telepon) {
        if (TextUtils.isEmpty(nama)) {
            nama_supplier.setError("Field Tidak Boleh Kosong!");
            return;
        }

        if (TextUtils.isEmpty(alamat)) {
            alamat_supplier.setError("Field Tidak Boleh Kosong!");
            return;
        }

        if (TextUtils.isEmpty(kota)) {
            kota_supplier.setError("Field Tidak Boleh Kosong!");
            return;
        }

        if (TextUtils.isEmpty(telepon)) {
            telepon_supplier.setError("Field Tidak Boleh Kosong!");
            return;
        }

        if (!Pattern.matches(MobilePattern, telepon)){
            telepon_supplier.setError("Masukkan Nomor Telepon yang Valid!");
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
                    Toast.makeText(activity_ubah_supplier.this, "Data Supplier Berhasil Diubah!", Toast.LENGTH_LONG).show();
                }
                else if(message.equalsIgnoreCase("Gagal")) {
                    Toast.makeText(activity_ubah_supplier.this, "Kesalahan Koneksi", Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
        }, 2000);
    }

    private void ubahSupplier(final String nama, final String alamat, final String kota, final String telepon, final String nama_pengguna){
        final String url = URLline + id_supplier;
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
                        Toast.makeText(activity_ubah_supplier.this, "Koneksi Terputus",
                                Toast.LENGTH_SHORT).show();
                    }
                }){

            //datayangdiinput
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String,String>();
                params.put("NAMA_SUPPLIER", nama);
                params.put("ALAMAT_SUPPLIER", alamat);
                params.put("KOTA_SUPPLIER", kota);
                params.put("NO_TLP_SUPPLIER", telepon);
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
