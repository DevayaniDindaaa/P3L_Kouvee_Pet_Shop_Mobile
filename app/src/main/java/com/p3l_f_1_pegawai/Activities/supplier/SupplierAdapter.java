package com.p3l_f_1_pegawai.Activities.supplier;

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
import com.p3l_f_1_pegawai.dao.supplierDAO;
import java.util.ArrayList;
import java.util.List;

public class SupplierAdapter extends RecyclerView.Adapter<SupplierAdapter.MyViewHolder> implements Filterable {
    private List<supplierDAO> SupplierList;
    private List<supplierDAO> SupplierFiltered;
    private Context context;

    public SupplierAdapter(List<supplierDAO> SupplierList, Context context) {
        this.SupplierList = SupplierList;
        this.SupplierFiltered = SupplierList;
        this.context = context;
    }

    @NonNull
    @Override
    public SupplierAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_adapter_supplier, parent, false);
        final SupplierAdapter.MyViewHolder holder = new SupplierAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final supplierDAO row = SupplierFiltered.get(position);
        holder.nama_supplier.setText(row.getNama_supplier());
        holder.alamat_supplier.setText(row.getAlamat_supplier() + ", " + row.getKota_supplier());
        holder.telepon_supplier.setText(row.getTelepon_supplier());
        holder.log_aktivitas.setText(row.getStatus_data() + " by " + row.getKeterangan() + " at " + row.getTime_stamp());

        if(row.getStatus_data().equalsIgnoreCase("deleted")){
            holder.recycler_supplier.findViewById(R.id.hapus_supplier).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Data ini sudah dihapus!", Toast.LENGTH_LONG).show();
                }
            });
            holder.recycler_supplier.findViewById(R.id.edit_supplier).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Data ini sudah dihapus!", Toast.LENGTH_LONG).show();
                }
            });
        }
        else {
            holder.recycler_supplier.findViewById(R.id.hapus_supplier).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context.getApplicationContext(), activity_hapus_supplier.class);
                    i.putExtra("id_supplier", row.getId_supplier());
                    i.putExtra("nama_supplier", row.getNama_supplier());
                    i.putExtra("alamat_supplier", row.getAlamat_supplier() + ", " + row.getKota_supplier());
                    i.putExtra("no_tlp_supplier", row.getTelepon_supplier());
                    i.putExtra("keterangan", row.getKeterangan());
                    context.getApplicationContext().startActivity(i);
                }
            });

            holder.recycler_supplier.findViewById(R.id.edit_supplier).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context.getApplicationContext(), activity_ubah_supplier.class);
                    i.putExtra("id_supplier", row.getId_supplier());
                    i.putExtra("nama_supplier", row.getNama_supplier());
                    i.putExtra("alamat_supplier", row.getAlamat_supplier());
                    i.putExtra("kota_supplier", row.getKota_supplier());
                    i.putExtra("no_tlp_supplier", row.getTelepon_supplier());
                    i.putExtra("keterangan", row.getKeterangan());
                    context.getApplicationContext().startActivity(i);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return SupplierFiltered.size();
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
                SupplierFiltered = SupplierList;
            }else{
                List<supplierDAO> filteredList = new ArrayList<>();
                for(supplierDAO supplier: SupplierList){
                    if(supplier.getNama_supplier().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(supplier);
                    }else
                    if(supplier.getKota_supplier().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(supplier);
                    }
                }

                SupplierFiltered = filteredList;
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = SupplierFiltered;
            return  filterResults;
        }

        //runs on a ui thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            SupplierFiltered = (List<supplierDAO>) filterResults.values;
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout recycler_supplier;
        TextView nama_supplier, alamat_supplier, telepon_supplier, log_aktivitas;
        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);
            recycler_supplier = itemView.findViewById(R.id.recycle_supplier);
            nama_supplier = itemView.findViewById(R.id.nama_supplier);
            alamat_supplier = itemView.findViewById(R.id.alamat_supplier);
            telepon_supplier = itemView.findViewById(R.id.telepon_supplier);
            log_aktivitas = itemView.findViewById(R.id.log_aktivitas);
        }
    }
}
