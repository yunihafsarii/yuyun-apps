package com.example.yunihafsari.fypversion3.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.yunihafsari.fypversion3.R;
import com.example.yunihafsari.fypversion3.ui.adapters.DuplicationFragmentPagerAdapter;

public class DuplicationActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duplication);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Duplication");
        setSupportActionBar(toolbar);


        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        //Log.i("CRISYEEE", users_result.get(0).email);

        viewPager.setAdapter(new DuplicationFragmentPagerAdapter(this, getSupportFragmentManager(), getResources().getStringArray(R.array.duplication_titles_tab)));

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.duplication_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if( id == R.id.add_setting){
            startActivity(new Intent(DuplicationActivity.this, DuplicationActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
