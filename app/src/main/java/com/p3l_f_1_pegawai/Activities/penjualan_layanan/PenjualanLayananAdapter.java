package com.p3l_f_1_pegawai.Activities.penjualan_layanan;

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

import com.p3l_f_1_pegawai.R;
import com.p3l_f_1_pegawai.dao.penjualan_layananDAO;

import java.util.ArrayList;
import java.util.List;

public class PenjualanLayananAdapter extends RecyclerView.Adapter<PenjualanLayananAdapter.MyViewHolder> implements Filterable {
    private List<penjualan_layananDAO> PenjualanLayananList;
    private List<penjualan_layananDAO> PenjualanLayananFiltered;
    private Context context;

    public PenjualanLayananAdapter(List<penjualan_layananDAO> penjualanLayananList, Context context) {
        this.PenjualanLayananList = penjualanLayananList;
        this.PenjualanLayananFiltered = penjualanLayananList;
        this.context = context;
    }

    @NonNull
    @Override
    public PenjualanLayananAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_adapter_penjualan_layanan, parent, false);
        final PenjualanLayananAdapter.MyViewHolder holder = new PenjualanLayananAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PenjualanLayananAdapter.MyViewHolder holder, int position) {
        final penjualan_layananDAO row = PenjualanLayananFiltered.get(position);
        holder.nomor_transaksi.setText(row.getNo_transaksi_layanan());
        holder.nama_konsumen.setText(row.getNama_konsumen() + " <---> " + row.getNama_hewan());
        holder.tgl_transaksi.setText(row.getWaktu_transaksi_layanan());
        holder.status_pengerjaan.setText(row.getStatus_pengerjaan_layanan());
        holder.status_pembayaran.setText(row.getStatus_pembayaran_layanan());
        holder.recycler_penjualan_layanan.findViewById(R.id.lihat_detail_transaksi_layanan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(), activity_detail_penjualan_layanan.class);
                i.putExtra("no_transaksi", row.getNo_transaksi_layanan());
                i.putExtra("tgl_transaksi", row.getWaktu_transaksi_layanan());
                i.putExtra("nama_konsumen", row.getNama_konsumen());
                i.putExtra("status_member", row.getStatus_member());
                i.putExtra("nama_hewan", row.getNama_hewan());
                i.putExtra("jenis_hewan", row.getNama_jenis_hewan());
                i.putExtra("ukuran_hewan", row.getNama_ukuran_hewan());
                i.putExtra("nama_cs", row.getNama_cs());
                i.putExtra("nama_kasir", row.getNama_kasir());
                i.putExtra("sub_total", String.valueOf(row.getSub_total_layanan()));
                i.putExtra("diskon", String.valueOf(row.getDiskon_layanan()));
                i.putExtra("total_bayar", String.valueOf(row.getTotal_pembayaran_layanan()));
                i.putExtra("status_kerja", row.getStatus_pengerjaan_layanan());
                i.putExtra("status_bayar", row.getStatus_pembayaran_layanan());
                i.putExtra("details", row.getDetail_penjualan_layanan().toString());
                context.getApplicationContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return PenjualanLayananFiltered.size();
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
                PenjualanLayananFiltered = PenjualanLayananList;
            }else{
                List<penjualan_layananDAO> filteredList = new ArrayList<>();
                for(penjualan_layananDAO penjualan_layanan: PenjualanLayananList){
                    if(penjualan_layanan.getNo_transaksi_layanan().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(penjualan_layanan);
                    }
                    else if(penjualan_layanan.getNama_konsumen().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(penjualan_layanan);
                    }
                }

                PenjualanLayananFiltered = filteredList;
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = PenjualanLayananFiltered;
            return  filterResults;
        }

        //runs on a ui thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            PenjualanLayananFiltered = (List<penjualan_layananDAO>) filterResults.values;
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout recycler_penjualan_layanan;
        TextView nomor_transaksi, nama_konsumen, tgl_transaksi, status_pembayaran, status_pengerjaan;

        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);
            recycler_penjualan_layanan = itemView.findViewById(R.id.recycle_penjualan_layanan);
            nama_konsumen = itemView.findViewById(R.id.nama_konsumen);
            nomor_transaksi = itemView.findViewById(R.id.no_transaksi_layanan);
            tgl_transaksi = itemView.findViewById(R.id.tgl_transaksi_layanan);
            status_pembayaran = itemView.findViewById(R.id.status_pembayaran_layanan);
            status_pengerjaan = itemView.findViewById(R.id.status_pengerjaan_layanan);
        }
    }
}