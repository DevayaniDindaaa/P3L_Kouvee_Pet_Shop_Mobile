package com.p3l_f_1_pegawai.Activities.penjualan_produk;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.p3l_f_1_pegawai.Activities.pengadaan.activity_ubah_detail_pengadaan;
import com.p3l_f_1_pegawai.R;
import com.p3l_f_1_pegawai.dao.detailProduk_penjualanDAO;
import com.p3l_f_1_pegawai.dao.detail_penjualan_layananDAO;
import com.p3l_f_1_pegawai.dao.detail_penjualan_produkDAO;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailUbahPenjualanProdukAdapter extends RecyclerView.Adapter<DetailUbahPenjualanProdukAdapter.ViewHolder> {
    private List<detail_penjualan_produkDAO> DetailPenjualanProdukList;
    private List<detail_penjualan_produkDAO> arrayList;
    private Context context;
    private String id_detail_produk;
    private String message = "-";

    public DetailUbahPenjualanProdukAdapter(List<detail_penjualan_produkDAO> detailPenjualanProdukList, Context context) {
        this.DetailPenjualanProdukList = detailPenjualanProdukList;
        this.arrayList = detailPenjualanProdukList;
        this.context = context;
    }

    @NonNull
    @Override
    public DetailUbahPenjualanProdukAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_adapter_ubah_hapus_penjualan_produk, parent, false);
        final DetailUbahPenjualanProdukAdapter.ViewHolder holder = new DetailUbahPenjualanProdukAdapter.ViewHolder(view, context);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DetailUbahPenjualanProdukAdapter.ViewHolder holder, final int position) {
        final detail_penjualan_produkDAO row = DetailPenjualanProdukList.get(position);
        final detail_penjualan_produkDAO data = arrayList.get(position);

        holder.nama_produk.setText(row.getNama_produk());
        holder.jumlah_produk.setText(String.valueOf(row.getJumlah_produk()) + " " + row.getSatuan_produk());
        holder.jenis_hewan_pesan.setText(row.getNama_jenis_hewan());
        holder.recycler_penjualan_produk.findViewById(R.id.hapus_pesan_produk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_detail_produk = row.getId_detail_trans_produk();
                hapusDetail(id_detail_produk);
                arrayList.remove(data);
                notifyDataSetChanged();
            }
        });
        holder.recycler_penjualan_produk.findViewById(R.id.edit_pesan_produk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(), activity_ubah_detail_penjualan_produk.class);
                i.putExtra("id_detail_penjualan_produk", row.getId_detail_trans_produk());
                i.putExtra("nama_produk", row.getNama_produk());
                i.putExtra("jumlah_produk_pesan", row.getJumlah_produk().toString());
                i.putExtra("id_produk", row.getId_produk());
                context.getApplicationContext().startActivity(i);
            }
        });
    }

    private void hapusDetail(final String id_detail_produk) {
        final String url = "http://192.168.8.101/CI_Mobile_P3L_1F/index.php/transaksiproduk/deletedetail";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            message = jsonObject.getString("message");
                            Toast.makeText(context, "Produk yang Dipesan Berhasil Dihapus!", Toast.LENGTH_LONG).show();

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
                params.put("ID_DETAIL_TRANS_PRODUK", id_detail_produk);
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
        return DetailPenjualanProdukList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Context context;
        LinearLayout recycler_penjualan_produk;
        TextView nama_produk, jumlah_produk, jenis_hewan_pesan;

        public ViewHolder(@NonNull View itemView, Context Mcontext) {
            super(itemView);
            this.context = Mcontext;

            recycler_penjualan_produk = itemView.findViewById(R.id.recycle_ubah_penjualan_produk);
            nama_produk = itemView.findViewById(R.id.nama_produk_dipesan);
            jumlah_produk = itemView.findViewById(R.id.jumlah_produk_pesan);
            jenis_hewan_pesan = itemView.findViewById(R.id.jenis_hewan_pesan);
        }
    }
}