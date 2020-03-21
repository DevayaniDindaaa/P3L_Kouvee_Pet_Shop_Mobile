package com.p3l_f_1_pegawai.Activities.ukuran_hewan;

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
import com.p3l_f_1_pegawai.dao.ukuran_hewan;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UkuranHewanAdapter extends RecyclerView.Adapter<UkuranHewanAdapter.MyViewHolder> implements Filterable {
    private List<ukuran_hewan> UkuranHewanList;
    private List<ukuran_hewan> UkuranHewanFiltered;
    private Context context;
    //private UkuranHewanAdapter listener;

    public UkuranHewanAdapter(List<ukuran_hewan> ukuranHewanList, Context context) {
        this.UkuranHewanList = ukuranHewanList;
        this.UkuranHewanFiltered = ukuranHewanList;
        this.context = context;
       //this.listener = listener;
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
        ukuran_hewan row = UkuranHewanFiltered.get(position);
        holder.nama_ukuran_hewan.setText(row.getNama_ukuran_hewan());
        holder.log_aktivitas.setText(row.getStatus_data() + " by " + row.getKeterangan() + " at " + row.getTime_stamp());
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
                List<ukuran_hewan> filteredList = new ArrayList<>();
                for(ukuran_hewan ukuran: UkuranHewanList){
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
            UkuranHewanFiltered = (List<ukuran_hewan>) filterResults.values;
            notifyDataSetChanged();
        }
    };

    public interface UkuranHewanAdapterListener {
        void onUkuranHewanSelected(ukuran_hewan ukuran);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nama_ukuran_hewan, log_aktivitas;
        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);
            nama_ukuran_hewan = itemView.findViewById(R.id.nama_ukuran_hewan);
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
