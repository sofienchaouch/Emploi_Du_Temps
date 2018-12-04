package com.example.babayaga.emploi_du_temps.SessionAdaptater;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.babayaga.emploi_du_temps.R;
import com.example.babayaga.emploi_du_temps.Session;

import java.util.ArrayList;

public class SessionListAdapter extends ArrayAdapter<Session> {
    private static final String TAG = "PersonList";
    private Context context ;
    int mResource ;

    public SessionListAdapter(@NonNull Context context, int resource, ArrayList<Session> objects) {
        super(context, resource, objects);
        this.context = context;
        mResource = resource;
    }

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

        return  convertView ;



    }
}
