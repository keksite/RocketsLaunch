package yotadevices.com.rocketslaunch;

/**
 * Created by dmitry.barbashin on 29.11.2017.
 */


import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Creating Get Data Task for Getting Data From Web
 */
class GetDataTask extends AsyncTask<Void, Void, Void> {
    private ArrayList<Rocket> rockets;
    private RocketAdapter adapter;

    public GetDataTask(RocketAdapter adapter, ArrayList<Rocket> rockets) {
        this.adapter = adapter;
        this.rockets = rockets;
    }

    @Override
    protected Void doInBackground(Void... params) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.spacexdata.com/v1/launches?year=2017")
                .build();
        try {
            Response response = client.newCall(request).execute();
            JSONArray jsonArray = new JSONArray(response.body().string());
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = null;
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
                if(jsonObject.has("launch_date_unix")) {
                    //get Data
                    data = jsonObject.getString("launch_date_unix");
                }
                else{
                    data = jsonObject.getString("launch_date_utc");
                }

                rockets.add(new Rocket(missionPatch, rocketName, data, details));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException ex){
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        adapter.notifyDataSetChanged();
        super.onPostExecute(aVoid);

    }
}
