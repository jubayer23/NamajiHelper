package com.creative.namajihelper.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.astuetz.PagerSlidingTabStrip;
import com.creative.namajihelper.R;
import com.creative.namajihelper.userview.fragment.NamajiFavMosque;
import com.creative.namajihelper.userview.fragment.NamajiMenu;
import com.creative.namajihelper.userview.fragment.NamajiSearch;

/**
 * Created by comsol on 24-Apr-16.
 */
public class NamajiFragmentPagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"Search", "Favourite", "Menu"};

    private final int[] ICONS = {R.drawable.search_icon, R.drawable.fav_icon,
            R.drawable.menu_icon};

    public NamajiFragmentPagerAdapter(FragmentManager fm) {
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
                return NamajiSearch.newInstance(0);
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return NamajiFavMosque.newInstance(1);
            case 2: // Fragment # 0 - This will show FirstFragment different title
                return NamajiMenu.newInstance(2);
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

    @Override
    public int getPageIconResId(int position) {
        return ICONS[position];
    }
}