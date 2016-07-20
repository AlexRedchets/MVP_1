package com.example.azvk.mvp_1;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.azvk.mvp_1.Fragments.ButtonsFragment;
import com.example.azvk.mvp_1.Fragments.RecycleViewFragment;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("MainActivity", "onCreate called");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction
                    .replace(R.id.buttonFrame, new ButtonsFragment(), "buttonFragment")
                    .replace(R.id.recycleViewFrame, new RecycleViewFragment(), "recycleViewFragment")
                    .commit();
        }
        else{
            FragmentManager fragmentManager = getFragmentManager();
            ButtonsFragment buttonsFragment = (ButtonsFragment) fragmentManager.findFragmentByTag("buttonFragment");
            RecycleViewFragment recycleViewFragment = (RecycleViewFragment) fragmentManager.findFragmentByTag("recycleViewFragment");

        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MainActivity", "onDestroy called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("MainActivity", "onStop called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("MainActivity", "onResume called");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("MainActivity", "onStart called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("MainActivity", "onPause called");
    }
}
