package com.p3l_f_1_pegawai.Activities.penjualan_layanan;

import android.content.Context;
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
import com.p3l_f_1_pegawai.R;
import com.p3l_f_1_pegawai.dao.detail_penjualan_layananDAO;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailUbahPenjualanLayananAdapter extends RecyclerView.Adapter<DetailUbahPenjualanLayananAdapter.ViewHolder> {
    private List<detail_penjualan_layananDAO> DetailPenjualanLayananList;
    private List<detail_penjualan_layananDAO> arrayList;
    private Context context;
    private String id_detail_layanan;
    private String message = "-";

    public DetailUbahPenjualanLayananAdapter(List<detail_penjualan_layananDAO> detailPenjualanLayananList, Context context) {
        this.DetailPenjualanLayananList = detailPenjualanLayananList;
        this.arrayList = detailPenjualanLayananList;
        this.context = context;
    }

    @NonNull
    @Override
    public DetailUbahPenjualanLayananAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_adapter_ubah_hapus_penjualan_layanan, parent, false);
        final DetailUbahPenjualanLayananAdapter.ViewHolder holder = new DetailUbahPenjualanLayananAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DetailUbahPenjualanLayananAdapter.ViewHolder holder, int position) {
        final detail_penjualan_layananDAO row = DetailPenjualanLayananList.get(position);
        final detail_penjualan_layananDAO data = arrayList.get(position);

        holder.nama_layanan.setText(row.getNama_layanan() + " " + row.getNama_jenis_hewan() + " " + row.getNama_ukuran_hewan());
        holder.jumlah.setText(String.valueOf(row.getJumlah_layanan()));
        holder.recycler_penjualan_layanan.findViewById(R.id.hapus_pesan_layanan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_detail_layanan = row.getId_detail_trans_layanan();
                hapusDetail(id_detail_layanan);
                arrayList.remove(data);
                notifyDataSetChanged();
            }
        });
    }

    private void hapusDetail(final String id_detail_layanan) {
        final String url = "http://192.168.8.102/CI_Mobile_P3L_1F/index.php/transaksilayanan/deletedetail";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            message = jsonObject.getString("message");
                            Toast.makeText(context, "Layanan yang Dipesan Berhasil Dihapus!", Toast.LENGTH_LONG).show();

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
                params.put("ID_DETAIL_TRANS_LAYANAN", id_detail_layanan);
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
        return DetailPenjualanLayananList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama_layanan, jumlah;
        LinearLayout recycler_penjualan_layanan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recycler_penjualan_layanan = itemView.findViewById(R.id.recycle_ubah_penjualan_layanan);
            nama_layanan = itemView.findViewById(R.id.nama_layanan_dipesan);
            jumlah = itemView.findViewById(R.id.jumlah_layanan_dipesan);
        }
    }
}