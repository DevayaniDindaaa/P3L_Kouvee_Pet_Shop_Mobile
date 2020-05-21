package com.p3l_f_1_pegawai.Activities.penjualan_produk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.p3l_f_1_pegawai.R;
import com.p3l_f_1_pegawai.dao.detailProduk_penjualanDAO;
import com.p3l_f_1_pegawai.dao.detail_penjualan_layananDAO;
import com.p3l_f_1_pegawai.dao.detail_penjualan_produkDAO;

import java.util.ArrayList;
import java.util.List;

public class DetailUbahPenjualanProdukAdapter extends RecyclerView.Adapter<DetailUbahPenjualanProdukAdapter.ViewHolder> {
    private List<detail_penjualan_produkDAO> DetailPenjualanProdukList;
    private Context context;

    public DetailUbahPenjualanProdukAdapter(List<detail_penjualan_produkDAO> detailPenjualanProdukList, Context context) {
        DetailPenjualanProdukList = detailPenjualanProdukList;
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
        holder.nama_produk.setText(row.getNama_produk());
        holder.jumlah_produk.setText(String.valueOf(row.getJumlah_produk()) + " " + row.getSatuan_produk());
        holder.jenis_hewan_pesan.setText(row.getNama_jenis_hewan());
        holder.recycler_penjualan_produk.findViewById(R.id.hapus_pesan_produk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.recycler_penjualan_produk.findViewById(R.id.edit_pesan_produk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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