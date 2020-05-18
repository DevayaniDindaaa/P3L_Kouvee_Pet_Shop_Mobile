package com.p3l_f_1_pegawai;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.p3l_f_1_pegawai.Activities.akun.AkunFragment;
import com.p3l_f_1_pegawai.Activities.pengadaan.PengadaanFragment;
import com.p3l_f_1_pegawai.Activities.pengadaan.activity_tambah_pengadaan;
import com.p3l_f_1_pegawai.dao.produkDAO;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class drawer_activity_owner extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private List<produkDAO> ProdukList;
    private static Uri alarmSound;
    private int messageCount = 0;
    private final long[] pattern = {101, 300, 300, 300};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_nav_activity_list_owner);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        ProdukList = new ArrayList<>();
        notifikasiStokProdukKurang();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_data_layanan, R.id.nav_data_produk, R.id.nav_data_supplier,
                R.id.nav_jenis_hewan, R.id.nav_ukuran_hewan, R.id.nav_pemesanan_produk, R.id.nav_hewan, R.id.nav_konsumen, R.id.nav_akun, R.id.nav_keluar)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    public void onBackPressed() { Toast.makeText(drawer_activity_owner.this, "Anda Sudah Masuk!", Toast.LENGTH_SHORT).show(); }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void notifikasiStokProdukKurang() {
        String url = "http://192.168.8.102/CI_Mobile_P3L_1F/index.php/produk";
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

                                ProdukList.add(r);
                            }
                            notifikasikurangdariStok(ProdukList);
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

    private void notifikasikurangdariStok(List<produkDAO> ProdukList) {
        System.out.println(ProdukList);
        for(int i=0; i<ProdukList.size(); i++){
            produkDAO data = ProdukList.get(i);
            if(data.getStok_produk()<data.getStok_minimal_produk()) {
                makeNotifikasi(data);
            }
        }
    }

    private void makeNotifikasi(produkDAO data) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "SIP";
            String description = "OKE";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(this, activity_tambah_pengadaan.class);
        intent.putExtra("Id", 1);
        intent.putExtra("IDSK", "1");

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.round_logo)
                .setTicker("Kouvee Pet Shop")
                .setContentTitle("Informasi Produk dengan Stok < Stok Minimal")
                .setContentText("Stok "+data.getNama_produk() +" Sisa "+ data.getStok_produk() + " " + data.getSatuan_produk())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(alarmSound)
                .setVibrate(pattern)
                .setNumber(++messageCount)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(0, builder.build());
    }
}
