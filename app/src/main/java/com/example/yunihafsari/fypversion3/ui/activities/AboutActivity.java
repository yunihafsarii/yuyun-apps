package com.example.yunihafsari.fypversion3.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.yunihafsari.fypversion3.R;

public class AboutActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //TOOLBAR
        toolbar = (Toolbar)findViewById(R.id.about_app_toolbar);
        toolbar.setTitle("About");
        setSupportActionBar(toolbar);
    }
}
