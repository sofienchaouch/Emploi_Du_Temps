package com.example.babayaga.emploi_du_temps;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.babayaga.emploi_du_temps.Emploi_Par_Groupe.Gr_Activity;
import com.example.babayaga.emploi_du_temps.SessionAdaptater.SessionListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupesList extends AppCompatActivity {

    ActionBar actionBar ;
    public RequestQueue mQueue;
    private Context context;
    private ListView list ;
    private String[] groupes = {"EI","IA","GT","Meca","GMP"};
    private  MenuItem searchMenuItem ;
    private ArrayAdapter<String> adap;
    private SearchView searchView;
    private SessionListAdapter adapter1;
    private EditText sv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupes_list);
        mQueue = Volley.newRequestQueue(getApplicationContext());
        list = (ListView) findViewById(R.id.list_view_groupes);
        getSupportActionBar().setTitle("Emplois Par Groupe");
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.barcolor)));
        getdata();








    }




    public  void getdata(){
        String url="http://eniso.info/ws/core/wscript?s=Return(bean('core').getPluginsAPI())";
        String url2="http://eniso.info/ws/core/wscript?s=Return(bean(%22academicPlanning%22).loadStudentPlanningListNames())";


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        JsonObjectRequest req = new JsonObjectRequest(com.android.volley.Request.Method.GET,url2,null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {


                            final ArrayList<String> items= new ArrayList();
                            final ArrayList<Session> names = new ArrayList();
                            JSONArray i = response.getJSONArray("$1");
                            JSONObject ob ;
                            String name ;

                            for (int j = 0; j <i.length() ; j++) {
                                ob = i.getJSONObject(j);
                                name = ob.getString("name");
                                names.add(new Session(name,"","",""));
                                items.add(name);
                            }



                            adapter1 = new SessionListAdapter(getApplication(),R.layout.adapter_view_layout,names);

                            list.setAdapter(adapter1);

                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    String item = names.get(position).getSubject();
                                    Intent i = new Intent(getApplicationContext(),Gr_Activity.class);
                                    i.putExtra("name",item);
                                    startActivity(i);
                                }
                            });

                            sv=(EditText) findViewById(R.id.searchView1);
                            adap = new ArrayAdapter(getApplication(),R.layout.adapter_view_layout,items);

                            sv.addTextChangedListener(new TextWatcher() {

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    // Call back the Adapter with current character to Filter
                                    adapter1.getFilter().filter(s.toString());
                                }

                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count,int after) {
                                }

                                @Override
                                public void afterTextChanged(Editable s) {
                                }
                            });














                        } catch (JSONException e) {
                            try {
                                JSONObject res1 = response.getJSONObject("$error");
                                String m = res1.getString("message");
                                //data.append("\n"+m+"\n"+Login.sessionId+"\n"+Login.login+"\n"+Login.password);
                            } catch (JSONException a) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                            }
                        }
                        adapter1.notifyDataSetChanged();
                        progressDialog.dismiss();

                    }



                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();

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






}
