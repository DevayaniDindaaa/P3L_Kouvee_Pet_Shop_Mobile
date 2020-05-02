package com.p3l_f_1_pegawai.Activities.konsumen;

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
import com.p3l_f_1_pegawai.Activities.pengadaan.activity_detail_pengadaan;
import com.p3l_f_1_pegawai.R;
import com.p3l_f_1_pegawai.dao.konsumenDAO;
import java.util.ArrayList;
import java.util.List;

public class KonsumenAdapter extends RecyclerView.Adapter<KonsumenAdapter.MyViewHolder> implements Filterable {
    private List<konsumenDAO> KonsumenList;
    private List<konsumenDAO> KonsumenFiltered;
    private Context context;

    public KonsumenAdapter(List<konsumenDAO> konsumenList, Context context) {
        this.KonsumenList = konsumenList;
        this.KonsumenFiltered = konsumenList;
        this.context = context;
    }

    @NonNull
    @Override
    public KonsumenAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_adapter_konsumen, parent, false);
        final KonsumenAdapter.MyViewHolder holder = new KonsumenAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull KonsumenAdapter.MyViewHolder holder, int position) {
        final konsumenDAO row = KonsumenFiltered.get(position);
        holder.nama_konsumen.setText(row.getNama_konsumen());
        holder.status_member.setText(row.getStatus_member());
        holder.log_aktivitas.setText(row.getStatus_data() + " by " + row.getKeterangan() + " at " + row.getTime_stamp());

        holder.recycler_konsumen.findViewById(R.id.lihat_detail_hewankonsumen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(), activity_detail_pengadaan.class);
                i.putExtra("nama_konsumen", row.getNama_konsumen());
                i.putExtra("status_member", row.getStatus_member());
                i.putExtra("alamat_konsumen", row.getAlamat_konsumen());
                i.putExtra("tgl_lahir_konsumen", row.getTgl_lahir_konsumen());
                i.putExtra("no_tlp_konsumen", row.getNo_tlp_konsumen());
                i.putExtra("details", row.getDetail_hewankonsumen().toString());
                context.getApplicationContext().startActivity(i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return KonsumenFiltered.size();
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
                KonsumenFiltered = KonsumenList;
            }else{
                List<konsumenDAO> filteredList = new ArrayList<>();
                for(konsumenDAO konsumen: KonsumenList){
                    if(konsumen.getNama_konsumen().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(konsumen);
                    }
                }

                KonsumenFiltered = filteredList;
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = KonsumenFiltered;
            return  filterResults;
        }

        //runs on a ui thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            KonsumenFiltered = (List<konsumenDAO>) filterResults.values;
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout recycler_konsumen;
        TextView nama_konsumen, status_member, log_aktivitas;

        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);
            recycler_konsumen = itemView.findViewById(R.id.recycle_konsumen);
            nama_konsumen = itemView.findViewById(R.id.nama_konsumen);
            status_member = itemView.findViewById(R.id.status_member);
            log_aktivitas = itemView.findViewById(R.id.log_aktivitas);
        }
    }
}