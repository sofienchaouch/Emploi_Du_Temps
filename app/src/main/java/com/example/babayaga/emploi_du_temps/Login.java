package com.example.babayaga.emploi_du_temps;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.babayaga.emploi_du_temps.Emploi_Par_Enseignant.En_Days_Frag.En_Emploi_Activity;
import com.example.babayaga.emploi_du_temps.Emploi_Par_Groupe.Gr_Activity;
import com.example.babayaga.emploi_du_temps.Mon_Emploi.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private Login context ;
    private String url1 ;
    private RequestQueue mQueue;
    public static String id;
    private EditText name;
    private EditText password;
    public static String iconURL ;
    public static String userFullTitle ;
    public static String sessionId ;
    public static boolean online = false;
    ActionBar actionBar ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        mQueue = Volley.newRequestQueue(this);
        name  = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        Button button = (Button) findViewById(R.id.login);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.barcolor)));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  Name = name.getText().toString();
                String Password = password.getText().toString();
                url1 = "http://eniso.info/ws/core/login/"+Name+"?password="+Password+"";
                jsonParse();
                Intent i = new Intent(getApplicationContext(),Main2Activity.class);
                startActivity(i);


            }
        });

    }


    private void jsonParse(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url1,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject res = response.getJSONObject("$1");
                    String ustid = res.getString("userId");
                    String usft =res.getString("userFullTitle");
                    String lastAcesTime = res.getString("lastAccessTime");
                    String profileNames = res.getString("profileNames");
                    userFullTitle = res.getString("userFullTitle");
                    iconURL = res.getString("iconURL");
                    id = ustid;
                    sessionId = res.getString("sessionId");
                    String name = res.getString("userFullName");
                    online = true;
                    Toast.makeText(getApplicationContext(),"welcome "+name,Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    try {
                        JSONObject resl =response.getJSONObject("$error");
                        String m = resl.getString("message");
                        online = false;
                        Toast.makeText(getApplicationContext(),m,Toast.LENGTH_LONG).show();



                    } catch (JSONException e1) {
                    }
                }

            }



        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"no response",Toast.LENGTH_LONG).show();
                        online = false;

                    }
                }){



            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {

                    Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
                    if (cacheEntry == null) {
                        cacheEntry = new Cache.Entry();
                    }
                    final long cacheHitButRefreshed = 3 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
                    final long cacheExpired = 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
                    long now = System.currentTimeMillis();
                    final long softExpire = now + cacheHitButRefreshed;
                    final long ttl = now + cacheExpired;
                    cacheEntry.data = response.data;
                    cacheEntry.softTtl = softExpire;
                    cacheEntry.ttl = ttl;
                    String headerValue;
                    headerValue = response.headers.get("Date");
                    if (headerValue != null) {
                        cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    headerValue = response.headers.get("Last-Modified");
                    if (headerValue != null) {
                        cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    cacheEntry.responseHeaders = response.headers;
                    final String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));
                    online = false;
                    return Response.success(new JSONObject(jsonString), cacheEntry);
                } catch (UnsupportedEncodingException | JSONException e) {
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            protected void deliverResponse(JSONObject response) {
                super.deliverResponse(response);
            }

            @Override
            public void deliverError(VolleyError error) {
                super.deliverError(error);
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                return super.parseNetworkError(volleyError);
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Cookie", "JSESSIONID="+sessionId);
                return params;
            }
        };




        mQueue.add(request);
        mQueue.start();




    }



}
