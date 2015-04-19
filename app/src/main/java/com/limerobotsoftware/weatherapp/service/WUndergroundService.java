package com.limerobotsoftware.weatherapp.service;

import com.limerobotsoftware.weatherapp.model.CurrentConditions;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface WUndergroundService {

    @GET("/api/{apiKey}/conditions/q/{location}.json")
    CurrentConditions getCurrentConditions(@Path("apiKey") String apiKey,
                                           @Path(value="location", encode=false) String location);

    @GET("/api/{apiKey}/conditions/q/{location}.json")
    void getCurrentConditions(@Path("apiKey") String apiKey,
                                           @Path(value="location", encode=false) String location,
                                           Callback<CurrentConditions> callback);
}
