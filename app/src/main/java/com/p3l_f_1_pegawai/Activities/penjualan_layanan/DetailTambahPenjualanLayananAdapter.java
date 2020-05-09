package com.p3l_f_1_pegawai.Activities.penjualan_layanan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.p3l_f_1_pegawai.Activities.penjualan_produk.DetailTambahPenjualanProdukAdapter;
import com.p3l_f_1_pegawai.R;
import com.p3l_f_1_pegawai.dao.detailLayanan_penjualanDAO;
import com.p3l_f_1_pegawai.dao.detailProduk_penjualanDAO;
import com.p3l_f_1_pegawai.dao.produkDAO;

import java.util.ArrayList;
import java.util.List;

public class DetailTambahPenjualanLayananAdapter extends RecyclerView.Adapter<DetailTambahPenjualanLayananAdapter.ViewHolder> {
    private Context Mcontext;
    private ArrayList<detailLayanan_penjualanDAO> arrayList;

    public DetailTambahPenjualanLayananAdapter(ArrayList<detailLayanan_penjualanDAO> arrayList, Context Mcontext) {
        this.arrayList = arrayList;
        this.Mcontext = Mcontext;
    }

    @NonNull
    @Override
    public DetailTambahPenjualanLayananAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(Mcontext).inflate(R.layout.recycle_adapter_tambah_penjualan_layanan, parent, false);
        final DetailTambahPenjualanLayananAdapter.ViewHolder holder = new DetailTambahPenjualanLayananAdapter.ViewHolder(view, Mcontext);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DetailTambahPenjualanLayananAdapter.ViewHolder holder, final int position) {
        final detailLayanan_penjualanDAO row = arrayList.get(position);
        holder.nama_layanan.setText(row.getNama_layanan());
        holder.jumlah_layanan.setText(String.valueOf(row.getJumlah_layanan()));
        holder.recycler_penjualan_layanan.findViewById(R.id.hapus_pesan_layanan).setOnClickListener(new View.OnClickListener() {
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
        LinearLayout recycler_penjualan_layanan;
        TextView nama_layanan, jumlah_layanan;

        public ViewHolder(@NonNull View itemView, Context Mcontext) {
            super(itemView);
            this.context = Mcontext;

            recycler_penjualan_layanan = itemView.findViewById(R.id.recycle_tambah_penjualan_layanan);
            nama_layanan = itemView.findViewById(R.id.nama_layanan_dipesan);
            jumlah_layanan = itemView.findViewById(R.id.jumlah_layanan_dipesan);
        }
    }
}