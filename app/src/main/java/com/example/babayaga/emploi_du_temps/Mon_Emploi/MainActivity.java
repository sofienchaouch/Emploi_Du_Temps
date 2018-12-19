package com.example.babayaga.emploi_du_temps.Mon_Emploi;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.babayaga.emploi_du_temps.Mon_Emploi.mFragments_Days.Jeudi_Frag;
import com.example.babayaga.emploi_du_temps.Mon_Emploi.mFragments_Days.Lundi_Frag;
import com.example.babayaga.emploi_du_temps.Mon_Emploi.mFragments_Days.Mardi_Frag;
import com.example.babayaga.emploi_du_temps.Mon_Emploi.mFragments_Days.Mercredi_Frag;
import com.example.babayaga.emploi_du_temps.Mon_Emploi.mFragments_Days.Samedi_Frag;
import com.example.babayaga.emploi_du_temps.Mon_Emploi.mFragments_Days.Vendredi_Frag;
import com.example.babayaga.emploi_du_temps.PageAdaptater.SectionPageAdapter;
import com.example.babayaga.emploi_du_temps.Login;
import com.example.babayaga.emploi_du_temps.R;

public class MainActivity extends AppCompatActivity  {

    private static final String TAG = "MainActivity";

    private SectionPageAdapter mSectionPageAdapter;

    private ViewPager mViewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d(TAG,"onCreate Starting");
        mSectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        getSupportActionBar().setTitle("Mon Emplois Temps");






    }

    private void setupViewPager(ViewPager viewPager){
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Lundi_Frag() , "LUN");
        adapter.addFragment(new Mardi_Frag() , "MAR");
        adapter.addFragment(new Mercredi_Frag() , "MER");
        adapter.addFragment(new Jeudi_Frag() , "JEU");
        adapter.addFragment(new Vendredi_Frag() , "VEN");
        adapter.addFragment(new Samedi_Frag() , "SAM");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        //menu.add(0,3,0,"email");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }
}
