package com.example.abhishek.fjord;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class ShowAQI extends AppCompatActivity {
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_aqi);
        tv = (TextView) findViewById(R.id.headingText);
        Bundle extras = getIntent().getExtras();
        String latitude, longitude;
        latitude = extras.getString("lat");
        longitude = extras.getString("long");
        Log.d("Latitude", latitude);
        Log.d("Longitude", longitude);
        String urlString = "https://api.waqi.info/feed/geo:"+latitude+";"+longitude+"/?token=427d4e86c04f476ae1af11fac004829fd53e8d20";
        BackgroundWorker backgroundWorker=new BackgroundWorker(this, tv);
        backgroundWorker.execute(urlString);
    }
    class BackgroundWorker extends AsyncTask<String,Void,String> {
        Context context;
        AlertDialog alertDialog;
        TextView tv;
        ProgressDialog pd;
        public BackgroundWorker(Context ctx, TextView t){
            this.context=ctx;
            this.tv = t;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String urlString = params[0];
                Log.d("received url", urlString);
                URL url = new URL(urlString);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                String jsonString = sb.toString();
                System.out.println("JSON: " + jsonString);
                Log.d("response", jsonString);
                return jsonString;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPreExecute() {
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Fetching");
            pd = new ProgressDialog(ShowAQI.this);
            pd.setMessage("Please Wait");
            pd.show();
        }
        @Override
        protected void onPostExecute(String result) {
            pd.dismiss();
            alertDialog.dismiss();
            String finalResult = "";
            HashMap<String, String> pollutantMap = new HashMap<>();
            try{
                JSONObject obj = new JSONObject(result);
                JSONObject data = obj.getJSONObject("data");
                Integer aqi = data.getInt("aqi");
                Log.d("iaqi", aqi.toString());
                JSONObject pollutant = data.getJSONObject("iaqi");
                JSONObject city = data.getJSONObject("city");
                pollutantMap.put("city", city.getString("name"));
                finalResult+="CITY:    "+city.getString("name")+"\n\n";
                pollutantMap.put("aqi", aqi.toString());
                finalResult+="AQI:    "+aqi.toString()+"\n\n";
                for(int i = 0; i<pollutant.names().length(); i++){
                    //Log.d("iaqi", "key = " + pollutant.names().getString(i) + " value = " + pollutant.get(pollutant.names().getString(i)));
                    JSONObject temp = pollutant.getJSONObject(pollutant.names().getString(i));
                    //Log.d("name", pollutant.names().getString(i));
                    //Log.d("value", temp.getString("v"));
                    pollutantMap.put(pollutant.names().getString(i), temp.getString("v"));
                    finalResult+=pollutant.names().getString(i).toUpperCase()+":    "+temp.getString("v")+"\n\n";
                }
            }catch (JSONException e1) {
                e1.printStackTrace();
            }
            System.out.println(pollutantMap);
            tv.setText(finalResult);

        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
