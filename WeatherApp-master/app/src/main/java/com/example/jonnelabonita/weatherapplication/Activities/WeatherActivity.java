package com.example.jonnelabonita.weatherapplication.Activities;

import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.example.jonnelabonita.weatherapplication.Fragments.WeatherFragment;
import com.example.jonnelabonita.weatherapplication.R;
import com.example.jonnelabonita.weatherapplication.adapters.FragPagerAdapter;

import butterknife.BindView;


public class WeatherActivity extends BaseActivity {

    @BindView(R.id.container)
    ViewPager viewPager;

    private String action;

    public static String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);


        action = getIntent().getAction();
        activateToolbar();
        setupViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager){
            location = action;
            FragPagerAdapter adapter = new FragPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(new WeatherFragment());
            viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        animatetoRight(this);
    }
}
