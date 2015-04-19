package com.limerobotsoftware.weatherapp.service;

import android.test.AndroidTestCase;
import android.util.Log;

import com.limerobotsoftware.weatherapp.R;
import com.limerobotsoftware.weatherapp.model.CurrentConditions;
import com.limerobotsoftware.weatherapp.model.CurrentObservation;
import com.limerobotsoftware.weatherapp.model.DisplayLocation;

import retrofit.RestAdapter;
import retrofit.android.AndroidLog;

public class WUndergroundServiceTest extends AndroidTestCase {

    private static final String LOG_KEY = WUndergroundServiceTest.class.getSimpleName();

    private String apiKey;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        apiKey = mContext.getString(R.string.wu_api_key);
    }

    public void testGetCurrentConditions() {

        Log.v(LOG_KEY, "testGetCurrentConditions started");
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.wunderground.com")
                .setLog(new AndroidLog(LOG_KEY))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        WUndergroundService service = restAdapter.create(WUndergroundService.class);

        CurrentConditions conditions = service.getCurrentConditions(apiKey, "CA/San_Francisco");

        assertNotNull("conditions is null", conditions);
        CurrentObservation observation = conditions.getCurrentObservation();
        assertNotNull("current observation is null", observation);
        DisplayLocation displayLocation = observation.getDisplayLocation();
        assertNotNull("display location is null", displayLocation);
    }
}
