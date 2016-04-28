package com.creative.namajihelper.userview.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.creative.namajihelper.NamajiSearchResult;
import com.creative.namajihelper.R;
import com.creative.namajihelper.alertbanner.AlertDialogForAnything;
import com.creative.namajihelper.appdata.AppConstant;
import com.creative.namajihelper.appdata.AppController;
import com.creative.namajihelper.model.Namaji;
import com.creative.namajihelper.utils.GPSTracker;
import com.creative.namajihelper.utils.GpsEnableTool;

/**
 * Created by comsol on 24-Apr-16.
 */
public class NamajiSearch extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    Button btn_neaby_mosque;

    TextView tv_welcomeNote;

    Namaji namaji;

    GpsEnableTool gpsEnableTool;

    GPSTracker gps;

    public static final String KEY_MOSQUE_TYPE = "mosque_type";
    public static final String KEY_LAT = "lat";
    public static final String KEY_LNG = "lng";
    public static final String KEY_RANGE = "range";


    public static NamajiSearch newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        NamajiSearch fragment = new NamajiSearch();
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
        View view = inflater.inflate(R.layout.namaji_mosque_search, container, false);
        // TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        // tvTitle.setText("Fragment #" + mPage);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();

        btn_neaby_mosque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gps = new GPSTracker(getActivity());

                if (gps.canGetLocation()) {
                    showSettingDialog();

                } else {
                    AlertDialogForAnything.showAlertDialogWhenComplte(getActivity(), "Gps Disabled", "Please Enable Gps", false);
                }

            }
        });

    }

    private void init() {

        namaji = AppController.getInstance().getPrefManger().getNamajiObject();

        gpsEnableTool = new GpsEnableTool(getActivity());
        gpsEnableTool.enableGPs();

        tv_welcomeNote = (TextView) getActivity().findViewById(R.id.tv_welcome);
        tv_welcomeNote.setText("Welcome " + namaji.getUserName());

        btn_neaby_mosque = (Button) getActivity().findViewById(R.id.btn_nearby_mosque);
    }

    private void showSettingDialog() {
        final String[] mosque_type = new String[1];
        final String lat=String.valueOf(gps.getLatitude());
        final String lng=String.valueOf(gps.getLongitude());
        final String[] range = new String[1];

        final Dialog dialog_start = new Dialog(getActivity(),
                android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog_start.setCancelable(true);
        dialog_start.setContentView(R.layout.dialog_search);

        Spinner mosqueType_sp = (Spinner) dialog_start.findViewById(R.id.dialog_spinner_mosque_type);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (getActivity(), R.layout.spinner_item, AppConstant.mosqueType);
        mosqueType_sp.setAdapter(dataAdapter);

        SeekBar rangeBar = (SeekBar) dialog_start.findViewById(R.id.dialog_seekbar);
        final TextView seekbar_text = (TextView) dialog_start.findViewById(R.id.dialog_seekbar_text);




        LinearLayout btn_submit = (LinearLayout) dialog_start.findViewById(R.id.dialog_btn_submit);



        mosqueType_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();

                item = item.trim();

                mosque_type[0] = item;

                // Log.d("DEBUG_type", item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        rangeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                seekbar_text.setText(progress + " miters");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                range[0] = String.valueOf(seekBar.getProgress());
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), NamajiSearchResult.class);

                intent.putExtra(KEY_MOSQUE_TYPE,mosque_type[0]);
                intent.putExtra(KEY_LAT,lat);
                intent.putExtra(KEY_LNG,lng);
                intent.putExtra(KEY_RANGE,range[0]);

                startActivity(intent);

            }
        });


        dialog_start.show();
    }
}