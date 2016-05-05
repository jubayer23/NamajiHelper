package com.creative.namajihelper.userview.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.creative.namajihelper.MosqueHome;
import com.creative.namajihelper.R;
import com.creative.namajihelper.appdata.AppController;
import com.creative.namajihelper.model.Mosque;
import com.creative.namajihelper.userview.LoginActivity;

/**
 * Created by comsol on 24-Apr-16.
 */
public class MosqueDetails extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    TextView tv_mosquename,tv_mosquetype;

    Button btn_logout,btn_showmap;


    public static MosqueDetails newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        MosqueDetails fragment = new MosqueDetails();
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
        View view = inflater.inflate(R.layout.mosque_details, container, false);
        // TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        // tvTitle.setText("Fragment #" + mPage);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();

        final Mosque mosque = AppController.getInstance().getPrefManger().getMosqueObject();

        tv_mosquename.setText(mosque.getMosqueName());

        tv_mosquetype.setText(mosque.getMosqueType());

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppController.getInstance().getPrefManger().setLoginType("");
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                MosqueHome.mosqueHomeActivity.finish();
            }
        });

        btn_showmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String geoUri = "http://maps.google.com/maps?q=loc:" + mosque.getLat() + "," + mosque.getLng() + " (" + mosque.getMosqueName() + ")";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        });

    }

    private void init() {

        btn_logout = (Button) getActivity().findViewById(R.id.btn_logout);

        btn_showmap = (Button) getActivity().findViewById(R.id.btn_show_location);

        tv_mosquename = (TextView) getActivity().findViewById(R.id.tv_mosque_name);

        tv_mosquetype = (TextView) getActivity().findViewById(R.id.tv_mosque_type);

    }
}