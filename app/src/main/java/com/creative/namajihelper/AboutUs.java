package com.creative.namajihelper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by comsol on 04-May-16.
 */
public class AboutUs extends AppCompatActivity {

    TextView tv_aboutus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        tv_aboutus = (TextView) findViewById(R.id.tv_aboutus);

        tv_aboutus.setText(Html.fromHtml("<h1>Bismullah ir-Rahman ir-Rahim</h1><br><p>May Allah bless the Prophet and\n" +
                " his companions for each of the namaz observed \n" +
                " and prayed because of this application, and may \n" +
                " he further reward and bless the Ummah, my parents \n" +
                " (Sunhera and Salim Kaliwala), my grandparents (Mumtaz and Usman Kaliwala)\n" +
                " (Abeda and Yusuf Bagasrawala), my deceased cousin (Aamir Bagasrawala)\n" +
                " and the developer (Jubayer) who made this application a possiblity in real term!</P>\n" +
                "\n" +
                "<br><br><p>For any recommendation or changes, contact me on <h2>kaliwalasarfaraz@gmail.com</h2> or <h2>+91-9879151617</h2></p>"));

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

