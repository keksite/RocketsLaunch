package yotadevices.com.rocketslaunch;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by dmitry.barbashin on 12.12.2017.
 */

public class QueryUtils {

    /** Tag for the log messages */
    private static final String LOG_TAG = QueryUtils.class.getName();


    private QueryUtils() {

    }
    public static List<Rocket> fetchEarthquakeData(String requestUrl) {
        Log.i(LOG_TAG,"fetchEarthquakeData");
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<Rocket> rockets = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return rockets;

    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(30, TimeUnit.SECONDS);
        client.setReadTimeout(30, TimeUnit.SECONDS);
        client.setWriteTimeout(30, TimeUnit.SECONDS);

        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            jsonResponse = response.body().string();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        }
        return jsonResponse;
    }

    private static List<Rocket> extractFeatureFromJson(String rocketsJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(rocketsJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<Rocket> rockets = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            JSONArray jsonArray = new JSONArray(rocketsJSON);
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

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the rockets JSON results", e);
        }

        // Return the list of earthquakes
        return rockets;
    }
}
