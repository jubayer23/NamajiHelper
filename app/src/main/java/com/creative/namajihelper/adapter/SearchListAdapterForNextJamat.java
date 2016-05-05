package com.creative.namajihelper.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.creative.namajihelper.R;
import com.creative.namajihelper.model.Mosque;

import java.sql.Time;
import java.util.Calendar;
import java.util.List;


@SuppressLint("DefaultLocale")
public class SearchListAdapterForNextJamat extends BaseAdapter {

    private List<Mosque> Displayedplaces;
    private List<Mosque> Originalplaces;
    private LayoutInflater inflater;
    Location myLocation;
    private int current_hour, current_min;
    @SuppressWarnings("unused")
    private Activity activity;

    public SearchListAdapterForNextJamat(Activity activity, List<Mosque> histories, double user_lat, double user_lng) {
        this.activity = activity;
        this.Displayedplaces = histories;
        this.Originalplaces = histories;

        myLocation = new Location("");//provider name is unecessary
        myLocation.setLatitude(user_lat);//your coords of course
        myLocation.setLongitude(user_lng);

        current_hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        current_min = Calendar.getInstance().get(Calendar.MINUTE);


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

            convertView = inflater.inflate(R.layout.list_search_by_jamat, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.mosqueName = (TextView) convertView
                    .findViewById(R.id.mosque_name);

            viewHolder.mosqueNextJamat = (TextView) convertView
                    .findViewById(R.id.mosque_next_jamat);


            viewHolder.distance = (TextView) convertView
                    .findViewById(R.id.mosque_distance);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Mosque mosque = Displayedplaces.get(position);

        viewHolder.mosqueName.setText(mosque.getMosqueName());

        boolean flag_break = false;
        for (int i = 1; i <= 5; i++) {

            switch (i) {
                case 1:

                    String time_Fajar[] = mosque.getFajar().split(":");
                    int Fajar_h = Integer.parseInt(time_Fajar[0]);
                    int Fajar_m = Integer.parseInt(time_Fajar[1]);
                    if (current_hour < Fajar_h) {
                        flag_break = true;
                        viewHolder.mosqueNextJamat.setText("Fajar " + mosque.getFajar());
                    } else if (current_hour == Fajar_h && current_min < Fajar_m) {
                        flag_break = true;
                        viewHolder.mosqueNextJamat.setText("Fajar " + mosque.getFajar());
                    }

                    break;
                case 2:

                    String time_Juhar[] = mosque.getJuhar().split(":");
                    int Juhar_h = Integer.parseInt(time_Juhar[0]);
                    int Juhar_m = Integer.parseInt(time_Juhar[1]);
                    if (current_hour < Juhar_h) {
                        flag_break = true;
                        viewHolder.mosqueNextJamat.setText("Juhar " + mosque.getJuhar());
                    } else if (current_hour == Juhar_h && current_min < Juhar_m) {
                        flag_break = true;
                        viewHolder.mosqueNextJamat.setText("Juhar " + mosque.getJuhar());
                    }

                    break;
                case 3:
                    String time_Asar[] = mosque.getAsar().split(":");
                    int Asar_h = Integer.parseInt(time_Asar[0]);
                    int Asar_m = Integer.parseInt(time_Asar[1]);
                    if (current_hour < Asar_h) {
                        flag_break = true;
                        viewHolder.mosqueNextJamat.setText("Asar " + mosque.getAsar());
                    } else if (current_hour == Asar_h && current_min < Asar_m) {
                        flag_break = true;
                        viewHolder.mosqueNextJamat.setText("Asar " + mosque.getAsar());
                    }
                    break;
                case 4:

                    String time_Magrib[] = mosque.getMagrib().split(":");
                    int Magrib_h = Integer.parseInt(time_Magrib[0]);
                    int Magrib_m = Integer.parseInt(time_Magrib[1]);
                    if (current_hour < Magrib_h) {
                        flag_break = true;
                        viewHolder.mosqueNextJamat.setText("Magrib " + mosque.getMagrib());
                    } else if (current_hour == Magrib_h && current_min < Magrib_m) {
                        flag_break = true;
                        viewHolder.mosqueNextJamat.setText("Magrib " + mosque.getMagrib());
                    }

                    break;
                case 5:

                    String time_Esha[] = mosque.getEsha().split(":");
                    int Esha_h = Integer.parseInt(time_Esha[0]);
                    int Esha_m = Integer.parseInt(time_Esha[1]);
                    if (current_hour < Esha_h) {
                        viewHolder.mosqueNextJamat.setText("Esha " + mosque.getEsha());
                    } else if (current_hour == Esha_h && current_min < Esha_m) {
                        viewHolder.mosqueNextJamat.setText("Esha " + mosque.getEsha());
                    }

                    break;
            }
            if (flag_break) break;
        }
        if (!flag_break) {

            viewHolder.mosqueNextJamat.setText("Fajar " + mosque.getFajar());
        }

        Location targetLocation = new Location("");//provider name is unecessary
        targetLocation.setLatitude(mosque.getLat());//your coords of course
        targetLocation.setLongitude(mosque.getLng());

        int distanceInMeters = (int) myLocation.distanceTo(targetLocation);

        //Log.d("DEBUG",String.valueOf(distanceInMeters));

        if (String.valueOf(distanceInMeters).length() > 3) {
            float distanceInKm = distanceInMeters / 1000;

            viewHolder.distance.setText(String.format("%.1f", distanceInKm) + " " + activity.getResources().getString(R.string.km));
        } else {
            viewHolder.distance.setText(String.valueOf(distanceInMeters) + " " + activity.getResources().getString(R.string.miters));
        }


        return convertView;
    }

    public void addMore() {
        //this.Displayedplaces.addAll(places);
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        private TextView mosqueName;
        private TextView mosqueNextJamat;
        private TextView distance;
    }
}