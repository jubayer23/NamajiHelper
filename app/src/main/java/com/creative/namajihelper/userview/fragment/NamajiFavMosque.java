package com.creative.namajihelper.userview.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.creative.namajihelper.NamajiSearchResult;
import com.creative.namajihelper.R;
import com.creative.namajihelper.SearchMosqueDetails;
import com.creative.namajihelper.adapter.FavListAdapter;
import com.creative.namajihelper.appdata.AppController;
import com.creative.namajihelper.model.Mosque;
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

    FavListAdapter favListAdapter;

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
            favListAdapter = new FavListAdapter(getActivity(), favMosqueList, gps.getLatitude(), gps.getLongitude());
            listView.setAdapter(favListAdapter);
        } else {
            favListAdapter = new FavListAdapter(getActivity(), favMosqueList, 0, 0);
            listView.setAdapter(favListAdapter);
        }

    }
}