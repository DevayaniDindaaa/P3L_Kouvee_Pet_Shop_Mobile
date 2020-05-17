package com.p3l_f_1_pegawai.Activities.pengadaan;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
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
import com.p3l_f_1_pegawai.R;
import com.p3l_f_1_pegawai.dao.detailProduk_pengadaanDAO;
import com.p3l_f_1_pegawai.dao.detail_pengadaanDAO;
import com.p3l_f_1_pegawai.dao.produkDAO;
import com.p3l_f_1_pegawai.dao.supplierDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class activity_ubah_pengadaan extends AppCompatActivity {
    private String URLline = "http://192.168.8.100/CI_Mobile_P3L_1F/index.php/transaksipengadaan/";
    private String URL = "http://192.168.8.100/CI_Mobile_P3L_1F/index.php/transaksipengadaan/detail/";
    private Button simpan_pengadaan, batal_simpan, tambah_produk;
    private TextView show_calendar, show_person, nomor_pemesanan, tanggal_pesan;
    private Spinner spinner_produk, spinner_supplier;
    String pesan = "-";
    private String message = "-";
    private String pesanku = "-";
    private String data = "-";
    ProgressDialog dialog;
    private String id_supplier, id_produk;
    String no_pesan, id_detail;
    private RecyclerView recyclerView;
    private List<supplierDAO> suppliers = new ArrayList<>();
    private ArrayList<String> tempSupplier = new ArrayList<>();
    private List<produkDAO> produks = new ArrayList<>();
    private ArrayList<String> tempProduk = new ArrayList<>();
    private List<detail_pengadaanDAO> detailUbahPengadaanProdukList = new ArrayList<>();
    private DetailUbahPengadaanProdukAdapter detailUbahPengadaanProdukAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_pengadaan);
        setAtribut();
        getDetailPengadaan();
        simpan_pengadaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ubahPengadaan(id_supplier, no_pesan);

                pesanku = "Berhasil";
                progDialog();
                waitingResponse();
            }
        });

        batal_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Batal Ubah Pengadaan Produk!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(activity_ubah_pengadaan.this, activity_detail_pengadaan.class);
                startActivity(intent);
            }
        });

