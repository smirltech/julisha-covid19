package org.smirl.julisha.core;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import org.smirl.julisha.core.volley.MyStringRequest;
import org.smirl.julisha.core.volley.StaticRequestQueue;

import java.io.File;

public class DataUpdater implements Constants {

  public static void populateCases(Context context, final UpdaterListener listener) {
   // Utilities.toastIt(context, "start update...!");
    MyStringRequest request = new MyStringRequest(Request.Method.GET, APP_URL, null,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
            response = response.replace("null", "1");
            Utilities.toastIt(context, "update done!");
            Julisha.load(response);
            Julisha.setLastUpdate(System.currentTimeMillis());
            Julisha.prepareCaseGraphs();
            File baseFolder = FileManager.getBaseDir(context, "julisha");
            if(!baseFolder.exists())baseFolder.mkdirs();
            File dataFile = new File(baseFolder, "data.json");
            Julisha.save(dataFile);

            if (listener != null)listener.onCompleted();
          }
        },
        new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            Utilities.toastIt(context, "update failed, view saved!");
            File baseFolder = FileManager.getBaseDir(context, "julisha");
            if(!baseFolder.exists())baseFolder.mkdirs();
            File dataFile = new File(baseFolder, "data.json");
            Julisha.read(dataFile);

            if (listener != null)listener.onFailed();
          }
        });

    StaticRequestQueue.from(context).append(request);
  }

  public static void populateLocalCases(Context context, final UpdaterListener listener) {
            File baseFolder = FileManager.getBaseDir(context, "julisha");
            if(!baseFolder.exists())baseFolder.mkdirs();
            File dataFile = new File(baseFolder, "data.json");
            Julisha.read(dataFile);

            if (listener != null)listener.onCompleted();
  }

  public interface UpdaterListener{
    void onCompleted();
    void onFailed();
  }
}
