package com.example.jonnelabonita.weatherapplication.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jonnelabonita.weatherapplication.Activities.WeatherActivity;
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
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


public class WeatherFragment extends Fragment {

    APIInterface apiInterface;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.lTemp)
    TextView lTemp;

    @BindView(R.id.lDesc)
    TextView lDesc;

    @BindView(R.id.lPressure)
    TextView lPressure;

    @BindView(R.id.lHumidity)
    TextView lHumidity;

    @BindView(R.id.lMinTemp)
    TextView lMinTemp;

    @BindView(R.id.lMaxTemp)
    TextView lMaxTemp;

    @BindView(R.id.lSpeed)
    TextView lSpeed;

    @BindView(R.id.lDegree)
    TextView lDegree;

    @BindView(R.id.lRefresh)
    ImageView lRefresh;

    @BindView(R.id.weatherDialog)
    ProgressBar weatherDialog;

    @BindView(R.id.icon)
    ImageView icon;


    private String BASE_URL = "http://openweathermap.org/img/w/";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this,v);

        Retrofit retrofit = RetrofitClient.getInstance();
        apiInterface = retrofit.create(APIInterface.class);

        setWeather();

        return v;
    }

    @OnClick(R.id.lRefresh) void refresh(){
        setWeather();
    }

    private void setWeather(){ 
        String loc = WeatherActivity.location;
        Observable<OpenWeather> observable = null;
        weatherDialog.setVisibility(View.GONE);
        if (loc.contains(Locations.London.name())){
            observable = apiInterface.getWeatherLondon(Keys.LONDON, Keys.APPID, Keys.METRIC);
        } else if(loc.contains(Locations.Prague.name())){
            observable = apiInterface.getWeatherPrague(Keys.PRAGUE, Keys.APPID, Keys.METRIC);
        } else if(loc.contains(Locations.SanFrancisco.name())){
            observable = apiInterface.getWeatherSanFrancisco(Keys.SANFRANCISCO, Keys.APPID, Keys.METRIC);
        } else if(loc.contains(Locations.Caloocan.name())){
            observable = apiInterface.getWeatherCaloocan(Keys.CALOOCAN_PSE_LATITUDE, Keys.CALOOCAN_PSE_LONGITUDE, Keys.APPID, Keys.METRIC);
        }

        tvTitle.setText(loc);
        if (observable != null) {
            compositeDisposable.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<OpenWeather>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void accept(OpenWeather openWeather) throws Exception {
                        lDesc.setText(openWeather.getWeather().get(0).getDescription());
                        lTemp.setText(openWeather.getMain().getTemp() + "°");
                        lPressure.setText("Pressure:" +openWeather.getMain().getPressure());
                        lHumidity.setText("Humidity: " + openWeather.getMain().getHumidity());
                        lMinTemp.setText("Min Temperature: "  + openWeather.getMain().getTempMin().toString() + "°");
                        lMaxTemp.setText("Max Temperature: " + openWeather.getMain().getTempMax().toString() + "°");
                        lSpeed.setText("Speed: " + openWeather.getWind().getSpeed().toString());

                        try{
                            lDegree.setText("Degree: " + openWeather.getWind().getDeg().toString());
                        }catch (NullPointerException e){
                            e.printStackTrace();
                            lDegree.setText("Degree: N/A" );
                        }
                        Picasso.get().load(BASE_URL+openWeather.getWeather().get(0).getIcon()+".png").into(icon);
                    }
                }));
        }
    }
}
