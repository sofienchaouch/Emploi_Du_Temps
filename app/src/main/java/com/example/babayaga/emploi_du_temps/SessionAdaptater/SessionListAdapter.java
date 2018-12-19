package com.example.babayaga.emploi_du_temps.SessionAdaptater;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.babayaga.emploi_du_temps.R;
import com.example.babayaga.emploi_du_temps.Session;

import java.util.ArrayList;
import java.util.Random;

public class SessionListAdapter extends ArrayAdapter<Session> {
    private static final String TAG = "PersonList";
    private Context context ;
    int mResource ;
    private static final float MIN_BRIGHTNESS = 1f;

    int [][] colors = {{221,230,203},{255,228,225},{234,211,249},{224,255,255},{245,245,220},{224,255,255},{245,245,220},{251,255,190},{253,213,224},{250,250,210},{255,218,185},{230,231,249},{255,228,196},{240,248,255},{192,247,186}};

    public SessionListAdapter(@NonNull Context context, int resource, ArrayList<Session> objects) {
        super(context, resource, objects);
        this.context = context;
        mResource = resource;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        String matier = getItem(position).getSubject();
        String  profe = getItem(position).getActor();
        String  classe = getItem(position).getSalle();
        String   temps = getItem(position).getTime();

        Session session = new Session(matier,profe,classe,temps);
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(mResource,parent,false);

        TextView tvHoraire = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvMatier = (TextView) convertView.findViewById(R.id.textView2);
        TextView tvProfe = (TextView) convertView.findViewById(R.id.textView3);
        TextView tvClasse = (TextView) convertView.findViewById(R.id.textView4);
        tvHoraire.setText(temps);
        tvMatier.setText(matier);
        tvProfe.setText(profe);
        tvClasse.setText(classe);
        Random random = new Random();


        if (!matier.equals("Pause") && !classe.equals("")){
            int r = random.nextInt(colors.length);

            int color = Color.rgb(colors[r][0],colors[r][1],colors[r][2]);

            convertView.setBackgroundColor(color);
        }




        return  convertView ;



    }
}
