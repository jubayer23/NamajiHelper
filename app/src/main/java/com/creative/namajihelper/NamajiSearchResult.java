package com.creative.namajihelper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.Collections;

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


    EditText ed_mosque_name;

    TextView tv_no_data;

    public static final String KEY_OBJECT = "mosque_ob";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        getSupportActionBar().setHomeButtonEnabled(true);

        init();

        final Intent intent = getIntent();


        //lat = Double.parseDouble(intent.getStringExtra(NamajiSearch.KEY_LAT));
        //lng = Double.parseDouble(intent.getStringExtra(NamajiSearch.KEY_LNG));


        if (intent.getStringExtra(NamajiSearch.KEY_SEARCH_TYPE).equalsIgnoreCase(NamajiSearch.BY_NAME)) {
            ed_mosque_name.setVisibility(View.VISIBLE);
            sendRequestToServer(AppConstant.getSearchByNameUrl(intent.getStringExtra(NamajiSearch.KEY_MOSQUE_NAME), intent.getStringExtra(NamajiSearch.KEY_LAT), intent.getStringExtra(NamajiSearch.KEY_LNG)));

        } else {

            sendRequestToServer(AppConstant.getNeabyMosqueUrl(intent.getStringExtra(NamajiSearch.KEY_MOSQUE_TYPE), intent.getStringExtra(NamajiSearch.KEY_LAT), intent.getStringExtra(NamajiSearch.KEY_LNG), intent.getStringExtra(NamajiSearch.KEY_RANGE)));

        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Mosque mosque = mosqueList.get(i);
                Intent intent = new Intent(NamajiSearchResult.this, NamajiMosqueDetails.class);
                intent.putExtra(KEY_OBJECT, mosque);
                startActivity(intent);
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });

        ed_mosque_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!ed_mosque_name.getText().toString().isEmpty()) {
                        searchListAdapter = null;
                        sendRequestToServer(AppConstant.getSearchByNameUrl(ed_mosque_name.getText().toString().replaceAll(" ", "%20"), intent.getStringExtra(NamajiSearch.KEY_LAT), intent.getStringExtra(NamajiSearch.KEY_LNG)));
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void init() {


        mosqueList = new ArrayList<>();

        listView = (ListView) findViewById(R.id.list_search);

        gson = new Gson();

        ed_mosque_name = (EditText) findViewById(R.id.ed_mosque_name);
        ed_mosque_name.setVisibility(View.GONE);

        tv_no_data = (TextView) findViewById(R.id.tv_no_data);
        tv_no_data.setVisibility(View.GONE);

        progressDialog = new ProgressDialog(NamajiSearchResult.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Login...");


    }

    public void sendRequestToServer(String url_all_products) {

        progressDialog.show();

        String url = url_all_products;

        // Log.d("DEBUG",url);

        StringRequest req = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //Log.d("DEBUG",response);

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


            Collections.sort(mosqueList, new Mosque.SalaryComparator());


            if (searchListAdapter == null && !mosqueList.isEmpty()) {
                listView.setVisibility(View.VISIBLE);
                tv_no_data.setVisibility(View.GONE);
                searchListAdapter = new SearchListAdapter(
                        NamajiSearchResult.this, mosqueList);
                listView.setAdapter(searchListAdapter);

            } else {
                //searchListAdapter.addMore();
                listView.setVisibility(View.GONE);
                tv_no_data.setVisibility(View.VISIBLE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (progressDialog.isShowing()) progressDialog.dismiss();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:

                onBackPressed();
                break;

        }

        return true;
    }
}
