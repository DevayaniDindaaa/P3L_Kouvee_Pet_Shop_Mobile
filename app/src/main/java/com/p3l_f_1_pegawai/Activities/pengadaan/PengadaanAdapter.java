package com.p3l_f_1_pegawai.Activities.pengadaan;

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
import com.p3l_f_1_pegawai.dao.pengadaanDAO;

import java.util.ArrayList;
import java.util.List;

public class PengadaanAdapter extends RecyclerView.Adapter<PengadaanAdapter.MyViewHolder> implements Filterable {
    private List<pengadaanDAO> PengadaanList;
    private List<pengadaanDAO> PengadaanFiltered;
    private Context context;

    public PengadaanAdapter(List<pengadaanDAO> pengadaanList, Context context) {
        this.PengadaanList = pengadaanList;
        this.PengadaanFiltered = pengadaanList;
        this.context = context;
    }

    @NonNull
    @Override
    public PengadaanAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_adapter_pengadaan, parent, false);
        final PengadaanAdapter.MyViewHolder holder = new PengadaanAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PengadaanAdapter.MyViewHolder holder, int position) {
        final pengadaanDAO row = PengadaanFiltered.get(position);
        holder.nomor_pemesanan.setText(row.getNomor_pemesanan());
        holder.nama_supplier.setText(row.getNama_supplier());
        holder.tgl_pemesanan.setText(row.getTgl_pemesanan());
        if(row.getTgl_cetak_surat().equalsIgnoreCase("null")){
            holder.tgl_cetak_surat.setText("-");
        }
        else{
            holder.tgl_cetak_surat.setText(row.getTgl_cetak_surat());
        }
        holder.status_kedatangan.setText(row.getStatus_kedatangan_produk());

        holder.recycler_pengadaan.findViewById(R.id.lihat_detail_pengadaan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(), activity_detail_pengadaan.class);
                i.putExtra("nomor_pemesanan", row.getNomor_pemesanan());
                i.putExtra("tanggal_pemesanan", row.getTgl_pemesanan());
                i.putExtra("tanggal_cetak", row.getTgl_cetak_surat());
                i.putExtra("id_supplier", row.getId_supplier());
                i.putExtra("nama_supplier", row.getNama_supplier());
                i.putExtra("alamat_supplier", row.getAlamat_supplier() + ", " + row.getKota_supplier());
                i.putExtra("telepon_supplier", row.getNo_tlp_supplier());
                i.putExtra("status_kedatangan", row.getStatus_kedatangan_produk());
                i.putExtra("details", row.getDetail_pengadaan().toString());
                context.getApplicationContext().startActivity(i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return PengadaanFiltered.size();
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
                PengadaanFiltered = PengadaanList;
            }else{
                List<pengadaanDAO> filteredList = new ArrayList<>();
                for(pengadaanDAO pengadaan: PengadaanList){
                    if(pengadaan.getNomor_pemesanan().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(pengadaan);
                    }
                    else if(pengadaan.getNama_supplier().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(pengadaan);
                    }
                }

                PengadaanFiltered = filteredList;
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = PengadaanFiltered;
            return  filterResults;
        }

        //runs on a ui thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            PengadaanFiltered = (List<pengadaanDAO>) filterResults.values;
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout recycler_pengadaan;
        TextView nomor_pemesanan, nama_supplier, tgl_pemesanan, tgl_cetak_surat, status_kedatangan;

        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);
            recycler_pengadaan = itemView.findViewById(R.id.recycle_pengadaan);
            nomor_pemesanan = itemView.findViewById(R.id.no_pemesanan);
            nama_supplier = itemView.findViewById(R.id.nama_supplier_pemesanan);
            tgl_pemesanan = itemView.findViewById(R.id.tgl_pemesanan);
            tgl_cetak_surat = itemView.findViewById(R.id.tgl_cetak_surat);
            status_kedatangan = itemView.findViewById(R.id.status_kedatangan);
        }
    }
}