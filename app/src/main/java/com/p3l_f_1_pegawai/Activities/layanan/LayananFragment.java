package com.p3l_f_1_pegawai.Activities.layanan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.p3l_f_1_pegawai.R;
import com.p3l_f_1_pegawai.dao.layananDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LayananFragment extends Fragment {
    Activity context;
    private List<layananDAO> LayananList;
    private RecyclerView recyclerView;
    private LayananAdapter recycleAdapter;
    private FloatingActionButton tambahLayanan;
    private String nama_user;
    private SwipeRefreshLayout refreshLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_layanan, container, false);
        tambahLayanan = view.findViewById(R.id.fab_layanan);
        tambahLayanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), activity_tambah_layanan.class);
                i.putExtra("USERNAME", nama_user);
                startActivity(i);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LayananList = new ArrayList<>();
        context = getActivity();
        refreshLayout = view.findViewById(R.id.swipe_refresh);
        recyclerView = view.findViewById(R.id.recycle_tampil_layanan);
        recycleAdapter = new LayananAdapter(LayananList, getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recycleAdapter);

        nama_user = getActivity().getIntent().getExtras().getString("USERNAME");

        getLayanan();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LayananList.clear();
                getLayanan();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    public void getLayanan(){
        String url = "http://192.168.0.200/CI_Mobile_P3L_1F/index.php/layanan";
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String layanan = jsonObject.getString("message");
                            JSONArray jsonArray = new JSONArray(layanan);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject objectReview = jsonArray.getJSONObject(i);
                                layananDAO r = new layananDAO(objectReview.getString("id_layanan"),
                                        objectReview.getString("nama_ukuran_hewan"),
                                        objectReview.getString("nama_jenis_hewan"),
                                        objectReview.getString("nama_layanan"),
                                        objectReview.getInt("harga_satuan_layanan"),
                                        objectReview.getString("status_data"),
                                        objectReview.getString("time_stamp"),
                                        objectReview.getString("keterangan"));

                                System.out.println(objectReview);
                                LayananList.add(r);
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
        searchView.setQueryHint("Cari Layanan ... (Nama/Jenis/Ukuran)");
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