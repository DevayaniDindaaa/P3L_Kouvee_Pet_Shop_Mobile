package com.p3l_f_1_pegawai.Activities.penjualan_layanan;

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
import com.p3l_f_1_pegawai.dao.penjualan_layananDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PenjualanLayananFragment extends Fragment {
    Activity context;
    private List<penjualan_layananDAO> PenjualanLayananList;
    private RecyclerView recyclerView;
    private PenjualanLayananAdapter recycleAdapter;
    private FloatingActionButton tambahPenjualanLayanan;
    private String nama_user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_penjualan_layanan, container, false);
        tambahPenjualanLayanan = view.findViewById(R.id.fab_penjualan_layanan);
        tambahPenjualanLayanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), activity_tambah_penjualan_layanan.class);
                i.putExtra("USERNAME", nama_user);
                startActivity(i);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PenjualanLayananList = new ArrayList<>();
        context = getActivity();
        recyclerView = view.findViewById(R.id.recycle_tampil_penjualan_layanan);
        recycleAdapter = new PenjualanLayananAdapter(PenjualanLayananList, getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recycleAdapter);

        nama_user = getActivity().getIntent().getExtras().getString("USERNAME");

        getPenjualanLayanan();
    }

    public void getPenjualanLayanan(){
        String url = "http://192.168.8.102/CI_Mobile_P3L_1F/index.php/transaksilayanan";
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String penjualan_layanan = jsonObject.getString("message");
                            JSONArray jsonArray = new JSONArray(penjualan_layanan);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject objectReview = jsonArray.getJSONObject(i);
                                penjualan_layananDAO r = new penjualan_layananDAO(objectReview.getString("no_transaksi_layanan"),
                                        objectReview.getString("nama_hewan"),
                                        objectReview.getString("nama_konsumen"),
                                        objectReview.getString("nama_cs"),
                                        objectReview.getString("nama_kasir"),
                                        objectReview.getString("waktu_transaksi_layanan"),
                                        objectReview.getString("status_pembayaran_layanan"),
                                        objectReview.getString("status_pengerjaan_layanan"),
                                        objectReview.getInt("sub_total_layanan"),
                                        objectReview.getInt("diskon_layanan"),
                                        objectReview.getInt("total_pembayaran_layanan"),
                                        objectReview.getJSONArray("detail"));
                                System.out.println(objectReview);
                                PenjualanLayananList.add(r);
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
        searchView.setQueryHint("Cari Penjualan Layanan ... ");
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