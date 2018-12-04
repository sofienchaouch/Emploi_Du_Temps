package com.example.babayaga.emploi_du_temps.Emploi_Par_Groupe;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.babayaga.emploi_du_temps.Emploi_Par_Groupe.Gr_days.Gr_Jeudi;
import com.example.babayaga.emploi_du_temps.Emploi_Par_Groupe.Gr_days.Gr_Lundi;
import com.example.babayaga.emploi_du_temps.Emploi_Par_Groupe.Gr_days.Gr_Mardi;
import com.example.babayaga.emploi_du_temps.Emploi_Par_Groupe.Gr_days.Gr_Mercredi;
import com.example.babayaga.emploi_du_temps.Emploi_Par_Groupe.Gr_days.Gr_Samedi;
import com.example.babayaga.emploi_du_temps.Emploi_Par_Groupe.Gr_days.Gr_Vendredi;
import com.example.babayaga.emploi_du_temps.Login;
import com.example.babayaga.emploi_du_temps.Mon_Emploi.mFragments_Days.Jeudi_Frag;
import com.example.babayaga.emploi_du_temps.Mon_Emploi.mFragments_Days.Lundi_Frag;
import com.example.babayaga.emploi_du_temps.Mon_Emploi.mFragments_Days.Mardi_Frag;
import com.example.babayaga.emploi_du_temps.Mon_Emploi.mFragments_Days.Mercredi_Frag;
import com.example.babayaga.emploi_du_temps.Mon_Emploi.mFragments_Days.Samedi_Frag;
import com.example.babayaga.emploi_du_temps.Mon_Emploi.mFragments_Days.Vendredi_Frag;
import com.example.babayaga.emploi_du_temps.PageAdaptater.SectionPageAdapter;
import com.example.babayaga.emploi_du_temps.R;
import com.example.babayaga.emploi_du_temps.Session;
import com.example.babayaga.emploi_du_temps.SessionAdaptater.SessionListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Gr_Activity extends AppCompatActivity {
    public ArrayList<String> ids = new ArrayList<>();
    public ArrayList<String> names = new ArrayList<>();
    public RequestQueue mQueue;
    int selected ;
    String n = "" ;
    private Context context;
    private static final String TAG = "Gr_Activity";
    private SectionPageAdapter Gr_SectionPageAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gr_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.gr_toolbar);
        setSupportActionBar(toolbar);
        Log.d(TAG,"onCreate Starting");
        Gr_SectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.gr_container);
        setupViewPager(mViewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.gr_tabs);
        tabLayout.setupWithViewPager(mViewPager);
        mQueue = Volley.newRequestQueue(getApplicationContext());

        getdata();



    }

    private void setupViewPager(ViewPager viewPager){
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Gr_Lundi() , "LUN");
        adapter.addFragment(new Gr_Mardi() , "MAR");
        adapter.addFragment(new Gr_Mercredi() , "MER");
        adapter.addFragment(new Gr_Jeudi() , "JEU");
        adapter.addFragment(new Gr_Vendredi() , "VEN");
        adapter.addFragment(new Gr_Samedi() , "SAM");
        viewPager.setAdapter(adapter);
    }



    public  void getdata(){
        String url="http://eniso.info/ws/core/wscript?s=Return(bean('core').getPluginsAPI())";
        String url2="http://eniso.info/ws/core/wscript?s=Return(bean(%22academicPlanning%22).loadStudentPlanningListNames())";




        JsonObjectRequest req = new JsonObjectRequest(com.android.volley.Request.Method.GET,url2,null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                         /*   Intent i = new Intent(getApplicationContext(),Groupes.class);
                            startActivity(i);*/
                            // JSONObject res =  response.getJSONObject("$1");
                            ArrayList<Session> sesssionList = new ArrayList();
                            JSONArray i = response.getJSONArray("$1");
                            JSONObject ob = i.getJSONObject(5);
                            String id = ob.getString("id");
                            String name = ob.getString("name");
                            n = name;
                            /*for (int j = 0; j <i.length() ; j++) {
                                ob = i.getJSONObject(j);
                                id = ob.getString("id");
                                name = ob.getString("name");
                                ids.add(id);
                                names.add(name);


                            }*/



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
        //getMenuInflater().inflate(R.menu.menu_gr_,menu);
        //menu.add(0,3,0,"email");
        //getdata();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_gr_, menu);
        //menu.add("hello");
        //getdata();
        menu.add(n+"hello");
        /*for (int i = 0; i <ids.size() ; i++) {
            menu.add(ids.get(i));
        }*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        selected = item.getItemId();


        return true;
    }

}
