package com.nextolive.kiteappmvvm.activities_views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.nextolive.kiteappmvvm.R;
import com.nextolive.kiteappmvvm.fragments.EmailFragment;
import com.nextolive.kiteappmvvm.fragments.MapFragment;
import com.nextolive.kiteappmvvm.utils.SharedData_Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        String userId = SharedData_Utils.personId(MainActivity.this);//if IS is exist , user is not first tym login

        if (userId != null && userId.equalsIgnoreCase("1")) {

            MapFragment fragment = new MapFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
            transaction.replace(R.id.framelayoutForFragments, fragment);
            //transaction.addToBackStack(null);
            transaction.commit();

        } else {

            EmailFragment fragment = new EmailFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
            transaction.replace(R.id.framelayoutForFragments, fragment);
            //transaction.addToBackStack("EmailFragment");
            transaction.commit();

        }


    }


}