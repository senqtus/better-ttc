package com.example.betterttc;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;


import com.example.betterttc.services.BusSchedule;
import com.example.betterttc.services.GetStopAsyncTask;

import java.util.ArrayList;

public class GetStopActivity extends Activity {

    private ListView buses;
    private BusScheduleArrayAdapter busScheduleArrayAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        buses = findViewById(R.id.schedule);
        String stopId = "0";
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            stopId= extras.getString("stopId");
        }


        busScheduleArrayAdapter = new BusScheduleArrayAdapter(this, 0, new ArrayList<BusSchedule>());
        GetStopAsyncTask getStopAsyncTask = new GetStopAsyncTask();
        GetStopAsyncTask.Callback callback = new GetStopAsyncTask.Callback() {
            @Override
            public void onDataReceived(ArrayList<BusSchedule> stops) {
                buses.setAdapter(busScheduleArrayAdapter);
                busScheduleArrayAdapter.addAll(stops);
            }
        };

        getStopAsyncTask.setCallback(callback);
        getStopAsyncTask.execute(stopId);
    }


    public class BusScheduleArrayAdapter extends ArrayAdapter<BusSchedule> {

        private Context context;

        public BusScheduleArrayAdapter(Context context, int resource, ArrayList<BusSchedule> objects) {
            super(context, resource, objects);
            this.context = context;
        }

        @Override
        public View getView(int position, @Nullable View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.view_schedule, parent, false);
            final BusSchedule bus = getItem(position);

            TextView number = view.findViewById(R.id.bus_number);
            TextView time = view.findViewById(R.id.time_left);

            number.setText(bus.getBusNumber());
            time.setText(bus.getBusTimeLeft());
            return view;
        }
    }


}
