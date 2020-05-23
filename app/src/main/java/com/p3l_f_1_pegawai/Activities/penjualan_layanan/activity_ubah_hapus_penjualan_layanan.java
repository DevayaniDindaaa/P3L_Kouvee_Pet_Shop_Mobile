package com.p3l_f_1_pegawai.Activities.penjualan_layanan;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
import com.p3l_f_1_pegawai.dao.detailLayanan_penjualanDAO;
import com.p3l_f_1_pegawai.dao.detail_penjualan_layananDAO;
import com.p3l_f_1_pegawai.dao.layananDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class activity_ubah_hapus_penjualan_layanan extends AppCompatActivity {
    Activity context;
    private List<detail_penjualan_layananDAO> DetailPenjualanLayananList;
    private RecyclerView recyclerView, tambahLayanan;
    private DetailUbahPenjualanLayananAdapter recycleAdapter;
    private TextView no_transaksi, tgl_transaksi, nama_konsumen, status_member, nama_hewan, jenis_hewan, ukuran_hewan, nama_cs, nama_kasir, sub_total, diskon, total_bayar, status_bayar, status_kerja;
    String nama_user, nama_jenis_hewan, id_layanan, nama_ukuran_hewan;
    private Spinner spinner_layanan;
    private Button button_ubah, button_batal, tambah_layanan;
    private List<layananDAO> layanans = new ArrayList<>();
    private ArrayList<String> tempLayanan = new ArrayList<>();
    private ArrayList<detailLayanan_penjualanDAO> arrayList = new ArrayList<>();
    private DetailTambahPenjualanLayananAdapter detailTambahPenjualanLayananAdapter;
    ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_hapus_penjualan_layanan);
        setAtribut();

        DetailPenjualanLayananList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycle_tampil_detail_penjualan_layanan);
        recycleAdapter = new DetailUbahPenjualanLayananAdapter(DetailPenjualanLayananList, this);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recycleAdapter);

        tambahLayanan = findViewById(R.id.recycle_tambah_jual_layanan);
        detailTambahPenjualanLayananAdapter = new DetailTambahPenjualanLayananAdapter(arrayList, getApplicationContext());
        tambahLayanan.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        tambahLayanan.setItemAnimator(new DefaultItemAnimator());
        tambahLayanan.setAdapter(detailTambahPenjualanLayananAdapter);

        getDetailPenjualanLayanan();

        button_ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity_ubah_hapus_penjualan_layanan.this, "Berhasil Ubah Detail Penjualan Layanan!", Toast.LENGTH_SHORT).show();
            }
        });

        tambah_layanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambah_layanan();
            }
        });
    }

    private void getDetailPenjualanLayanan() {
        try {
            String detailJualLayanan = getIntent().getStringExtra("details");
            JSONArray detail = new JSONArray(detailJualLayanan);
            for (int j = 0; j < detail.length(); j++) {
                JSONObject objectDetail = detail.getJSONObject(j);
                detail_penjualan_layananDAO d = new detail_penjualan_layananDAO(objectDetail.getString("id_detail_trans_layanan"),
                        objectDetail.getString("id_layanan"),
                        objectDetail.getString("nama_layanan"),
                        objectDetail.getString("nama_jenis_hewan"),
                        objectDetail.getString("nama_ukuran_hewan"),
                        objectDetail.getString("status_data"),
                        objectDetail.getString("time_stamp"),
                        objectDetail.getString("keterangan"),
                        objectDetail.getInt("harga_satuan_layanan"),
                        objectDetail.getInt("jumlah_layanan"),
                        objectDetail.getInt("jumlah_harga_layanan"));
                DetailPenjualanLayananList.add(d);
            }
            recycleAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setAtribut() {
        SharedPreferences mSettings = getSharedPreferences("Login", Context.MODE_PRIVATE);
        nama_user = mSettings.getString("nama_user_login", "user_tidak_terdeteksi");

        no_transaksi = findViewById(R.id.nomor_transaksi);
        no_transaksi.setText(getIntent().getStringExtra("no_transaksi"));
        tgl_transaksi = findViewById(R.id.tanggal_transaksi);
        tgl_transaksi.setText(getIntent().getStringExtra("tgl_transaksi"));
        nama_konsumen = findViewById(R.id.nama_konsumen_layanan);
        nama_konsumen.setText(getIntent().getStringExtra("nama_konsumen"));
        nama_hewan = findViewById(R.id.nama_hewan_layanan);
        nama_hewan.setText(getIntent().getStringExtra("nama_hewan"));

        nama_jenis_hewan = getIntent().getStringExtra("nama_jenis_hewan");
        nama_ukuran_hewan = getIntent().getStringExtra("ukuran_hewan");

        button_ubah = findViewById(R.id.button_simpan);
        button_batal = findViewById(R.id.button_batal);
        tambah_layanan = (Button) findViewById(R.id.button_tambah_layanan);
    }

    public void setSpinner_layanan() {
        String url = "http://192.168.8.101/CI_Mobile_P3L_1F/index.php/layanan";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String produk = jsonObject.getString("message");
                            JSONArray jsonArray = new JSONArray(produk);

                            layanans.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject objectReview = jsonArray.getJSONObject(i);
                                layananDAO r = new layananDAO(objectReview.getString("id_layanan"),
                                        objectReview.getString("nama_ukuran_hewan"),
                                        objectReview.getString("nama_jenis_hewan"),
                                        objectReview.getString("nama_layanan"),
                                        objectReview.getInt("harga_satuan_layanan"),
                                        objectReview.getString("status_data"),
                                        objectReview.getString("time_stamp"),
                                        objectReview.getString("keterangan"));

                                if (objectReview.getString("status_data").compareToIgnoreCase("deleted") != 0 && objectReview.getString("nama_jenis_hewan").compareToIgnoreCase(nama_jenis_hewan) == 0
                                        && objectReview.getString("nama_ukuran_hewan").compareToIgnoreCase(nama_ukuran_hewan) == 0) {
                                    layanans.add(r);
                                }
                            }

                            tempLayanan.clear();
                            for (int i = 0; i < layanans.size(); i++) {
                                tempLayanan.add(layanans.get(i).getNama_layanan() + " " + layanans.get(i).getNama_jenis_hewan() + " " + layanans.get(i).getNama_ukuran_hewan());
                            }
                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, tempLayanan);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_layanan.setAdapter(spinnerArrayAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Kesalahan Koneksi!", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(getRequest);
    }

    private void tambah_layanan() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(activity_ubah_hapus_penjualan_layanan.this).create();
        LayoutInflater inflater = LayoutInflater.from(activity_ubah_hapus_penjualan_layanan.this);
        View dialogView = inflater.inflate(R.layout.dialog_tambah_penjualan_layanan, null);

        spinner_layanan = (Spinner) dialogView.findViewById(R.id.spinner_layanan_pesan);
        setSpinner_layanan();
        spinner_layanan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_layanan = parent.getSelectedItem().toString();
                for (int count = 0; count < layanans.size(); count++) {
                    if (id_layanan.equalsIgnoreCase(layanans.get(count).getNama_layanan() + " " + layanans.get(count).getNama_jenis_hewan() + " " + layanans.get(count).getNama_ukuran_hewan())) {
                        id_layanan = layanans.get(count).getId_layanan();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button simpan = (Button) dialogView.findViewById(R.id.button_simpan_layanan);
        Button batal = (Button) dialogView.findViewById(R.id.button_batal_tambah);

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
            }
        });
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String namaLayanan = spinner_layanan.getSelectedItem().toString();

                final int jumlah = 1;

                detailLayanan_penjualanDAO detailLayanan = new detailLayanan_penjualanDAO(id_layanan, namaLayanan, jumlah);
                arrayList.add(detailLayanan);
                detailTambahPenjualanLayananAdapter.notifyDataSetChanged();
                dialogBuilder.dismiss();
                layanans.clear();
                tempLayanan.clear();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    private void progDialog() {
        dialog.setMessage("Menyimpan Data ...");
        dialog.show();
    }

