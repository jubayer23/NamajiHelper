package com.creative.namajihelper.appdata;


/**
 * Created by comsol on 11/8/2015.
 */
public class AppConstant {


    public static final String GCM_SENDER_ID = "651399612622";

    public static String BaseUrl = "http://namaji.eu5.org/mobileapp/";

    public static int NUM_OF_UNSEEN_HISTORY = 2;

    public static final String[] mosqueType = {"Sunni", "Shia", "Tabliq", "Other"};

    public static final int REQUEST_CHECK_SETTINGS = 100;


    public static String getUserRegUrl(String username, String email, String password) {
        return BaseUrl + "register.php?"
                + "username=" + username
                + "&email=" + email
                + "&password=" + password;
    }

    public static String getUrlForHelpSend(String regId, String lat, String lang, int range) {
        return BaseUrl + "send_message_to_all.php?"
                + "regId=" + regId
                + "&lat=" + lat
                + "&lng=" + lang
                + "&range=" + String.valueOf(range);

    }

    public static String getLoginUrl(String mobileNo, String password) {
        return BaseUrl + "login.php?"
                + "mobile_no=" + mobileNo
                + "&password=" + password;

    }

    public static String getMosqueRegisterUrl(String mosqueName, String mosqueType, String mobileNo, String password, String lat, String lng) {
        return BaseUrl + "mosque_register.php?"
                + "mosque_name=" + mosqueName
                + "&mosque_type=" + mosqueType
                + "&mobile_no=" + mobileNo
                + "&password=" + password
                + "&lat=" + lat
                + "&lng=" + lng;

    }

    public static String getNamajiRegisterUrl(String namajiName, String mobileNo, String password) {
        return BaseUrl + "namaji_register.php?"
                + "mosque_name=" + namajiName
                + "&mosque_type=" + mobileNo
                + "&mobile_no=" + password;

    }

    public static String getUrlForPoliceInfo(String region) {
        return BaseUrl + "police.php?"
                + "region=" + region;
    }

    public static String getUrlForHistoryList(String user_id, double lat, double lng, int range) {
        return BaseUrl + "history.php?"
                + "&user_id=" + user_id
                + "&lat=" + lat
                + "&lng=" + lng
                + "&range=" + String.valueOf(range);

    }

    public static String getUrlForSetHistorySeenUnseen(String user_id, String event_id) {
        return BaseUrl + "seen.php?"
                + "user_id=" + user_id + "&event_id=" + event_id;

        // user_id=16&event_id=2
    }

    public static String getUrlForHeatMap() {
        return BaseUrl + "hitmap.php";
    }

    public static String DirectionApiUrl(double sourcelat, double sourcelog, double destlat, double destlog) {
        StringBuilder urlString = new StringBuilder();
        urlString.append("http://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// from
        urlString.append(Double.toString(sourcelat));
        urlString.append(",");
        urlString
                .append(Double.toString(sourcelog));
        urlString.append("&destination=");// to
        urlString
                .append(Double.toString(destlat));
        urlString.append(",");
        urlString.append(Double.toString(destlog));
        urlString.append("&sensor=false&mode=driving&alternatives=true");
        urlString.append("AIzaSyBJonkf9zcXK2o1Y9mSbVfHiYjjw6qFkRY");

        return urlString.toString();
    }


}
