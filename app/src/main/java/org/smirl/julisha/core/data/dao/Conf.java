package org.smirl.julisha.core.data.dao;


import org.json.JSONException;
import org.json.JSONObject;
import org.smirl.julisha.core.Constants;

public class Conf implements Constants {

    public static final String url = DB_URL;
    /*private String dbname = "wzexetat";
    private String user = "root";
    private String pswd = "";*/

    private String dbname = "smirlorg_wzexetat";
    private String user = "smirlorg_wz";
    private String pswd = "nDo=nW+]~;Fo";

    private JSONObject db;

    public Conf() {

        db = new JSONObject();

        try {
            db.put("dbname", dbname);
            db.put("user", user);
            db.put("pswd", pswd);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public String getUrl() {
        return url;
    }


    public JSONObject getCreds() {
        return db;
    }
}
