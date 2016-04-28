package com.creative.namajihelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.creative.namajihelper.model.Mosque;

import java.util.ArrayList;

/**
 * Created by comsol on 29-Apr-16.
 */
public class SearchMosqueDetails extends AppCompatActivity{

    TextView tv_fajar,tv_juhar,tv_asar,tv_magrib,tv_esha,tv_eid,tv_mosque_name;

    Mosque mosque;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_mosque_details);
        getSupportActionBar().setHomeButtonEnabled(true);


        mosque =  getIntent().getParcelableExtra(NamajiSearchResult.KEY_OBJECT);

        init();
    }

    private void init() {

        tv_mosque_name = (TextView)findViewById(R.id.mosque_name);
        tv_mosque_name.setText(mosque.getMosqueName());

        tv_fajar = (TextView)findViewById(R.id.fajar);
        setText(mosque.getFajar(),tv_fajar);
        tv_juhar = (TextView)findViewById(R.id.juhar);
        setText(mosque.getJuhar(),tv_juhar);
        tv_asar = (TextView)findViewById(R.id.asar);
        setText(mosque.getAsar(),tv_asar);
        tv_magrib = (TextView)findViewById(R.id.magrib);
        setText(mosque.getMagrib(),tv_magrib);
        tv_esha = (TextView)findViewById(R.id.esha);
        setText(mosque.getEsha(),tv_esha);
        tv_eid = (TextView)findViewById(R.id.eid);
        setText(mosque.getEid(),tv_eid);

    }

    private void setText(String namaji_time, TextView tv) {
        String time_text = "",am_pm="";
        String b[] = namaji_time.split(":");
        for (int i = 0; i < b.length; i++) {

            b[i] = b[i].replaceAll("\\s+", "");
            if (i == 0) {
                int hour = Integer.parseInt(b[i]);

                if (hour > 12) {
                    am_pm = "PM";
                    time_text = time_text + String.valueOf(hour - 12);
                } else {
                    am_pm = "AM";
                    time_text = time_text + b[i];
                }
            } else {

                time_text = time_text + " : " + b[i] + " " + am_pm;
                tv.setText(time_text);
            }


        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:

                onBackPressed();
                break;

        }

        return true;
    }
}
