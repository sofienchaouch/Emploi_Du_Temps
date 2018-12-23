package com.example.babayaga.emploi_du_temps;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.babayaga.emploi_du_temps.SessionAdaptater.SessionListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.babayaga.emploi_du_temps.Login.sessionId;

public class ActiveNow extends AppCompatActivity {


    public RequestQueue mQueue;
    private Context context;
    private ListView activeNowList;
    private ActiveListAdapter adapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_now);

        activeNowList = (ListView) findViewById(R.id.active_list);

        mQueue = Volley.newRequestQueue(getApplicationContext());
        getSupportActionBar().setTitle("Utilisateurs Connectés ");

        getdata();
    }



    public  void getdata(){
        String url="http://eniso.info/ws/core/wscript?s=Return(bean('core').getPluginsAPI())";
        String url2="http://eniso.info/ws/core/wscript?s=Return(bean(%22core%22).getActiveSessions(true,true,false))";
        /*final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();*/



        JsonObjectRequest req = new JsonObjectRequest(com.android.volley.Request.Method.GET,url2,null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                         /*   Intent i = new Intent(getApplicationContext(),Groupes.class);
                            startActivity(i);*/
                            // JSONObject res =  response.getJSONObject("$1");
                            ArrayList<Active> ActiveList = new ArrayList();
                            JSONArray i = response.getJSONArray("$1");

                            JSONObject s1 ;
                            String  userFullTitle;
                            String iconURL ;
                            String userTypeName ;
                            for (int j = 0; j <i.length() ; j++) {
                                s1 = i.getJSONObject(j);
                                userFullTitle = s1.getString("userFullTitle");
                                iconURL = "http://eniso.info/fs"+s1.getString("iconURL");
                                userTypeName = s1.getString("userTypeName");

                                ActiveList.add(new Active(userFullTitle,iconURL,userTypeName));


                            }








                            adapter1 = new ActiveListAdapter(getApplicationContext(),R.layout.activity_active_now_adapter,ActiveList);

                            activeNowList.setAdapter(adapter1);





                        } catch (JSONException e) {
                            try {
                                JSONObject res1 = response.getJSONObject("$error");
                                String m = res1.getString("message");
                                //data.append("\n"+m+"\n"+Login.sessionId+"\n"+Login.login+"\n"+Login.password);
                            } catch (JSONException a) {
                                /*e.printStackTrace();
                                progressDialog.dismiss();*/
                            }
                        }
                        /*adapter1.notifyDataSetChanged();
                        progressDialog.dismiss();*/
                    }

                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // handle error

            }
        })
        {



            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Cookie", "JSESSIONID="+sessionId);
                return params;
            }



        };



        mQueue.add(req);
        mQueue.start();

    }
}
