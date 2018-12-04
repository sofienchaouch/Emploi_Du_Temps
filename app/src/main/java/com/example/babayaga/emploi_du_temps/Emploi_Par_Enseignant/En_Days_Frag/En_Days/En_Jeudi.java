package com.example.babayaga.emploi_du_temps.Emploi_Par_Enseignant.En_Days_Frag.En_Days;

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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.babayaga.emploi_du_temps.Day;
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

public class En_Jeudi extends Fragment {
    public RequestQueue mQueue;
    private Context context;
    private ListView En_JeudiListView;
    private static final String TAG = "Jeudi_Frag";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.en_jeudi,container,false);

        En_JeudiListView = (ListView) view.findViewById(R.id.en_jeudi_list);
        mQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        //getdata();
        return view;
    }

    public  void getdata(){
        String url="http://eniso.info/ws/core/wscript?s=Return(bean('core').getPluginsAPI())";
        String url2="http://eniso.info/ws/core/wscript?s=Return(bean(%22academicPlanning%22).loadTeacherPlanning())";




        JsonObjectRequest req = new JsonObjectRequest(com.android.volley.Request.Method.GET,url2,null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                         /*   Intent i = new Intent(getApplicationContext(),Groupes.class);
                            startActivity(i);*/
                            // JSONObject res =  response.getJSONObject("$1");
                            JSONObject i = response.getJSONObject("$1");
                            JSONArray days = i.getJSONArray("days");
                            JSONObject day= days.getJSONObject(3);
                            JSONArray hours = day.getJSONArray("hours");
                            JSONObject s1 = hours.getJSONObject(0);
                            String  hour = s1.getString("hour");
                            String room = s1.getString("actor");
                            String actor = s1.getString("actor");
                            String subject = s1.getString("subject");

                            Session S1 = new Session(subject,actor,room,hour);
                            ArrayList<Session> sesssionList = new ArrayList();

                            SessionListAdapter adapter1 = new SessionListAdapter(getActivity(),R.layout.adapter_view_layout,sesssionList);

                            En_JeudiListView.setAdapter(adapter1);
                            sesssionList.add(S1);





                        } catch (JSONException e) {
                            try {
                                JSONObject res1 = response.getJSONObject("$error");
                                String m = res1.getString("message");
                                Toast.makeText(getContext(),m,Toast.LENGTH_LONG).show();
                            } catch (JSONException a) {

                            }
                        }
                    }

                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // handle error
                Toast.makeText(getContext(),"no response",Toast.LENGTH_LONG).show();

            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Cookie", "JSESSIONID="+Login.sessionId);
                return params;
            }


/*
           @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Cookie", Login.sessionId);



                return params;
            }
*/

        };



        mQueue.add(req);
        mQueue.start();

    }
}
