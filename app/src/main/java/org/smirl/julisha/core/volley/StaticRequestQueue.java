package org.smirl.julisha.core.volley;

import android.content.Context;

import com.android.volley.*;
import com.android.volley.toolbox.*;

public class StaticRequestQueue
{
 private static RequestQueue queue;
 private static StaticRequestQueue staticRequestQueue;
 
 private StaticRequestQueue(Context context){
	queue = Volley.newRequestQueue(context);
 }
 
 public static StaticRequestQueue from(Context context){
	if(staticRequestQueue == null){
	 staticRequestQueue = new StaticRequestQueue(context);
	 }
	return staticRequestQueue;
 }
 
 public <T> void append(Request<T> request){
	queue.add(request);
 }
}
