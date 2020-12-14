package com.example.softec.Volley;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

public class VolleyChecks {

    VolleyResult volleyResult;
    Context context;

    public VolleyChecks(Context context,VolleyResult volleyResult){
        this.context = context;
        this.volleyResult = volleyResult;
    }

    public void myMethod(String url,final Map map){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(volleyResult != null)
                    volleyResult.getStringResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(volleyResult != null)
                    volleyResult.getStringErrorResponse(error);
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };

        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 20000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 20000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                Toast.makeText(context,"Time error:" +error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        MySingleton.getInstance(context).addToRequestque(stringRequest);

    }

}
