package com.example.babayaga.emploi_du_temps.Mon_Emploi.mFragments_Days;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.babayaga.emploi_du_temps.Day;
import com.example.babayaga.emploi_du_temps.Login;
import com.example.babayaga.emploi_du_temps.Session;
import com.example.babayaga.emploi_du_temps.PageAdaptater.SectionPageAdapter;
import com.example.babayaga.emploi_du_temps.R;
import com.example.babayaga.emploi_du_temps.Session;
import com.example.babayaga.emploi_du_temps.SessionAdaptater.SessionListAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.babayaga.emploi_du_temps.Login.sessionId;


public class Lundi_Frag extends Fragment {

    public RequestQueue mQueue;
    private Context context;
    private ListView LundiListView;
    private SessionListAdapter adapter1;
    private static final String TAG = "Lundi_Frag";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.lundi,container,false);
        //ListView lundiList = (ListView) view.findViewById(R.id.lundi_list);


       LundiListView = (ListView) view.findViewById(R.id.lundi_list);

        mQueue = Volley.newRequestQueue( getActivity().getApplicationContext());



        getdata();













        return view;
    }

    public  void getdata(){
        String url="http://eniso.info/ws/core/wscript?s=Return(bean('core').getPluginsAPI())";
        String url2="http://eniso.info/ws/core/wscript?s=Return(bean(%22calendars%22).findMergedUserPublicWeekCalendar("+Login.id+"))";
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
                            ArrayList<Session> sesssionList = new ArrayList();
                            JSONObject i = response.getJSONObject("$1");
                            JSONArray days = i.getJSONArray("days");
                            JSONObject day= days.getJSONObject(0);
                            JSONArray hours = day.getJSONArray("hours");
                            JSONObject s1 ;
                            String  hour;
                            String room ;
                            String actor ;
                            String subject;
                            for (int j = 0; j <5 ; j++) {
                                s1 = hours.getJSONObject(j);
                                hour = s1.getString("hour");
                                actor = s1.getString("actor");
                                if (hour.equals("Pause")&& actor.equals("")  ){
                                    sesssionList.add(new Session(hour,"","","11:45-13:45"));
                                }else if(actor.equals("") && !hour.equals("Pause")){
                                    sesssionList.add(new Session("","","",hour));

                                }else{
                                    room = s1.getString("room");
                                    subject = s1.getString("subject");
                                    sesssionList.add(new Session(subject,actor,room,hour));
                                }

                            }








                            adapter1 = new SessionListAdapter(getActivity(),R.layout.adapter_view_layout,sesssionList);

                            LundiListView.setAdapter(adapter1);





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



        mQueue.add(req);
        mQueue.start();

    }

}
