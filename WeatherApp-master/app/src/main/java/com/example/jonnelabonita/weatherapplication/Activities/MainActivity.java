package com.example.jonnelabonita.weatherapplication.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jonnelabonita.weatherapplication.R;
import com.example.jonnelabonita.weatherapplication.constants.Keys;
import com.example.jonnelabonita.weatherapplication.constants.Locations;
import com.example.jonnelabonita.weatherapplication.models.OpenWeather;
import com.example.jonnelabonita.weatherapplication.service.APIInterface;
import com.example.jonnelabonita.weatherapplication.service.RetrofitClient;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


public class MainActivity extends BaseActivity {

    APIInterface apiInterface;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @BindView(R.id.londonDesc)
    TextView londonDesc;

    @BindView(R.id.londonDegree)
    TextView londonDegree;

    @BindView(R.id.pragueDesc)
    TextView seoulDesc;

    @BindView(R.id.pragueDegree)
    TextView seoulDegree;

    @BindView(R.id.sanfranciscoDesc)
    TextView newyorkDesc;

    @BindView(R.id.sanfranciscoDegree)
    TextView newyorkDegree;

    @BindView(R.id.caloocanDesc)
    TextView ortigasDesc;

    @BindView(R.id.caloocanDegree)
    TextView ortigasDegree;

    @BindView(R.id.ivRefresh)
    ImageView ivRefresh;

    @BindView(R.id.londonPD)
    ProgressBar londonPD;

    @BindView(R.id.praguePD)
    ProgressBar seoulPD;

    @BindView(R.id.sanfranciscoPD)
    ProgressBar newyorkPD;

    @BindView(R.id.caloocanPD)
    ProgressBar ortigasPD;

    @BindView(R.id.londonIcon)
    ImageView londonIcon;

    @BindView(R.id.pragueIcon)
    ImageView seoulIcon;

    @BindView(R.id.sanfranciscoIcon)
    ImageView newyorkIcon;

    @BindView(R.id.caloocanIcon)
    ImageView ortigasIcon;

    private String BASE_URL = "http://openweathermap.org/img/w/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activateToolbar();
        ButterKnife.bind(this);

        Retrofit retrofit = RetrofitClient.getInstance();
        apiInterface = retrofit.create(APIInterface.class);
        setLondonCard();
        setPragueCard();
        setSanFranciscoCard();
        setOrtigasCard();

    }

    @OnClick(R.id.ivRefresh) void refresh(){
        londonPD.setVisibility(View.VISIBLE);

        seoulPD.setVisibility(View.VISIBLE);

        newyorkPD.setVisibility(View.VISIBLE);

        ortigasPD.setVisibility(View.VISIBLE);

        setLondonCard();
        setPragueCard();
        setSanFranciscoCard();
        setOrtigasCard();
    }

    @OnClick(R.id.cvLondon) void getLondon(){
        moveToOtherActivity(Locations.London.name(), WeatherActivity.class);
    }

    @OnClick(R.id.cvPrague) void getSeoul(){
        moveToOtherActivity(Locations.Prague.name(), WeatherActivity.class);
    }

    @OnClick(R.id.cvSanFrancisco) void getNewYork(){
        moveToOtherActivity(Locations.SanFrancisco.name(), WeatherActivity.class);
    }

    @OnClick(R.id.cvCaloocan) void getOrtigas(){
        moveToOtherActivity(Locations.Caloocan.name(), WeatherActivity.class);
    }



    private void setLondonCard(){
        compositeDisposable.add(apiInterface.getWeatherLondon(Keys.LONDON, Keys.APPID, Keys.METRIC)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<OpenWeather>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void accept(OpenWeather openWeather) throws Exception {
                    londonPD.setVisibility(View.GONE);
                    londonDesc.setVisibility(View.VISIBLE);
                    londonDegree.setVisibility(View.VISIBLE);
                    londonDesc.setText(openWeather.getWeather().get(0).getDescription());
                    londonDegree.setText(openWeather.getMain().getTemp() + "°");
                    Picasso.get().load(BASE_URL+openWeather.getWeather().get(0).getIcon()+".png").into(londonIcon);

                }
            }));
    }

    private void setPragueCard(){
        compositeDisposable.add(apiInterface.getWeatherPrague(Keys.PRAGUE, Keys.APPID, Keys.METRIC)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<OpenWeather>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void accept(OpenWeather openWeather) throws Exception {
                    seoulPD.setVisibility(View.GONE);
                    seoulDesc.setVisibility(View.VISIBLE);
                    seoulDegree.setVisibility(View.VISIBLE);
                    seoulDesc.setText(openWeather.getWeather().get(0).getDescription());
                    seoulDegree.setText(openWeather.getMain().getTemp() + "°");
                    Picasso.get().load(BASE_URL+openWeather.getWeather().get(0).getIcon()+".png").into(seoulIcon);
            }
            }));
    }

    private void setSanFranciscoCard (){
        compositeDisposable.add(apiInterface.getWeatherSanFrancisco(Keys.SANFRANCISCO, Keys.APPID, Keys.METRIC)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<OpenWeather>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void accept(OpenWeather openWeather) throws Exception {
                        newyorkPD.setVisibility(View.GONE);
                        newyorkDesc.setVisibility(View.VISIBLE);
                        newyorkDegree.setVisibility(View.VISIBLE);
                        newyorkDesc.setText(openWeather.getWeather().get(0).getDescription());
                        newyorkDegree.setText(openWeather.getMain().getTemp() + "°");
                        Picasso.get().load(BASE_URL+openWeather.getWeather().get(0).getIcon()+".png").into(newyorkIcon);
                    }
                }));
    }

    private void setOrtigasCard(){
        compositeDisposable.add(apiInterface.getWeatherCaloocan(Keys.CALOOCAN_PSE_LATITUDE,Keys.CALOOCAN_PSE_LONGITUDE, Keys.APPID, Keys.METRIC)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<OpenWeather>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void accept(OpenWeather openWeather) throws Exception {
                        ortigasPD.setVisibility(View.GONE);
                        ortigasDesc.setVisibility(View.VISIBLE);
                        ortigasDegree.setVisibility(View.VISIBLE);
                        ortigasDesc.setText(openWeather.getWeather().get(0).getDescription());
                        ortigasDegree.setText(openWeather.getMain().getTemp() + "°");
                        Picasso.get().load(BASE_URL+openWeather.getWeather().get(0).getIcon()+".png").into(ortigasIcon);
                    }
                }));
    }

//    @Override
//    protected void onStop() {
//        compositeDisposable.clear();
//        super.onStop();
//    }
}
