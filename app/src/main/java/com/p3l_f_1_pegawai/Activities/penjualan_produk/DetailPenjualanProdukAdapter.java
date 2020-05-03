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
import com.p3l_f_1_pegawai.dao.detail_penjualan_produkDAO;

import java.util.List;

public class DetailPenjualanProdukAdapter extends RecyclerView.Adapter<DetailPenjualanProdukAdapter.ViewHolder> {
    private List<detail_penjualan_produkDAO> DetailPenjualanProdukList;
    private Context context;

    public DetailPenjualanProdukAdapter(List<detail_penjualan_produkDAO> detailPenjualanProdukList, Context context) {
        this.DetailPenjualanProdukList = detailPenjualanProdukList;
        this.context = context;
    }

    @NonNull
    @Override
    public DetailPenjualanProdukAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_adapter_detail_penjualan_produk, parent, false);
        final DetailPenjualanProdukAdapter.ViewHolder holder = new DetailPenjualanProdukAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DetailPenjualanProdukAdapter.ViewHolder holder, int position) {
        final detail_penjualan_produkDAO row = DetailPenjualanProdukList.get(position);
        holder.nama_produk.setText(row.getNama_produk());
        holder.jenis_hewan.setText(row.getNama_jenis_hewan());
        holder.harga_satuan.setText(String.valueOf("Rp " + row.getHarga_satuan_produk()));
        holder.jumlah.setText(String.valueOf(row.getJumlah_produk()));
        holder.total_harga.setText(String.valueOf("Rp " + row.getJumlah_harga_produk()));
        holder.log_aktivitas.setText(row.getStatus_data() + " by " + row.getKeterangan() + " at " + row.getTime_stamp());
    }

    @Override
    public int getItemCount() {
        return DetailPenjualanProdukList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout recycle_detail;
        TextView jenis_hewan, nama_produk, harga_satuan, jumlah, total_harga, log_aktivitas;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recycle_detail = itemView.findViewById(R.id.recycle_detail_penjualan_produk);
            jenis_hewan = itemView.findViewById(R.id.jenis_produk_detail);
            nama_produk = itemView.findViewById(R.id.nama_produk_detail);
            harga_satuan = itemView.findViewById(R.id.harga_satuan_produk_detail);
            jumlah = itemView.findViewById(R.id.jumlah_produk_detail);
            total_harga = itemView.findViewById(R.id.total_harga_produk_detail);
            log_aktivitas = itemView.findViewById(R.id.log_aktivitas);
        }
    }
}