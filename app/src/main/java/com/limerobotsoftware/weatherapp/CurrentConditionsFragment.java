package com.limerobotsoftware.weatherapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.limerobotsoftware.weatherapp.model.CurrentConditions;
import com.limerobotsoftware.weatherapp.model.CurrentObservation;
import com.limerobotsoftware.weatherapp.model.DisplayLocation;
import com.limerobotsoftware.weatherapp.service.WUndergroundServiceHelper;
import com.squareup.picasso.Picasso;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.limerobotsoftware.weatherapp.util.ViewHelper.setTextView;
import static com.limerobotsoftware.weatherapp.util.ViewHelper.setViewVisibility;

/**
 * Fragment for displaying current conditions.
 */
public class CurrentConditionsFragment extends Fragment implements Callback<CurrentConditions> {

    private static String LOG_KEY = CurrentConditionsFragment.class.getSimpleName();

    private String initialLocation = "OH/Mason";
    private CurrentConditions currentConditions = null;

    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_conditions, container, false);

        if (currentConditions == null) {
            loadCurrentConditions(initialLocation);
        } else {
            displayCurrentConditions(currentConditions);
        }
        return rootView;
    }

    private void loadCurrentConditions(String location) {
        setViewVisibility(rootView, R.id.conditions_content, View.GONE);
        setViewVisibility(rootView, R.id.loading_message, View.VISIBLE);
        setTextView(rootView, R.id.loading_message, getString(R.string.loading_conditions, location));
        WUndergroundServiceHelper.retrieveCurrentConditions(getActivity(), location, this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (R.id.action_refresh == item.getItemId()) {
            loadCurrentConditions(initialLocation);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayCurrentConditions(CurrentConditions currentConditions)
    {
        setViewVisibility(rootView, R.id.loading_message, View.GONE);
        CurrentObservation observation = currentConditions.getCurrentObservation();
        DisplayLocation location = observation.getDisplayLocation();
        Picasso.with(getActivity())
               .load(observation.getIconUrl())
               .into((ImageView)rootView.findViewById(R.id.conditions_image));
        setTextView(rootView, R.id.location_name, location.getFull());
        setTextView(rootView, R.id.conditions, observation.getWeather());
        setTextView(rootView, R.id.current_temp, observation.getTemperatureString());
        setTextView(rootView, R.id.humidity, observation.getRelativeHumidity());
        setTextView(rootView, R.id.wind_speed, observation.getWindString());
        setTextView(rootView, R.id.feels_like, observation.getFeelslikeString());
        setTextView(rootView, R.id.observation_time, observation.getObservationTime());
        setViewVisibility(rootView, R.id.conditions_content, View.VISIBLE);
    }

    @Override
    public void success(CurrentConditions currentConditions, Response response) {
        if (currentConditions != null)
        {
            this.currentConditions = currentConditions;
            displayCurrentConditions(currentConditions);
        }
    }

    @Override
    public void failure(RetrofitError error) {
        Log.e(LOG_KEY, "Error retrieving current conditions", error);
        setTextView(rootView, R.id.loading_message, getString(R.string.current_conditions_error));
    }
}
