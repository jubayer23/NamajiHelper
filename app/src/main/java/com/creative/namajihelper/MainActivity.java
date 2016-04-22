package com.creative.namajihelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.creative.namajihelper.appdata.AppController;
import com.creative.namajihelper.userview.LoginActivity;
import com.creative.namajihelper.userview.RegisterNamaji;

public class MainActivity extends AppCompatActivity {

    TextView tv_dummy;
    Button logout;  

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        if (AppController.getInstance().getPrefManger().getLoginType().equalsIgnoreCase("namaji")) {
            tv_dummy.setText("Welcome Namaji");

        } else {
            tv_dummy.setText("Welcome Mosque");

        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppController.getInstance().getPrefManger().setLoginType("");
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void init() {
        tv_dummy = (TextView) findViewById(R.id.tv_dummy);
        logout = (Button) findViewById(R.id.btn_logout);
    }
}
