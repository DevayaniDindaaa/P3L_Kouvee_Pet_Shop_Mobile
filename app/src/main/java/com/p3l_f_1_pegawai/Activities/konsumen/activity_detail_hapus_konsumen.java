package com.p3l_f_1_pegawai.Activities.konsumen;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.p3l_f_1_pegawai.R;
import com.p3l_f_1_pegawai.dao.hewanDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class activity_detail_hapus_konsumen extends AppCompatActivity {
    private String URLhewan = "http://192.168.8.102/CI_Mobile_P3L_1F/index.php/hewan/delete/";
    private String URLkonsumen = "http://192.168.8.102/CI_Mobile_P3L_1F/index.php/konsumen/delete/";
    Activity context;
    private List<hewanDAO> DetailHewanKonsumen;
    private RecyclerView recyclerView;
    private DetailKonsumenAdapter recycleAdapter;
    private TextView nama_konsumen, alamat_konsumen, tgl_lahir_konsumen, nomor_telepon, status_member, status_data;
    String nama_user;
    private Button ubah_konsumen, hapus_konsumen;
    private String data = "-";
    private String message = "-";
    private String id_hewan = "-";
    private String id_konsumen = "-";
    private String status_hapus = "-";
    ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_konsumen);
        setAtribut();

        DetailHewanKonsumen = new ArrayList<>();

        ubah_konsumen = findViewById(R.id.ubah_konsumen);
        hapus_konsumen = findViewById(R.id.hapus_konsumen);
        recyclerView = findViewById(R.id.recycle_tampil_detail_hewankonsumen);
        recycleAdapter = new DetailKonsumenAdapter(DetailHewanKonsumen, this);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recycleAdapter);

        getDetailHewan();

        ubah_konsumen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(nama_user);
                if(nama_user.equalsIgnoreCase("owner")){
                    Toast.makeText(activity_detail_hapus_konsumen.this, "Owner tidak bisa mengubah data konsumen!", Toast.LENGTH_SHORT).show();
                }
                else if(status_hapus.equalsIgnoreCase("deleted")){
                    Toast.makeText(activity_detail_hapus_konsumen.this, "Data ini sudah dihapus, tidak dapat dihapus lagi!", Toast.LENGTH_SHORT).show();
                }
                else{
                    //DISINI INTENT KE HALAMAN UBAH DATA KONSUMEN
                }
            }});

        hapus_konsumen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(nama_user);
                if(nama_user.equalsIgnoreCase("owner")){
                    Toast.makeText(activity_detail_hapus_konsumen.this, "Owner tidak bisa mengubah data konsumen!", Toast.LENGTH_SHORT).show();
                }
                else if(status_hapus.equalsIgnoreCase("deleted")){
                    Toast.makeText(activity_detail_hapus_konsumen.this, "Data ini sudah dihapus, tidak dapat dihapus lagi!", Toast.LENGTH_SHORT).show();
                }
                else{
                    AlertDialog.Builder alertDialog2 = new
                            AlertDialog.Builder(activity_detail_hapus_konsumen.this);

                    alertDialog2.setTitle("Konfirmasi");
                    alertDialog2.setMessage("Apakah Anda Yakin Ingin Menghapus Data Ini? Jika Ya, maka Data Konsumen dan Seluruh Data Hewan Milik Konsumen akan Terhapus");

                    alertDialog2.setPositiveButton("Ya",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    hapusDataKonsumen(nama_user);
                                    System.out.println(message);
                                    progDialog();
                                    waitingResponse();
                                }
                            });

                    alertDialog2.setNegativeButton("Tidak",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(activity_detail_hapus_konsumen.this,
                                            "Batal Hapus Data Konsumen!", Toast.LENGTH_SHORT)
                                            .show();
                                    dialog.cancel();
                                }
                            });
                    alertDialog2.show();
                }
            }
        });

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
                    Toast.makeText(activity_detail_hapus_konsumen.this, "Data Konsumen beserta Hewan Milik Konsumen Berhasil Dihapus!", Toast.LENGTH_LONG).show();
                }
                else if(message.equalsIgnoreCase("Gagal")) {
                    Toast.makeText(activity_detail_hapus_konsumen.this, "Kesalahan Koneksi", Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
        }, 2000);
    }

    private void getDetailHewan() {
        try {
            String detailHewan = getIntent().getStringExtra("details");
            JSONArray detail = new JSONArray(detailHewan);
            for (int j = 0; j < detail.length(); j++) {
                JSONObject objectDetail = detail.getJSONObject(j);
                hewanDAO d = new hewanDAO(objectDetail.getString("id_hewan"),
                        objectDetail.getString("nama_jenis_hewan"),
                        objectDetail.getString("nama_ukuran_hewan"),
                        objectDetail.getString("nama_konsumen"),
                        objectDetail.getString("alamat_konsumen"),
                        objectDetail.getString("tgl_lahir_konsumen"),
                        objectDetail.getString("no_tlp_konsumen"),
                        objectDetail.getString("status_member"),
                        objectDetail.getString("nama_hewan"),
                        objectDetail.getString("tgl_lahir_hewan"),
                        objectDetail.getString("status_data"),
                        objectDetail.getString("time_stamp"),
                        objectDetail.getString("keterangan"));
                DetailHewanKonsumen.add(d);
            }
            recycleAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setAtribut(){
        SharedPreferences mSettings = getSharedPreferences("Login", Context.MODE_PRIVATE);
        nama_user = mSettings.getString("nama_user_login", "user_tidak_terdeteksi");

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
        status_data = findViewById(R.id.log_aktivitas);
        status_data.setText(getIntent().getStringExtra("status_data"));

        id_konsumen = getIntent().getStringExtra("id_konsumen");
        status_hapus = getIntent().getStringExtra("status_hapus");
        dialog = new ProgressDialog(this);
    }

    private  void  hapusDataKonsumen(final String nama_user_login){
        final String url = URLkonsumen + id_konsumen;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            data = jsonObject.getString("message");
                            if(data.equalsIgnoreCase("Berhasil")){
                                for (int i = 0; i<DetailHewanKonsumen.size(); i++)
                                {
                                    hapusDataHewan(nama_user_login, DetailHewanKonsumen.get(i).getId_hewan());
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity_detail_hapus_konsumen.this, "Koneksi Terputus",
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

    private  void  hapusDataHewan(final String nama_user_login, String id_hewan_hapus){
        final String url = URLhewan + id_hewan_hapus;
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
                        Toast.makeText(activity_detail_hapus_konsumen.this, "Koneksi Terputus",
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