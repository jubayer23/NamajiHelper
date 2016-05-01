package com.creative.namajihelper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.creative.namajihelper.adapter.SearchListAdapter;
import com.creative.namajihelper.appdata.AppConstant;
import com.creative.namajihelper.appdata.AppController;
import com.creative.namajihelper.model.Mosque;
import com.creative.namajihelper.userview.fragment.NamajiSearch;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by comsol on 28-Apr-16.
 */


public class NamajiSearchResult extends AppCompatActivity {

    ListView listView;

    ArrayList<Mosque> mosqueList;

    //SqliteDb theDb;
    ProgressDialog progressDialog;

    Gson gson;

    SearchListAdapter searchListAdapter;

    double lat, lng;

    public static final String KEY_OBJECT = "mosque_ob";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        getSupportActionBar().setHomeButtonEnabled(true);

        init();

        Intent intent = getIntent();


        lat = Double.parseDouble(intent.getStringExtra(NamajiSearch.KEY_LAT));
        lng = Double.parseDouble(intent.getStringExtra(NamajiSearch.KEY_LNG));


        if (intent.getStringExtra(NamajiSearch.KEY_SEARCH_TYPE).equalsIgnoreCase(NamajiSearch.BY_NAME)) {
            sendRequestToServer(AppConstant.getSearchByNameUrl(intent.getStringExtra(NamajiSearch.KEY_MOSQUE_NAME)));

        } else {
            sendRequestToServer(AppConstant.getNeabyMosqueUrl(intent.getStringExtra(NamajiSearch.KEY_MOSQUE_TYPE), intent.getStringExtra(NamajiSearch.KEY_LAT), intent.getStringExtra(NamajiSearch.KEY_LNG), intent.getStringExtra(NamajiSearch.KEY_RANGE)));

        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Mosque mosque = mosqueList.get(i);
                Intent intent = new Intent(NamajiSearchResult.this, SearchMosqueDetails.class);
                intent.putExtra(KEY_OBJECT, mosque);
                startActivity(intent);
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
    }

    private void init() {


        mosqueList = new ArrayList<>();

        listView = (ListView) findViewById(R.id.list_search);

        gson = new Gson();

        progressDialog = new ProgressDialog(NamajiSearchResult.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Login...");


    }

    public void sendRequestToServer(String url_all_products) {

        progressDialog.show();

        String url = url_all_products;

        //Log.d("DEBUG",url);

        StringRequest req = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {


                            parseJsonFeed(new JSONArray(response));
                        } catch (JSONException e) {

                            if (progressDialog.isShowing()) progressDialog.dismiss();

                        }


                    }


                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (progressDialog.isShowing()) progressDialog.dismiss();


            }
        });
        // req.setRetryPolicy(new DefaultRetryPolicy(Constants.MY_SOCKET_TIMEOUT_MS,
        //        DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(req);
    }

    private void parseJsonFeed(JSONArray jsonArray) {

        mosqueList.clear();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                Mosque mosque = gson.fromJson(jsonObject.toString(), Mosque.class);

                mosqueList.add(mosque);

            }

            if (searchListAdapter == null) {
                searchListAdapter = new SearchListAdapter(
                        NamajiSearchResult.this, mosqueList, lat, lng);
                listView.setAdapter(searchListAdapter);

            } else {
                searchListAdapter.addMore();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (progressDialog.isShowing()) progressDialog.dismiss();

    }
}
