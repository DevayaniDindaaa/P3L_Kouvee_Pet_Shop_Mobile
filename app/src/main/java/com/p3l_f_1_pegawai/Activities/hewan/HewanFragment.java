package com.p3l_f_1_pegawai.Activities.hewan;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.p3l_f_1_pegawai.dao.hewanDAO;
import com.p3l_f_1_pegawai.dao.konsumenDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HewanFragment extends Fragment {
    Activity context;
    private List<hewanDAO> HewanList;
    private RecyclerView recyclerView;
    private HewanAdapter recycleAdapter;
    private FloatingActionButton tambahHewan;
    private String nama_user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_hewan, container, false);
        tambahHewan = view.findViewById(R.id.fab_hewan);
        tambahHewan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nama_user.equalsIgnoreCase("owner")){
                    Toast.makeText(context, "Owner tidak bisa menambah data hewan!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent i = new Intent(getActivity(), activity_tambah_hewan.class);
                    i.putExtra("USERNAME", nama_user);
                    startActivity(i);
                }
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HewanList = new ArrayList<>();
        context = getActivity();
        recyclerView = view.findViewById(R.id.recycle_tampil_hewan);
        recycleAdapter = new HewanAdapter(HewanList, getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recycleAdapter);

        nama_user = getActivity().getIntent().getExtras().getString("USERNAME");

        getHewan();
    }

    public void getHewan(){
        String url = "http://192.168.8.100/CI_Mobile_P3L_1F/index.php/hewan";
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String hewan = jsonObject.getString("message");
                            JSONArray jsonArray = new JSONArray(hewan);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject objectReview = jsonArray.getJSONObject(i);
                                hewanDAO d = new hewanDAO(objectReview.getString("id_hewan"),
                                        objectReview.getString("nama_jenis_hewan"),
                                        objectReview.getString("nama_ukuran_hewan"),
                                        objectReview.getString("id_konsumen"),
                                        objectReview.getString("nama_konsumen"),
                                        objectReview.getString("alamat_konsumen"),
                                        objectReview.getString("tgl_lahir_konsumen"),
                                        objectReview.getString("no_tlp_konsumen"),
                                        objectReview.getString("status_member"),
                                        objectReview.getString("nama_hewan"),
                                        objectReview.getString("tgl_lahir_hewan"),
                                        objectReview.getString("status_data"),
                                        objectReview.getString("time_stamp"),
                                        objectReview.getString("keterangan"));
                                System.out.println(objectReview);
                                HewanList.add(d);
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
        searchView.setQueryHint("Cari Hewan ... (Nama, Jenis, Ukuran)");
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