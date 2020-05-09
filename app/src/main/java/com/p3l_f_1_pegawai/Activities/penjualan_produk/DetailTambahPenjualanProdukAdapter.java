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

import java.util.ArrayList;

public class DetailTambahPenjualanProdukAdapter extends RecyclerView.Adapter<DetailTambahPenjualanProdukAdapter.ViewHolder> {
    private Context Mcontext;
    private ArrayList<detailProduk_penjualanDAO> arrayList;

    public DetailTambahPenjualanProdukAdapter(ArrayList<detailProduk_penjualanDAO> arrayList, Context Mcontext) {
        this.arrayList = arrayList;
        this.Mcontext = Mcontext;
    }

    @NonNull
    @Override
    public DetailTambahPenjualanProdukAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(Mcontext).inflate(R.layout.recycle_adapter_tambah_penjualan_produk, parent, false);
        final DetailTambahPenjualanProdukAdapter.ViewHolder holder = new DetailTambahPenjualanProdukAdapter.ViewHolder(view, Mcontext);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DetailTambahPenjualanProdukAdapter.ViewHolder holder, final int position) {
        final detailProduk_penjualanDAO row = arrayList.get(position);
        holder.nama_produk.setText(row.getNama_produk());
        holder.jumlah_produk.setText(String.valueOf(row.getJumlah_produk()) + " " + row.getSatuan_produk());
        holder.jenis_hewan_pesan.setText(row.getJenis_hewan());
        holder.recycler_penjualan_produk.findViewById(R.id.hapus_pesan_produk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.remove(row);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Context context;
        LinearLayout recycler_penjualan_produk;
        TextView nama_produk, jumlah_produk, jenis_hewan_pesan;

        public ViewHolder(@NonNull View itemView, Context Mcontext) {
            super(itemView);
            this.context = Mcontext;

            recycler_penjualan_produk = itemView.findViewById(R.id.recycle_tambah_penjualan_produk);
            nama_produk = itemView.findViewById(R.id.nama_produk_dipesan);
            jumlah_produk = itemView.findViewById(R.id.jumlah_produk_pesan);
            jenis_hewan_pesan = itemView.findViewById(R.id.jenis_hewan_pesan);
        }
    }
}