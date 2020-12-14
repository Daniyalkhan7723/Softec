package com.example.softec.Volley;

import com.android.volley.VolleyError;

public interface VolleyResult {

//    public void getResponse(String requestType, JSONObject jsonObject);
//    public void getError(String requestType, VolleyError volleyError);

    void getStringResponse(String response);
    void getStringErrorResponse(VolleyError volleyError);

}
