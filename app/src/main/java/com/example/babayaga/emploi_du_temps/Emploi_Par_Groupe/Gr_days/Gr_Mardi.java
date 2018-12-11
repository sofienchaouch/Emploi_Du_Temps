package com.example.babayaga.emploi_du_temps.Emploi_Par_Groupe.Gr_days;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.babayaga.emploi_du_temps.Day;
import com.example.babayaga.emploi_du_temps.Emploi_Par_Groupe.Gr_Activity;
import com.example.babayaga.emploi_du_temps.Login;
import com.example.babayaga.emploi_du_temps.R;
import com.example.babayaga.emploi_du_temps.Session;
import com.example.babayaga.emploi_du_temps.SessionAdaptater.SessionListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Gr_Mardi extends Fragment {

    public RequestQueue mQueue;
    private Context context;
    private ListView Gr_MardiListView;
    private static final String TAG = "Gr_Mardi_Frag";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.gr_mardi,container,false);

        Gr_MardiListView = (ListView) view.findViewById(R.id.gr_mardi_list);

        mQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        getdata();


        return view;
    }

    public void getdata() {
        String url = "http://eniso.info/ws/core/wscript?s=Return(bean('core').getPluginsAPI())";
        String url2 = "http://eniso.info/ws/core/wscript?s=Return(bean(%22academicPlanning%22).loadClassPlanning(%22"+Gr_Activity.name+"%22))";


        JsonObjectRequest req = new JsonObjectRequest(com.android.volley.Request.Method.GET, url2, null,
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
                            JSONObject day = days.getJSONObject(1);
                            JSONArray hours = day.getJSONArray("hours");
                            JSONObject s1;
                            String hour;
                            String room;
                            String actor;
                            String subject;
                            for (int j = 0; j < 5; j++) {
                                s1 = hours.getJSONObject(j);
                                hour = s1.getString("hour");
                                actor = s1.getString("actor");
                                if (hour.equals("Pause")&& actor.equals("")  ) {
                                    sesssionList.add(new Session(hour, "", "", "11:45-13:45"));
                                } else if (actor.equals("") && !hour.equals("Pause")) {
                                    sesssionList.add(new Session("", "", "", hour));

                                } else {
                                    room = s1.getString("room");
                                    subject = s1.getString("subject");
                                    sesssionList.add(new Session(subject, actor, room, hour));
                                }

                            }


                            SessionListAdapter adapter1 = new SessionListAdapter(getActivity(), R.layout.adapter_view_layout, sesssionList);

                            Gr_MardiListView.setAdapter(adapter1);


                        } catch (JSONException e) {
                            try {
                                JSONObject res1 = response.getJSONObject("$error");
                                String m = res1.getString("message");
                                //data.append("\n"+m+"\n"+Login.sessionId+"\n"+Login.login+"\n"+Login.password);
                            } catch (JSONException a) {

                            }
                        }
                    }

                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // handle error

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Cookie", "JSESSIONID=" + Login.sessionId);
                return params;
            }


        };


        mQueue.add(req);
        mQueue.start();


    }

}
