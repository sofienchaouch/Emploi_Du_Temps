package com.example.babayaga.emploi_du_temps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.babayaga.emploi_du_temps.Emploi_Par_Enseignant.En_Days_Frag.En_Emploi_Activity;
import com.example.babayaga.emploi_du_temps.Emploi_Par_Groupe.Gr_Activity;
import com.example.babayaga.emploi_du_temps.Mon_Emploi.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private Login context ;
    private String url1 ;
    private RequestQueue mQueue;
    public static String id;
    EditText name;
    EditText password;
    public static String sessionId ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        mQueue = Volley.newRequestQueue(this);
        name  = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        Button button = (Button) findViewById(R.id.login);
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
                    id = ustid;
                    sessionId = res.getString("sessionId");

                    Toast.makeText(getApplicationContext(),"welcome "+profileNames,Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    try {
                        JSONObject resl =response.getJSONObject("$error");
                        String m = resl.getString("message");
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

                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Cookie", "JSESSIONID="+sessionId);
                return params;
            }
        };

        mQueue.add(request);




    }



}
