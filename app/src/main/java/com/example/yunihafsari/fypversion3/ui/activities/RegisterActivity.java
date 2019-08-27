package com.example.yunihafsari.fypversion3.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.yunihafsari.fypversion3.R;
import com.example.yunihafsari.fypversion3.ui.fragments.main_fragment.RegisterFragment;

public class RegisterActivity extends AppCompatActivity {

    // this will be called by other activity
    public static void startActivity(Context context){
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        startTheFragment();
    }

    private void startTheFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_content_register, RegisterFragment.newInstance(), RegisterFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }
}
