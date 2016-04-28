package com.creative.namajihelper.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.creative.namajihelper.userview.fragment.MosqueDetails;
import com.creative.namajihelper.userview.fragment.MosqueTimeTable;

/**
 * Created by comsol on 24-Apr-16.
 */
public class MosqueFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"Time-Table", "Details"};

    public MosqueFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        //return MosqueDetails.newInstance(position + 1);

        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return MosqueTimeTable.newInstance(0);
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return MosqueDetails.newInstance(1);
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}