//        tambah_produk.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                tambah_produk();
////            }
////        });

    }

    private void getDetailPengadaan() {
        try {
            String detailPengadaan = getIntent().getStringExtra("details");
            JSONArray detail = new JSONArray(detailPengadaan);
            for (int j = 0; j < detail.length(); j++) {
                JSONObject objectDetail = detail.getJSONObject(j);
                detail_pengadaanDAO d = new detail_pengadaanDAO(objectDetail.getString("id_detail_pengadaan"),
                        objectDetail.getString("id_produk"),
                        objectDetail.getString("nama_produk"),
                        objectDetail.getString("satuan_produk"),
                        objectDetail.getString("status_data"),
                        objectDetail.getString("time_stamp"),
                        objectDetail.getString("keterangan"),
                        objectDetail.getInt("jumlah_produk_dipesan"));
                System.out.println(objectDetail);
                detailUbahPengadaanProdukList.add(d);
            }
            detailUbahPengadaanProdukAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setAtribut(){
        simpan_pengadaan = (Button) findViewById(R.id.button_ubah_pengadaan);
        batal_simpan = (Button) findViewById(R.id.button_batal_ubah_pengadaan);
        tambah_produk = (Button) findViewById(R.id.button_tambah_produk);
        show_person = (TextView) findViewById(R.id.show_person);
        nomor_pemesanan = findViewById(R.id.nomor_pemesanan);
        nomor_pemesanan.setText(getIntent().getStringExtra("nomor_pemesanan"));
        no_pesan = getIntent().getStringExtra("nomor_pemesanan");
        tanggal_pesan = findViewById(R.id.tanggal_pemesanan);
        tanggal_pesan.setText(getIntent().getStringExtra("tanggal_pemesanan"));
        spinner_supplier = (Spinner) findViewById(R.id.spinner_supplier);
        setSpinner_supplier();
        spinner_supplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_supplier = parent.getSelectedItem().toString();
                for(int count = 0; count < suppliers.size(); count++){
                    if (id_supplier.equalsIgnoreCase(suppliers.get(count).getNama_supplier())){
                        id_supplier = suppliers.get(count).getId_supplier();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        recyclerView = findViewById(R.id.recycle_ubah_pengadaan_produk);
        detailUbahPengadaanProdukAdapter = new DetailUbahPengadaanProdukAdapter(detailUbahPengadaanProdukList, getApplicationContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(detailUbahPengadaanProdukAdapter);

        dialog = new ProgressDialog(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        detailUbahPengadaanProdukAdapter.notifyDataSetChanged();
    }

    public void setSpinner_supplier(){
        String url = "http://192.168.8.100/CI_Mobile_P3L_1F/index.php/supplier";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String supplier = jsonObject.getString("message");
                            JSONArray jsonArray = new JSONArray(supplier);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject objectReview = jsonArray.getJSONObject(i);
                                supplierDAO r = new supplierDAO(objectReview.getString("ID_SUPPLIER"),
                                        objectReview.getString("NAMA_SUPPLIER"),
                                        objectReview.getString("ALAMAT_SUPPLIER"),
                                        objectReview.getString("KOTA_SUPPLIER"),
                                        objectReview.getString("NO_TLP_SUPPLIER"),
                                        objectReview.getString("STATUS_DATA"),
                                        objectReview.getString("TIME_STAMP"),
                                        objectReview.getString("KETERANGAN"));

                                if(objectReview.getString("STATUS_DATA").compareToIgnoreCase("deleted") != 0){
                                    suppliers.add(r);
                                }
                            }
                            for (int i = 0; i < suppliers.size(); i++) {
                                tempSupplier.add(suppliers.get(i).getNama_supplier());
                            }
                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, tempSupplier);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_supplier.setAdapter(spinnerArrayAdapter);
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

    public void setSpinner_produk(){
        String url = "http://192.168.8.100/CI_Mobile_P3L_1F/index.php/produk";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String produk = jsonObject.getString("message");
                            JSONArray jsonArray = new JSONArray(produk);

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

                                if(objectReview.getString("status_data").compareToIgnoreCase("deleted") != 0 && objectReview.getInt("stok_produk") < objectReview.getInt("stok_minimal_produk")){
                                    produks.add(r);
                                }
                            }
                            for (int i = 0; i < produks.size(); i++) {
                                tempProduk.add(produks.get(i).getNama_produk());
                            }
                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, tempProduk);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_produk.setAdapter(spinnerArrayAdapter);
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

//    private void tambah_produk() {
//        final AlertDialog dialogBuilder = new AlertDialog.Builder(activity_tambah_pengadaan.this).create();
//        LayoutInflater inflater = LayoutInflater.from(activity_tambah_pengadaan.this);
//        View dialogView = inflater.inflate(R.layout.dialog_tambah_detail_pengadaan, null);
//
//        spinner_produk = (Spinner) dialogView.findViewById(R.id.spinner_produk_pesan);
//        setSpinner_produk();
//        spinner_produk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                id_produk = parent.getSelectedItem().toString();
//                for(int count = 0; count < produks.size(); count++){
//                    if (id_produk.equalsIgnoreCase(produks.get(count).getNama_produk())){
//                        id_produk = produks.get(count).getId_produk();
//                    }
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        final String[] satuanProduk = new String[1];
//        final TextInputEditText Jumlah = (TextInputEditText) dialogView.findViewById(R.id.jumlah_produk_pengadaan);
//        Button simpan = (Button) dialogView.findViewById(R.id.button_simpan_produk);
//        Button batal = (Button) dialogView.findViewById(R.id.button_batal_tambah);
//
//        batal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialogBuilder.dismiss();
//            }
//        });
//        simpan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (Jumlah.getText().toString().isEmpty()) {
//                    Jumlah.setError("Field Tidak Boleh Kosong!");
//                    return;
//                } else {
//                    final String namaProduk = spinner_produk.getSelectedItem().toString();
//                    for(int count = 0; count < produks.size(); count++){
//                        if (namaProduk.equalsIgnoreCase(produks.get(count).getNama_produk())){
//                            satuanProduk[0] = produks.get(count).getSatuan_produk();
//                        }
//                    }
//
//                    final int jumlah = Integer.valueOf(Jumlah.getText().toString());
//
//                    detailProduk_pengadaanDAO detailProduk = new detailProduk_pengadaanDAO(id_produk, namaProduk, satuanProduk[0], jumlah);
//                    arrayList.add(detailProduk);
//                    detailPengadaanProdukAdapter.notifyDataSetChanged();
//                    dialogBuilder.dismiss();
//                    produks.clear();
//                    tempProduk.clear();
//                }
//            }
//        });
//
//        dialogBuilder.setView(dialogView);
//        dialogBuilder.show();
//    }
//
    private void progDialog() {
        dialog.setMessage("Mengubah Data ...");
        dialog.show();
    }

    private void waitingResponse() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(pesanku.equalsIgnoreCase("Berhasil")) {
                    Toast.makeText(activity_ubah_pengadaan.this, "Data Pemesanan Produk Berhasil Diubah!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(activity_ubah_pengadaan.this, PengadaanFragment.class);
                    startActivity(intent);
                }
                else if(pesanku.equalsIgnoreCase("Gagal")) {
                    Toast.makeText(activity_ubah_pengadaan.this, "Kesalahan Koneksi", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(activity_ubah_pengadaan.this, PengadaanFragment.class);
                    startActivity(intent);
                }
                dialog.dismiss();
            }
        }, 2000);
    }

    private void ubahPengadaan(final String id_supplier, final String no_pesan){
        final String urlku = URLline + no_pesan;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlku,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            pesan = jsonObject.getString("message");

//                            System.out.println(pesan);
//                            for (int i = 0; i<arrayList.size(); i++)
//                            {
//                                tambahDetailPengadaan(pesan, arrayList.get(i).getId_produk_tambah(), arrayList.get(i).getJumlah_produk());
//                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity_ubah_pengadaan.this, "Koneksi Terputus",
                                Toast.LENGTH_SHORT).show();
                    }
                }){

            //datayangdiinput
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("NOMOR_PEMESANAN", no_pesan);
                params.put("ID_SUPPLIER", id_supplier);
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

//    private void tambahDetailPengadaan(final String nomor_pemesanan, final String id_produk, final Integer jumlah_produk_dipesan){
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject json = new JSONObject(response);
//
//                            message = json.getString("message");
//                            data = json.getString("data");
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(activity_ubah_pengadaan.this, "Koneksi Terputus",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                }){
//
//            //datayangdiinput
//            @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params = new HashMap<>();
//                params.put("NOMOR_PEMESANAN", nomor_pemesanan);
//                params.put("ID_PRODUK", id_produk);
//                params.put("JUMLAH_PRODUK_DIPESAN", String.valueOf(jumlah_produk_dipesan));
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
