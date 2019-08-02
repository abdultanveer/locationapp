package com.example.locationapp;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class FetchAddressIntentService extends IntentService {


    public FetchAddressIntentService() {
        super("intent service");
    }

    @Override
    protected void onHandleIntent(Intent myIntent) {//2
        mReceiver = myIntent.getParcelableExtra(Constants.RECEIVER);
        Location mLastLocation = myIntent.getParcelableExtra(Constants.LOCATION_DATA_EXTRA);

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;

        

        try {
            addresses = geocoder.getFromLocation(
                    mLastLocation.getLatitude(),
                    mLastLocation.getLongitude(),
                    // In this sample, we get just a single address.
                    1);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
