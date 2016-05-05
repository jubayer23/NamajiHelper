package com.creative.namajihelper.adapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.creative.namajihelper.R;
import com.creative.namajihelper.model.Mosque;

import java.util.List;


@SuppressLint("DefaultLocale")
public class FavListAdapter extends BaseAdapter {

    private List<Mosque> Displayedplaces;
    private List<Mosque> Originalplaces;
    private LayoutInflater inflater;
    Location myLocation;
    @SuppressWarnings("unused")
    private Activity activity;
    private boolean FLAG_DISTANCE_AVAILABLE = true;

    public FavListAdapter(Activity activity, List<Mosque> histories, double user_lat, double user_lng) {
        this.activity = activity;
        this.Displayedplaces = histories;
        this.Originalplaces = histories;
        if (user_lat == 0 && user_lng == 0) {
            FLAG_DISTANCE_AVAILABLE = false;
        } else {
            FLAG_DISTANCE_AVAILABLE = true;
            myLocation = new Location("");//provider name is unecessary
            myLocation.setLatitude(user_lat);//your coords of course
            myLocation.setLongitude(user_lng);
        }

        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return Displayedplaces.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.list_search, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.mosqueName = (TextView) convertView
                    .findViewById(R.id.mosque_name);


            viewHolder.distance = (TextView) convertView
                    .findViewById(R.id.mosque_distance);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Mosque mosque = Displayedplaces.get(position);

        viewHolder.mosqueName.setText(mosque.getMosqueName());

        if (FLAG_DISTANCE_AVAILABLE) {
            Location targetLocation = new Location("");//provider name is unecessary
            targetLocation.setLatitude(mosque.getLat());//your coords of course
            targetLocation.setLongitude(mosque.getLng());

            int distanceInMeters = (int) myLocation.distanceTo(targetLocation);

            //Log.d("DEBUG",String.valueOf(distanceInMeters));

            if(String.valueOf(distanceInMeters).length() > 3)
            {
                float distanceInKm = distanceInMeters / 1000;

                viewHolder.distance.setText(String.format("%.1f", distanceInKm) + " " + activity.getResources().getString(R.string.km));
            }else
            {
                viewHolder.distance.setText(String.valueOf(distanceInMeters) + " " +activity.getResources().getString(R.string.miters));
            }


        } else {
            viewHolder.distance.setVisibility(View.GONE);
        }


        return convertView;
    }

    public void addMore() {
        //this.Displayedplaces.addAll(places);
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        private TextView mosqueName;
        private TextView distance;
    }
}