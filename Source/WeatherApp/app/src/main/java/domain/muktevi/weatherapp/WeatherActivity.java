package domain.muktevi.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import domain.muktevi.weatherapp.domain.muktevi.weatherapp.beans.Weather;
import domain.muktevi.weatherapp.domain.muktevi.weatherapp.beans.WeatherObj;
import domain.muktevi.weatherapp.domain.muktevi.weatherapp.utils.WeatherUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class WeatherActivity extends AppCompatActivity {

    private static String url = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static String appKey = "f37d72f01e44978f931f8518e5c588cc";
    private Gson gson;
    Map wMap;

    TextView cityName;
    TextView country;
    TextView description;
    TextView temp;
    TextView pressure;
    TextView humidity;
    TextView windSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String name = intent.getStringExtra(MainActivity.EXTRA_USERNAME);
        TextView textViewName= (TextView) findViewById(R.id.textView_user);
        textViewName.setText("Hi "+name);
    }
    public void doLogout (View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void fetchWeather (View view){
        EditText location = (EditText) findViewById(R.id.editText_location);
        String locationString = location.getText().toString();
        String requestUrl = url+locationString+"&units=metric&appid="+appKey;
        OkHttpClient client = new OkHttpClient();
        country = (TextView) findViewById(R.id.country);
        cityName = (TextView) findViewById(R.id.cityName);
        temp = (TextView) findViewById(R.id.temp);
        pressure = (TextView) findViewById(R.id.pressure);
        humidity = (TextView) findViewById(R.id.humidity);
        windSpeed = (TextView) findViewById(R.id.wind_speed);
        description = (TextView) findViewById(R.id.description);

        try{
            Request request = new Request.Builder().url(requestUrl).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("Failure in service call",e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String respString = response.body().string();
                    Log.i("Json",respString);
                    gson = new Gson();
                    final WeatherObj weatherObj = gson.fromJson(respString,WeatherObj.class);
                    WeatherUtils utils = new WeatherUtils();
                    final Date sunriseTime = utils.getDate(Long.parseLong(weatherObj.getSys().getSunrise()));
                    final Date sunsetTime = utils.getDate(Long.parseLong(weatherObj.getSys().getSunset()));
                    wMap=(LinkedTreeMap) weatherObj.getWeather().get(0);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            country.setText("Country: " + weatherObj.getSys().getCountry());
                            country.setVisibility(View.VISIBLE);
                            cityName.setText("City: " + weatherObj.getName());
                            cityName.setVisibility(View.VISIBLE);
                            temp.setText("Temperature: " + weatherObj.getMain().getTemp() + " C");
                            temp.setVisibility(View.VISIBLE);
                            pressure.setText("Pressure: " + weatherObj.getMain().getPressure());
                            pressure.setVisibility(View.VISIBLE);
                            humidity.setText("Humidity: " + weatherObj.getMain().getHumidity());
                            humidity.setVisibility(View.VISIBLE);
                            windSpeed.setText("Wind: " + weatherObj.getWind().getSpeed() + "meter/sec");
                            windSpeed.setVisibility(View.VISIBLE);
                            description.setText("Description: " + wMap.get("description"));
                            description.setVisibility(View.VISIBLE);

                        }
                    });
                }
            });
        }catch (Exception e){
            Log.e("Exception occured",e.getMessage());
        }

    }

}
