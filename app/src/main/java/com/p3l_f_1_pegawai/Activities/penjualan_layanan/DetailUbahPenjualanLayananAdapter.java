package com.p3l_f_1_pegawai.Activities.penjualan_layanan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.p3l_f_1_pegawai.R;
import com.p3l_f_1_pegawai.dao.detail_penjualan_layananDAO;

import java.util.List;

public class DetailUbahPenjualanLayananAdapter extends RecyclerView.Adapter<DetailUbahPenjualanLayananAdapter.ViewHolder> {
    private List<detail_penjualan_layananDAO> DetailPenjualanLayananList;
    private Context context;

    public DetailUbahPenjualanLayananAdapter(List<detail_penjualan_layananDAO> detailPenjualanLayananList, Context context) {
        this.DetailPenjualanLayananList = detailPenjualanLayananList;
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
        holder.nama_layanan.setText(row.getNama_layanan() + " " + row.getNama_jenis_hewan() + " " + row.getNama_ukuran_hewan());
        holder.jumlah.setText(String.valueOf(row.getJumlah_layanan()));
        holder.recycler_penjualan_layanan.findViewById(R.id.hapus_pesan_layanan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.recycler_penjualan_layanan.findViewById(R.id.edit_pesan_layanan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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