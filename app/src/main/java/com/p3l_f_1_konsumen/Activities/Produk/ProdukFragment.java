package com.p3l_f_1_konsumen.Activities.Produk;

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

public class ProdukFragment extends Fragment {
    Activity context;
    private List<produkDAO> ProdukListNormal, ProdukListHargaTerendah, ProdukListStokTerendah, ProdukListHargaTertinggi, ProdukListStokTertinggi;
    private RecyclerView recyclerView;
    private ProdukAdapter recycleAdapter;
    private Spinner sortby;
    private String sorted;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_produk, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProdukListNormal = new ArrayList<>();
        ProdukListHargaTerendah = new ArrayList<>();
        ProdukListStokTerendah = new ArrayList<>();
        ProdukListHargaTertinggi = new ArrayList<>();
        ProdukListStokTertinggi = new ArrayList<>();
        context = getActivity();

        recyclerView = view.findViewById(R.id.recycle_tampil_produk);
        recycleAdapter = new ProdukAdapter(ProdukListNormal, getActivity());

        sortby = view.findViewById(R.id.sorting);

        sortby.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sorted = parent.getSelectedItem().toString();
                if(sorted.equalsIgnoreCase("Nama Produk")){
                    recycleAdapter = new ProdukAdapter(ProdukListNormal, getActivity());
                    recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(recycleAdapter);
                    getProduk();
                    Toast.makeText(context, "Data Produk diurutkan Berdasarkan Nama Produk!", Toast.LENGTH_SHORT).show();
                }
                else if(sorted.equalsIgnoreCase("Harga Terendah")){
                    recycleAdapter = new ProdukAdapter(ProdukListHargaTerendah, getActivity());
                    recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(recycleAdapter);
                    sortHarga();
                    Toast.makeText(context, "Data Produk diurutkan Berdasarkan Harga Terendah!", Toast.LENGTH_SHORT).show();
                }
                else if(sorted.equalsIgnoreCase("Stok Terendah")){
                    recycleAdapter = new ProdukAdapter(ProdukListStokTerendah, getActivity());
                    recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(recycleAdapter);
                    sortStok();
                    Toast.makeText(context, "Data Produk diurutkan Berdasarkan Stok Terendah!", Toast.LENGTH_SHORT).show();
                }
                else if(sorted.equalsIgnoreCase("Harga Tertinggi")){
                    recycleAdapter = new ProdukAdapter(ProdukListHargaTertinggi, getActivity());
                    recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(recycleAdapter);
                    sortHarga2();
                    //recycleAdapter.notifyDataSetChanged();
                    Toast.makeText(context, "Data Produk diurutkan Berdasarkan Harga Tertinggi!", Toast.LENGTH_SHORT).show();
                }
                else if(sorted.equalsIgnoreCase("Stok Tertinggi")){
                    recycleAdapter = new ProdukAdapter(ProdukListStokTertinggi, getActivity());
                    recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(recycleAdapter);
                    sortStok2();
                    Toast.makeText(context, "Data Produk diurutkan Berdasarkan Stok Tertinggi!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getProduk(){
        String url = "http://192.168.8.102/CI_Mobile_P3L_1F/index.php/produk";
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String produk = jsonObject.getString("message");
                            JSONArray jsonArray = new JSONArray(produk);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject objectReview = jsonArray.getJSONObject(i);
                                produkDAO r = new produkDAO(objectReview.getString("id_produk"),
                                        objectReview.getString("nama_jenis_hewan"),
                                        objectReview.getString("nama_produk"),
                                        objectReview.getString("satuan_produk"),
                                        objectReview.getString("foto_produk"),
                                        objectReview.getString("status_data"),
                                        objectReview.getString("time_stamp"),
                                        objectReview.getString("keterangan"),
                                        objectReview.getInt("stok_produk"),
                                        objectReview.getInt("stok_minimal_produk"),
                                        objectReview.getInt("harga_satuan_produk"));

                                System.out.println(objectReview);
                                if(objectReview.getString("status_data").compareToIgnoreCase("deleted") != 0) {
                                    ProdukListNormal.add(r);
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
        String url = "http://192.168.8.102/CI_Mobile_P3L_1F/index.php/produk/sortHarga";
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String produk = jsonObject.getString("message");
                            JSONArray jsonArray = new JSONArray(produk);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject objectReview = jsonArray.getJSONObject(i);
                                produkDAO r = new produkDAO(objectReview.getString("id_produk"),
                                        objectReview.getString("nama_jenis_hewan"),
                                        objectReview.getString("nama_produk"),
                                        objectReview.getString("satuan_produk"),
                                        objectReview.getString("foto_produk"),
                                        objectReview.getString("status_data"),
                                        objectReview.getString("time_stamp"),
                                        objectReview.getString("keterangan"),
                                        objectReview.getInt("stok_produk"),
                                        objectReview.getInt("stok_minimal_produk"),
                                        objectReview.getInt("harga_satuan_produk"));

                                System.out.println(objectReview);
                                if(objectReview.getString("status_data").compareToIgnoreCase("deleted") != 0) {
                                    ProdukListHargaTerendah.add(r);
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

    public void sortStok(){
        String url = "http://192.168.8.102/CI_Mobile_P3L_1F/index.php/produk/sortStok";
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String produk = jsonObject.getString("message");
                            JSONArray jsonArray = new JSONArray(produk);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject objectReview = jsonArray.getJSONObject(i);
                                produkDAO r = new produkDAO(objectReview.getString("id_produk"),
                                        objectReview.getString("nama_jenis_hewan"),
                                        objectReview.getString("nama_produk"),
                                        objectReview.getString("satuan_produk"),
                                        objectReview.getString("foto_produk"),
                                        objectReview.getString("status_data"),
                                        objectReview.getString("time_stamp"),
                                        objectReview.getString("keterangan"),
                                        objectReview.getInt("stok_produk"),
                                        objectReview.getInt("stok_minimal_produk"),
                                        objectReview.getInt("harga_satuan_produk"));

                                System.out.println(objectReview);
                                if(objectReview.getString("status_data").compareToIgnoreCase("deleted") != 0) {
                                    ProdukListStokTerendah.add(r);
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
        String url = "http://192.168.8.102/CI_Mobile_P3L_1F/index.php/produk/sortHarga2";
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String produk = jsonObject.getString("message");
                            JSONArray jsonArray = new JSONArray(produk);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject objectReview = jsonArray.getJSONObject(i);
                                produkDAO r = new produkDAO(objectReview.getString("id_produk"),
                                        objectReview.getString("nama_jenis_hewan"),
                                        objectReview.getString("nama_produk"),
                                        objectReview.getString("satuan_produk"),
                                        objectReview.getString("foto_produk"),
                                        objectReview.getString("status_data"),
                                        objectReview.getString("time_stamp"),
                                        objectReview.getString("keterangan"),
                                        objectReview.getInt("stok_produk"),
                                        objectReview.getInt("stok_minimal_produk"),
                                        objectReview.getInt("harga_satuan_produk"));

                                System.out.println(objectReview);
                                if(objectReview.getString("status_data").compareToIgnoreCase("deleted") != 0) {
                                    ProdukListHargaTertinggi.add(r);
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

    public void sortStok2(){
        String url = "http://192.168.8.102/CI_Mobile_P3L_1F/index.php/produk/sortStok2";
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String produk = jsonObject.getString("message");
                            JSONArray jsonArray = new JSONArray(produk);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject objectReview = jsonArray.getJSONObject(i);
                                produkDAO r = new produkDAO(objectReview.getString("id_produk"),
                                        objectReview.getString("nama_jenis_hewan"),
                                        objectReview.getString("nama_produk"),
                                        objectReview.getString("satuan_produk"),
                                        objectReview.getString("foto_produk"),
                                        objectReview.getString("status_data"),
                                        objectReview.getString("time_stamp"),
                                        objectReview.getString("keterangan"),
                                        objectReview.getInt("stok_produk"),
                                        objectReview.getInt("stok_minimal_produk"),
                                        objectReview.getInt("harga_satuan_produk"));

                                System.out.println(objectReview);
                                if(objectReview.getString("status_data").compareToIgnoreCase("deleted") != 0) {
                                    ProdukListStokTertinggi.add(r);
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
        searchView.setQueryHint("Cari Produk ... (Nama/Jenis)");
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
