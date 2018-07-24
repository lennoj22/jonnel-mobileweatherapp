package com.example.jonnelabonita.weatherapplication.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jonnelabonita.weatherapplication.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jonnel.abonita on 7/24/2018.
 */


abstract class BaseActivity extends AppCompatActivity {


    @BindView(R.id.tvDate)
    TextView tvDate;

    @BindView(R.id.tvDay)
    TextView tvDay;

    private RelativeLayout relativeLayout;

    protected void activateToolbar(){
        Date c = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat df = new SimpleDateFormat("dd, yyy");
        String formattedDate = df.format(c);

        Calendar calendar = Calendar.getInstance();
        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
        String weekDay = dayFormat.format(calendar.getTime());

        if (relativeLayout == null){
            ButterKnife.bind(this);
            relativeLayout = findViewById(R.id.appbar);
            if (relativeLayout != null){
                Toolbar toolbar = findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                tvDate.setText(month + " " + formattedDate);
                tvDay.setText(weekDay);
            }
        }
    }


    public void moveToOtherActivity(String action,Class clz) {
        Intent intent = new Intent(this,clz);
        intent.setAction(action);
        startActivity(intent);
        animateToLeft(this);
    }

    public void animateToLeft(Activity activity) {
        activity.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    public void animatetoRight(Activity activity) {
        activity.overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

}
