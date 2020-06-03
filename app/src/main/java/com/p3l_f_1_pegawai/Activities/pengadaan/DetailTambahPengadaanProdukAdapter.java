package com.p3l_f_1_pegawai.Activities.pengadaan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.p3l_f_1_pegawai.R;
import com.p3l_f_1_pegawai.dao.detailProduk_pengadaanDAO;
import com.p3l_f_1_pegawai.dao.produkDAO;

import java.util.ArrayList;
import java.util.List;

public class DetailTambahPengadaanProdukAdapter extends RecyclerView.Adapter<DetailTambahPengadaanProdukAdapter.ViewHolder> {
    private Context Mcontext;
    private ArrayList<detailProduk_pengadaanDAO> arrayList;

    public DetailTambahPengadaanProdukAdapter(ArrayList<detailProduk_pengadaanDAO> arrayList, Context Mcontext) {
        this.arrayList = arrayList;
        this.Mcontext = Mcontext;
    }

    @NonNull
    @Override
    public DetailTambahPengadaanProdukAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(Mcontext).inflate(R.layout.recycle_adapter_tambah_detail_pengadaan, parent, false);
        final DetailTambahPengadaanProdukAdapter.ViewHolder holder = new DetailTambahPengadaanProdukAdapter.ViewHolder(view, Mcontext);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DetailTambahPengadaanProdukAdapter.ViewHolder holder, final int position) {
        final detailProduk_pengadaanDAO row = arrayList.get(position);
        holder.nama_produk.setText(row.getNama_produk());
        holder.jumlah_produk.setText(String.valueOf(row.getJumlah_produk()) + " " + row.getSatuan_produk());
        holder.recycler_detail_pengadaan.findViewById(R.id.hapus_pesan_produk).setOnClickListener(new View.OnClickListener() {
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
        LinearLayout recycler_detail_pengadaan;
        TextView nama_produk, jumlah_produk;

        public ViewHolder(@NonNull View itemView, Context Mcontext) {
            super(itemView);
            this.context = Mcontext;

            recycler_detail_pengadaan = itemView.findViewById(R.id.recycle_detail_tambah_pengadaan);
            nama_produk = itemView.findViewById(R.id.nama_produk_dipesan);
            jumlah_produk = itemView.findViewById(R.id.jumlah_produk_pesan);
        }
    }
}