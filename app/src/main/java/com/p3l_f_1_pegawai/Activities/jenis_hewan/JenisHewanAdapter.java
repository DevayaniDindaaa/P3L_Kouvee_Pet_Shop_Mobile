package com.p3l_f_1_pegawai.Activities.jenis_hewan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.p3l_f_1_pegawai.R;
import com.p3l_f_1_pegawai.dao.jenis_hewan;
import com.p3l_f_1_pegawai.dao.ukuran_hewan;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JenisHewanAdapter extends RecyclerView.Adapter<JenisHewanAdapter.MyViewHolder> implements Filterable {
    private List<jenis_hewan> JenisHewanList;
    private List<jenis_hewan> JenisHewanFiltered;
    private Context context;
    //private UkuranHewanAdapter listener;

    public JenisHewanAdapter(List<jenis_hewan> jenisHewanList, Context context) {
        this.JenisHewanList = jenisHewanList;
        this.JenisHewanFiltered = jenisHewanList;
        this.context = context;
        //this.listener = listener;
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
        jenis_hewan row = JenisHewanFiltered.get(position);
        holder.nama_jenis_hewan.setText(row.getNama_jenis_hewan());
        holder.log_aktivitas.setText(row.getStatus_data() + " by " + row.getKeterangan() + " at " + row.getTime_stamp());
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
                List<jenis_hewan> filteredList = new ArrayList<>();
                for(jenis_hewan jenis: JenisHewanList){
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
            JenisHewanFiltered = (List<jenis_hewan>) filterResults.values;
            notifyDataSetChanged();
        }
    };

    public interface UkuranHewanAdapterListener {
        void onUkuranHewanSelected(ukuran_hewan ukuran);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nama_jenis_hewan, log_aktivitas;
        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);
            nama_jenis_hewan = itemView.findViewById(R.id.nama_jenis_hewan);
            log_aktivitas = itemView.findViewById(R.id.log_aktivitas);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //listener.onUkuranHewanSelected(UkuranHewanFiltered.get(getAdapterPosition()));
                }
            });
        }
    }
}
