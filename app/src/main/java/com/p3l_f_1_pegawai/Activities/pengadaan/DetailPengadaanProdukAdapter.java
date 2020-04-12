package com.p3l_f_1_pegawai.Activities.pengadaan;

import android.app.AlertDialog;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.p3l_f_1_pegawai.R;
import com.p3l_f_1_pegawai.dao.detailProduk_pengadaanDAO;
import com.p3l_f_1_pegawai.dao.produkDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailPengadaanProdukAdapter extends RecyclerView.Adapter<DetailPengadaanProdukAdapter.ViewHolder> {
    private Context Mcontext;
    private ArrayList<detailProduk_pengadaanDAO> arrayList;
    private Spinner spinner_produk;
    private String id_produk;
    private List<produkDAO> produks = new ArrayList<>();
    private ArrayList<String> tempProduk = new ArrayList<>();

    public DetailPengadaanProdukAdapter(ArrayList<detailProduk_pengadaanDAO> arrayList, Context Mcontext) {
        this.arrayList = arrayList;
        this.Mcontext = Mcontext;
    }

    @NonNull
    @Override
    public DetailPengadaanProdukAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(Mcontext).inflate(R.layout.recycle_adapter_detail_produk_pengadaan, parent, false);
        final DetailPengadaanProdukAdapter.ViewHolder holder = new DetailPengadaanProdukAdapter.ViewHolder(view, Mcontext);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DetailPengadaanProdukAdapter.ViewHolder holder, final int position) {
        final detailProduk_pengadaanDAO row = arrayList.get(position);
        holder.nama_produk.setText(row.getNama_produk());
        holder.jumlah_produk.setText(String.valueOf(row.getJumlah_produk()) + " " + row.getSatuan_produk());
        holder.recycler_detail_pengadaan.findViewById(R.id.hapus_pesan_produk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.remove(row);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Context context;
        LinearLayout recycler_detail_pengadaan;
        TextView nama_produk, jumlah_produk;

        public ViewHolder(@NonNull View itemView, Context Mcontext) {
            super(itemView);
            this.context = Mcontext;

            recycler_detail_pengadaan = itemView.findViewById(R.id.recycle_detail_tambah_pengadaan);
            nama_produk = itemView.findViewById(R.id.nama_produk_dipesan);
            jumlah_produk = itemView.findViewById(R.id.jumlah_produk_pesan);
        }
    }
}