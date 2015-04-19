package com.limerobotsoftware.weatherapp.service;

import android.content.Context;
import android.util.Log;

import com.limerobotsoftware.weatherapp.BuildConfig;
import com.limerobotsoftware.weatherapp.R;
import com.limerobotsoftware.weatherapp.model.CurrentConditions;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.android.AndroidLog;

public class WUndergroundServiceHelper {
    private static final String LOG_KEY = WUndergroundServiceHelper.class.getSimpleName();

    public static void retrieveCurrentConditions(Context context, String location, Callback<CurrentConditions> callback) {

        Log.v(LOG_KEY, "Retrieving current conditions for " + location);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(context.getString(R.string.wu_endpoint_url))
                .setLog(new AndroidLog(LOG_KEY))
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .build();

        WUndergroundService service = restAdapter.create(WUndergroundService.class);

        service.getCurrentConditions(context.getString(R.string.wu_api_key), location, callback);
    }

}
