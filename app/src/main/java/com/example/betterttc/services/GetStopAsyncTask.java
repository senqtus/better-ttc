package com.example.betterttc.services;

import android.os.AsyncTask;

import java.util.ArrayList;

public class GetStopAsyncTask extends AsyncTask<String, Void, ArrayList<BusSchedule>> {

    private GetStopAsyncTask.Callback callback;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<BusSchedule> doInBackground(String... stopId) {
        ArrayList<BusSchedule> buses = new ArrayList<BusSchedule>();

        try
        {
            /*
            String url = String.format("http://transfer.ttc.com.ge:8080/otp/routers/ttc/stopArrivalTimes?stopId=%s",stopId);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            xmlData = response.body().string();

                        Document doc = (Document) Jsoup.parse(xmlData, "", Parser.xmlParser());
            Elements elements = doc.getElementsByTag("Bus");
            for(int i=0;i<elements.size(); i++)
            {
                    BusSchedule bus = new BusSchedule();
                    radgan saitze xml ar iyo
                    pirobitad shevqmeni tagebi
                    bus.setBusNumber(elements.get(i).getElementsByTag("BusNumber").get(0).text());
                    bus.setArrivalTimeLeft(elements.get(i).getElementsByTag("TimeLeft").get(0).text());
                    buses.add(bus);

            }
            */


            //static data for test
            for(int i=0;i<3; i++)
            {
                BusSchedule bus = new BusSchedule();
                bus.setBusNumber(Integer.toString(i + 1));
                bus.setArrivalTimeLeft(Integer.toString(i + 10));
                buses.add(bus);

            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return buses;
    }

    @Override
    protected void onPostExecute(ArrayList<BusSchedule> buses) {
        if (callback != null) {
            callback.onDataReceived(buses);
        }
    }

    public void setCallback(Callback callback)
    {
        this.callback = callback;
    }


    public interface Callback {
        void onDataReceived(ArrayList<BusSchedule> buses);
    }
}
