package com.p3l_f_1_pegawai.Activities.produk;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.p3l_f_1_pegawai.Activities.layanan.LayananFragment;
import com.p3l_f_1_pegawai.Activities.layanan.activity_tambah_layanan;
import com.p3l_f_1_pegawai.R;
import com.p3l_f_1_pegawai.dao.spinner_jenis_hewan;
import com.p3l_f_1_pegawai.dao.spinner_ukuran_hewan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class activity_tambah_produk extends AppCompatActivity {
    private String URLline = "http://192.168.8.102/CI_Mobile_P3L_1F/index.php/produk";
    private TextInputEditText nama_produk, satuan_produk, harga_produk, stok_produk, stok_minimal_produk;
    private ImageView foto_produk;
    private Button tambah_foto, simpan_produk, batal_simpan;
    private TextView show_calendar, show_person;
    private String data = "-";
    private String message = "-";
    private String doc_foto = "-";
    private String id_jenis_hewan;
    ProgressDialog dialogProg;
    private List<spinner_jenis_hewan> jenisHewans = new ArrayList<>();
    private ArrayList<String> tempJenis = new ArrayList<>();
    private Spinner spinner_jenis;
    private int GALLERY = 1, CAMERA = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_produk);
        setAtribut();

        simpan_produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final String nama_layanan_hewan, nama_pengguna;
//                final Integer harga_layanan_hewan;
//                nama_layanan_hewan = nama_layanan.getText().toString();
//                harga_layanan_hewan = Integer.valueOf(harga_layanan.getText().toString());
//                nama_pengguna = show_person.getText().toString();
//
//                formValidation(nama_layanan_hewan);
//                tambahLayanan(nama_layanan_hewan, harga_layanan_hewan, id_jenis_hewan, id_ukuran_hewan, nama_pengguna);
//                System.out.println(message);
//                progDialog();
//                waitingResponse();
            }
        });

        batal_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                nama_layanan.getText().clear();
//                Toast.makeText(activity_tambah_layanan.this, "Batal Tambah Layanan!", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(activity_tambah_layanan.this, LayananFragment.class);
//                startActivity(intent);
            }
        });

        tambah_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    private void setAtribut(){
        nama_produk = (TextInputEditText) findViewById(R.id.tambah_produk);
        harga_produk = (TextInputEditText) findViewById(R.id.harga_produk_tambah);
        satuan_produk = (TextInputEditText) findViewById(R.id.satuan_produk);
        stok_produk = (TextInputEditText) findViewById(R.id.stok_produk);
        stok_minimal_produk = (TextInputEditText) findViewById(R.id.stok_minimal_produk);
        foto_produk = (ImageView) findViewById(R.id.foto_produk);
        simpan_produk = (Button) findViewById(R.id.button_simpan);
        batal_simpan = (Button) findViewById(R.id.button_batal);
        tambah_foto = (Button) findViewById(R.id.button_tambah_foto);
        show_calendar = (TextView) findViewById(R.id.show_calendar);
        show_person = (TextView) findViewById(R.id.show_person);
        show_person.setText(getIntent().getStringExtra("USERNAME"));
        dialogProg = new ProgressDialog(this);
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
    }

    public void setSpinner_jenis(){
        String url = "http://192.168.8.102/CI_Mobile_P3L_1F/index.php/jenishewan";
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
                                spinner_jenis_hewan r = new spinner_jenis_hewan(objectReview.getString("ID_JENIS_HEWAN"),
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

    private void selectImage() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_tambah_foto, null);
        Button camera = (Button) dialogView.findViewById(R.id.camera);
        Button gallery = (Button) dialogView.findViewById(R.id.gallery);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA);
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY);
            }
        });

        alertDialog.setView(dialogView);
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    public byte[] getStringImage(Bitmap bmp){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_OK) {
            if (requestCode == GALLERY) {
                if (data != null) {
                    Uri contentURI = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), contentURI);
                        foto_produk.setImageBitmap(getResizedBitmap(bitmap, 1024));
                        Toast.makeText(getApplicationContext(), "Foto Produk Berhasil Diambil", Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Gagal Ambil Foto!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            else if (requestCode == CAMERA){
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                foto_produk.setImageBitmap(bitmap);
                Toast.makeText(getApplicationContext(), "Foto Produk Berhasil Diambil", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public Bitmap getResizedBitmap(Bitmap foto_produk, int maxSize) {
        int width = foto_produk.getWidth();
        int height = foto_produk.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(foto_produk, width, height, true);
    }

//    private void formValidation(String nama_layanan_hewan) {
//        if (TextUtils.isEmpty(nama_layanan_hewan)) {
//            nama_layanan.setError("Field Tidak Boleh Kosong!");
//            return;
//        }
//    }
//
//    private void progDialog() {
//        dialog.setMessage("Menyimpan Data ...");
//        dialog.show();
//    }
//
//    private void waitingResponse() {
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if(message.equalsIgnoreCase("Berhasil")) {
//                    Toast.makeText(activity_tambah_layanan.this, "Data Layanan Berhasil Disimpan!", Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(activity_tambah_layanan.this, LayananFragment.class);
//                    startActivity(intent);
//                }
//                else if(message.equalsIgnoreCase("Gagal")) {
//                    Toast.makeText(activity_tambah_layanan.this, "Kesalahan Koneksi", Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(activity_tambah_layanan.this, LayananFragment.class);
//                    startActivity(intent);
//                }
//                dialog.dismiss();
//            }
//        }, 2000);
//    }
//
//    private void tambahLayanan(final String string_nama, final Integer int_harga, final String id_jenis_hewan, final String id_ukuran_hewan, final String nama_pengguna){
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//
//                            message = jsonObject.getString("message");
//                            data = jsonObject.getString("data");
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(activity_tambah_layanan.this, "Koneksi Terputus",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                }){
//
//            //datayangdiinput
//            @Override
//            protected Map<String, String> getParams(){
//                Map<String,String> params = new HashMap<String,String>();
//                params.put("ID_JENIS_HEWAN", id_jenis_hewan);
//                params.put("ID_UKURAN_HEWAN", id_ukuran_hewan);
//                params.put("NAMA_LAYANAN", string_nama);
//                params.put("HARGA_SATUAN_LAYANAN", String.valueOf(int_harga));
//                params.put("KETERANGAN", nama_pengguna);
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


