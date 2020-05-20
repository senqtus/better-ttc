package com.example.betterttc.services;

import android.os.AsyncTask;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetRouteStopsAsyncTask extends AsyncTask<String, Void, Stop []> {

    private CallBack callback;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Stop [] doInBackground(String... routeNumber) {
        try
        {
            String url = String.format("http://transfer.ttc.com.ge:8080/otp/routers/ttc/routeInfo?routeNumber=%s&type=bus",routeNumber);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();
            JSONArray jsonArray = (new JSONObject(jsonData)).getJSONArray("RouteStops") ;
            Stop [] stops = new Gson().fromJson(jsonArray.toString(), Stop[].class);
            return stops;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return new Stop[]{};
    }

    @Override
    protected void onPostExecute(Stop [] stops) {
        if (callback != null) {
            callback.onDataReceived(stops);
        }
    }


    public void setCallback(CallBack callback)
    {
        this.callback = callback;
    }


    public interface CallBack {
        void onDataReceived(Stop[] stops);
    }

}
