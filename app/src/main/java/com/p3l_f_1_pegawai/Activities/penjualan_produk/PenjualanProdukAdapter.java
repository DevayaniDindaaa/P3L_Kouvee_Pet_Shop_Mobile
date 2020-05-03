package com.p3l_f_1_pegawai.Activities.penjualan_produk;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.p3l_f_1_pegawai.Activities.penjualan_layanan.PenjualanLayananAdapter;
import com.p3l_f_1_pegawai.R;
import com.p3l_f_1_pegawai.dao.penjualan_layananDAO;
import com.p3l_f_1_pegawai.dao.penjualan_produkDAO;

import java.util.ArrayList;
import java.util.List;

public class PenjualanProdukAdapter extends RecyclerView.Adapter<PenjualanProdukAdapter.MyViewHolder> implements Filterable {
    private List<penjualan_produkDAO> PenjualanProdukList;
    private List<penjualan_produkDAO> PenjualanProdukFiltered;
    private Context context;

    public PenjualanProdukAdapter(List<penjualan_produkDAO> penjualanProdukList, Context context) {
        this.PenjualanProdukList = penjualanProdukList;
        this.PenjualanProdukFiltered = penjualanProdukList;
        this.context = context;
    }

    @NonNull
    @Override
    public PenjualanProdukAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_adapter_penjualan_produk, parent, false);
        final PenjualanProdukAdapter.MyViewHolder holder = new PenjualanProdukAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PenjualanProdukAdapter.MyViewHolder holder, int position) {
        final penjualan_produkDAO row = PenjualanProdukFiltered.get(position);
        holder.nomor_transaksi.setText(row.getNo_transaksi_produk());
        holder.nama_konsumen.setText(row.getNama_konsumen() + " <---> " + row.getNama_hewan());
        holder.tgl_transaksi.setText(row.getWaktu_transaksi_produk());
        holder.status_pembayaran.setText(row.getStatus_pembayaran_produk());
        holder.recycler_penjualan_produk.findViewById(R.id.lihat_detail_transaksi_produk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(), activity_detail_penjualan_produk.class);
                i.putExtra("no_transaksi", row.getNo_transaksi_produk());
                i.putExtra("tgl_transaksi", row.getWaktu_transaksi_produk());
                i.putExtra("nama_konsumen", row.getNama_konsumen());
                i.putExtra("status_member", row.getStatus_member());
                i.putExtra("nama_hewan", row.getNama_hewan());
                i.putExtra("jenis_hewan", row.getNama_jenis_hewan());
                i.putExtra("nama_cs", row.getNama_cs());
                i.putExtra("nama_kasir", row.getNama_kasir());
                i.putExtra("sub_total", String.valueOf(row.getSub_total_produk()));
                i.putExtra("diskon", String.valueOf(row.getDiskon_produk()));
                i.putExtra("total_bayar", String.valueOf(row.getTotal_pembayaran_produk()));
                i.putExtra("status_bayar", row.getStatus_pembayaran_produk());
                i.putExtra("details", row.getDetail_penjualan_produk().toString());
                context.getApplicationContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return PenjualanProdukFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {

        //run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if(charSequence.toString().isEmpty()){
                PenjualanProdukFiltered = PenjualanProdukList;
            }else{
                List<penjualan_produkDAO> filteredList = new ArrayList<>();
                for(penjualan_produkDAO penjualan_produk: PenjualanProdukList){
                    if(penjualan_produk.getNo_transaksi_produk().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(penjualan_produk);
                    }
                    else if(penjualan_produk.getNama_konsumen().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(penjualan_produk);
                    }
                }

                PenjualanProdukFiltered = filteredList;
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = PenjualanProdukFiltered;
            return  filterResults;
        }

        //runs on a ui thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            PenjualanProdukFiltered = (List<penjualan_produkDAO>) filterResults.values;
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout recycler_penjualan_produk;
        TextView nomor_transaksi, nama_konsumen, tgl_transaksi, status_pembayaran;

        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);
            recycler_penjualan_produk = itemView.findViewById(R.id.recycle_penjualan_produk);
            nama_konsumen = itemView.findViewById(R.id.nama_konsumen);
            nomor_transaksi = itemView.findViewById(R.id.no_transaksi_produk);
            tgl_transaksi = itemView.findViewById(R.id.tgl_transaksi_produk);
            status_pembayaran = itemView.findViewById(R.id.status_pembayaran_produk);
        }
    }
}