package com.creative.namajihelper.userview;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.creative.namajihelper.MainActivity;
import com.creative.namajihelper.NamajiHome;
import com.creative.namajihelper.R;
import com.creative.namajihelper.alertbanner.AlertDialogForAnything;
import com.creative.namajihelper.appdata.AppConstant;
import com.creative.namajihelper.appdata.AppController;
import com.creative.namajihelper.model.Namaji;
import com.creative.namajihelper.utils.CheckDeviceConfig;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by comsol on 21-Apr-16.
 */
public class RegisterNamaji extends AppCompatActivity implements View.OnClickListener {

    Button signUp;

    EditText namajiName_ed, mobileNo_ed, password_ed,password_ed_2;


    CheckDeviceConfig checkDeviceConfig;


    ProgressDialog progressDialog;

    String namajiName, mobileNo, password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_namaji_register);

        init();


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkDeviceConfig.isConnectingToInternet()) {


                    boolean checkWarn = showWarningDialog();
                    if (checkWarn) {

                        if(isBothPasswordSame())
                        {
                            namajiName = namajiName_ed.getText().toString().trim();
                            namajiName = namajiName.replaceAll(" ", "%20");
                            mobileNo = mobileNo_ed.getText().toString().trim();
                            password = password_ed.getText().toString().trim();

                            sendRequestToServer(AppConstant.getNamajiRegisterUrl(namajiName, mobileNo, password));

                        }


                    }
                } else {
                    checkDeviceConfig.showAlertDialogToNetworkConnection(RegisterNamaji.this, "No Internet Connection",
                            "You don't have internet connection.", false);
                }
            }
        });

    }

    private void init() {


        checkDeviceConfig = new CheckDeviceConfig(this);


        namajiName_ed = (EditText) findViewById(R.id.namajiname_ed);
        mobileNo_ed = (EditText) findViewById(R.id.mobileno_ed);
        password_ed = (EditText) findViewById(R.id.password_ed);
        password_ed_2 = (EditText) findViewById(R.id.password_ed_2);


        signUp = (Button) findViewById(R.id.btn_signup);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Sign Up...");


    }

    public boolean showWarningDialog() {

        boolean valid = true;

        if (namajiName_ed.getText().toString().isEmpty()) {
            namajiName_ed.setError("Enter Mosque Name");
            valid = false;
        } else {
            namajiName_ed.setError(null);
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
        if (password_ed_2.getText().toString().isEmpty()) {
            password_ed_2.setError("Enter password");
            valid = false;
        } else {
            password_ed_2.setError(null);
        }

        if (namajiName_ed.getText().toString().isEmpty() && mobileNo_ed.getText().toString().isEmpty() && password_ed.getText().toString().isEmpty()) {
            namajiName_ed.requestFocus();
        } else {
            if (namajiName_ed.getText().toString().isEmpty() && !mobileNo_ed.getText().toString().isEmpty() && !password_ed.getText().toString().isEmpty()) {
                namajiName_ed.requestFocus();
            }
            if (!namajiName_ed.getText().toString().isEmpty() && mobileNo_ed.getText().toString().isEmpty() && !password_ed.getText().toString().isEmpty()) {
                mobileNo_ed.requestFocus();
            }
            if (!namajiName_ed.getText().toString().isEmpty() && !mobileNo_ed.getText().toString().isEmpty() && password_ed.getText().toString().isEmpty()) {
                password_ed.requestFocus();
            }
        }

        return valid;
    }

    private boolean isBothPasswordSame() {
        if (password_ed.getText().toString().equals(password_ed_2.getText().toString())) {
            return true;
        } else {
            password_ed_2.setError("PassWord Does Not Match");
            password_ed_2.requestFocus();
            return false;
        }
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

                int id = response.getInt("user_id");



                Namaji namaji = new Namaji(id, namajiName.replaceAll("%20", " "), mobileNo);


                AppController.getInstance().getPrefManger().setNamajiObject(namaji);

                gotoFrontPage();

            } else {

                String mobileNo = response.getString("mobileNo");

                if (mobileNo.equalsIgnoreCase("used")) {
                    AlertDialogForAnything.showAlertDialogWhenComplte(this, "Register Failed", "Your mobileNo Already Taken. Please Register with different Email", false);

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


        AppController.getInstance().getPrefManger().setLoginType(AppConstant.LOGIN_TYPE_NAMAJI);

        Intent intent = new Intent(RegisterNamaji.this, NamajiHome.class);

        startActivity(intent);
        LoginActivity.LoginActivity.finish();

        finish();


    }

    @Override
    public void onClick(View view) {

    }

}
