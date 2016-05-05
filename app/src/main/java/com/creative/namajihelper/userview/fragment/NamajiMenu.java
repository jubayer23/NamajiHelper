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

import com.creative.namajihelper.AboutUs;
import com.creative.namajihelper.NamajiHome;
import com.creative.namajihelper.NamajiSearchResult;
import com.creative.namajihelper.R;
import com.creative.namajihelper.SearchMosqueDetails;
import com.creative.namajihelper.adapter.SearchListAdapter;
import com.creative.namajihelper.appdata.AppConstant;
import com.creative.namajihelper.appdata.AppController;
import com.creative.namajihelper.model.Mosque;
import com.creative.namajihelper.model.Namaji;
import com.creative.namajihelper.userview.LoginActivity;
import com.creative.namajihelper.utils.GPSTracker;

import java.util.ArrayList;

/**
 * Created by comsol on 24-Apr-16.
 */
public class NamajiMenu extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";


    private int mPage;

    Button btn_aboutUs, btn_logout;

    GPSTracker gps;


    public static NamajiMenu newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        NamajiMenu fragment = new NamajiMenu();
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
        View view = inflater.inflate(R.layout.namaji_menu, container, false);
        // TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        // tvTitle.setText("Fragment #" + mPage);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        init();


        btn_aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AboutUs.class));
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppController.getInstance().getPrefManger().setLoginType("");
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                NamajiHome.namajiHomeActivity.finish();
            }
        });


    }

    private void init() {

        btn_aboutUs = (Button) getActivity().findViewById(R.id.btn_aboutus);
        btn_logout = (Button) getActivity().findViewById(R.id.btn_logout);

        Namaji namaji = AppController.getInstance().getPrefManger().getNamajiObject();

        if(namaji.getUserName().equalsIgnoreCase(AppConstant.user_anoymus))
        {
            btn_logout.setEnabled(false);
            btn_logout.getBackground().setAlpha(128);
        }
    }


}