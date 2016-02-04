package domain.muktevi.weatherapp.domain.muktevi.weatherapp.utils;

import java.util.Date;

/**
 * Created by Muktevi on 2/3/2016.
 */
public class WeatherUtils {

    public Date getDate(long millis){
        return new Date(millis*1000);
    }
}
