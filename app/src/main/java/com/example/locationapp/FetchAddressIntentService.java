package com.example.locationapp;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FetchAddressIntentService extends IntentService {
    private ResultReceiver mResultReceiver;


    public FetchAddressIntentService() {
        super("intent service");
    }

    @Override
    protected void onHandleIntent(Intent myIntent) {//2
        mResultReceiver = myIntent.getParcelableExtra(Constants.RECEIVER);

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

        Address address = addresses.get(0);
        ArrayList<String> addressLines = new ArrayList<>();

        for(int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
            addressLines.add(address.getAddressLine(i));
        }


        deliverResultToReceiver(Constants.SUCCESS_RESULT,
                TextUtils.join(System.getProperty("line.separator"), addressLines));

    }

    private void deliverResultToReceiver(int resultCode, String message) {
        Bundle resultData = new Bundle();
        resultData.putString(Constants.RESULT_DATA_KEY, message);
        mResultReceiver.send(resultCode, resultData);//3
    }
}
