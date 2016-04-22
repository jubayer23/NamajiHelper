package com.creative.namajihelper.userview;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.creative.namajihelper.MainActivity;
import com.creative.namajihelper.R;
import com.creative.namajihelper.alertbanner.AlertDialogForAnything;
import com.creative.namajihelper.appdata.AppConstant;
import com.creative.namajihelper.appdata.AppController;
import com.creative.namajihelper.model.Mosque;
import com.creative.namajihelper.utils.CheckDeviceConfig;
import com.creative.namajihelper.utils.GPSTracker;
import com.creative.namajihelper.utils.GpsEnableTool;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by comsol on 21-Apr-16.
 */
public class RegisterMosque extends AppCompatActivity implements View.OnClickListener {

    Button signUp;

    EditText mosqueName_ed, mobileNo_ed, password_ed;

    RelativeLayout locationChange, location_details_rl;

    Spinner mosqueType_sp;

    TextView location_details_tv;

    private static final int PLACE_PICKER_REQUEST = 1001;

    GpsEnableTool gpsEnableTool;

    GPSTracker gps;

    CheckDeviceConfig checkDeviceConfig;


    ProgressDialog progressDialog;

    String mosqueName, mosqueType = "", mobileNo, password, lat = "", lng = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mosque_register);

        init();

        mosqueType_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();

                item = item.trim();

                mosqueType = item;

               // Log.d("DEBUG_type", item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkDeviceConfig.isConnectingToInternet()) {


                    boolean checkWarn = showWarningDialog();
                    if (checkWarn && !lat.isEmpty() && !lng.isEmpty()) {

                        mosqueName = mosqueName_ed.getText().toString().trim();
                        mosqueName = mosqueName.replaceAll(" ","%20");
                        mobileNo = mobileNo_ed.getText().toString().trim();
                        password = password_ed.getText().toString().trim();

                        sendRequestToServer(AppConstant.getMosqueRegisterUrl(mosqueName, mosqueType, mobileNo, password, lat, lng));

                    }
                } else {
                    checkDeviceConfig.showAlertDialogToNetworkConnection(RegisterMosque.this, "No Internet Connection",
                            "You don't have internet connection.", false);
                }
            }
        });

    }

    private void init() {

        gpsEnableTool = new GpsEnableTool(this);
        //gpsEnableTool.enableGPs();

        gps = new GPSTracker(this);

        checkDeviceConfig = new CheckDeviceConfig(this);

        mosqueType_sp = (Spinner) findViewById(R.id.spinner_mosque_type);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, R.layout.spinner_item, AppConstant.mosqueType);
        mosqueType_sp.setAdapter(dataAdapter);

        mosqueName_ed = (EditText) findViewById(R.id.mosquename_ed);
        mobileNo_ed = (EditText) findViewById(R.id.mobileno_ed);
        password_ed = (EditText) findViewById(R.id.password_ed);

        locationChange = (RelativeLayout) findViewById(R.id.change_location);
        locationChange.setOnClickListener(this);

        location_details_rl = (RelativeLayout) findViewById(R.id.location_details_rl);

        location_details_tv = (TextView) findViewById(R.id.location_details_tv);


        signUp = (Button) findViewById(R.id.btn_signup);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Sign Up...");


    }

    public boolean showWarningDialog() {

        boolean valid = true;

        if (mosqueName_ed.getText().toString().isEmpty()) {
            mosqueName_ed.setError("Enter Mosque Name");
            valid = false;
        } else {
            mosqueName_ed.setError(null);
        }

        if (mobileNo_ed.getText().toString().isEmpty()) {
            mobileNo_ed.setError("Enter MobileNo");
            valid = false;
        } else {
            mobileNo_ed.setError(null);
        }

        if (password_ed.getText().toString().isEmpty()) {
            password_ed.setError("Enter password");
            valid = false;
        } else {
            password_ed.setError(null);
        }

        if (mosqueName_ed.getText().toString().isEmpty() || mobileNo_ed.getText().toString().isEmpty() || password_ed.getText().toString().isEmpty()) {
            if (mosqueName_ed.getText().toString().isEmpty() && !mobileNo_ed.getText().toString().isEmpty() && !password_ed.getText().toString().isEmpty()) {
                mosqueName_ed.requestFocus();
            }
            if (!mosqueName_ed.getText().toString().isEmpty() && mobileNo_ed.getText().toString().isEmpty() && !password_ed.getText().toString().isEmpty()) {
                mobileNo_ed.requestFocus();
            }
            if (!mosqueName_ed.getText().toString().isEmpty() && !mobileNo_ed.getText().toString().isEmpty() && password_ed.getText().toString().isEmpty()) {
                password_ed.requestFocus();
            }
        } else {
            mosqueName_ed.requestFocus();
        }

        return valid;
    }

    public void sendRequestToServer(String url_all_products) {

        progressDialog.show();

        String url = url_all_products;

        //Log.d("DEBUG_url",url);

        StringRequest req = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {


                            parseJsonFeed(new JSONObject(response));
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

    private void parseJsonFeed(JSONObject response) {
        try {

            if (progressDialog.isShowing()) progressDialog.dismiss();

            int status = response.getInt("success");

            if (status == 1) {

                int id = response.getInt("mosque_id");


                Mosque mosque = new Mosque(id, mosqueName.replaceAll("%20"," "), mosqueType, mobileNo, Double.parseDouble(lat),Double.parseDouble(lng));

                AppController.getInstance().getPrefManger().setMosqueObject(mosque);


                gotoFrontPage();

            } else {

                String mosqueName = response.getString("mosqueName");
                String mobileNo = response.getString("mobileNo");

                if (mosqueName.equalsIgnoreCase("used")) {
                    AlertDialogForAnything.showAlertDialogWhenComplte(this, "Register Failed", "You mosqueName Already Taken. Please Register with different UserName", false);

                } else if (mobileNo.equalsIgnoreCase("used")) {
                    AlertDialogForAnything.showAlertDialogWhenComplte(this, "Register Failed", "You mobileNo Already Taken. Please Register with different Email", false);

                }


                // AlertDialogForAnything.showAlertDialogWhenComplte(this, "Register Failed", "You Are Device Already Registered. Please Login with exiting Id", false);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {

        } catch (Exception e) {

        }
        if (progressDialog.isShowing()) progressDialog.dismiss();

    }

    public void gotoFrontPage() {

        AppController.getInstance().getPrefManger().setLoginType(AppConstant.LOGIN_TYPE_MOSQUE);
        Intent intent = new Intent(RegisterMosque.this, MainActivity.class);

        startActivity(intent);
        LoginActivity.LoginActivity.finish();

        finish();


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.change_location) {

            if (gps.canGetLocation()) {
                if (checkDeviceConfig.isConnectingToInternet()) {
                    openPicker(gps.getLatitude(), gps.getLongitude());
                }

            } else {
                gpsEnableTool.enableGPs();
            }

        }
    }

    private void openPicker(double lattitude, double langitude) {
        PlacePicker.IntentBuilder intentBuilder;
        Intent intent;

        try {
            intentBuilder = new PlacePicker.IntentBuilder();
            intentBuilder.setLatLngBounds(new LatLngBounds(new LatLng(lattitude - .002, langitude - .002),
                    new LatLng(lattitude + .002, langitude + .002)));

            intent = intentBuilder.build(getApplicationContext());
            startActivityForResult(intent, PLACE_PICKER_REQUEST);

        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstant.REQUEST_CHECK_SETTINGS) {

            if (resultCode == RESULT_OK) {
                gps = new GPSTracker(this);

                if (gps.canGetLocation() && checkDeviceConfig.isConnectingToInternet()) {
                    openPicker(gps.getLatitude(), gps.getLongitude());
                }

                Toast.makeText(getApplicationContext(), "GPS enabled", Toast.LENGTH_LONG).show();
            } else {

                Toast.makeText(getApplicationContext(), "GPS is not enabled", Toast.LENGTH_LONG).show();
            }

        }

        if (requestCode == PLACE_PICKER_REQUEST
                && resultCode == Activity.RESULT_OK) {
            final Place place = PlacePicker.getPlace(data, this);
            location_details_rl.setVisibility(View.VISIBLE);
            // Log.d("DEBUG",place.getName() + " " +place.getAddress() + " " + place.getLocale());
            location_details_tv.setText(place.getName() + "\n" + place.getAddress());
            lat = place.getLatLng().latitude + "";
            lng = place.getLatLng().longitude + "";

            //openPicker(place.getLatLng().latitude, place.getLatLng().longitude);
        }
    }
}
