package com.p3l_f_1_pegawai.Activities.konsumen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.p3l_f_1_pegawai.R;
import com.p3l_f_1_pegawai.dao.hewanDAO;

import java.util.List;

public class DetailHewanKonsumenAdapter extends RecyclerView.Adapter<DetailHewanKonsumenAdapter.ViewHolder> {
    private List<hewanDAO> DetailHewanKonsumenList;
    private Context context;

    public DetailHewanKonsumenAdapter(List<hewanDAO> detailHewanKonsumenList, Context context) {
        this.DetailHewanKonsumenList = detailHewanKonsumenList;
        this.context = context;
    }

    @NonNull
    @Override
    public DetailHewanKonsumenAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_adapter_detail_hewankonsumen, parent, false);
        final DetailHewanKonsumenAdapter.ViewHolder holder = new DetailHewanKonsumenAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DetailHewanKonsumenAdapter.ViewHolder holder, int position) {
        final hewanDAO row = DetailHewanKonsumenList.get(position);
        holder.nama_hewan_konsumen.setText(row.getNama_hewan());
        holder.jenis_hewan_konsumen.setText(String.valueOf(row.getId_jenis_hewan()));
        holder.ukuran_hewan_konsumen.setText(String.valueOf(row.getId_ukuran_hewan()));
        holder.tgl_lahir_hewan_konsumen.setText(String.valueOf(row.getTgl_lahir_hewan()));
        holder.log_aktivitas.setText(row.getStatus_data() + " by " + row.getKeterangan() + " at " + row.getTime_stamp());
    }

    @Override
    public int getItemCount() {
        return DetailHewanKonsumenList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout recycle_detail;
        TextView nama_hewan_konsumen, jenis_hewan_konsumen, ukuran_hewan_konsumen, tgl_lahir_hewan_konsumen, log_aktivitas;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recycle_detail = itemView.findViewById(R.id.recycle_detail_hewankonsumen);
            nama_hewan_konsumen = itemView.findViewById(R.id.nama_hewan_konsumen);
            jenis_hewan_konsumen = itemView.findViewById(R.id.jenis_hewan_konsumen);
            ukuran_hewan_konsumen = itemView.findViewById(R.id.ukuran_hewan_konsumen);
            tgl_lahir_hewan_konsumen = itemView.findViewById(R.id.tgl_lahir_hewan_konsumen);
            log_aktivitas = itemView.findViewById(R.id.log_aktivitas);
        }
    }
}
