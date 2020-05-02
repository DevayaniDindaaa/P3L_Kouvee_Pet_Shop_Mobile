package com.p3l_f_1_pegawai.Activities.konsumen;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.p3l_f_1_pegawai.R;
import com.p3l_f_1_pegawai.dao.hewanDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class activity_detail_konsumen extends AppCompatActivity {
    Activity context;
    private List<hewanDAO> DetailHewanKonsumen;
    private RecyclerView recyclerView;
    private DetailKonsumenAdapter recycleAdapter;
    private TextView nama_konsumen, alamat_konsumen, tgl_lahir_konsumen, nomor_telepon, status_member, status_data;
    String nama_user;
    private Button ubah_konsumen, hapus_konsumen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_konsumen);
        setAtribut();

        DetailHewanKonsumen = new ArrayList<>();

        ubah_konsumen = findViewById(R.id.ubah_konsumen);
        hapus_konsumen = findViewById(R.id.hapus_konsumen);
        recyclerView = findViewById(R.id.recycle_tampil_detail_hewankonsumen);
        recycleAdapter = new DetailKonsumenAdapter(DetailHewanKonsumen, this);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recycleAdapter);

        getDetailHewan();

        ubah_konsumen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nama_user.equalsIgnoreCase("owner")){
                    Toast.makeText(context, "Owner tidak bisa mengubah data konsumen!", Toast.LENGTH_SHORT).show();
                }
                else{
                    //DISINI INTENT KE HALAMAN UBAH DATA KONSUMEN
                }
            }});

        hapus_konsumen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nama_user.equalsIgnoreCase("owner")){
                    Toast.makeText(context, "Owner tidak bisa menghapus data konsumen!", Toast.LENGTH_SHORT).show();
                }
                else{
                    //DISINI INTENT KE HALAMAN HAPUS DATA KONSUMEN
                }
            }
        });

    }

    private void getDetailHewan() {
        try {
            String detailHewan = getIntent().getStringExtra("details");
            JSONArray detail = new JSONArray(detailHewan);
            for (int j = 0; j < detail.length(); j++) {
                JSONObject objectDetail = detail.getJSONObject(j);
                hewanDAO d = new hewanDAO(objectDetail.getString("id_hewan"),
                        objectDetail.getString("nama_jenis_hewan"),
                        objectDetail.getString("nama_ukuran_hewan"),
                        objectDetail.getString("nama_konsumen"),
                        objectDetail.getString("alamat_konsumen"),
                        objectDetail.getString("tgl_lahir_konsumen"),
                        objectDetail.getString("no_tlp_konsumen"),
                        objectDetail.getString("status_member"),
                        objectDetail.getString("nama_hewan"),
                        objectDetail.getString("tgl_lahir_hewan"),
                        objectDetail.getString("status_data"),
                        objectDetail.getString("time_stamp"),
                        objectDetail.getString("keterangan"));
                DetailHewanKonsumen.add(d);
            }
            recycleAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setAtribut(){
        //nama_user = context.getIntent().getExtras().getString("USERNAME");
        nama_konsumen = findViewById(R.id.nama_konsumen_detail);
        nama_konsumen.setText(getIntent().getStringExtra("nama_konsumen"));
        alamat_konsumen = findViewById(R.id.alamat_konsumen_detail);
        alamat_konsumen.setText(getIntent().getStringExtra("alamat_konsumen"));
        tgl_lahir_konsumen = findViewById(R.id.tgl_lahir_konsumen_detail);
        tgl_lahir_konsumen.setText(getIntent().getStringExtra("tgl_lahir_konsumen"));
        nomor_telepon = findViewById(R.id.no_tlp_konsumen_detail);
        nomor_telepon.setText(getIntent().getStringExtra("no_tlp_konsumen"));
        status_member = findViewById(R.id.status_member_konsumen_detail);
        status_member.setText(getIntent().getStringExtra("status_member"));
        status_data = findViewById(R.id.log_aktivitas);
        status_data.setText(getIntent().getStringExtra("status_data"));

        ubah_konsumen = findViewById(R.id.ubah_konsumen);
        hapus_konsumen = findViewById(R.id.hapus_konsumen);
    }
}