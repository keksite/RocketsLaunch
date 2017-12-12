package yotadevices.com.rocketslaunch;

/**
 * Created by dmitry.barbashin on 29.11.2017.
 */


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Creating Get Data Task for Getting Data From Web
 */
class GetDataTask extends AsyncTask<Void, Void, Void> {
   // private ProgressDialog pdialog;
    private ArrayList<Rocket> rockets;
    private RocketAdapter adapter;
    private String year;

    public GetDataTask(RocketAdapter adapter, /*ArrayList<Rocket> rockets,*/ String year/*Context context*/) {
        this.adapter = adapter;
        this.rockets = new ArrayList<>();
        this.year = year;
       // pdialog = new ProgressDialog(context);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate();

    }

    @Override
    protected Void doInBackground(Void... param) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.spacexdata.com/v2/launches?launch_year=" + year)
                .build();
        try {
            Response response = client.newCall(request).execute();
            JSONArray jsonArray = new JSONArray(response.body().string());
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject;
                jsonObject = jsonArray.getJSONObject(i);


                Object obj = jsonObject.get("rocket");
                String rocketName;
                if (obj instanceof String) {
                    rocketName = jsonObject.getString("rocket");
                } else {
                    JSONObject rocket = jsonObject.getJSONObject("rocket");
                    rocketName = rocket.getString("rocket_name");
                }
                // get rocketName


                //get rocketImage
                JSONObject link = jsonObject.getJSONObject("links");
                String missionPatch = link.getString("mission_patch");

                //get details
                String details = jsonObject.getString("details");
                String data;
                if (jsonObject.has("launch_date_unix")) {
                    //get Data
                    data = jsonObject.getString("launch_date_unix");
                } else {
                    data = jsonObject.getString("launch_date_utc");
                }
                rockets.add(new Rocket(missionPatch, rocketName, data, details));
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        /*pdialog.setCancelable(false);
        pdialog.setTitle("Please wait.");
        pdialog.setMessage("doing stuff...");
        pdialog.show();*/
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
       /* if (pdialog.isShowing())
            pdialog.dismiss();*/
        adapter.swap(rockets);


    }
}
