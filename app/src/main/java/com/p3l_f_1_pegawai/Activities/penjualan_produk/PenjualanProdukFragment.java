package com.p3l_f_1_pegawai.Activities.penjualan_produk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.p3l_f_1_pegawai.Activities.konsumen.KonsumenAdapter;
import com.p3l_f_1_pegawai.Activities.konsumen.activity_tambah_konsumen;
import com.p3l_f_1_pegawai.R;
import com.p3l_f_1_pegawai.dao.konsumenDAO;
import com.p3l_f_1_pegawai.dao.penjualan_produkDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PenjualanProdukFragment extends Fragment {
    Activity context;
    private List<penjualan_produkDAO> PenjualanProdukList;
    private RecyclerView recyclerView;
    private PenjualanProdukAdapter recycleAdapter;
    private FloatingActionButton tambahPenjualanProduk;
    private String nama_user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_penjualan_produk, container, false);
        tambahPenjualanProduk = view.findViewById(R.id.fab_penjualan_produk);
        tambahPenjualanProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), activity_tambah_penjualan_produk.class);
                i.putExtra("USERNAME", nama_user);
                startActivity(i);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PenjualanProdukList = new ArrayList<>();
        context = getActivity();
        recyclerView = view.findViewById(R.id.recycle_tampil_penjualan_produk);
        recycleAdapter = new PenjualanProdukAdapter(PenjualanProdukList, getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recycleAdapter);

        nama_user = getActivity().getIntent().getExtras().getString("USERNAME");

        getPenjualanProduk();
    }

    public void getPenjualanProduk(){
        String url = "http://192.168.8.103/CI_Mobile_P3L_1F/index.php/transaksiproduk";
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String penjualan_produk = jsonObject.getString("message");
                            JSONArray jsonArray = new JSONArray(penjualan_produk);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject objectReview = jsonArray.getJSONObject(i);
                                penjualan_produkDAO r = new penjualan_produkDAO(objectReview.getString("no_transaksi_produk"),
                                        objectReview.getString("nama_hewan"),
                                        objectReview.getString("nama_jenis_hewan"),
                                        objectReview.getString("nama_konsumen"),
                                        objectReview.getString("status_member"),
                                        objectReview.getString("nama_cs"),
                                        objectReview.getString("nama_kasir"),
                                        objectReview.getString("waktu_transaksi_produk"),
                                        objectReview.getString("status_pembayaran_produk"),
                                        objectReview.getInt("sub_total_produk"),
                                        objectReview.getInt("diskon_produk"),
                                        objectReview.getInt("total_pembayaran_produk"),
                                        objectReview.getJSONArray("detail"));
                                System.out.println(objectReview);
                                PenjualanProdukList.add(r);
                            }
                            recycleAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Kesalahan Koneksi!", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(getRequest);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Cari Penjualan Produk ... ");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                recycleAdapter.getFilter().filter(newText);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recycleAdapter.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
}