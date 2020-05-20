package com.example.betterttc.services;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetRoutesAsyncTask extends AsyncTask<Void, Void, ArrayList<Route>> {

    private Callback callback;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Route> doInBackground(Void... voids) {
        ArrayList<Route> busRoutes = new ArrayList<Route>();
        try
        {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://transfer.ttc.com.ge:8080/otp/routers/ttc/routes")
                    .build();
            Response response = client.newCall(request).execute();
            String xmlData = response.body().string();
            Document doc = (Document) Jsoup.parse(xmlData, "", Parser.xmlParser());
            Elements elements = doc.getElementsByTag("Route");
            for(int i=0;i<elements.size(); i++)
            {
                if(elements.get(i).getElementsByTag("Type").text().equals("bus") &&
                        StringUtil.isNumeric(elements.get(i).getElementsByTag("RouteNumber").text())) {
                        Route route = new Route();
                        route.setFirstStopName(elements.get(i).getElementsByTag("StopA").get(0).text());
                        route.setSecondStopName(elements.get(i).getElementsByTag("StopB").get(0).text());
                        route.setRouteNumber(elements.get(i).getElementsByTag("RouteNumber").get(0).text());
                        busRoutes.add(route);
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return busRoutes;
    }

    @Override
    protected void onPostExecute(ArrayList<Route> busRoutes) {
        if (callback != null) {
            callback.onDataReceived(busRoutes);
        }
    }

    public void setCallback(Callback callback)
    {
        this.callback = callback;
    }


    public interface Callback {
        void onDataReceived(ArrayList<Route> movies);
    }

}
