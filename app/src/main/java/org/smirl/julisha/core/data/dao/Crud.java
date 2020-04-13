package org.smirl.julisha.core.data.dao;


/**
 * @author Marien MUPENDA
 * Date 21/12/2019
 * Time  12:13
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.smirl.julisha.core.data.dao.sql.QueryBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * This is an implementation of the CRUD concept in 5 methods, this was designed to be used for any kind of projects
 * GET : for getting data, needs table _option, conditions, order by
 * PUT : for inserting data, needs table _option, data
 * SET : for updating data, needs table _option, data conditions
 * DEL : for removing data, needs table _option and conditions
 * SQL : for custom queries, needs and SQL statement to be executed
 */

public class Crud {
    protected Context ctx;
    protected RequestQueue queue;
    protected String url;
    protected OnResponseListener listener;
    protected QueryBuilder queryBuilder;


    protected Map<String, String> params;
    private ProgressDialog progress;
    private boolean showDialog;


    public Crud(Context ctx, Conf conf) {
        this(ctx, conf, null);
    }

    public Crud(Context ctx, Conf conf, OnResponseListener listener) {
        this.ctx = ctx;
        this.queue = Volley.newRequestQueue(ctx);
        this.url = conf.getUrl();
        this.listener = listener;
        this.queryBuilder = new QueryBuilder();

        params = new HashMap<>();
        params.put("creds", conf.getCreds().toString());
    }

    public Crud(Context ctx) {
        this(ctx, new Conf(), null);

    }


    public Crud(Context ctx, OnResponseListener listener) {
        this(ctx, new Conf(), listener);

    }




    public void setShowProgressDialog(boolean showDialog) {
        this.showDialog = showDialog;
    }

    public void setOnResponseListener(OnResponseListener listener) {
        this.setOnResponseListener(listener, true);
    }

    public void setOnResponseListener(OnResponseListener listener, boolean dialog) {
        this.listener = listener;
        this.showDialog = dialog;
    }



    /*----------------------GET-----------------------------------*/

    public void get(String table, JSONObject conds, JSONObject order, int code) {
        params.put("table", table);
        params.put("conds", conds.toString());
        params.put("order", order.toString());

        request(url + "/get.php", params, listener, code);
    }

    public void get(String table, JSONObject conds, JSONObject order, OnResponseListener listener) {
        params.put("table", table);
        params.put("conds", conds.toString());
        params.put("order", order.toString());

        request(url + "/get.php", params, listener, -1);
    }

    public void get(String table, JSONObject conds, int code) {
        get(table, conds, listener, code);
    }

    public void get(String table, JSONObject conds, OnResponseListener listener) {
        get(table, conds, listener, 0);

    }

    private void get(String table, JSONObject conds, OnResponseListener listener, int code) {
        params.put("table", table);
        params.put("conds", (conds != null) ? conds.toString() : new JSONObject().toString());
        request(url + "/get.php", params, listener, code);
    }

    public void get(String url, OnResponseListener listener) {
        request(url, params, listener, -1);
    }


    /*--------------------------------------------SET----------------------------------------------*/
    public void set(String table, JSONObject vals, JSONObject conds, int code) {
        set(table, vals, conds, listener, code);
    }

    public void set(String table, JSONObject vals, JSONObject conds, OnResponseListener listener) {
        set(table, vals, conds, listener, 0);
    }

    public void set(String table, JSONObject vals, JSONObject conds, OnResponseListener listener, int code) {
        params.put("table", table);
        params.put("vals", vals.toString());
        params.put("conds", conds.toString());
        request(url + "/set.php", params, listener, code);
    }



    /*-----------------------------------------PUT--------------------------------------*/

    public void put(String table, JSONObject params, int code) {
        put(table, params, listener, code);
    }

    public void put(String table, JSONObject params, OnResponseListener listener) {
        put(table, params, listener, 0);
    }

    public void put(String table, JSONObject params, OnResponseListener listener, int code) {
        this.params.put("table", table);
        this.params.put("params", params.toString());
        request(url + "/put.php", this.params, listener, code);
    }


    /*----------------------------------------DELETE-----------------------------------*/

    public void del(String table, JSONObject vals, JSONObject conds, int code) {
        params.put("table", table);
        params.put("vals", vals.toString());
        params.put("conds", conds.toString());
        request(url + "/del.php", params, listener, code);
    }


    /*----------------------------------------------SQL-------------------------------*/
    public void sql(String sql, int code) {
        params.put("sql", sql);
        request(url + "/sql.php", params, listener, code);
    }

    public void sql(String sql, OnResponseListener listener) {
        sql(sql, listener, -1);
    }

    public void sql(String sql, OnResponseListener listener, int code) {
        params.put("sql", sql);
        request(url + "/sql.php", params, listener, code);
    }
    /*----------------------------------------END------------------------------------------------------------------*/


    public void request(String http, Map<String, String> params, final OnResponseListener listener, final int code) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;

        if (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) {
            if (showDialog) {
                progress = new ProgressDialog(ctx);
                progress.setMessage("Veillez patienter...");
                progress.setIndeterminate(true);
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setCancelable(false);
                progress.show();
            }

            CrudRequest req = new CrudRequest(Request.Method.POST, http, params, new Response.Listener<String>() {

                @Override
                public void onResponse(String p1) {


                    if (showDialog)
                        progress.dismiss();

                    if (listener != null) {
                        listener.onResponse(p1, code);
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError p1) {
                    if (showDialog)
                        progress.dismiss();
                    if (listener != null) {
                        listener.onError(p1.getMessage(), code);
                    }
                }
            });

            queue.add(req);
        } else {
            listener.onError("Impossible de se connecter a l'interner", 0);
        }
    }




    public class CrudRequest extends StringRequest {
        private Map<String, String> params;

        public CrudRequest(int method, String URL, Map<String, String> params, Response.Listener<String> listener, Response.ErrorListener error) {
            super(method, URL, listener, error);
            this.params = params;
        }

        @Override
        protected Map<String, String> getParams() {
            // TODO: Implement this method
            return params;
        }

    }

    public interface OnResponseListener {
        void onResponse(String response, int code);

        void onError(String error, int code);

    }

}