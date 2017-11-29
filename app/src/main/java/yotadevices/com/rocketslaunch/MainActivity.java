package yotadevices.com.rocketslaunch;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Rocket> rockets;
    private RocketAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rocket_recycleview);
        /**
         * Array List for Binding Data from JSON to this List
         */
                rockets = new ArrayList<>();
        /**
         * Binding that List to Adapter
         */
        adapter = new RocketAdapter(this,rockets);


        final RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(itemAnimator);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        new GetDataTask().execute();



    }

    /**
     * Creating Get Data Task for Getting Data From Web
     */
    class GetDataTask extends AsyncTask<Void, Void, Void> {

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
}
