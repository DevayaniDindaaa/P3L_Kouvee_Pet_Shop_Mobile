package com.p3l_f_1_pegawai.Activities.layanan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.p3l_f_1_pegawai.R;
import com.p3l_f_1_pegawai.dao.layanan;
import com.p3l_f_1_pegawai.dao.ukuran_hewan;
import java.util.ArrayList;
import java.util.List;

public class LayananAdapter extends RecyclerView.Adapter<LayananAdapter.MyViewHolder> implements Filterable {
    private List<layanan> LayananList;
    private List<layanan> LayananFiltered;
    private Context context;

    public LayananAdapter(List<layanan> layananList, Context context) {
        this.LayananList = layananList;
        this.LayananFiltered = layananList;
        this.context = context;
    }

    @NonNull
    @Override
    public LayananAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_adapter_layanan, parent, false);
        final LayananAdapter.MyViewHolder holder = new LayananAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final layanan row = LayananFiltered.get(position);
        holder.nama_layanan.setText(row.getNama_layanan() + " " + row.getNama_jenis_hewan() + " " + row.getNama_ukuran_hewan());
        holder.harga_layanan.setText(String.valueOf(row.getHarga_layanan()));
        holder.log_aktivitas.setText(row.getStatus_data() + " by " + row.getKeterangan() + " at " + row.getTime_stamp());

        if(row.getStatus_data().equalsIgnoreCase("deleted")){
            holder.recycler_layanan.findViewById(R.id.hapus_layanan).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Data ini sudah dihapus!", Toast.LENGTH_LONG).show();
                }
            });
            holder.recycler_layanan.findViewById(R.id.edit_layanan).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Data ini sudah dihapus!", Toast.LENGTH_LONG).show();
                }
            });
        }
        else {
            holder.recycler_layanan.findViewById(R.id.hapus_layanan).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context.getApplicationContext(), activity_hapus_layanan.class);
                    i.putExtra("nama_layanan_hewan", row.getNama_layanan() + " " + row.getNama_jenis_hewan() + " " + row.getNama_ukuran_hewan() );
                    i.putExtra("keterangan", row.getKeterangan());
                    i.putExtra("id_layanan", row.getId_layanan());
                    i.putExtra("harga_layanan", String.valueOf(row.getHarga_layanan()));
                    context.getApplicationContext().startActivity(i);
                }
            });

            holder.recycler_layanan.findViewById(R.id.edit_layanan).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context.getApplicationContext(), activity_ubah_layanan.class);
                    i.putExtra("nama_layanan", row.getNama_layanan());
                    i.putExtra("nama_jenis_hewan", row.getNama_jenis_hewan());
                    i.putExtra("nama_ukuran_hewan", row.getNama_ukuran_hewan());
                    i.putExtra("keterangan", row.getKeterangan());
                    i.putExtra("id_layanan", row.getId_layanan());
                    i.putExtra("harga_layanan", String.valueOf(row.getHarga_layanan()));
                    context.getApplicationContext().startActivity(i);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return LayananFiltered.size();
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
                LayananFiltered = LayananList;
            }else{
                List<layanan> filteredList = new ArrayList<>();
                for(layanan layanan: LayananList){
                    if(layanan.getNama_layanan().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(layanan);
                    }else
                    if(layanan.getNama_ukuran_hewan().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(layanan);
                    }
                    else
                    if(layanan.getNama_jenis_hewan().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(layanan);
                    }
                }

                LayananFiltered = filteredList;
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = LayananFiltered;
            return  filterResults;
        }

        //runs on a ui thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            LayananFiltered = (List<layanan>) filterResults.values;
            notifyDataSetChanged();
        }
    };

    public interface UkuranHewanAdapterListener {
        void onUkuranHewanSelected(ukuran_hewan ukuran);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout recycler_layanan;
        TextView nama_layanan, harga_layanan, log_aktivitas;
        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);
            recycler_layanan = itemView.findViewById(R.id.recycle_layanan);
            nama_layanan = itemView.findViewById(R.id.nama_layanan);
            harga_layanan = itemView.findViewById(R.id.harga_layanan);
            log_aktivitas = itemView.findViewById(R.id.log_aktivitas);
        }
    }
}
