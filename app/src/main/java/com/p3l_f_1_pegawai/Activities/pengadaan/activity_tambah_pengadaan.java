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

public class activity_tambah_pengadaan extends AppCompatActivity {
    private String URLline = "http://192.168.0.200/CI_Mobile_P3L_1F/index.php/transaksipengadaan";
    private String URL = "http://192.168.0.200/CI_Mobile_P3L_1F/index.php/transaksipengadaan/detail";
    private Button simpan_pengadaan, batal_simpan, tambah_produk;
    private TextView show_calendar, show_person;
    private Spinner spinner_produk, spinner_supplier;
    String pesan = "-";
    private String message = "-";
    private String pesanku = "-";
    private String data = "-";
    ProgressDialog dialog;
    private String id_supplier, id_produk;
    String nomor_pemesanan;
    private RecyclerView recyclerView;
    private List<supplierDAO> suppliers = new ArrayList<>();
    private ArrayList<String> tempSupplier = new ArrayList<>();
    private List<produkDAO> produks = new ArrayList<>();
    private ArrayList<String> tempProduk = new ArrayList<>();
    private ArrayList<detailProduk_pengadaanDAO> arrayList = new ArrayList<>();
    private DetailTambahPengadaanProdukAdapter detailPengadaanProdukAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pengadaan);
        setAtribut();

        simpan_pengadaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tanggal_pesan;
                tanggal_pesan = show_calendar.getText().toString();

                tambahPengadaan(tanggal_pesan, id_supplier);

                pesanku = "Berhasil";
                progDialog();
                waitingResponse();
            }
        });

        batal_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Batal Tambah Pengadaan Produk!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(activity_tambah_pengadaan.this, PengadaanFragment.class);
                startActivity(intent);
            }
        });

        tambah_produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambah_produk();
            }
        });

    }

    private void setAtribut(){
        simpan_pengadaan = (Button) findViewById(R.id.button_simpan);
        batal_simpan = (Button) findViewById(R.id.button_batal);
        tambah_produk = (Button) findViewById(R.id.button_tambah_produk);
        show_calendar = (TextView) findViewById(R.id.show_calendar);
        show_person = (TextView) findViewById(R.id.show_person);

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

        recyclerView = findViewById(R.id.recycle_tambah_pengadaan_produk);
        detailPengadaanProdukAdapter = new DetailTambahPengadaanProdukAdapter(arrayList, getApplicationContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(detailPengadaanProdukAdapter);

        show_person.setText(getIntent().getStringExtra("USERNAME"));
        dialog = new ProgressDialog(this);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        String format = simpleDateFormat.format(new Date());
        show_calendar.setText(format);
    }

    public void setSpinner_supplier(){
        String url = "http://192.168.0.200/CI_Mobile_P3L_1F/index.php/supplier";
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
        String url = "http://192.168.0.200/CI_Mobile_P3L_1F/index.php/produk";
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

    private void tambah_produk() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(activity_tambah_pengadaan.this).create();
        LayoutInflater inflater = LayoutInflater.from(activity_tambah_pengadaan.this);
        View dialogView = inflater.inflate(R.layout.dialog_tambah_detail_pengadaan, null);

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
        final TextInputEditText Jumlah = (TextInputEditText) dialogView.findViewById(R.id.jumlah_produk_pengadaan);
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
                        }
                    }

                    final int jumlah = Integer.valueOf(Jumlah.getText().toString());

                    detailProduk_pengadaanDAO detailProduk = new detailProduk_pengadaanDAO(id_produk, namaProduk, satuanProduk[0], jumlah);
                    arrayList.add(detailProduk);
                    detailPengadaanProdukAdapter.notifyDataSetChanged();
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

    private void waitingResponse() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(pesanku.equalsIgnoreCase("Berhasil")) {
                    Toast.makeText(activity_tambah_pengadaan.this, "Data Pemesanan Produk Berhasil Disimpan!", Toast.LENGTH_LONG).show();
                }
                else if(pesanku.equalsIgnoreCase("Gagal")) {
                    Toast.makeText(activity_tambah_pengadaan.this, "Kesalahan Koneksi", Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
        }, 2000);
    }

    private void tambahPengadaan(final String string_tanggal, final String id_supplier){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            pesan = jsonObject.getString("message");

                            System.out.println(pesan);
                            for (int i = 0; i<arrayList.size(); i++)
                            {
                                tambahDetailPengadaan(pesan, arrayList.get(i).getId_produk_tambah(), arrayList.get(i).getJumlah_produk());
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity_tambah_pengadaan.this, "Koneksi Terputus",
                                Toast.LENGTH_SHORT).show();
                    }
                }){

            //datayangdiinput
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("ID_SUPPLIER", id_supplier);
                params.put("TGL_PEMESANAN", string_tanggal);
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

    private void tambahDetailPengadaan(final String nomor_pemesanan, final String id_produk, final Integer jumlah_produk_dipesan){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);

                            message = json.getString("message");
                            data = json.getString("data");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity_tambah_pengadaan.this, "Koneksi Terputus",
                                Toast.LENGTH_SHORT).show();
                    }
                }){

            //datayangdiinput
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("NOMOR_PEMESANAN", nomor_pemesanan);
                params.put("ID_PRODUK", id_produk);
                params.put("JUMLAH_PRODUK_DIPESAN", String.valueOf(jumlah_produk_dipesan));
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
