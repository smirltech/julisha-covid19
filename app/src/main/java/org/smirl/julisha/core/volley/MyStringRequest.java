package org.smirl.julisha.core.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

public class MyStringRequest extends StringRequest {
 private Map<String, String> params;

 public MyStringRequest(int method, String url, Map<String, String> params, Listener<String> responseListener, ErrorListener errorListener) {
  super(method, url, responseListener, errorListener);
  this.params = params;
 }

 @Override
 protected Map<String, String> getParams() throws AuthFailureError {
  return params;
 }

}
