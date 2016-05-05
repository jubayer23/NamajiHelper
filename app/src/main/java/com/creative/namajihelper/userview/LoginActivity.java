package com.creative.namajihelper.userview;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.creative.namajihelper.MainActivity;
import com.creative.namajihelper.MosqueHome;
import com.creative.namajihelper.NamajiHome;
import com.creative.namajihelper.R;
import com.creative.namajihelper.alertbanner.AlertDialogForAnything;
import com.creative.namajihelper.appdata.AppConstant;
import com.creative.namajihelper.appdata.AppController;
import com.creative.namajihelper.model.Mosque;
import com.creative.namajihelper.model.Namaji;
import com.creative.namajihelper.utils.CheckDeviceConfig;
import com.creative.namajihelper.utils.GPSTracker;
import com.creative.namajihelper.utils.GpsEnableTool;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by comsol on 20-Apr-16.
 */
public class LoginActivity extends AppCompatActivity {
    public static Activity LoginActivity;

    String mobileNo;
    String password;

    Button loginB,btn_skip;
    EditText mobileNoEd, passwordEd;
    TextView registerUser;
    //SqliteDb theDb;
    ProgressDialog progressDialog;


    CheckDeviceConfig cd;

    GPSTracker gps;

    GpsEnableTool gpsEnableTool;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (!AppController.getInstance().getPrefManger().getLoginType().isEmpty()) {
            if (AppController.getInstance().getPrefManger().getLoginType().equalsIgnoreCase(AppConstant.LOGIN_TYPE_NAMAJI)) {
                Intent intent = new Intent(LoginActivity.this, NamajiHome.class);

                startActivity(intent);

                finish();
            } else {
                Intent intent = new Intent(LoginActivity.this, MosqueHome.class);

                startActivity(intent);

                finish();
            }

        }


        LoginActivity = this;

        init();


        /****************BUTTON INITIALIZATION******************/


        loginB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (cd.isConnectingToInternet()) {
                    mobileNo = mobileNoEd.getText().toString();
                    password = passwordEd.getText().toString();

                    if (showWarningDialog()) {

                        sendRequestToServer(AppConstant.getLoginUrl(mobileNo, password));

                    }

                } else {
                    cd.showAlertDialogToNetworkConnection(LoginActivity.this, "No Internet Connection",
                            "You don't have internet connection.", false);
                }

            }
        });




        registerUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // Intent signUpIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
                // startActivity(signUpIntent);
                showSettingDialog();
            }
        });


        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Namaji namajiObj = new Namaji(0,AppConstant.user_anoymus,"0000");
                AppController.getInstance().getPrefManger().setNamajiObject(namajiObj);

                Intent intent = new Intent(LoginActivity.this, NamajiHome.class);

                startActivity(intent);



            }
        });
    }

    private void init() {
        // gson = new Gson();
        //theDb = AppController.getsqliteDbInstance();
        cd = new CheckDeviceConfig(this);

        gps = new GPSTracker(this);

        gpsEnableTool = new GpsEnableTool(this);

        if (!gps.canGetLocation()) {
            gpsEnableTool.enableGPs();
        }


        loginB = (Button) findViewById(R.id.loginInnerB);

        mobileNoEd = (EditText) findViewById(R.id.mobileno_ed);

        passwordEd = (EditText) findViewById(R.id.pasword_ed);

        registerUser = (TextView) findViewById(R.id.btn_signup);

        btn_skip = (Button)findViewById(R.id.btn_skip);

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Login...");

    }

    public boolean showWarningDialog() {

        boolean valid = true;

        if (mobileNoEd.getText().toString().isEmpty()) {
            mobileNoEd.setError("Enter MobileNo");
            valid = false;
        } else {
            mobileNoEd.setError(null);
        }

        if (passwordEd.getText().toString().isEmpty()) {
            passwordEd.setError("Enter Password");
            valid = false;
        } else {
            passwordEd.setError(null);
        }

        if (!(mobileNoEd.getText().toString().isEmpty() && passwordEd.getText().toString().isEmpty())) {
            if (mobileNoEd.getText().toString().isEmpty() && !passwordEd.getText().toString().isEmpty()) {
                mobileNoEd.requestFocus();
            }
            if (!mobileNoEd.getText().toString().isEmpty() && passwordEd.getText().toString().isEmpty()) {
                passwordEd.requestFocus();
            }
        }


        return valid;
    }


    public void sendRequestToServer(String url_all_products) {

        progressDialog.show();

        String url = url_all_products;

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
            //Log.d("DEBUG_loginStatus", String.valueOf(status));

            if (status == 1) {

                String login_type = response.getString("user_type");

                if (login_type.equalsIgnoreCase(AppConstant.LOGIN_TYPE_NAMAJI)) {
                    int id = response.getInt("user_id");
                    String name = response.getString("username");

                    Namaji namajiObj = new Namaji(id, name, mobileNo);
                    AppController.getInstance().getPrefManger().setNamajiObject(namajiObj);

                } else {
                    int id = response.getInt("mosque_id");
                    String mosqueName = response.getString("mosque_name");
                    String mosqueType = response.getString("mosque_type");
                    Double lat = response.getDouble("lat");
                    Double lng = response.getDouble("lng");
                    String fajar = response.getString("fajar");
                    String juhar = response.getString("juhar");
                    String asar = response.getString("asar");
                    String magrib = response.getString("magrib");
                    String esha = response.getString("esha");
                    String eid = response.getString("eid");

                    Mosque mosqueObj = new Mosque(id, mosqueName, mosqueType, mobileNo, lat, lng, fajar, juhar, asar, magrib, esha, eid,0);
                    AppController.getInstance().getPrefManger().setMosqueObject(mosqueObj);

                }

                AppController.getInstance().getPrefManger().setLoginType(login_type);
                gotoFrontPage(login_type);

            } else {
                AlertDialogForAnything.showAlertDialogWhenComplte(this, "Login Failed", "InValid Login Information", false);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {

        } catch (Exception e) {

        }
        if (progressDialog.isShowing()) progressDialog.dismiss();

    }


    public void gotoFrontPage(String login_type) {
        if (login_type.equalsIgnoreCase(AppConstant.LOGIN_TYPE_NAMAJI)) {

            Intent intent = new Intent(LoginActivity.this, NamajiHome.class);

            startActivity(intent);


            finish();
        } else {
            Intent intent = new Intent(LoginActivity.this, MosqueHome.class);

            startActivity(intent);


            finish();
        }

    }


    /********************
     * ALL DIALOG
     ****************************/


    private void showSettingDialog() {

        final Dialog dialog_start = new Dialog(this,
                android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog_start.setCancelable(true);
        dialog_start.setContentView(R.layout.dialog_signup);


        LinearLayout namaji = (LinearLayout) dialog_start.findViewById(R.id.dialog_signup_namaji);
        LinearLayout mosque = (LinearLayout) dialog_start.findViewById(R.id.dialog_signup_mosque);

        namaji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, RegisterNamaji.class);
                startActivity(intent);

                dialog_start.dismiss();
            }
        });
        mosque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, RegisterMosque.class);
                startActivity(intent);

                dialog_start.dismiss();
            }
        });


        dialog_start.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstant.REQUEST_CHECK_SETTINGS) {

            if (resultCode == RESULT_OK) {

                Toast.makeText(getApplicationContext(), "GPS enabled", Toast.LENGTH_LONG).show();
            } else {

                Toast.makeText(getApplicationContext(), "GPS is not enabled", Toast.LENGTH_LONG).show();
            }

        }

    }
}
