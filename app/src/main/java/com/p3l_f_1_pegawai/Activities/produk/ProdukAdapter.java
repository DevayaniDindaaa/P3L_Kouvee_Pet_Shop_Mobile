package com.p3l_f_1_pegawai.Activities.produk;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.p3l_f_1_pegawai.R;
import com.p3l_f_1_pegawai.dao.produk;
import java.util.ArrayList;
import java.util.List;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.MyViewHolder> implements Filterable {
    private List<produk> ProdukList;
    private List<produk> ProdukFiltered;
    private Context context;

    public ProdukAdapter(List<produk> produkList, Context context) {
        this.ProdukList = produkList;
        this.ProdukFiltered = produkList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProdukAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_adapter_produk, parent, false);
        final ProdukAdapter.MyViewHolder holder = new ProdukAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProdukAdapter.MyViewHolder holder, int position) {
        final produk row = ProdukFiltered.get(position);
        holder.nama_produk.setText(row.getNama_produk());
        holder.jenis_hewan_produk.setText(row.getNama_jenis_hewan());
        holder.stok_produk.setText((String.valueOf(row.getStok_produk())) + " " + row.getSatuan_produk());
        holder.stok_minimal_produk.setText((String.valueOf(row.getStok_minimal_produk())) + " " + row.getSatuan_produk());
        holder.harga_produk.setText((String.valueOf(row.getHarga_produk())) + " /" + row.getSatuan_produk());
        holder.log_aktivitas.setText(row.getStatus_data() + " by " + row.getKeterangan() + " at " + row.getTime_stamp());
        if(row.getFoto_produk().equalsIgnoreCase("null")){
            Glide.with(context).load("http://192.168.8.102/CI_Mobile_P3L_1F/upload/default.png").into(holder.foto_produk);;
        }
        else{
            Glide.with(context).load("http://192.168.8.102/CI_Mobile_P3L_1F/" + row.getFoto_produk()).into(holder.foto_produk);
        }

        if(row.getStatus_data().equalsIgnoreCase("deleted")){
            holder.recycler_produk.findViewById(R.id.hapus_produk).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Data ini sudah dihapus!", Toast.LENGTH_LONG).show();
                }
            });
            holder.recycler_produk.findViewById(R.id.edit_produk).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Data ini sudah dihapus!", Toast.LENGTH_LONG).show();
                }
            });
        }
        else {
            holder.recycler_produk.findViewById(R.id.hapus_produk).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context.getApplicationContext(), activity_hapus_produk.class);
                    i.putExtra("nama_produk", row.getNama_produk());
                    i.putExtra("jenis_hewan", row.getNama_jenis_hewan());
                    i.putExtra("stok_produk",(String.valueOf(row.getStok_produk())) + " " + row.getSatuan_produk());
                    i.putExtra("stok_minimal_produk",(String.valueOf(row.getStok_minimal_produk())) + " " + row.getSatuan_produk());
                    i.putExtra("keterangan", row.getKeterangan());
                    i.putExtra("id_produk", row.getId_produk());
                    i.putExtra("harga_produk", (String.valueOf(row.getHarga_produk())) + " /" + row.getSatuan_produk());
                    if(row.getFoto_produk().equalsIgnoreCase("null")){
                        i.putExtra("foto_produk", "http://192.168.8.102/CI_Mobile_P3L_1F/upload/default.png");
                    }
                    else
                    {
                        i.putExtra("foto_produk", "http://192.168.8.102/CI_Mobile_P3L_1F/" + row.getFoto_produk());
                    }
                    context.getApplicationContext().startActivity(i);
                }
            });

            holder.recycler_produk.findViewById(R.id.edit_produk).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context.getApplicationContext(), activity_ubah_produk.class);
                    i.putExtra("nama_produk", row.getNama_produk());
                    i.putExtra("satuan_produk", row.getSatuan_produk());
                    i.putExtra("stok_produk",String.valueOf(row.getStok_produk()));
                    i.putExtra("stok_minimal_produk",String.valueOf(row.getStok_minimal_produk()));
                    i.putExtra("keterangan", row.getKeterangan());
                    i.putExtra("id_produk", row.getId_produk());
                    i.putExtra("harga_produk", String.valueOf(row.getHarga_produk()));
                    if(row.getFoto_produk().equalsIgnoreCase("null")){
                        i.putExtra("foto_produk", "http://192.168.8.102/CI_Mobile_P3L_1F/upload/default.png");
                    }
                    else
                    {
                        i.putExtra("foto_produk", "http://192.168.8.102/CI_Mobile_P3L_1F/" + row.getFoto_produk());
                    }
                    context.getApplicationContext().startActivity(i);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return ProdukFiltered.size();
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
                ProdukFiltered = ProdukList;
            }else{
                List<produk> filteredList = new ArrayList<>();
                for(produk produk: ProdukList){
                    if(produk.getNama_produk().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(produk);
                    }else
                    if(produk.getNama_jenis_hewan().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(produk);
                    }
                }

                ProdukFiltered = filteredList;
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = ProdukFiltered;
            return  filterResults;
        }

        //runs on a ui thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ProdukFiltered = (List<produk>) filterResults.values;
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout recycler_produk;
        TextView nama_produk, harga_produk, jenis_hewan_produk, stok_produk, stok_minimal_produk, log_aktivitas;
        ImageView foto_produk;

        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);
            recycler_produk = itemView.findViewById(R.id.recycle_produk);
            nama_produk = itemView.findViewById(R.id.nama_produk);
            harga_produk = itemView.findViewById(R.id.harga_produk);
            jenis_hewan_produk = itemView.findViewById(R.id.jenis_hewan_produk);
            stok_produk = itemView.findViewById(R.id.stok_produk);
            stok_minimal_produk = itemView.findViewById(R.id.stok_minimal);
            foto_produk = itemView.findViewById(R.id.foto_produk);
            log_aktivitas = itemView.findViewById(R.id.log_aktivitas);
        }
    }
}