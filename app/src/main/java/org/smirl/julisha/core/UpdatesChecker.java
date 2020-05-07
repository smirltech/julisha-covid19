package org.smirl.julisha.core;

import android.content.Context;
import fnn.smirl.simple.Serializer;
import org.smirl.julisha.core.data.dao.Crud;


public class UpdatesChecker {


    Context ctx;
    private PrefManager prefm;
    private Crud crud;
    private String url, key = "update";
    private OnUpdateListener listener;


    public UpdatesChecker(Context context, String url, OnUpdateListener listener) {

        this.ctx = context;
        this.url = url;
        this.prefm = new PrefManager(context);
        this.listener = listener;


        fetch();
    }

    private void fetch() {
        crud = new Crud(ctx);
        crud.get(url, new Crud.OnResponseListener() {
            @Override
            public void onResponse(String response, int code) {
               // DialogFactory.print(ctx, response);
                prefm.putString(key, response);
                check();
            }

            @Override
            public void onErrorResponse(String error, int code) {
               // DialogFactory.toast(ctx, error);
                check();

            }
        });
    }

    private void check() {

        try {
            Update u = new Serializer().fromJson(prefm.getString(key, null), Update.class);
            if (u.versionCode > AppInfo.from(ctx).getAppVersionCode()) {
                if (listener != null)
                    listener.onUpdate(true, u);
            } else {
                if (listener != null)
                    listener.onUpdate(false, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // DialogFactory.print(ctx, e.toString());
        }

    }

    public interface OnUpdateListener {
        void onUpdate(boolean found, Update update);
    }

    public class Update {
        public String updatedDate, versionName,releaseNote, path;
        public int versionCode;


        @Override
        public String toString() {
            return new Serializer().toJson(this);
        }
    }

}
