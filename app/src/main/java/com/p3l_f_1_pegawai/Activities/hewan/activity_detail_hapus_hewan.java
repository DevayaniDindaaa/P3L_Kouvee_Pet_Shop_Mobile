package com.p3l_f_1_pegawai.Activities.hewan;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.p3l_f_1_pegawai.Activities.konsumen.activity_detail_hapus_konsumen;
import com.p3l_f_1_pegawai.Activities.konsumen.activity_ubah_konsumen;
import com.p3l_f_1_pegawai.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class activity_detail_hapus_hewan extends AppCompatActivity {
    private String URLline = "http://192.168.8.103/CI_Mobile_P3L_1F/index.php/hewan/delete/";
    Activity context;
    private TextView nama_konsumen, alamat_konsumen, tgl_lahir_konsumen, nomor_telepon, status_member, status_data, nama_hewan, jenis_hewan, ukuran_hewan, tgl_lahir_hewan;
    String nama_user;
    private Button ubah_hewan, hapus_hewan;
    private String data = "-";
    private String message = "-";
    private String id_hewan = "-";
    private String status_hapus = "-";
    ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_hewan);
        setAtribut();

        ubah_hewan = findViewById(R.id.ubah_hewan);
        hapus_hewan = findViewById(R.id.hapus_hewan);

        ubah_hewan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(nama_user);
                if(nama_user.equalsIgnoreCase("owner")){
                    Toast.makeText(activity_detail_hapus_hewan.this, "Owner tidak bisa mengubah data hewan!", Toast.LENGTH_SHORT).show();
                }
                else if(status_hapus.equalsIgnoreCase("deleted")){
                    Toast.makeText(activity_detail_hapus_hewan.this, "Data ini sudah dihapus, tidak dapat dihapus lagi!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent i = new Intent(activity_detail_hapus_hewan.this, activity_ubah_hewan.class);
                    i.putExtra("id_hewan", id_hewan);
                    i.putExtra("nama_hewan", nama_hewan.getText().toString());
                    i.putExtra("tgl_lahir_hewan", tgl_lahir_hewan.getText().toString());
                    i.putExtra("nama_jenis", jenis_hewan.getText().toString());
                    i.putExtra("nama_ukuran", ukuran_hewan.getText().toString());
                    i.putExtra("nama_konsumen", nama_konsumen.getText().toString());
                    startActivity(i);
                }
            }});

        hapus_hewan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(nama_user);
                if(nama_user.equalsIgnoreCase("owner")){
                    Toast.makeText(activity_detail_hapus_hewan.this, "Owner tidak bisa menghapus data hewan!", Toast.LENGTH_SHORT).show();
                }
                else if(status_hapus.equalsIgnoreCase("deleted")){
                    Toast.makeText(activity_detail_hapus_hewan.this, "Data ini sudah dihapus, tidak dapat dihapus lagi!", Toast.LENGTH_SHORT).show();
                }
                else{
                    AlertDialog.Builder alertDialog2 = new
                            AlertDialog.Builder(activity_detail_hapus_hewan.this);

                    alertDialog2.setTitle("Konfirmasi");
                    alertDialog2.setMessage("Apakah Anda Yakin Ingin Menghapus Data Ini?");

                    alertDialog2.setPositiveButton("Ya",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    hapusDataHewan(nama_user);
                                    System.out.println(message);
                                    progDialog();
                                    waitingResponse();
                                }
                            });

                    alertDialog2.setNegativeButton("Tidak",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(activity_detail_hapus_hewan.this,
                                            "Batal Hapus Data Hewan!", Toast.LENGTH_SHORT)
                                            .show();
                                    dialog.cancel();
                                }
                            });
                    alertDialog2.show();
                }
            }
        });
    }

    private void setAtribut(){
        SharedPreferences mSettings = getSharedPreferences("Login", Context.MODE_PRIVATE);
        nama_user = mSettings.getString("nama_user_login", "user_tidak_terdeteksi");

        nama_hewan = findViewById(R.id.nama_hewan_detail);
        nama_hewan.setText(getIntent().getStringExtra("nama_hewan"));
        jenis_hewan = findViewById(R.id.jenis_hewan_detail);
        jenis_hewan.setText(getIntent().getStringExtra("jenis_hewan"));
        ukuran_hewan = findViewById(R.id.ukuran_hewan_detail);
        ukuran_hewan.setText(getIntent().getStringExtra("ukuran_hewan"));
        tgl_lahir_hewan = findViewById(R.id.tgl_lahir_hewan_detail);
        tgl_lahir_hewan.setText(getIntent().getStringExtra("tgl_lahir_hewan"));
        status_data = findViewById(R.id.log_aktivitas);
        status_data.setText(getIntent().getStringExtra("status_data"));

        nama_konsumen = findViewById(R.id.nama_konsumen_detail);
        nama_konsumen.setText(getIntent().getStringExtra("nama_konsumen"));
        alamat_konsumen = findViewById(R.id.alamat_konsumen_detail);
        alamat_konsumen.setText(getIntent().getStringExtra("alamat_konsumen"));
        tgl_lahir_konsumen = findViewById(R.id.tgl_lahir_konsumen_detail);
        tgl_lahir_konsumen.setText(getIntent().getStringExtra("tgl_lahir_konsumen"));
        nomor_telepon = findViewById(R.id.no_tlp_konsumen_detail);
        nomor_telepon.setText(getIntent().getStringExtra("no_tlp_konsumen"));
        status_member = findViewById(R.id.status_member_konsumen_detail);
        status_member.setText(getIntent().getStringExtra("status_member"));

        id_hewan = getIntent().getStringExtra("id_hewan");
        status_hapus = getIntent().getStringExtra("status_hapus");
        dialog = new ProgressDialog(this);
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
                    Toast.makeText(activity_detail_hapus_hewan.this, "Data Hewan Berhasil Dihapus!", Toast.LENGTH_LONG).show();
                }
                else if(message.equalsIgnoreCase("Gagal")) {
                    Toast.makeText(activity_detail_hapus_hewan.this, "Kesalahan Koneksi", Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
        }, 2000);
    }

    private  void  hapusDataHewan(final String nama_user_login){
        final String url = URLline + id_hewan;
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
                        Toast.makeText(activity_detail_hapus_hewan.this, "Koneksi Terputus",
                                Toast.LENGTH_SHORT).show();
                    }
                }){

            //datayangdiinput
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String,String>();
                params.put("KETERANGAN", nama_user_login);
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