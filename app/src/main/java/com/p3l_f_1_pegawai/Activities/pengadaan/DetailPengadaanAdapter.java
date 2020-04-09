package com.p3l_f_1_pegawai.Activities.pengadaan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.p3l_f_1_pegawai.R;
import com.p3l_f_1_pegawai.dao.detail_pengadaanDAO;

import java.util.List;

public class DetailPengadaanAdapter extends RecyclerView.Adapter<DetailPengadaanAdapter.ViewHolder> {
    private List<detail_pengadaanDAO> DetailPengadaanList;
    private Context context;

    public DetailPengadaanAdapter(List<detail_pengadaanDAO> detailPengadaanList, Context context) {
        this.DetailPengadaanList = detailPengadaanList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_adapter_detail_pengadaan, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final detail_pengadaanDAO row = DetailPengadaanList.get(position);
        holder.nama_produk.setText(row.getNama_produk());
        holder.jumlah_produk.setText(String.valueOf(row.getJumlah_produk_dipesan()));
        holder.log_aktivitas.setText(row.getStatus_data() + " by " + row.getKeterangan() + " at " + row.getTime_stamp());

//        holder.wisata_recyle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(context.getApplicationContext(), detail_wisata.class);
//                i.putExtra("url_foto", row.getUrl_foto());
//                i.putExtra("nama_tempat", row.getNama_tempat());
//                i.putExtra("alamat_tempat", row.getAlamat_tempat());
//                i.putExtra("sejarah_tempat", row.getSejarah_tempat());
//                i.putExtra("email_user", row.getEmail_user());
//                context.getApplicationContext().startActivity(i);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return DetailPengadaanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout recycle_detail;
        TextView nama_produk, jumlah_produk, log_aktivitas;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recycle_detail = itemView.findViewById(R.id.recycle_detail_pengadaan);
            nama_produk = itemView.findViewById(R.id.nama_produk_dipesan);
            jumlah_produk = itemView.findViewById(R.id.jumlah_produk_pesan);
            log_aktivitas = itemView.findViewById(R.id.log_aktivitas);
        }
    }
}
