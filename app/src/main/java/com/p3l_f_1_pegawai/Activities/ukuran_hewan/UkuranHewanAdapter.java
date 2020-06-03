package com.p3l_f_1_pegawai.Activities.ukuran_hewan;

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
import com.p3l_f_1_pegawai.dao.ukuran_hewanDAO;
import java.util.ArrayList;
import java.util.List;

public class UkuranHewanAdapter extends RecyclerView.Adapter<UkuranHewanAdapter.MyViewHolder> implements Filterable {
    private List<ukuran_hewanDAO> UkuranHewanList;
    private List<ukuran_hewanDAO> UkuranHewanFiltered;
    private Context context;

    public UkuranHewanAdapter(List<ukuran_hewanDAO> ukuranHewanList, Context context) {
        this.UkuranHewanList = ukuranHewanList;
        this.UkuranHewanFiltered = ukuranHewanList;
        this.context = context;
    }

    @NonNull
    @Override
    public UkuranHewanAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_adapter_ukuran, parent, false);
        final UkuranHewanAdapter.MyViewHolder holder = new UkuranHewanAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ukuran_hewanDAO row = UkuranHewanFiltered.get(position);
        holder.nama_ukuran_hewan.setText(row.getNama_ukuran_hewan());
        holder.log_aktivitas.setText(row.getStatus_data() + " by " + row.getKeterangan() + " at " + row.getTime_stamp());

        if(row.getStatus_data().equalsIgnoreCase("deleted")){
            holder.recycler_ukuran.findViewById(R.id.hapus_ukuran).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Data ini sudah dihapus!", Toast.LENGTH_LONG).show();
                }
            });
            holder.recycler_ukuran.findViewById(R.id.edit_ukuran).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Data ini sudah dihapus!", Toast.LENGTH_LONG).show();
                }
            });
        }
        else {
            holder.recycler_ukuran.findViewById(R.id.hapus_ukuran).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context.getApplicationContext(), activity_hapus_ukuran.class);
                    i.putExtra("id_ukuran_hewan", row.getId_ukuran_hewan());
                    i.putExtra("nama_ukuran_hewan", row.getNama_ukuran_hewan());
                    i.putExtra("keterangan", row.getKeterangan());
                    context.getApplicationContext().startActivity(i);
                }
            });

            holder.recycler_ukuran.findViewById(R.id.edit_ukuran).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context.getApplicationContext(), activity_ubah_ukuran.class);
                    i.putExtra("id_ukuran_hewan", row.getId_ukuran_hewan());
                    i.putExtra("nama_ukuran_hewan", row.getNama_ukuran_hewan());
                    i.putExtra("keterangan", row.getKeterangan());
                    context.getApplicationContext().startActivity(i);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return UkuranHewanFiltered.size();
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
                UkuranHewanFiltered = UkuranHewanList;
            }else{
                List<ukuran_hewanDAO> filteredList = new ArrayList<>();
                for(ukuran_hewanDAO ukuran: UkuranHewanList){
                    if(ukuran.getNama_ukuran_hewan().toLowerCase().contains(charSequence.toString().toLowerCase())){
                       filteredList.add(ukuran);
                    }
                }

                UkuranHewanFiltered = filteredList;
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = UkuranHewanFiltered;
            return  filterResults;
        }

        //runs on a ui thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            UkuranHewanFiltered = (List<ukuran_hewanDAO>) filterResults.values;
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout recycler_ukuran;
        TextView nama_ukuran_hewan, log_aktivitas;
        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);
            recycler_ukuran = itemView.findViewById(R.id.recycle_ukuran);
            nama_ukuran_hewan = itemView.findViewById(R.id.nama_ukuran_hewan);
            log_aktivitas = itemView.findViewById(R.id.log_aktivitas);
        }
    }
}
