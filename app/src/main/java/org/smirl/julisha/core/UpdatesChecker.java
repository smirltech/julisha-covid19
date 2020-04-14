package org.smirl.julisha.core;

import android.content.Context;
import android.content.pm.PackageManager;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.smirl.julisha.core.data.dao.Crud;


public class UpdatesChecker implements Constants {


    Context ctx;
    private PrefManager prefm;
    private Crud crud;
    private String url;

    public UpdatesChecker(Context context, String url) {

        this.ctx = context;
        this.url = url;
        this.prefm = new PrefManager(context);
    }

    private void fetch() {
        crud = new Crud(ctx);
        crud.get(url, new Crud.OnResponseListener() {
            @Override
            public void onResponse(String response, int code) {
               /* try {
                    if (AppInfo.from(ctx).getAppVersionCode() < obj.getInt("vCode")) {

                        prefm.putString("news", obj.getString("news"));
                        prefm.putBoolean("shutdown", obj.getBoolean("shutdown"));
                        prefm.putString("link", obj.getString("link"));
                        prefm.putInt(WEB_VERSION_CODE_NAME, obj.getInt("vCode"));

                    }
                } catch (JSONException e) {
                    System.out.println(e);
                }*/
            }

            @Override
            public void onErrorResponse(String error, int code) {
                System.out.println(error);

            }
        });
    }

}
