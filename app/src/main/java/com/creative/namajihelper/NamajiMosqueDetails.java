package com.creative.namajihelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.creative.namajihelper.appdata.AppController;
import com.creative.namajihelper.model.Mosque;

import java.util.ArrayList;

/**
 * Created by comsol on 29-Apr-16.
 */
public class NamajiMosqueDetails extends AppCompatActivity {

    TextView tv_fajar, tv_juhar, tv_asar, tv_magrib, tv_esha,tv_jummah, tv_eid, tv_mosque_name;

    Mosque mosque;

    ImageView img_fav;

    boolean FLAG_ALREADY_FAV;

    ArrayList<Mosque> favMosqueList;

    int fav_mosque_index_in_arraylist = 0;

    Button btn_show_direction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_mosque_details);
        getSupportActionBar().setHomeButtonEnabled(true);

        FLAG_ALREADY_FAV = false;
        mosque = getIntent().getParcelableExtra(NamajiSearchResult.KEY_OBJECT);

        init();


        img_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (FLAG_ALREADY_FAV) {

                    showAlertDialog();

                } else {
                    FLAG_ALREADY_FAV = true;
                    img_fav.setImageResource(R.drawable.fav_yes);
                    favMosqueList.add(mosque);
                    fav_mosque_index_in_arraylist = favMosqueList.size() - 1;
                    AppController.getInstance().getPrefManger().setFavPlaces(favMosqueList);
                }


            }
        });

        btn_show_direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q="
                        + String.valueOf(mosque.getLat()) + ","
                        + String.valueOf(mosque.getLng()));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
    }

    private void init() {

        favMosqueList = AppController.getInstance().getPrefManger().getFavPlaces();

        for (int i = 0; i < favMosqueList.size(); i++) {
            if (favMosqueList.get(i).getId() == mosque.getId()) {
                FLAG_ALREADY_FAV = true;
                fav_mosque_index_in_arraylist = i;
                break;
            }
        }


        tv_mosque_name = (TextView) findViewById(R.id.mosque_name);
        tv_mosque_name.setText(mosque.getMosqueName());

        tv_fajar = (TextView) findViewById(R.id.fajar);
        setText(mosque.getFajar(), tv_fajar);
        tv_juhar = (TextView) findViewById(R.id.juhar);
        setText(mosque.getJuhar(), tv_juhar);
        tv_asar = (TextView) findViewById(R.id.asar);
        setText(mosque.getAsar(), tv_asar);
        tv_magrib = (TextView) findViewById(R.id.magrib);
        setText(mosque.getMagrib(), tv_magrib);
        tv_esha = (TextView) findViewById(R.id.esha);
        setText(mosque.getEsha(), tv_esha);
        tv_jummah = (TextView) findViewById(R.id.jummah);
        setText(mosque.getJummah(), tv_jummah);
        tv_eid = (TextView) findViewById(R.id.eid);
        setText(mosque.getEid(), tv_eid);


        img_fav = (ImageView) findViewById(R.id.img_fav);
        if (FLAG_ALREADY_FAV) img_fav.setImageResource(R.drawable.fav_yes);
        else img_fav.setImageResource(R.drawable.fav_no);

        btn_show_direction = (Button) findViewById(R.id.btn_show_direction);

    }

    private void setText(String namaji_time, TextView tv) {
        String time_text = "", am_pm = "";
        String b[] = namaji_time.split(":");
        for (int i = 0; i < b.length; i++) {

            b[i] = b[i].replaceAll("\\s+", "");
            if (i == 0) {
                int hour = Integer.parseInt(b[i]);

                if (hour > 12) {
                    am_pm = getResources().getString(R.string.pm);
                    time_text = time_text + String.valueOf(hour - 12);
                } else {
                    am_pm = getResources().getString(R.string.am);
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
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                break;

        }

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }


    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.alertTitle);
        builder.setMessage(R.string.alertMessage);

        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                FLAG_ALREADY_FAV = false;
                // Do nothing but close the dialog
                img_fav.setImageResource(R.drawable.fav_no);
                favMosqueList.remove(fav_mosque_index_in_arraylist);
                AppController.getInstance().getPrefManger().setFavPlaces(favMosqueList);

                dialog.dismiss();
            }

        });

        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
