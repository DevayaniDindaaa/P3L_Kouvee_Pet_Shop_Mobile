package com.p3l_f_1_konsumen.Activities.Layanan;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.p3l_f_1_konsumen.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LayananFragment extends Fragment {
    Activity context;
    private List<layananDAO> LayananList,  LayananListHargaTerendah, LayananListHargaTertinggi;
    private RecyclerView recyclerView;
    private LayananAdapter recycleAdapter;
    private Spinner sortby;
    private String sorted;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_layanan, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LayananList = new ArrayList<>();
        LayananListHargaTerendah = new ArrayList<>();
        LayananListHargaTertinggi = new ArrayList<>();

        context = getActivity();
        recyclerView = view.findViewById(R.id.recycle_tampil_layanan);
        recycleAdapter = new LayananAdapter(LayananList, getActivity());

        sortby = view.findViewById(R.id.sorting);

        sortby.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sorted = parent.getSelectedItem().toString();
                if(sorted.equalsIgnoreCase("Nama Layanan")){
                    recycleAdapter = new LayananAdapter(LayananList, getActivity());
                    recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(recycleAdapter);
                    getLayanan();
                    Toast.makeText(context, "Data Layanan diurutkan Berdasarkan Nama Layanan!", Toast.LENGTH_SHORT).show();
                }
                else if(sorted.equalsIgnoreCase("Harga Terendah")){
                    recycleAdapter = new LayananAdapter(LayananListHargaTerendah, getActivity());
                    recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(recycleAdapter);
                    sortHarga();
                    Toast.makeText(context, "Data Layanan diurutkan Berdasarkan Harga Terendah!", Toast.LENGTH_SHORT).show();
                }
                else if(sorted.equalsIgnoreCase("Harga Tertinggi")){
                    recycleAdapter = new LayananAdapter(LayananListHargaTertinggi, getActivity());
                    recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(recycleAdapter);
                    sortHarga2();
                    Toast.makeText(context, "Data Layanan diurutkan Berdasarkan Harga Tertinggi!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getLayanan(){
        String url = "http://192.168.8.102/CI_Mobile_P3L_1F/index.php/layanan";
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
                                if(objectReview.getString("status_data").compareToIgnoreCase("deleted") != 0) {
                                    LayananList.add(r);
                                }
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

    public void sortHarga(){
        String url = "http://192.168.8.102/CI_Mobile_P3L_1F/index.php/layanan/sortHarga";
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
                                if(objectReview.getString("status_data").compareToIgnoreCase("deleted") != 0) {
                                    LayananListHargaTerendah.add(r);
                                }
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

    public void sortHarga2(){
        String url = "http://192.168.8.102/CI_Mobile_P3L_1F/index.php/layanan/sortHarga2";
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
                                if(objectReview.getString("status_data").compareToIgnoreCase("deleted") != 0) {
                                    LayananListHargaTertinggi.add(r);
                                }
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