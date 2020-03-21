package com.p3l_f_1_pegawai.Activities.ukuran_hewan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.p3l_f_1_pegawai.R;
import com.p3l_f_1_pegawai.dao.ukuran_hewan;
import com.p3l_f_1_pegawai.login_activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UkuranHewanFragment extends Fragment {
    Activity context;
    private List<ukuran_hewan> UkuranHewanList;
    private RecyclerView recyclerView;
    private UkuranHewanAdapter recycleAdapter;
    private FloatingActionButton tambahUkuran;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_ukuran_hewan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UkuranHewanList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recycle_tampil_ukuran);
        tambahUkuran = view.findViewById(R.id.fab_ukuran);
        recycleAdapter = new UkuranHewanAdapter(UkuranHewanList, getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recycleAdapter);

        getUkuranHewan();

        tambahUkuran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertAddUkuran();
            }
        });
    }

    public void alertAddUkuran()
    {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View dialog = layoutInflater.inflate(R.layout.layout_dialog_ukuran, null);

        AlertDialog.Builder dialogtambahukuran = new
                AlertDialog.Builder(getActivity());

        dialogtambahukuran.setView(dialog);

        dialogtambahukuran.setPositiveButton("Simpan",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(context,
                                login_activity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        Toast.makeText(context,
                                "Terimakasih!", Toast.LENGTH_SHORT)
                                .show();
                    }
                });

        dialogtambahukuran.setNegativeButton("Batal",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(),
                                "Batal Tambah Ukuran Hewan", Toast.LENGTH_SHORT)
                                .show();
                        dialog.cancel();
                    }
                });

        dialogtambahukuran.show();
    }

    public void getUkuranHewan(){
        String url = "http://192.168.8.101/CI_Mobile_P3L_1F/index.php/ukuranhewan";
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ukuran_hewan = jsonObject.getString("message");
                            JSONArray jsonArray = new JSONArray(ukuran_hewan);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject objectReview = jsonArray.getJSONObject(i);
                                ukuran_hewan r = new ukuran_hewan(objectReview.getString("ID_UKURAN_HEWAN"),
                                        objectReview.getString("NAMA_UKURAN_HEWAN"),
                                        objectReview.getString("STATUS_DATA"),
                                        objectReview.getString("TIME_STAMP"),
                                        objectReview.getString("KETERANGAN"));

                                System.out.println(objectReview);
                                UkuranHewanList.add(r);
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
                        Toast.makeText(getActivity(), "Kesalahan Koneksi!", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(getRequest);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Cari Ukuran Hewan ...");
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