package com.example.betterttc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.betterttc.services.Stop;
import com.example.betterttc.services.GetRouteStopsAsyncTask;

import java.util.ArrayList;
import java.util.Arrays;

public class GetRouteStopsActivity extends Activity {

    private ListView routeStops;
    private StopsArrayAdapter stopsArrayAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stops);
        routeStops = findViewById(R.id.stops);

        String routeNumber = "0";
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            routeNumber = extras.getString("routeNumber");
        }


        stopsArrayAdapter = new StopsArrayAdapter(this, 0, new ArrayList<Stop>());
        GetRouteStopsAsyncTask routeStopsAsyncTask = new GetRouteStopsAsyncTask();
        GetRouteStopsAsyncTask.CallBack callback = new GetRouteStopsAsyncTask.CallBack() {
            @Override
            public void onDataReceived(Stop [] stops) {
                routeStops.setAdapter(stopsArrayAdapter);
                stopsArrayAdapter.addAll(new ArrayList<Stop>(Arrays.asList(stops)));
            }
        };

        routeStopsAsyncTask.setCallback(callback);
        routeStopsAsyncTask.execute(routeNumber);
    }



    public class StopsArrayAdapter extends ArrayAdapter<Stop> {

        private Context context;

        public StopsArrayAdapter(Context context, int resource, ArrayList<Stop> objects) {
            super(context, resource, objects);
            this.context = context;
        }

        @Override
        public View getView(int position, @Nullable View convertView,  ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.view_stop, parent, false);
            final Stop stop = getItem(position);

            TextView name = view.findViewById(R.id.stop_name);

            name.setText(stop.getName());

            view.findViewById(R.id.more_info).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GetRouteStopsActivity.this, GetStopActivity.class);
                    intent.putExtra("stopId",stop.getBusStopId());
                    startActivity(intent);
                    finish();
                }
            });

            return view;
        }
    }
}
