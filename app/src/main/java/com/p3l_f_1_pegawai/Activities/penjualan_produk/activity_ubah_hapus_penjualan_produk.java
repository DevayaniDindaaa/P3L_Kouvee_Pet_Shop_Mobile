package com.p3l_f_1_pegawai.Activities.penjualan_produk;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.google.android.material.textfield.TextInputEditText;
import com.p3l_f_1_pegawai.Activities.penjualan_layanan.DetailUbahPenjualanLayananAdapter;
import com.p3l_f_1_pegawai.Activities.penjualan_layanan.activity_ubah_hapus_penjualan_layanan;
import com.p3l_f_1_pegawai.R;
import com.p3l_f_1_pegawai.dao.detailProduk_penjualanDAO;
import com.p3l_f_1_pegawai.dao.detail_penjualan_layananDAO;
import com.p3l_f_1_pegawai.dao.detail_penjualan_produkDAO;
import com.p3l_f_1_pegawai.dao.produkDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class activity_ubah_hapus_penjualan_produk extends AppCompatActivity {
    Activity context;
    private List<detail_penjualan_produkDAO> DetailPenjualanProdukList;
    private RecyclerView recyclerView, recycleTambah;
    private DetailUbahPenjualanProdukAdapter recycleAdapter;
    private TextView no_transaksi, tgl_transaksi, nama_konsumen, status_member, nama_hewan, jenis_hewan, nama_cs, nama_kasir, sub_total, diskon, total_bayar, status_bayar;
    String nama_user, id_produk, nama_jenis_hewan;
    private Spinner spinner_produk;
    private Button button_ubah, button_batal, tambah_produk;
    private List<produkDAO> produks = new ArrayList<>();
    private ArrayList<String> tempProduk = new ArrayList<>();
    private ArrayList<detailProduk_penjualanDAO> arrayList = new ArrayList<>();
    private DetailTambahPenjualanProdukAdapter detailPenjualanProdukAdapter;
    ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_hapus_penjualan_produk);
        setAtribut();

        DetailPenjualanProdukList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycle_tampil_detail_penjualan_produk);
        recycleAdapter = new DetailUbahPenjualanProdukAdapter(DetailPenjualanProdukList, this);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recycleAdapter);

        recycleTambah = findViewById(R.id.recycle_tambah_jual_produk);
        detailPenjualanProdukAdapter = new DetailTambahPenjualanProdukAdapter(arrayList, getApplicationContext());
        recycleTambah.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        recycleTambah.setItemAnimator(new DefaultItemAnimator());
        recycleTambah.setAdapter(detailPenjualanProdukAdapter);

        getDetailPenjualanProduk();

        button_ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity_ubah_hapus_penjualan_produk.this, "Berhasil Ubah Detail Penjualan Layanan!", Toast.LENGTH_SHORT).show();
            }});

        tambah_produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambah_produk();
            }
        });
    }

    private void getDetailPenjualanProduk() {
        try {
            String detailJualProduk = getIntent().getStringExtra("details");
            JSONArray detail = new JSONArray(detailJualProduk);
            for (int j = 0; j < detail.length(); j++) {
                JSONObject objectDetail = detail.getJSONObject(j);
                detail_penjualan_produkDAO d = new detail_penjualan_produkDAO(objectDetail.getString("id_detail_trans_produk"),
                        objectDetail.getString("id_produk"),
                        objectDetail.getString("nama_produk"),
                        objectDetail.getString("nama_jenis_hewan"),
                        objectDetail.getString("satuan_produk"),
                        objectDetail.getString("status_data"),
                        objectDetail.getString("time_stamp"),
                        objectDetail.getString("keterangan"),
                        objectDetail.getInt("harga_satuan_produk"),
                        objectDetail.getInt("jumlah_produk"),
                        objectDetail.getInt("jumlah_harga_produk"));
                DetailPenjualanProdukList.add(d);
            }
            recycleAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setAtribut(){
        SharedPreferences mSettings = getSharedPreferences("Login", Context.MODE_PRIVATE);
        nama_user = mSettings.getString("nama_user_login", "user_tidak_terdeteksi");

        no_transaksi = findViewById(R.id.nomor_transaksi);
        no_transaksi.setText(getIntent().getStringExtra("no_transaksi"));
        tgl_transaksi = findViewById(R.id.tanggal_transaksi);
        tgl_transaksi.setText(getIntent().getStringExtra("tgl_transaksi"));
        nama_konsumen = findViewById(R.id.nama_konsumen_produk);
        nama_konsumen.setText(getIntent().getStringExtra("nama_konsumen"));
        nama_hewan = findViewById(R.id.nama_hewan_produk);
        nama_hewan.setText(getIntent().getStringExtra("nama_hewan"));

        nama_jenis_hewan = getIntent().getStringExtra("nama_jenis_hewan");

        button_ubah = findViewById(R.id.button_simpan);
        button_batal = findViewById(R.id.button_batal);
        tambah_produk = (Button) findViewById(R.id.button_tambah_produk);
    }

    public void setSpinner_produk(){
        String url = "http://192.168.8.101/CI_Mobile_P3L_1F/index.php/produk";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String produk = jsonObject.getString("message");
                            JSONArray jsonArray = new JSONArray(produk);

                            produks.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject objectReview = jsonArray.getJSONObject(i);
                                produkDAO r = new produkDAO(objectReview.getString("id_produk"),
                                        objectReview.getString("nama_jenis_hewan"),
                                        objectReview.getString("nama_produk"),
                                        objectReview.getString("satuan_produk"),
                                        objectReview.getString("foto_produk"),
                                        objectReview.getString("status_data"),
                                        objectReview.getString("time_stamp"),
                                        objectReview.getString("keterangan"),
                                        objectReview.getInt("stok_produk"),
                                        objectReview.getInt("stok_minimal_produk"),
                                        objectReview.getInt("harga_satuan_produk"));

                                if(objectReview.getString("status_data").compareToIgnoreCase("deleted") != 0 && objectReview.getString("nama_jenis_hewan").compareToIgnoreCase(nama_jenis_hewan) == 0){
                                    produks.add(r);
                                }
                            }

                            tempProduk.clear();
                            for (int i = 0; i < produks.size(); i++) {
                                tempProduk.add(produks.get(i).getNama_produk());
                            }

                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, tempProduk);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_produk.setAdapter(spinnerArrayAdapter);
                            spinnerArrayAdapter.notifyDataSetChanged();
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

    private void tambah_produk() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(activity_ubah_hapus_penjualan_produk.this).create();
        LayoutInflater inflater = LayoutInflater.from(activity_ubah_hapus_penjualan_produk.this);
        View dialogView = inflater.inflate(R.layout.dialog_tambah_penjualan_produk, null);

        spinner_produk = (Spinner) dialogView.findViewById(R.id.spinner_produk_pesan);
        setSpinner_produk();
        spinner_produk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_produk = parent.getSelectedItem().toString();
                for(int count = 0; count < produks.size(); count++){
                    if (id_produk.equalsIgnoreCase(produks.get(count).getNama_produk())){
                        id_produk = produks.get(count).getId_produk();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final String[] satuanProduk = new String[1];
        final String[] nama_jenis_hewan = new String[1];
        final TextInputEditText Jumlah = (TextInputEditText) dialogView.findViewById(R.id.jumlah_produk_beli);
        Button simpan = (Button) dialogView.findViewById(R.id.button_simpan_produk);
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
                if (Jumlah.getText().toString().isEmpty()) {
                    Jumlah.setError("Field Tidak Boleh Kosong!");
                    return;
                } else {
                    final String namaProduk = spinner_produk.getSelectedItem().toString();
                    for(int count = 0; count < produks.size(); count++){
                        if (namaProduk.equalsIgnoreCase(produks.get(count).getNama_produk())){
                            satuanProduk[0] = produks.get(count).getSatuan_produk();
                            nama_jenis_hewan[0] = produks.get(count).getNama_jenis_hewan();
                        }
                    }

                    final int jumlah = Integer.valueOf(Jumlah.getText().toString());

                    detailProduk_penjualanDAO detailProduk = new detailProduk_penjualanDAO(id_produk, namaProduk, satuanProduk[0], nama_jenis_hewan[0], jumlah);
                    arrayList.add(detailProduk);
                    detailPenjualanProdukAdapter.notifyDataSetChanged();
                    dialogBuilder.dismiss();
                    produks.clear();
                    tempProduk.clear();
                }
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

//    private void tambahPenjualanProduk(final String id_cs, final String id_hewan){
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
//                                tambahDetailProduk(pesan, arrayList.get(i).getId_produk_tambah(), arrayList.get(i).getJumlah_produk(), nama_cs);
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
//                        Toast.makeText(activity_tambah_penjualan_produk.this, "Koneksi Terputus",
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
//    private void tambahDetailProduk(final String no_transaksi_produk, final String id_produk, final Integer jumlah_produk, final String keterangan){
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Toast.makeText(activity_tambah_penjualan_produk.this, "Detail Penjualan Produk Berhasil Disimpan!", Toast.LENGTH_LONG).show();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(activity_tambah_penjualan_produk.this, "Koneksi Terputus",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                }){
//
//            //datayangdiinput
//            @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params = new HashMap<>();
//                params.put("NO_TRANSAKSI_PRODUK", no_transaksi_produk);
//                params.put("ID_PRODUK", id_produk);
//                params.put("JUMLAH_PRODUK", String.valueOf(jumlah_produk));
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
//    private void updateTotalHarga(final String no_transaksi_produk){
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLHarga,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(activity_tambah_penjualan_produk.this, "Data Penjualan Produk Berhasil Disimpan!", Toast.LENGTH_LONG).show();
//                                dialog.dismiss();
//                                arrayList.clear();
//                                detailPenjualanProdukAdapter.notifyDataSetChanged();
//                            }
//                        }, 2000);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(activity_tambah_penjualan_produk.this, "Koneksi Terputus",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                }){
//
//            //datayangdiinput
//            @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params = new HashMap<>();
//                params.put("NO_TRANSAKSI_PRODUK", no_transaksi_produk);
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