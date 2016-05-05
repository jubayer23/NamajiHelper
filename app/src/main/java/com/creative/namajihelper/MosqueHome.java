package com.creative.namajihelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.creative.namajihelper.adapter.MosqueFragmentPagerAdapter;
import com.creative.namajihelper.appdata.AppController;
import com.creative.namajihelper.userview.LoginActivity;

/**
 * Created by comsol on 24-Apr-16.
 */
public class MosqueHome extends AppCompatActivity {

    public static Activity mosqueHomeActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mosque_home);

        mosqueHomeActivity = this;

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
       // getSupportActionBar().setTitle(R.string.mosque_home_activity);
       // getSupportActionBar().setIcon(R.mipmap.logo);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new MosqueFragmentPagerAdapter(getSupportFragmentManager()));

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);
    }



}