//    private void waitingResponse() {
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if(pesanku.equalsIgnoreCase("Berhasil")) {
//                    Toast.makeText(activity_tambah_penjualan_produk.this, "Data Penjualan Produk Berhasil Disimpan!", Toast.LENGTH_LONG).show();
//                }
//                else if(pesanku.equalsIgnoreCase("Gagal")) {
//                    Toast.makeText(activity_tambah_penjualan_produk.this, "Kesalahan Koneksi", Toast.LENGTH_LONG).show();
//                }
//                dialog.dismiss();
//            }
//        }, 2000);
//    }

//    private void tambahPenjualanLayanan(final String id_cs, final String id_hewan){
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//
//                            pesan = jsonObject.getString("message");
//
//                            System.out.println(pesan);
//                            for (int i = 0; i<arrayList.size(); i++)
//                            {
//                                tambahDetailLayanan(pesan, arrayList.get(i).getId_layanan_tambah(), arrayList.get(i).getJumlah_layanan(), nama_cs);
//                            }
//
//                            updateTotalHarga(pesan);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(activity_tambah_penjualan_layanan.this, "Koneksi Terputus",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                }){
//
//            //datayangdiinput
//            @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params = new HashMap<>();
//                params.put("ID_CUSTOMER_SERVICE", id_cs);
//                params.put("ID_HEWAN", id_hewan);
//                return params;
//            }
//        };
//        stringRequest.setRetryPolicy(
//                new DefaultRetryPolicy(
//                        500000,
//                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
//                )
//        );
//        requestQueue.add(stringRequest);
//    }
//
//    private void tambahDetailLayanan(final String no_transaksi_layanan, final String id_layanan, final Integer jumlah_layanan, final String keterangan){
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Toast.makeText(activity_tambah_penjualan_layanan.this, "Detail Penjualan Layanan Berhasil Disimpan!", Toast.LENGTH_LONG).show();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(activity_tambah_penjualan_layanan.this, "Koneksi Terputus",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                }){
//
//            //datayangdiinput
//            @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params = new HashMap<>();
//                params.put("NO_TRANSAKSI_LAYANAN", no_transaksi_layanan);
//                params.put("ID_LAYANAN", id_layanan);
//                params.put("JUMLAH_LAYANAN", String.valueOf(jumlah_layanan));
//                params.put("KETERANGAN", keterangan);
//                return params;
//            }
//        };
//        stringRequest.setRetryPolicy(
//                new DefaultRetryPolicy(
//                        50000,
//                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
//                )
//        );
//        requestQueue.add(stringRequest);
//    }
//
//    private void updateTotalHarga(final String no_transaksi_layanan){
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLHarga,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(activity_tambah_penjualan_layanan.this, "Data Penjualan Layanan Berhasil Disimpan!", Toast.LENGTH_LONG).show();
//
//                                dialog.dismiss();
//                                arrayList.clear();
//                                detailTambahPenjualanLayananAdapter.notifyDataSetChanged();
//                            }
//                        }, 2000);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(activity_tambah_penjualan_layanan.this, "Koneksi Terputus",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                }){
//
//            //datayangdiinput
//            @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params = new HashMap<>();
//                params.put("NO_TRANSAKSI_LAYANAN", no_transaksi_layanan);
//                return params;
//            }
//        };
//        stringRequest.setRetryPolicy(
//                new DefaultRetryPolicy(
//                        50000,
//                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
//                )
//        );
//        requestQueue.add(stringRequest);
//    }
}