package com.p3l_f_1_pegawai.Activities.penjualan_layanan;

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
import com.p3l_f_1_pegawai.dao.hewanDAO;
import com.p3l_f_1_pegawai.dao.konsumenDAO;
import com.p3l_f_1_pegawai.dao.layananDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class activity_tambah_penjualan_layanan extends AppCompatActivity {
    private String URLline = "http://192.168.8.102/CI_Mobile_P3L_1F/index.php/transaksilayanan";
    private String URL = "http://192.168.8.102/CI_Mobile_P3L_1F/index.php/transaksilayanan/detail";
    private String URLHarga = "http://192.168.8.102/CI_Mobile_P3L_1F/index.php/transaksilayanan/totalHarga";
    private Button simpan_jual_layanan, batal_simpan, tambah_layanan;
    private TextView show_calendar, show_person;
    private Spinner spinner_layanan, spinner_konsumen, spinner_hewan;
    String pesan = "-";
    private String message = "-";
    private String pesanku = "-";
    private String data = "-";
    ProgressDialog dialog;
    private String id_konsumen, id_layanan, id_hewan, id_cs, nama_jenis_hewan, nama_ukuran_hewan, nama_cs;
    String no_transaksi;
    private RecyclerView recyclerView;
    private List<konsumenDAO> konsumens = new ArrayList<>();
    private ArrayList<String> tempKonsumen = new ArrayList<>();
    private List<hewanDAO> hewans = new ArrayList<>();
    private ArrayList<String> tempHewan = new ArrayList<>();
    private List<layananDAO> layanans = new ArrayList<>();
    private ArrayList<String> tempLayanan = new ArrayList<>();
    private ArrayList<detailLayanan_penjualanDAO> arrayList = new ArrayList<>();
    private DetailTambahPenjualanLayananAdapter detailTambahPenjualanLayananAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_penjualan_layanan);
        setAtribut();

        simpan_jual_layanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahPenjualanLayanan(id_cs, id_hewan);
                //pesanku = "Berhasil";
                progDialog();
                //waitingResponse();
            }
        });

        batal_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Batal Tambah Transaksi Layanan!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(activity_tambah_penjualan_layanan.this, PenjualanLayananAdapter.class);
                startActivity(intent);
            }
        });

        tambah_layanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambah_layanan();
            }
        });

    }

    private void setAtribut(){
        SharedPreferences mSettings = getSharedPreferences("Login", Context.MODE_PRIVATE);
        id_cs = mSettings.getString("id_user_login", "user_tidak_terdeteksi");
        nama_cs = mSettings.getString("nama_user_login", "user_tidak_terdeteksi");

        simpan_jual_layanan = (Button) findViewById(R.id.button_simpan);
        batal_simpan = (Button) findViewById(R.id.button_batal);
        tambah_layanan = (Button) findViewById(R.id.button_tambah_layanan);
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
                        nama_ukuran_hewan = hewans.get(count).getId_ukuran_hewan();
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

        recyclerView = findViewById(R.id.recycle_tambah_jual_layanan);
        detailTambahPenjualanLayananAdapter = new DetailTambahPenjualanLayananAdapter(arrayList, getApplicationContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(detailTambahPenjualanLayananAdapter);

        show_person.setText(getIntent().getStringExtra("USERNAME"));
        dialog = new ProgressDialog(this);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        String format = simpleDateFormat.format(new Date());
        show_calendar.setText(format);
    }

    public void setSpinner_konsumen(){
        String url = "http://192.168.8.102/CI_Mobile_P3L_1F/index.php/konsumen";
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
        String url = "http://192.168.8.102/CI_Mobile_P3L_1F/index.php/hewan";
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

    public void setSpinner_layanan(){
        String url = "http://192.168.8.102/CI_Mobile_P3L_1F/index.php/layanan";
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

                                if(objectReview.getString("status_data").compareToIgnoreCase("deleted") != 0 && objectReview.getString("nama_jenis_hewan").compareToIgnoreCase(nama_jenis_hewan) == 0
                                        && objectReview.getString("nama_ukuran_hewan").compareToIgnoreCase(nama_ukuran_hewan) == 0){
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
        final AlertDialog dialogBuilder = new AlertDialog.Builder(activity_tambah_penjualan_layanan.this).create();
        LayoutInflater inflater = LayoutInflater.from(activity_tambah_penjualan_layanan.this);
        View dialogView = inflater.inflate(R.layout.dialog_tambah_penjualan_layanan, null);

        spinner_layanan = (Spinner) dialogView.findViewById(R.id.spinner_layanan_pesan);
        setSpinner_layanan();
        spinner_layanan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_layanan = parent.getSelectedItem().toString();
                for(int count = 0; count < layanans.size(); count++){
                    if (id_layanan.equalsIgnoreCase(layanans.get(count).getNama_layanan() + " " + layanans.get(count).getNama_jenis_hewan() + " " + layanans.get(count).getNama_ukuran_hewan())){
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

    private void tambahPenjualanLayanan(final String id_cs, final String id_hewan){
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
                                tambahDetailLayanan(pesan, arrayList.get(i).getId_layanan_tambah(), arrayList.get(i).getJumlah_layanan(), nama_cs);
                            }

                            updateTotalHarga(pesan);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity_tambah_penjualan_layanan.this, "Koneksi Terputus",
                                Toast.LENGTH_SHORT).show();
                    }
                }){

            //datayangdiinput
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("ID_CUSTOMER_SERVICE", id_cs);
                params.put("ID_HEWAN", id_hewan);
                return params;
            }
        };
        stringRequest.setRetryPolicy(
                new DefaultRetryPolicy(
                        500000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
        );
        requestQueue.add(stringRequest);
    }

    private void tambahDetailLayanan(final String no_transaksi_layanan, final String id_layanan, final Integer jumlah_layanan, final String keterangan){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(activity_tambah_penjualan_layanan.this, "Detail Penjualan Layanan Berhasil Disimpan!", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity_tambah_penjualan_layanan.this, "Koneksi Terputus",
                                Toast.LENGTH_SHORT).show();
                    }
                }){

            //datayangdiinput
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("NO_TRANSAKSI_LAYANAN", no_transaksi_layanan);
                params.put("ID_LAYANAN", id_layanan);
                params.put("JUMLAH_LAYANAN", String.valueOf(jumlah_layanan));
                params.put("KETERANGAN", keterangan);
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

    private void updateTotalHarga(final String no_transaksi_layanan){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLHarga,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity_tambah_penjualan_layanan.this, "Data Penjualan Layanan Berhasil Disimpan!", Toast.LENGTH_LONG).show();

                                dialog.dismiss();
                                arrayList.clear();
                                detailTambahPenjualanLayananAdapter.notifyDataSetChanged();
                            }
                        }, 2000);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity_tambah_penjualan_layanan.this, "Koneksi Terputus",
                                Toast.LENGTH_SHORT).show();
                    }
                }){

            //datayangdiinput
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("NO_TRANSAKSI_LAYANAN", no_transaksi_layanan);
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
