package com.p3l_f_1_pegawai.Activities.layanan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
import com.p3l_f_1_pegawai.dao.spinner_jenis_hewanDAO;
import com.p3l_f_1_pegawai.dao.spinner_ukuran_hewanDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class activity_ubah_layanan extends AppCompatActivity {
    private String URLline = "http://192.168.8.100/CI_Mobile_P3L_1F/index.php/layanan/";
    private TextInputEditText nama_layanan, harga_layanan;
    private Button simpan_layanan, batal_simpan;
    private TextView show_calendar, show_person;
    private String data = "-";
    private String message = "-";
    private String Numeric = "\\d+";
    private String id_jenis_hewan, id_ukuran_hewan, id_layanan_hewan;
    ProgressDialog dialog;
    private List<spinner_jenis_hewanDAO> jenisHewans = new ArrayList<>();
    private ArrayList<String> tempJenis = new ArrayList<>();
    private List<spinner_ukuran_hewanDAO> ukuranHewans = new ArrayList<spinner_ukuran_hewanDAO>();
    private ArrayList<String> tempUkuran = new ArrayList<>();
    private Spinner spinner_jenis, spinner_ukuran;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_layanan);
        setAtribut();

        simpan_layanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nama_layanan_hewan, nama_pengguna;
                final Integer harga_layanan_hewan;
                nama_layanan_hewan = nama_layanan.getText().toString();
                harga_layanan_hewan = Integer.valueOf(harga_layanan.getText().toString());
                nama_pengguna = show_person.getText().toString();

                formValidation(nama_layanan_hewan, String.valueOf(harga_layanan_hewan));
                ubahLayanan(nama_layanan_hewan, harga_layanan_hewan, id_jenis_hewan, id_ukuran_hewan, nama_pengguna);
                System.out.println(message);
                progDialog();
                waitingResponse();
            }
        });

        batal_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama_layanan.getText().clear();
                Toast.makeText(activity_ubah_layanan.this, "Batal Ubah Layanan!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(activity_ubah_layanan.this, LayananFragment.class);
                startActivity(intent);
            }
        });
    }

    private void setAtribut(){
        nama_layanan = (TextInputEditText) findViewById(R.id.tambah_layanan_hewan);
        nama_layanan.setText(getIntent().getStringExtra("nama_layanan"));
        harga_layanan = (TextInputEditText) findViewById(R.id.harga_layanan_hewan);
        harga_layanan.setText(getIntent().getStringExtra("harga_layanan"));
        simpan_layanan = (Button) findViewById(R.id.button_simpan);
        batal_simpan = (Button) findViewById(R.id.button_batal);
        show_calendar = (TextView) findViewById(R.id.show_calendar);
        show_person = (TextView) findViewById(R.id.show_person);
        show_person.setText(getIntent().getStringExtra("keterangan"));
        id_layanan_hewan = getIntent().getStringExtra("id_layanan");
        dialog = new ProgressDialog(this);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        String format = simpleDateFormat.format(new Date());
        show_calendar.setText(format);

        spinner_jenis = (Spinner) findViewById(R.id.spinner_jenis);
        setSpinner_jenis();

        spinner_jenis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_jenis_hewan = parent.getSelectedItem().toString();
                for(int count = 0; count < jenisHewans.size(); count++){
                    if (id_jenis_hewan.equalsIgnoreCase(jenisHewans.get(count).getNama_jenis_hewan())){
                        id_jenis_hewan = jenisHewans.get(count).getId_jenis_hewan();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_ukuran = (Spinner) findViewById(R.id.spinner_ukuran);
        setSpinner_ukuran();
        spinner_ukuran.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_ukuran_hewan = parent.getSelectedItem().toString();
                for(int count = 0; count < ukuranHewans.size(); count++){
                    if (id_ukuran_hewan.equalsIgnoreCase(ukuranHewans.get(count).getNama_ukuran_hewan())){
                        id_ukuran_hewan = ukuranHewans.get(count).getId_ukuran_hewan();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setSpinner_jenis(){
        String url = "http://192.168.8.100/CI_Mobile_P3L_1F/index.php/jenishewan";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String jenis_hewan = jsonObject.getString("message");
                            JSONArray jsonArray = new JSONArray(jenis_hewan);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject objectReview = jsonArray.getJSONObject(i);
                                spinner_jenis_hewanDAO r = new spinner_jenis_hewanDAO(objectReview.getString("ID_JENIS_HEWAN"),
                                        objectReview.getString("NAMA_JENIS_HEWAN"),
                                        objectReview.getString("STATUS_DATA"));

                                System.out.println(objectReview);
                                if(objectReview.getString("STATUS_DATA").compareToIgnoreCase("deleted") != 0){
                                    jenisHewans.add(r);
                                }
                            }
                            for (int i = 0; i < jenisHewans.size(); i++) {
                                tempJenis.add(jenisHewans.get(i).getNama_jenis_hewan());
                            }
                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, tempJenis);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_jenis.setAdapter(spinnerArrayAdapter);
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

    public void setSpinner_ukuran(){
        String url = "http://192.168.8.100/CI_Mobile_P3L_1F/index.php/ukuranhewan";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ukuran_hewan = jsonObject.getString("message");
                            JSONArray jsonArray = new JSONArray(ukuran_hewan);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject objectReview = jsonArray.getJSONObject(i);
                                spinner_ukuran_hewanDAO r = new spinner_ukuran_hewanDAO(objectReview.getString("ID_UKURAN_HEWAN"),
                                        objectReview.getString("NAMA_UKURAN_HEWAN"),
                                        objectReview.getString("STATUS_DATA"));

                                System.out.println(objectReview);
                                if(objectReview.getString("STATUS_DATA").compareToIgnoreCase("deleted") != 0) {
                                    ukuranHewans.add(r);
                                }
                            }
                            for (int i = 0; i < ukuranHewans.size(); i++) {
                                tempUkuran.add(ukuranHewans.get(i).getNama_ukuran_hewan());
                            }
                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, tempUkuran);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_ukuran.setAdapter(spinnerArrayAdapter);
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

    private void formValidation(String nama_layanan_hewan, String harga_layanan_hewan) {
        if (TextUtils.isEmpty(nama_layanan_hewan)) {
            nama_layanan.setError("Field Tidak Boleh Kosong!");
            return;
        }

        if (TextUtils.isEmpty(harga_layanan_hewan)) {
            harga_layanan.setError("Field Tidak Boleh Kosong!");
            return;
        }

        if (!Pattern.matches(Numeric, harga_layanan_hewan)){
            harga_layanan.setError("Harga Hanya dalam Bentuk Angka");
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
                    Toast.makeText(activity_ubah_layanan.this, "Data Layanan Berhasil Diubah!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(activity_ubah_layanan.this, LayananFragment.class);
                    startActivity(intent);
                }
                else if(message.equalsIgnoreCase("Gagal")) {
                    Toast.makeText(activity_ubah_layanan.this, "Kesalahan Koneksi", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(activity_ubah_layanan.this, LayananFragment.class);
                    startActivity(intent);
                }
                dialog.dismiss();
            }
        }, 2000);
    }

    private void ubahLayanan(final String string_nama, final Integer int_harga, final String id_jenis_hewan, final String id_ukuran_hewan, final String nama_pengguna){
        final String url = URLline + id_layanan_hewan;
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
                        Toast.makeText(activity_ubah_layanan.this, "Koneksi Terputus",
                                Toast.LENGTH_SHORT).show();
                    }
                }){

            //datayangdiinput
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<String,String>();
                params.put("ID_JENIS_HEWAN", id_jenis_hewan);
                params.put("ID_UKURAN_HEWAN", id_ukuran_hewan);
                params.put("NAMA_LAYANAN", string_nama);
                params.put("HARGA_SATUAN_LAYANAN", String.valueOf(int_harga));
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