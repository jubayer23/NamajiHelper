package com.creative.namajihelper.userview.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.creative.namajihelper.R;
import com.creative.namajihelper.appdata.AppConstant;
import com.creative.namajihelper.appdata.AppController;
import com.creative.namajihelper.model.Mosque;
import com.creative.namajihelper.utils.CheckDeviceConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by comsol on 24-Apr-16.
 */
public class MosqueTimeTable extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    Mosque mosque;

    LinearLayout ll_fajar, ll_juhar, ll_asar, ll_magrib, ll_esha, ll_eid;

    ArrayList<LinearLayout> ll_container;

    ArrayList<TextView> tv_container;

    private static final int TEXTVIEW_SIZE_FOR_TIME = 25;

    Button btn_edit;

    private ProgressDialog pDialog;


    CheckDeviceConfig cd;

    public static MosqueTimeTable newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        MosqueTimeTable fragment = new MosqueTimeTable();
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
        View view = inflater.inflate(R.layout.mosque_time_table, container, false);
        // TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        // tvTitle.setText("Fragment #" + mPage);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();


        setTextView();

        showTextViewHideEditText();

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cd.isConnectingToInternet()) {
                    if (btn_edit.getText().toString().equalsIgnoreCase(getResources().getString(R.string.edit))) {
                        btn_edit.setText(getResources().getString(R.string.submit));

                        showEditTextHideTextView();

                    } else {
                        pDialog.show();


                        updateValue();


                    }
                } else {
                    cd.showAlertDialogToNetworkConnection(getActivity(), getResources().getString(R.string.alertTitleInterner),
                            getResources().getString(R.string.alertMessageInternet), false);
                }


            }
        });


        //showEditTextHideTextView();
    }


    private void init() {
        mosque = AppController.getInstance().getPrefManger().getMosqueObject();

        ll_container = new ArrayList<>();

        tv_container = new ArrayList<>();


        ll_fajar = (LinearLayout) getActivity().findViewById(R.id.ll_fajar);
        ll_juhar = (LinearLayout) getActivity().findViewById(R.id.ll_juhar);
        ll_asar = (LinearLayout) getActivity().findViewById(R.id.ll_asar);
        ll_magrib = (LinearLayout) getActivity().findViewById(R.id.ll_magrib);
        ll_esha = (LinearLayout) getActivity().findViewById(R.id.ll_esha);
        ll_eid = (LinearLayout) getActivity().findViewById(R.id.ll_eid);


        btn_edit = (Button) getActivity().findViewById(R.id.btn_edit);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage(getResources().getString(R.string.savingMessage));
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);

        cd = new CheckDeviceConfig(getActivity());

    }


    private void setTextView() {

        int counter = 1;


        while (counter <= 6) {
            LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);

            TextView tv = new TextView(getActivity());
            tv.setLayoutParams(LLParams);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(TEXTVIEW_SIZE_FOR_TIME);


            LinearLayout ll_for_ed = new LinearLayout(getActivity());
            ll_for_ed.setLayoutParams(LLParams);


            LinearLayout.LayoutParams LLParams_ed_1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 3f);


            LinearLayout.LayoutParams LLParams_ed_2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 4f);

            LinearLayout.LayoutParams LLParams_btn = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 3f);

            ArrayList<EditText> temp_ed_container = new ArrayList<>();

            EditText ed_1 = new EditText(getActivity());
            ed_1.setInputType(InputType.TYPE_CLASS_NUMBER);
            ed_1.setLayoutParams(LLParams_ed_1);
            temp_ed_container.add(ed_1);


            EditText ed_2 = new EditText(getActivity());
            ed_2.setInputType(InputType.TYPE_CLASS_NUMBER);
            ed_2.setLayoutParams(LLParams_ed_2);
            temp_ed_container.add(ed_2);

            final Button btn_am_pm = new Button(getActivity());
            btn_am_pm.setLayoutParams(LLParams_btn);
            btn_am_pm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (btn_am_pm.getText().toString().equalsIgnoreCase(getResources().getString(R.string.am))) {
                        btn_am_pm.setText(getResources().getString(R.string.pm));
                    } else {
                        btn_am_pm.setText(getResources().getString(R.string.am));
                    }
                }
            });

            ll_for_ed.addView(ed_1);
            ll_for_ed.addView(ed_2);
            ll_for_ed.addView(btn_am_pm);


            switch (counter) {
                case AppConstant.FAJAR:
                    //tv.setText(mosque.getFajar());
                    ll_fajar.addView(tv);
                    ll_fajar.addView(ll_for_ed);
                    tv_container.add(tv);
                    ll_container.add(ll_for_ed);
                    setEditTextAndButtonText(mosque.getFajar(), btn_am_pm, temp_ed_container, tv);

                    break;
                case AppConstant.JUHAR:
                    // Log.d("DEBUG",String.valueOf(counter));
                    //tv.setText(mosque.getJuhar());
                    ll_juhar.addView(tv);
                    ll_juhar.addView(ll_for_ed);
                    tv_container.add(tv);
                    ll_container.add(ll_for_ed);
                    setEditTextAndButtonText(mosque.getJuhar(), btn_am_pm, temp_ed_container, tv);

                    break;
                case AppConstant.ASAR:
                    // Log.d("DEBUG",String.valueOf(counter));
                    //tv.setText(mosque.getJuhar());
                    ll_asar.addView(tv);
                    ll_asar.addView(ll_for_ed);
                    tv_container.add(tv);
                    ll_container.add(ll_for_ed);
                    setEditTextAndButtonText(mosque.getAsar(), btn_am_pm, temp_ed_container, tv);

                    break;
                case AppConstant.MAGRIB:
                    // Log.d("DEBUG",String.valueOf(counter));
                    //tv.setText(mosque.getJuhar());
                    ll_magrib.addView(tv);
                    ll_magrib.addView(ll_for_ed);
                    tv_container.add(tv);
                    ll_container.add(ll_for_ed);
                    setEditTextAndButtonText(mosque.getMagrib(), btn_am_pm, temp_ed_container, tv);

                    break;
                case AppConstant.ESHA:
                    // Log.d("DEBUG",String.valueOf(counter));
                    //tv.setText(mosque.getJuhar());
                    ll_esha.addView(tv);
                    ll_esha.addView(ll_for_ed);
                    tv_container.add(tv);
                    ll_container.add(ll_for_ed);
                    setEditTextAndButtonText(mosque.getEsha(), btn_am_pm, temp_ed_container, tv);

                    break;
                case AppConstant.EID:
                    // Log.d("DEBUG",String.valueOf(counter));
                    //tv.setText(mosque.getJuhar());
                    ll_eid.addView(tv);
                    ll_eid.addView(ll_for_ed);
                    tv_container.add(tv);
                    ll_container.add(ll_for_ed);
                    setEditTextAndButtonText(mosque.getEid(), btn_am_pm, temp_ed_container, tv);

                    break;
                default:

            }

            counter++;
        }
    }

    private void setEditTextAndButtonText(String namaji_time, Button btn_am_pm, ArrayList<EditText> temp_ed_container, TextView tv) {
        String time_text = "";
        String b[] = namaji_time.split(":");
        for (int i = 0; i < b.length; i++) {

            b[i] = b[i].replaceAll("\\s+", "");
            if (i == 0) {
                int hour = Integer.parseInt(b[i]);

                if (hour > 12) {
                    btn_am_pm.setText(getResources().getString(R.string.pm));
                    temp_ed_container.get(i).setText(String.valueOf(hour - 12));
                    time_text = time_text + String.valueOf(hour - 12);
                } else {
                    btn_am_pm.setText(getResources().getString(R.string.am));
                    temp_ed_container.get(i).setText(b[i]);
                    time_text = time_text + b[i];
                }
            } else {
                temp_ed_container.get(i).setText(b[i]);

                time_text = time_text + " : " + b[i] + " " + btn_am_pm.getText().toString();
                tv.setText(time_text);
            }


        }

    }

    private void showTextViewHideEditText() {

        for (int i = 0; i < tv_container.size(); i++) {
            tv_container.get(i).setVisibility(View.VISIBLE);
            ll_container.get(i).setVisibility(View.GONE);
        }
    }

    private void showEditTextHideTextView() {

        for (int i = 0; i < ll_container.size(); i++) {
            tv_container.get(i).setVisibility(View.GONE);
            ll_container.get(i).setVisibility(View.VISIBLE);
        }
    }


    private void updateValue() {
        //String fajar="1.1",juhar="1.1",asar="1.1",magrib="1.1",esha="1.1",eid="1.1";

        for (int i = 0; i < ll_container.size(); i++) {
            String time_text = "";
            String hour = "", minute = "";
            for (int j = 0; j < ll_container.get(i).getChildCount(); j++) {
                View v = ll_container.get(i).getChildAt(j);
                if (j == 0) {
                    hour = ((EditText) v).getText().toString().replaceAll("\\s+", "");
                    if (hour.isEmpty()) {
                        if (i == ll_container.size() - 1)//here (ll_container.size()-1) == EID field
                        {
                            hour = "0";
                        } else {
                            pDialog.dismiss();
                            cd.showAlertDialogToNetworkConnection(getActivity(), getResources().getString(R.string.alertFieldEmptyTitle),
                                    getResources().getString(R.string.alertFieldEmptyMessage), false);
                            return;
                        }

                    }
                    if (Integer.parseInt(hour) > 12) {
                        pDialog.dismiss();
                        cd.showAlertDialogToNetworkConnection(getActivity(), getResources().getString(R.string.alertInvalidInputTitle),
                                getResources().getString(R.string.alertInvalidInputHour), false);
                        return;
                    }
                    time_text = hour;
                } else if (j == 1) {
                    minute = ((EditText) v).getText().toString().replaceAll("\\s+", "");
                    if (minute.isEmpty()) {
                        if (i == ll_container.size() - 1)//here (ll_container.size()-1) == EID field
                        {
                            minute = "00";
                        } else {
                            pDialog.dismiss();
                            cd.showAlertDialogToNetworkConnection(getActivity(), getResources().getString(R.string.alertFieldEmptyTitle),
                                    getResources().getString(R.string.alertFieldEmptyMessage2), false);
                            return;
                        }
                    }
                    if (Integer.parseInt(minute) > 60) {
                        pDialog.dismiss();
                        cd.showAlertDialogToNetworkConnection(getActivity(), getResources().getString(R.string.alertInvalidInputTitle),
                                getResources().getString(R.string.alertInvalidInputMinute), false);
                        return;
                    }
                } else {
                    if (((Button) v).getText().toString().equalsIgnoreCase("PM")) {
                        hour = String.valueOf(Integer.parseInt(hour) + 12);
                    }
                    time_text = time_text + " : " + minute + " " + ((Button) v).getText().toString();
                    tv_container.get(i).setText(time_text);
                }
            }

            switch (i + 1) {
                case AppConstant.FAJAR:
                    mosque.setFajar(hour + ":" + minute);
                    break;
                case AppConstant.JUHAR:
                    mosque.setJuhar(hour + ":" + minute);
                    break;
                case AppConstant.ASAR:
                    mosque.setAsar(hour + ":" + minute);
                    break;
                case AppConstant.MAGRIB:
                    mosque.setMagrib(hour + ":" + minute);
                    break;
                case AppConstant.ESHA:
                    mosque.setEsha(hour + ":" + minute);
                    break;
                case AppConstant.EID:
                    mosque.setEid(hour + ":" + minute);
                    break;
            }

        }

        hitUrl(AppConstant.getMosueTimeUpdateUrl(String.valueOf(mosque.getId()), mosque.getFajar(), mosque.getJuhar(), mosque.getAsar(), mosque.getMagrib(), mosque.getEsha(), mosque.getEid()));


    }

    private void hitUrl(String mosueTimeUpdateUrl) {


        String url = mosueTimeUpdateUrl.replaceAll(" ", "%20");

        StringRequest req = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {


                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            if (success == 1) {
                                AppController.getInstance().getPrefManger().setMosqueObject(mosque);

                                btn_edit.setText(getResources().getString(R.string.edit));
                                showTextViewHideEditText();

                            }
                        } catch (JSONException e) {

                            if (pDialog.isShowing()) pDialog.dismiss();

                        }

                        if (pDialog.isShowing()) pDialog.dismiss();


                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (pDialog.isShowing()) pDialog.dismiss();


            }
        });
        // req.setRetryPolicy(new DefaultRetryPolicy(Constants.MY_SOCKET_TIMEOUT_MS,
        //        DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(req);
    }


}