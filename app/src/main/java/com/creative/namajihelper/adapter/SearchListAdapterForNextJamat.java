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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.creative.namajihelper.R;
import com.creative.namajihelper.model.Mosque;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


@SuppressLint("DefaultLocale")
public class SearchListAdapterForNextJamat extends BaseAdapter {

    private List<Mosque> Displayedplaces;
    private List<Mosque> Originalplaces;
    private LayoutInflater inflater;
    private int current_hour, current_min, current_day;
    @SuppressWarnings("unused")
    private Activity activity;

    public SearchListAdapterForNextJamat(Activity activity, List<Mosque> histories) {
        this.activity = activity;
        this.Displayedplaces = histories;
        this.Originalplaces = histories;

        current_hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        current_min = Calendar.getInstance().get(Calendar.MINUTE);
        current_day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);


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
           // Log.d("DEBUG","YES");

            switch (i) {
                case 1:
                    String time_Fajar[] = mosque.getFajar().split(":");
                    int Fajar_h = Integer.parseInt(time_Fajar[0].replaceAll("\\s+", ""));
                    int Fajar_m = Integer.parseInt(time_Fajar[1].replaceAll("\\s+", ""));
                    if (current_hour < Fajar_h) {
                        flag_break = true;
                        viewHolder.mosqueNextJamat.setText("Fajar " + getTimeStringInFormated(mosque.getFajar()));
                    } else if (current_hour == Fajar_h && current_min < Fajar_m) {
                        flag_break = true;
                        viewHolder.mosqueNextJamat.setText("Fajar " + getTimeStringInFormated(mosque.getFajar()));
                    }

                    break;
                case 2:


                    if (current_day == Calendar.FRIDAY) {
                        String time_Jummah[] = mosque.getEsha().split(":");
                        int Jummah_h = Integer.parseInt(time_Jummah[0].replaceAll("\\s+", ""));
                        int Jummah_m = Integer.parseInt(time_Jummah[1].replaceAll("\\s+", ""));
                        if (current_hour < Jummah_h) {
                            flag_break = true;
                            viewHolder.mosqueNextJamat.setText("Jummah " + getTimeStringInFormated(mosque.getJummah()));
                        } else if (current_hour == Jummah_h && current_min < Jummah_m) {
                            flag_break = true;
                            viewHolder.mosqueNextJamat.setText("Jummah " + getTimeStringInFormated(mosque.getJummah()));
                        }

                    } else {
                        String time_Juhar[] = mosque.getJuhar().split(":");
                        int Juhar_h = Integer.parseInt(time_Juhar[0].replaceAll("\\s+", ""));
                        int Juhar_m = Integer.parseInt(time_Juhar[1].replaceAll("\\s+", ""));
                        if (current_hour < Juhar_h) {
                            flag_break = true;
                            viewHolder.mosqueNextJamat.setText("Juhar " + getTimeStringInFormated(mosque.getJuhar()));
                        } else if (current_hour == Juhar_h && current_min < Juhar_m) {
                            flag_break = true;
                            viewHolder.mosqueNextJamat.setText("Juhar " + getTimeStringInFormated(mosque.getJuhar()));
                        }
                    }


                    break;
                case 3:
                    String time_Asar[] = mosque.getAsar().split(":");
                    int Asar_h = Integer.parseInt(time_Asar[0].replaceAll("\\s+", ""));
                    int Asar_m = Integer.parseInt(time_Asar[1].replaceAll("\\s+", ""));
                    if (current_hour < Asar_h) {
                        flag_break = true;
                        viewHolder.mosqueNextJamat.setText("Asar " + getTimeStringInFormated(mosque.getAsar()));
                    } else if (current_hour == Asar_h && current_min < Asar_m) {
                        flag_break = true;
                        viewHolder.mosqueNextJamat.setText("Asar " + getTimeStringInFormated(mosque.getAsar()));
                    }
                    break;
                case 4:

                    String time_Magrib[] = mosque.getMagrib().split(":");
                    int Magrib_h = Integer.parseInt(time_Magrib[0].replaceAll("\\s+", ""));
                    int Magrib_m = Integer.parseInt(time_Magrib[1].replaceAll("\\s+", ""));
                    if (current_hour < Magrib_h) {
                        flag_break = true;
                        viewHolder.mosqueNextJamat.setText("Magrib " + getTimeStringInFormated(mosque.getMagrib()));
                    } else if (current_hour == Magrib_h && current_min < Magrib_m) {
                        flag_break = true;
                        viewHolder.mosqueNextJamat.setText("Magrib " + getTimeStringInFormated(mosque.getMagrib()));
                    }

                    break;
                case 5:

                    String time_Esha[] = mosque.getEsha().split(":");
                    int Esha_h = Integer.parseInt(time_Esha[0].replaceAll("\\s+", ""));
                    int Esha_m = Integer.parseInt(time_Esha[1].replaceAll("\\s+", ""));
                    if (current_hour < Esha_h) {
                        flag_break = true;
                        viewHolder.mosqueNextJamat.setText("Esha " + getTimeStringInFormated(mosque.getEsha()));
                    } else if (current_hour == Esha_h && current_min < Esha_m) {
                        flag_break = true;
                        viewHolder.mosqueNextJamat.setText("Esha " + getTimeStringInFormated(mosque.getEsha()));
                    }

                    break;

            }
            if (flag_break) break;
        }
        if (!flag_break) {

            viewHolder.mosqueNextJamat.setText("Fajar " + getTimeStringInFormated(mosque.getFajar()));
        }

        if (mosque.getDistance() != -9999) {
            if (mosque.getDistance() > 999) {
                float distanceInKm = mosque.getDistance() / 1000;
                viewHolder.distance.setText(String.format("%.1f", distanceInKm) + " " + activity.getResources().getString(R.string.km));
            } else {
                viewHolder.distance.setText(String.format("%.1f", mosque.getDistance()) + " " + activity.getResources().getString(R.string.miters));
            }

        } else {
            viewHolder.distance.setVisibility(View.GONE);
        }


        return convertView;
    }

    private String getTimeStringInFormated(String namaji_time) {
        String time_text = "",amOrPm="AM";
        String b[] = namaji_time.split(":");
        for (int i = 0; i < b.length; i++) {

            b[i] = b[i].replaceAll("\\s+", "");
            if (i == 0) {
                int hour = Integer.parseInt(b[i]);

                if (hour > 12) {
                    amOrPm = "PM";
                    time_text = time_text + String.valueOf(hour - 12);
                } else {
                    amOrPm = "AM";
                    time_text = time_text + b[i];
                }
            } else {

                time_text = time_text + " : " + b[i] + " " + amOrPm;
            }


        }
        Log.d("DEBUG",time_text);
        return time_text;

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