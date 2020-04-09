package com.p3l_f_1_pegawai.Activities.jenis_hewan;

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
import com.p3l_f_1_pegawai.dao.jenis_hewanDAO;
import com.p3l_f_1_pegawai.dao.ukuran_hewanDAO;
import java.util.ArrayList;
import java.util.List;

public class JenisHewanAdapter extends RecyclerView.Adapter<JenisHewanAdapter.MyViewHolder> implements Filterable {
    private List<jenis_hewanDAO> JenisHewanList;
    private List<jenis_hewanDAO> JenisHewanFiltered;
    private Context context;

    public JenisHewanAdapter(List<jenis_hewanDAO> jenisHewanList, Context context) {
        this.JenisHewanList = jenisHewanList;
        this.JenisHewanFiltered = jenisHewanList;
        this.context = context;
    }

    @NonNull
    @Override
    public JenisHewanAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_adapter_jenis, parent, false);
        final JenisHewanAdapter.MyViewHolder holder = new JenisHewanAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final jenis_hewanDAO row = JenisHewanFiltered.get(position);
        holder.nama_jenis_hewan.setText(row.getNama_jenis_hewan());
        holder.log_aktivitas.setText(row.getStatus_data() + " by " + row.getKeterangan() + " at " + row.getTime_stamp());

        if(row.getStatus_data().equalsIgnoreCase("deleted")){
            holder.recycler_jenis.findViewById(R.id.hapus_jenis).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Data ini sudah dihapus!", Toast.LENGTH_LONG).show();
                }
            });
            holder.recycler_jenis.findViewById(R.id.edit_jenis).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Data ini sudah dihapus!", Toast.LENGTH_LONG).show();
                }
            });
        }
        else {
            holder.recycler_jenis.findViewById(R.id.hapus_jenis).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context.getApplicationContext(), activity_hapus_jenis.class);
                    i.putExtra("id_jenis_hewan", row.getId_jenis_hewan());
                    i.putExtra("nama_jenis_hewan", row.getNama_jenis_hewan());
                    i.putExtra("keterangan", row.getKeterangan());
                    context.getApplicationContext().startActivity(i);
                }
            });

            holder.recycler_jenis.findViewById(R.id.edit_jenis).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context.getApplicationContext(), activity_ubah_jenis.class);
                    i.putExtra("id_jenis_hewan", row.getId_jenis_hewan());
                    i.putExtra("nama_jenis_hewan", row.getNama_jenis_hewan());
                    i.putExtra("keterangan", row.getKeterangan());
                    context.getApplicationContext().startActivity(i);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return JenisHewanFiltered.size();
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
                JenisHewanFiltered = JenisHewanList;
            }else{
                List<jenis_hewanDAO> filteredList = new ArrayList<>();
                for(jenis_hewanDAO jenis: JenisHewanList){
                    if(jenis.getNama_jenis_hewan().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(jenis);
                    }
                }

                JenisHewanFiltered = filteredList;
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = JenisHewanFiltered;
            return  filterResults;
        }

        //runs on a ui thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            JenisHewanFiltered = (List<jenis_hewanDAO>) filterResults.values;
            notifyDataSetChanged();
        }
    };

    public interface UkuranHewanAdapterListener {
        void onUkuranHewanSelected(ukuran_hewanDAO ukuran);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout recycler_jenis;
        TextView nama_jenis_hewan, log_aktivitas;
        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);
            recycler_jenis = itemView.findViewById(R.id.recycle_jenis);
            nama_jenis_hewan = itemView.findViewById(R.id.nama_jenis_hewan);
            log_aktivitas = itemView.findViewById(R.id.log_aktivitas);
        }
    }
}
