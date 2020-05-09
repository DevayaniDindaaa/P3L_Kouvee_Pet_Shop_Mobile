package com.p3l_f_1_pegawai.Activities.penjualan_produk;

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
import com.google.android.material.textfield.TextInputEditText;
import com.p3l_f_1_pegawai.Activities.pengadaan.DetailTambahPengadaanProdukAdapter;
import com.p3l_f_1_pegawai.Activities.pengadaan.PengadaanFragment;
import com.p3l_f_1_pegawai.Activities.pengadaan.activity_tambah_pengadaan;
import com.p3l_f_1_pegawai.R;
import com.p3l_f_1_pegawai.dao.detailProduk_pengadaanDAO;
import com.p3l_f_1_pegawai.dao.detailProduk_penjualanDAO;
import com.p3l_f_1_pegawai.dao.hewanDAO;
import com.p3l_f_1_pegawai.dao.konsumenDAO;
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

public class activity_tambah_penjualan_produk extends AppCompatActivity {
    private String URLline = "http://192.168.8.103/CI_Mobile_P3L_1F/index.php/transaksipengadaan";
    private String URL = "http://192.168.8.103/CI_Mobile_P3L_1F/index.php/transaksipengadaan/detail";
    private Button simpan_jual_produk, batal_simpan, tambah_produk;
    private TextView show_calendar, show_person;
    private Spinner spinner_produk, spinner_konsumen, spinner_hewan;
    String pesan = "-";
    private String message = "-";
    private String pesanku = "-";
    private String data = "-";
    ProgressDialog dialog;
    private String id_konsumen, id_produk, id_hewan, id_cs, nama_jenis_hewan;
    String no_transaksi;
    private RecyclerView recyclerView;
    private List<konsumenDAO> konsumens = new ArrayList<>();
    private ArrayList<String> tempKonsumen = new ArrayList<>();
    private List<hewanDAO> hewans = new ArrayList<>();
    private ArrayList<String> tempHewan = new ArrayList<>();
    private List<produkDAO> produks = new ArrayList<>();
    private ArrayList<String> tempProduk = new ArrayList<>();
    private ArrayList<detailProduk_penjualanDAO> arrayList = new ArrayList<>();
    private DetailTambahPenjualanProdukAdapter detailPenjualanProdukAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_penjualan_produk);
        setAtribut();

        simpan_jual_produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tanggal_pesan;
                tanggal_pesan = show_calendar.getText().toString();

                //tambahPengadaan(tanggal_pesan, id_hewan, id_cs);

                pesanku = "Berhasil";
               /* progDialog();
                waitingResponse();*/
            }
        });

        batal_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Batal Tambah Transaski Produk!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(activity_tambah_penjualan_produk.this, PenjualanProdukFragment.class);
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
        SharedPreferences mSettings = getSharedPreferences("Login", Context.MODE_PRIVATE);
        id_cs = mSettings.getString("id_user_login", "user_tidak_terdeteksi");

        simpan_jual_produk = (Button) findViewById(R.id.button_simpan);
        batal_simpan = (Button) findViewById(R.id.button_batal);
        tambah_produk = (Button) findViewById(R.id.button_tambah_produk);
        show_calendar = (TextView) findViewById(R.id.show_calendar);
        show_person = (TextView) findViewById(R.id.show_person);

        spinner_hewan = (Spinner) findViewById(R.id.spinner_hewan);
        spinner_hewan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_hewan = parent.getSelectedItem().toString();
                for(int count = 0; count < hewans.size(); count++){
                    if (id_hewan.equalsIgnoreCase(hewans.get(count).getNama_hewan())){
                        id_hewan =  hewans.get(count).getId_hewan();
                        nama_jenis_hewan = hewans.get(count).getId_jenis_hewan();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_konsumen = (Spinner) findViewById(R.id.spinner_konsumen);
        setSpinner_konsumen();
        spinner_konsumen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tempHewan.clear();
                id_konsumen = parent.getSelectedItem().toString();
                for(int count = 0; count < konsumens.size(); count++){
                    if (id_konsumen.equalsIgnoreCase(konsumens.get(count).getNama_konsumen())){
                        id_konsumen = konsumens.get(count).getId_konsumen();
                        setSpinner_hewan();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        recyclerView = findViewById(R.id.recycle_tambah_jual_produk);
        detailPenjualanProdukAdapter = new DetailTambahPenjualanProdukAdapter(arrayList, getApplicationContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(detailPenjualanProdukAdapter);

        show_person.setText(getIntent().getStringExtra("USERNAME"));
        dialog = new ProgressDialog(this);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        String format = simpleDateFormat.format(new Date());
        show_calendar.setText(format);
    }

    public void setSpinner_konsumen(){
        String url = "http://192.168.8.103/CI_Mobile_P3L_1F/index.php/konsumen";
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
                                konsumenDAO r = new konsumenDAO(objectReview.getString("id_konsumen"),
                                        objectReview.getString("nama_konsumen"),
                                        objectReview.getString("alamat_konsumen"),
                                        objectReview.getString("tgl_lahir_konsumen"),
                                        objectReview.getString("no_tlp_konsumen"),
                                        objectReview.getString("status_member"),
                                        objectReview.getString("status_data"),
                                        objectReview.getString("time_stamp"),
                                        objectReview.getString("keterangan"),
                                        objectReview.getJSONArray("detail"));

                                if(objectReview.getString("status_data").compareToIgnoreCase("deleted") != 0){
                                    konsumens.add(r);
                                }
                            }
                            for (int i = 0; i < konsumens.size(); i++) {
                                tempKonsumen.add(konsumens.get(i).getNama_konsumen());
                            }
                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, tempKonsumen);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_konsumen.setAdapter(spinnerArrayAdapter);
                            //spinnerArrayAdapter.notifyDataSetChanged();
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

    public void setSpinner_hewan(){
        String url = "http://192.168.8.103/CI_Mobile_P3L_1F/index.php/hewan";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String supplier = jsonObject.getString("message");
                            JSONArray jsonArray = new JSONArray(supplier);

                            hewans.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject objectReview = jsonArray.getJSONObject(i);
                                hewanDAO d = new hewanDAO(objectReview.getString("id_hewan"),
                                        objectReview.getString("nama_jenis_hewan"),
                                        objectReview.getString("nama_ukuran_hewan"),
                                        objectReview.getString("id_konsumen"),
                                        objectReview.getString("nama_konsumen"),
                                        objectReview.getString("alamat_konsumen"),
                                        objectReview.getString("tgl_lahir_konsumen"),
                                        objectReview.getString("no_tlp_konsumen"),
                                        objectReview.getString("status_member"),
                                        objectReview.getString("nama_hewan"),
                                        objectReview.getString("tgl_lahir_hewan"),
                                        objectReview.getString("status_data"),
                                        objectReview.getString("time_stamp"),
                                        objectReview.getString("keterangan"));

                                if(objectReview.getString("status_data").compareToIgnoreCase("deleted") != 0 && objectReview.getString("id_konsumen").compareToIgnoreCase(id_konsumen) == 0){
                                    hewans.add(d);
                                }
                            }

                            tempHewan.clear();
                            for (int i = 0; i < hewans.size(); i++) {
                                tempHewan.add(hewans.get(i).getNama_hewan());
                            }

                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, tempHewan);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_hewan.setAdapter(spinnerArrayAdapter);
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

    public void setSpinner_produk(){
        String url = "http://192.168.8.103/CI_Mobile_P3L_1F/index.php/produk";
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
        final AlertDialog dialogBuilder = new AlertDialog.Builder(activity_tambah_penjualan_produk.this).create();
        LayoutInflater inflater = LayoutInflater.from(activity_tambah_penjualan_produk.this);
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

   /* private void progDialog() {
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
                    Intent intent = new Intent(activity_tambah_pengadaan.this, PengadaanFragment.class);
                    startActivity(intent);
                }
                else if(pesanku.equalsIgnoreCase("Gagal")) {
                    Toast.makeText(activity_tambah_pengadaan.this, "Kesalahan Koneksi", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(activity_tambah_pengadaan.this, PengadaanFragment.class);
                    startActivity(intent);
                }
                dialog.dismiss();
            }
        }, 2000);
    }*/

   /* private void tambahPengadaan(String tanggal_pesan, final String string_tanggal, final String id_supplier){
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
    }*/
}
