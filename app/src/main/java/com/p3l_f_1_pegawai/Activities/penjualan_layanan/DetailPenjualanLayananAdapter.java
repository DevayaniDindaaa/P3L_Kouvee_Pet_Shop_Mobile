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

public class DetailPenjualanLayananAdapter extends RecyclerView.Adapter<DetailPenjualanLayananAdapter.ViewHolder> {
    private List<detail_penjualan_layananDAO> DetailPenjualanLayananList;
    private Context context;

    public DetailPenjualanLayananAdapter(List<detail_penjualan_layananDAO> detailPenjualanLayananList, Context context) {
        this.DetailPenjualanLayananList = detailPenjualanLayananList;
        this.context = context;
    }

    @NonNull
    @Override
    public DetailPenjualanLayananAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_adapter_detail_penjualan_layanan, parent, false);
        final DetailPenjualanLayananAdapter.ViewHolder holder = new DetailPenjualanLayananAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DetailPenjualanLayananAdapter.ViewHolder holder, int position) {
        final detail_penjualan_layananDAO row = DetailPenjualanLayananList.get(position);
        holder.nama_layanan.setText(row.getNama_layanan() + " " + row.getNama_jenis_hewan() + " " + row.getNama_ukuran_hewan());
        holder.harga_satuan.setText(String.valueOf("Rp " + row.getHarga_satuan_layanan()));
        holder.jumlah.setText(String.valueOf(row.getJumlah_layanan()));
        holder.total_harga.setText(String.valueOf("Rp " + row.getJumlah_harga_layanan()));
        holder.log_aktivitas.setText(row.getStatus_data() + " by " + row.getKeterangan() + " at " + row.getTime_stamp());
    }

    @Override
    public int getItemCount() {
        return DetailPenjualanLayananList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout recycle_detail;
        TextView nama_layanan, harga_satuan, jumlah, total_harga, log_aktivitas;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recycle_detail = itemView.findViewById(R.id.recycle_detail_penjualan_layanan);
            nama_layanan = itemView.findViewById(R.id.nama_layanan_detail);
            harga_satuan = itemView.findViewById(R.id.harga_satuan_layanan_detail);
            jumlah = itemView.findViewById(R.id.jumlah_layanan_detail);
            total_harga = itemView.findViewById(R.id.total_harga_layanan_detail);
            log_aktivitas = itemView.findViewById(R.id.log_aktivitas);
        }
    }
}