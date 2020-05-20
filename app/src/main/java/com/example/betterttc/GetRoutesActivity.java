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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.betterttc.services.GetRoutesAsyncTask;
import com.example.betterttc.services.Route;

import java.util.ArrayList;

public class GetRoutesActivity extends Activity {

    private ListView routeList;
    private RouteArrayAdapter busRouteAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);
        routeList = findViewById(R.id.routes);
        fillContent();
    }

    private void fillContent()
    {
        busRouteAdapter = new RouteArrayAdapter(this, 0, new ArrayList<Route>());
        GetRoutesAsyncTask routesAsyncTask = new GetRoutesAsyncTask();
        GetRoutesAsyncTask.Callback callback = new GetRoutesAsyncTask.Callback() {
            @Override
            public void onDataReceived(ArrayList<Route> routes) {
                routeList.setAdapter(busRouteAdapter);
                busRouteAdapter.addAll(routes);
            }
        };

        routesAsyncTask.setCallback(callback);
        routesAsyncTask.execute();
    }


    class RouteArrayAdapter extends ArrayAdapter<Route> {

        private Context context;

        public RouteArrayAdapter(Context context, int resource, ArrayList<Route> objects) {
            super(context, resource, objects);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.view_route, parent, false);
            final Route route = getItem(position);

            TextView firstStop = view.findViewById(R.id.start);
            TextView secondStop = view.findViewById(R.id.end);

            firstStop.setText(route.getFirstStopName());
            secondStop.setText(route.getSecondStopName());

            view.findViewById(R.id.more).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GetRoutesActivity.this, GetRouteStopsActivity.class);
                    intent.putExtra("routeNumber", route.getRouteNumber());

                    startActivity(intent);
                }
            });
            return view;
        }
    }
}
