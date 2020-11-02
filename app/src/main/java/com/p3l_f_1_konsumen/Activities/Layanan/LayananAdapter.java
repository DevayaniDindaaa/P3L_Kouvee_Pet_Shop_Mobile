package com.p3l_f_1_konsumen.Activities.Layanan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.p3l_f_1_konsumen.R;

import java.util.ArrayList;
import java.util.List;

public class LayananAdapter  extends RecyclerView.Adapter<LayananAdapter.MyViewHolder> implements Filterable {
    private List<layananDAO> LayananList;
    private List<layananDAO> LayananFiltered;
    private Context context;

    public LayananAdapter(List<layananDAO> layananList, Context context) {
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
        final layananDAO row = LayananFiltered.get(position);
        holder.nama_layanan.setText(row.getNama_layanan() + " " + row.getNama_jenis_hewan() + " " + row.getNama_ukuran_hewan());
        holder.harga_layanan.setText(String.valueOf(row.getHarga_layanan()));
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
                List<layananDAO> filteredList = new ArrayList<>();
                for(layananDAO layanan: LayananList){
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
            LayananFiltered = (List<layananDAO>) filterResults.values;
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout recycler_layanan;
        TextView nama_layanan, harga_layanan;
        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);
            recycler_layanan = itemView.findViewById(R.id.recycle_layanan);
            nama_layanan = itemView.findViewById(R.id.nama_layanan);
            harga_layanan = itemView.findViewById(R.id.harga_layanan);
        }
    }
}
