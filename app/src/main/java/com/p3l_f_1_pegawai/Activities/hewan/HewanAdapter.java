package com.p3l_f_1_pegawai.Activities.hewan;

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
import com.p3l_f_1_pegawai.dao.hewanDAO;

import java.util.ArrayList;
import java.util.List;

public class HewanAdapter extends RecyclerView.Adapter<HewanAdapter.MyViewHolder> implements Filterable {
    private List<hewanDAO> HewanList;
    private List<hewanDAO> HewanFiltered;
    private Context context;

    public HewanAdapter(List<hewanDAO> hewanList, Context context) {
        this.HewanList = hewanList;
        this.HewanFiltered = hewanList;
        this.context = context;
    }

    @NonNull
    @Override
    public HewanAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_adapter_hewan, parent, false);
        final HewanAdapter.MyViewHolder holder = new HewanAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HewanAdapter.MyViewHolder holder, int position) {
        final hewanDAO row = HewanFiltered.get(position);
        holder.nama_hewan.setText(row.getNama_hewan());
        holder.jenis_ukuran.setText(row.getId_jenis_hewan() + " --- " + row.getId_ukuran_hewan());
        holder.log_aktivitas.setText(row.getStatus_data() + " by " + row.getKeterangan() + " at " + row.getTime_stamp());

        holder.recycler_hewan.findViewById(R.id.lihat_detail_hewan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(), activity_detail_hapus_hewan.class);
                i.putExtra("id_hewan", row.getId_hewan());
                i.putExtra("nama_hewan", row.getNama_hewan());
                i.putExtra("jenis_hewan", row.getId_jenis_hewan());
                i.putExtra("ukuran_hewan", row.getId_ukuran_hewan());
                i.putExtra("tgl_lahir_hewan", row.getTgl_lahir_hewan());
                i.putExtra("status_hapus", row.getStatus_data());

                i.putExtra("nama_konsumen", row.getId_konsumen());
                i.putExtra("status_member", row.getStatus_member());
                i.putExtra("alamat_konsumen", row.getAlamat_konsumen());
                i.putExtra("tgl_lahir_konsumen", row.getTgl_lahir_konsumen());
                i.putExtra("no_tlp_konsumen", row.getNo_tlp_konsumen());
                i.putExtra("status_data", row.getStatus_data() + " by " + row.getKeterangan() + " at " + row.getTime_stamp());
                context.getApplicationContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return HewanFiltered.size();
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
                HewanFiltered = HewanList;
            }else{
                List<hewanDAO> filteredList = new ArrayList<>();
                for(hewanDAO hewan: HewanList){
                    if(hewan.getNama_hewan().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(hewan);
                    }else if(hewan.getId_ukuran_hewan().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(hewan);
                    }else if(hewan.getId_jenis_hewan().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(hewan);
                    }
                }

                HewanFiltered = filteredList;
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = HewanFiltered;
            return  filterResults;
        }

        //runs on a ui thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            HewanFiltered = (List<hewanDAO>) filterResults.values;
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout recycler_hewan;
        TextView nama_hewan, jenis_ukuran, log_aktivitas;

        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);
            recycler_hewan = itemView.findViewById(R.id.recycle_hewan);
            nama_hewan = itemView.findViewById(R.id.nama_hewan);
            jenis_ukuran = itemView.findViewById(R.id.jenis_dan_ukuran);
            log_aktivitas = itemView.findViewById(R.id.log_aktivitas);
        }
    }
}