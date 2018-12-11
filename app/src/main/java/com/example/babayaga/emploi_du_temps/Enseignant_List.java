package com.example.babayaga.emploi_du_temps;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.babayaga.emploi_du_temps.Emploi_Par_Enseignant.En_Days_Frag.En_Emploi_Activity;
import com.example.babayaga.emploi_du_temps.Emploi_Par_Groupe.Gr_Activity;
import com.example.babayaga.emploi_du_temps.SessionAdaptater.SessionListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Enseignant_List extends AppCompatActivity {
    public RequestQueue mQueue;
    private Context context;
    private ListView list ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enseignant__list);
        mQueue = Volley.newRequestQueue(getApplicationContext());
        list = (ListView) findViewById(R.id.enseignants_list);
        getSupportActionBar().setTitle("Emplois Par Enseignant");

        getdata();
    }

    public  void getdata(){
        String url="http://eniso.info/ws/core/wscript?s=Return(bean('core').getPluginsAPI())";
        String url2="http://eniso.info/ws/core/wscript?s=Return(bean(%22academic%22).findActiveTeachersStrict())";


        JsonObjectRequest req = new JsonObjectRequest(com.android.volley.Request.Method.GET,url2,null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {


                            final ArrayList<Session> names = new ArrayList();
                            final ArrayList<Integer> ids = new ArrayList();
                            JSONArray i = response.getJSONArray("$1");
                            JSONObject ob ;
                            String name ;
                            int id ;

                            for (int j = 0; j <i.length() ; j++) {
                                ob = i.getJSONObject(j);
                                name = ob.getString("fullName");
                                id = ob.getInt("id");
                                names.add(new Session(name,"","",""));
                                ids.add(id);

                            }




                            SessionListAdapter adapter1 = new SessionListAdapter(getApplication(),R.layout.adapter_view_layout,names);

                            list.setAdapter(adapter1);

                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    int  item = ids.get(position);
                                    //int vid = Integer.parseInt(item);
                                    Intent i = new Intent(getApplicationContext(),En_Emploi_Activity.class);
                                    i.putExtra("id",item);
                                    i.putExtra("name",names.get(position).getSubject());
                                    startActivity(i);
                                }
                            });



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
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Cookie", "JSESSIONID="+Login.sessionId);
                return params;
            }




        };



        mQueue.add(req);
        mQueue.start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.enseignant_search,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }


}
