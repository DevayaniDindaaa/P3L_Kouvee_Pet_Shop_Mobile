package com.p3l_f_1_pegawai;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login_activity extends AppCompatActivity {
    private TextInputEditText edtusername, edtpassword;
    private Button masuk;
    private String data = "-";
    private String message = "-";
    String id_pengguna = "-";
    String id_role = "-";
    String nama_pengguna = "-";
    String username_pengguna = "-";
    String alamat_pengguna = "-";
    String tgl_lahir_pengguna = "-";
    String no_telp = "-";
    ProgressDialog dialog;
    private boolean doubleBackToExitPressedOnce;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtusername = (TextInputEditText) findViewById(R.id.username);
        edtpassword = (TextInputEditText) findViewById(R.id.password);
        masuk = (Button) findViewById(R.id.masuk);
        dialog = new ProgressDialog(this);

        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String usernameku, passwordku;
                usernameku = edtusername.getText().toString();
                passwordku = edtpassword.getText().toString();

                System.out.println("test");
                loginUser(usernameku, passwordku);
                System.out.println(message);
                progDialog();
                waitingResponse();
            }
        });
    }

    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tekan Tombol Kembali 2x untuk Keluar Aplikasi!", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    private void progDialog() {
        dialog.setMessage("Membuat Koneksi ...");
        dialog.show();
    }

    private void waitingResponse() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(message.equalsIgnoreCase("owner")) {
                    id_role = "Owner";
                    Intent i = new Intent(getApplicationContext(), drawer_activity_owner.class);
                    i.putExtra("ID_PEGAWAI", id_pengguna);
                    i.putExtra("ID_ROLE", id_role);
                    i.putExtra("NAMA_PEGAWAI", nama_pengguna);
                    i.putExtra("ALAMAT_PEGAWAI", alamat_pengguna);
                    i.putExtra("TGL_LAHIR_PEGAWAI", tgl_lahir_pengguna);
                    i.putExtra("USERNAME", username_pengguna);
                    i.putExtra("NO_TELP", no_telp);
                    Toast.makeText(login_activity.this, "Selamat Datang " + nama_pengguna + "!",
                            Toast.LENGTH_SHORT).show();
                    startActivity(i);
                }
                else if(message.equalsIgnoreCase("customer service")) {
                    id_role = "Customer Service";
                    Intent i = new Intent(getApplicationContext(), drawer_activity_cs.class);
                    i.putExtra("ID_PEGAWAI", id_pengguna);
                    i.putExtra("ID_ROLE", id_role);
                    i.putExtra("NAMA_PEGAWAI", nama_pengguna);
                    i.putExtra("ALAMAT_PEGAWAI", alamat_pengguna);
                    i.putExtra("TGL_LAHIR_PEGAWAI", tgl_lahir_pengguna);
                    i.putExtra("USERNAME", username_pengguna);
                    i.putExtra("NO_TELP", no_telp);
                    Toast.makeText(login_activity.this, "Selamat Datang " + nama_pengguna + "!",
                            Toast.LENGTH_SHORT).show();
                    startActivity(i);
                }
                else if(message.equalsIgnoreCase("kasir")){
                    Toast.makeText(login_activity.this, "Mohon Maaf, Kasir tidak Memiliki Akses ke Aplikasi Mobile!",
                            Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(login_activity.this, "Kesalahan Autentikasi, Silahkan Coba Lagi!",
                            Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }, 2000);
    }

    private void loginUser(final String usernameku, final String passwordku) {

        String url = "http://192.168.8.101/CI_Mobile_P3L_1F/index.php/pegawai/login";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            message = jsonObject.getString("message");
                            data = jsonObject.getString("data");

                            System.out.println("Message  : " + message);
                            System.out.println("Data : " + data);

                            String user = jsonObject.getString("data");
                            JSONObject objUser = new JSONObject(user);

                            id_pengguna = objUser.getString("ID_PEGAWAI");
                            id_role = objUser.getString("ID_ROLE");
                            nama_pengguna = objUser.getString("NAMA_PEGAWAI");
                            alamat_pengguna = objUser.getString("ALAMAT_PEGAWAI");
                            tgl_lahir_pengguna = objUser.getString("TGL_LAHIR_PEGAWAI");
                            username_pengguna = objUser.getString("USERNAME");
                            no_telp = objUser.getString("NO_TLP_PEGAWAI");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(login_activity.this, "Koneksi Terputus",
                                Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("USERNAME", usernameku);
                params.put("PASSWORD", passwordku);

                return params;
            }
        };
        postRequest.setRetryPolicy(
                new DefaultRetryPolicy(
                        50000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
        );
        queue.add(postRequest);
    }
}