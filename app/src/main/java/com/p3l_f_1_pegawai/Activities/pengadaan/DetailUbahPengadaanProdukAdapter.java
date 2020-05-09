package com.p3l_f_1_pegawai.Activities.pengadaan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import com.p3l_f_1_pegawai.dao.detail_pengadaanDAO;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailUbahPengadaanProdukAdapter extends RecyclerView.Adapter<DetailUbahPengadaanProdukAdapter.ViewHolder> {
    private List<detail_pengadaanDAO> DetailUbahPengadaanProdukList;
    private List<detail_pengadaanDAO> arrayList;
    private Context context;
    private String id_detail_pengadaan;
    private String message = "-";

    public DetailUbahPengadaanProdukAdapter(List<detail_pengadaanDAO> detailUbahPengadaanProdukList, Context context) {
        this.DetailUbahPengadaanProdukList = detailUbahPengadaanProdukList;
        this.arrayList = detailUbahPengadaanProdukList;
        this.context = context;
    }

    @NonNull
    @Override
    public DetailUbahPengadaanProdukAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_adapter_ubah_detail_pengadaan, parent, false);
        final DetailUbahPengadaanProdukAdapter.ViewHolder holder = new DetailUbahPengadaanProdukAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DetailUbahPengadaanProdukAdapter.ViewHolder holder, final int position) {
        final detail_pengadaanDAO row = DetailUbahPengadaanProdukList.get(position);
        final detail_pengadaanDAO data = arrayList.get(position);

        holder.nama_produk.setText(row.getNama_produk());
        holder.jumlah_produk.setText(String.valueOf(row.getJumlah_produk_dipesan()));
        holder.log_aktivitas.setText(row.getStatus_data() + " by " + row.getKeterangan() + " at " + row.getTime_stamp());

        holder.ubah_produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(), activity_ubah_detail_pengadaan.class);
                i.putExtra("id_detail_pengadaan", row.getId_detail_pengadaan());
                i.putExtra("nama_produk", row.getNama_produk());
                i.putExtra("jumlah_produk_pesan", row.getJumlah_produk_dipesan());
                context.getApplicationContext().startActivity(i);
            }
        });

        holder.hapus_produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_detail_pengadaan = row.getId_detail_pengadaan();
                hapusDetail(id_detail_pengadaan);
                arrayList.remove(data);
                notifyDataSetChanged();
            }
        });
    }

    private void hapusDetail(final String id_detail_pengadaan) {
        final String url = "http://192.168.8.103/CI_Mobile_P3L_1F/index.php/transaksipengadaan/deletedetail";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            message = jsonObject.getString("message");
                            Toast.makeText(context, "Data Detail Produk Berhasil Dihapus!", Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Koneksi Terputus",
                                Toast.LENGTH_SHORT).show();
                    }
                }){

            //datayangdiinput
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String,String>();
                params.put("ID_DETAIL_PENGADAAN", id_detail_pengadaan);
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

    @Override
    public int getItemCount() {
        return DetailUbahPengadaanProdukList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout recycle_detail;
        TextView nama_produk, jumlah_produk, log_aktivitas;
        Button ubah_produk, hapus_produk;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recycle_detail = itemView.findViewById(R.id.recycle_detail_ubah_pengadaan);
            nama_produk = itemView.findViewById(R.id.nama_produk_dipesan);
            jumlah_produk = itemView.findViewById(R.id.jumlah_produk_pesan);
            log_aktivitas = itemView.findViewById(R.id.log_aktivitas);
            hapus_produk = itemView.findViewById(R.id.hapus_pengadan_produk);
            ubah_produk = itemView.findViewById(R.id.edit_pengadaan_produk);
        }
    }
}
