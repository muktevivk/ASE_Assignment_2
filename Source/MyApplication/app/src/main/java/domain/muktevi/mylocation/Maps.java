package domain.muktevi.mylocation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class Maps extends FragmentActivity implements OnMapReadyCallback {

    public GoogleMap gMap;
    public Geocoder gCoder;
    public LatLng userCurrentLocationCorodinates = null;
    public double lat = 0, longi = 0;
    public StringBuilder uAddress = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        gMap = googleMap;
        gCoder = new Geocoder(this);

        // Add a marker in Sydney and move the camera
        final LocationManager userCurrentLocation = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);
        final LocationListener userCurrentLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                if (ActivityCompat.checkSelfPermission(Maps.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Maps.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }

                lat = userCurrentLocation
                        .getLastKnownLocation(LocationManager.GPS_PROVIDER)
                        .getLatitude();
                longi = userCurrentLocation
                        .getLastKnownLocation(LocationManager.GPS_PROVIDER)
                        .getLongitude();
                userCurrentLocationCorodinates = new LatLng(lat,longi);
                //Getting the address of the user based on latitude and longi.
                try {
                    List<Address> addresses = gCoder.getFromLocation(lat, longi, 1);
                    Address address = addresses.get(0);
                    uAddress =  new StringBuilder();
                    for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                        uAddress.append(address.getAddressLine(i)).append("\t");
                    }
                    uAddress.append(address.getCountryName()).append("\t");

                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                //Setting our image as the marker icon.
                gMap.addMarker(new MarkerOptions().position(userCurrentLocationCorodinates)
                        .title("Your current address.").snippet(uAddress.toString())
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker)));
                //Setting the zoom level of the map.
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userCurrentLocationCorodinates, 7));

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //show message or ask permissions from the user.
            return;
        }
        //Getting the current location of the user.
        userCurrentLocation.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                0, 0, userCurrentLocationListener);
        System.out.println("userCurrentLocation------" + userCurrentLocation);
        lat = userCurrentLocation
                .getLastKnownLocation(LocationManager.GPS_PROVIDER)
                .getLatitude();
        longi = userCurrentLocation
                .getLastKnownLocation(LocationManager.GPS_PROVIDER)
                .getLongitude();
        userCurrentLocationCorodinates = new LatLng(lat,longi);
        //Getting the address of the user based on latitude and longi.
        try {
            List<Address> addresses = gCoder.getFromLocation(lat, longi, 1);
            Address address = addresses.get(0);
            uAddress =  new StringBuilder();
            for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                uAddress.append(address.getAddressLine(i)).append("\t");
            }
            uAddress.append(address.getCountryName()).append("\t");

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        //Setting our image as the marker icon.
        gMap.addMarker(new MarkerOptions().position(userCurrentLocationCorodinates)
                .title("Your current address.").snippet(uAddress.toString())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker)));
        //Setting the zoom level of the map.
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userCurrentLocationCorodinates, 7));

    }
}
