package com.p3l_f_1_pegawai.Activities.produk;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.p3l_f_1_pegawai.R;
import com.p3l_f_1_pegawai.dao.spinner_jenis_hewan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class activity_tambah_produk extends AppCompatActivity {
    private String URLline = "http://192.168.8.102/CI_Mobile_P3L_1F/index.php/produk";
    private TextInputEditText nama_produk, satuan_produk, harga_produk, stok_produk, stok_minimal_produk;
    private ImageView foto_produk;
    private Button tambah_foto, simpan_produk, batal_simpan;
    private TextView show_calendar, show_person;
    private String data = "-";
    private String message = "-";
    private String doc_foto = "-";
    private String Numeric = "\\d+";
    private String id_jenis_hewan;
    private Bitmap bitmap;
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
                final String nama_produk_hewan, nama_pengguna, satuan_produk_hewan;
                final Integer harga_produk_hewan, stok_produk_hewan, stok_minimal_produk_hewan;
                nama_produk_hewan = nama_produk.getText().toString();
                stok_produk_hewan = Integer.valueOf(stok_produk.getText().toString());
                stok_minimal_produk_hewan = Integer.valueOf(stok_minimal_produk.getText().toString());
                harga_produk_hewan = Integer.valueOf(harga_produk.getText().toString());
                nama_pengguna = show_person.getText().toString();
                satuan_produk_hewan = satuan_produk.getText().toString();

                formValidation(nama_produk_hewan, String.valueOf(satuan_produk_hewan), String.valueOf(stok_produk_hewan), String.valueOf(harga_produk_hewan), String.valueOf(stok_minimal_produk_hewan));
                tambahProduk(nama_produk_hewan, satuan_produk_hewan, harga_produk_hewan, stok_produk_hewan, stok_minimal_produk_hewan, id_jenis_hewan, nama_pengguna, bitmap);
                System.out.println(message);
                progDialog();
                waitingResponse();
            }
        });

        batal_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity_tambah_produk.this, "Batal Tambah Produk!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(activity_tambah_produk.this, ProdukFragment.class);
                startActivity(intent);
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
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
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
                        bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), contentURI);
                        foto_produk.setImageBitmap(getResizedBitmap(bitmap, 1024));
                        Toast.makeText(getApplicationContext(), "Foto Produk Berhasil Diambil", Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Gagal Ambil Foto!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            else if (requestCode == CAMERA){
                bitmap = (Bitmap) data.getExtras().get("data");
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

    private void formValidation(String nama_produk_hewan, String satuan_produk_hewan, String stok_produk_hewan, String harga_produk_hewan, String stok_minimal_produk_hewan) {
        if (TextUtils.isEmpty(nama_produk_hewan)) {
            nama_produk.setError("Field Tidak Boleh Kosong!");
            return;
        }

        if (TextUtils.isEmpty(satuan_produk_hewan)) {
            satuan_produk.setError("Field Tidak Boleh Kosong!");
            return;
        }

        if (TextUtils.isEmpty(stok_produk_hewan)) {
            stok_produk.setError("Field Tidak Boleh Kosong!");
            return;
        }

        if (TextUtils.isEmpty(stok_minimal_produk_hewan)) {
            stok_minimal_produk.setError("Field Tidak Boleh Kosong!");
            return;
        }

        if (TextUtils.isEmpty(harga_produk_hewan)) {
            harga_produk.setError("Field Tidak Boleh Kosong!");
            return;
        }

        if (!Pattern.matches(Numeric, stok_produk_hewan)){
            stok_produk.setError("Stok Hanya dalam Bentuk Angka");
            return;
        }

        if (!Pattern.matches(Numeric, stok_minimal_produk_hewan)){
            stok_minimal_produk.setError("Stok Minimal Hanya dalam Bentuk Angka");
            return;
        }

        if (!Pattern.matches(Numeric, harga_produk_hewan)){
            harga_produk.setError("Harga Hanya dalam Bentuk Angka");
            return;
        }
    }

    private void progDialog() {
        dialogProg.setMessage("Menyimpan Data ...");
        dialogProg.show();
    }

    private void waitingResponse() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(message.equalsIgnoreCase("Berhasil")) {
                    Toast.makeText(activity_tambah_produk.this, "Data Produk Berhasil Disimpan!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(activity_tambah_produk.this, ProdukFragment.class);
                    startActivity(intent);
                }
                else if(message.equalsIgnoreCase("Gagal")) {
                    Toast.makeText(activity_tambah_produk.this, "Kesalahan Koneksi", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(activity_tambah_produk.this, ProdukFragment.class);
                    startActivity(intent);
                }
                dialogProg.dismiss();
            }
        }, 2000);
    }

    private void tambahProduk(final String string_nama, final String string_satuan, final Integer int_harga, final Integer int_stok, final Integer int_stok_minimal, final String id_jenis_hewan, final String nama_pengguna, final Bitmap bitmap){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        VolleyMultiPartRequest multipartRequest = new VolleyMultiPartRequest(Request.Method.POST, URLline,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject jsonObject = new JSONObject((Map) response);

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
                        Toast.makeText(activity_tambah_produk.this, "Koneksi Terputus",
                                Toast.LENGTH_SHORT).show();
                    }
                }){

            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<String,String>();
                params.put("ID_JENIS_HEWAN", id_jenis_hewan);
                params.put("NAMA_PRODUK", string_nama);
                params.put("SATUAN_PRODUK", string_satuan);
                params.put("STOK_PRODUK", String.valueOf(int_stok));
                params.put("STOK_MINIMAL_PRODUK", String.valueOf(int_stok_minimal));
                params.put("HARGA_SATUAN_PRODUK", String.valueOf(int_harga));
                params.put("KETERANGAN", nama_pengguna);
                return params;
            }

            @Override
            protected Map<String, VolleyMultiPartRequest.DataPart> getByteData() {
                Map<String, VolleyMultiPartRequest.DataPart> params = new HashMap<>();
                if (bitmap != null){
                    long nama_foto = System.currentTimeMillis();
                    params.put("FOTO_PRODUK", new VolleyMultiPartRequest.DataPart(nama_foto + ".png", getStringImage(bitmap)));
                    return params;
                }
                else
                    return null;
            }
        };

        multipartRequest.setRetryPolicy(
                new DefaultRetryPolicy(
                        50000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
        );

        requestQueue.add(multipartRequest);
    }
}


