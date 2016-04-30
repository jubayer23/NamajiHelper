package com.creative.namajihelper.userview.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.creative.namajihelper.MosqueHome;
import com.creative.namajihelper.NamajiHome;
import com.creative.namajihelper.NamajiSearchResult;
import com.creative.namajihelper.R;
import com.creative.namajihelper.SearchMosqueDetails;
import com.creative.namajihelper.adapter.SearchListAdapter;
import com.creative.namajihelper.appdata.AppController;
import com.creative.namajihelper.model.Mosque;
import com.creative.namajihelper.userview.LoginActivity;
import com.creative.namajihelper.utils.GPSTracker;

import java.util.ArrayList;

/**
 * Created by comsol on 24-Apr-16.
 */
public class NamajiFavMosque extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    ListView listView;

    ArrayList<Mosque> favMosqueList;

    private int mPage;


    GPSTracker gps;

    SearchListAdapter searchListAdapter;

    public static NamajiFavMosque newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        NamajiFavMosque fragment = new NamajiFavMosque();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favourite_mosque, container, false);
        // TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        // tvTitle.setText("Fragment #" + mPage);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        init();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Mosque mosque = favMosqueList.get(i);
                Intent intent = new Intent(getActivity(), SearchMosqueDetails.class);
                intent.putExtra(NamajiSearchResult.KEY_OBJECT, mosque);
                startActivity(intent);
            }
        });

    }

    private void init() {

        listView = (ListView) getActivity().findViewById(R.id.list_fav);

    }

    @Override
    public void onResume() {
        super.onResume();
        gps = new GPSTracker(getActivity());
        favMosqueList = AppController.getInstance().getPrefManger().getFavPlaces();

        if (gps.canGetLocation()) {
            searchListAdapter = new SearchListAdapter(getActivity(), favMosqueList, gps.getLatitude(), gps.getLongitude());
            listView.setAdapter(searchListAdapter);
        } else {
            searchListAdapter = new SearchListAdapter(getActivity(), favMosqueList, 0, 0);
            listView.setAdapter(searchListAdapter);
        }

    }
}