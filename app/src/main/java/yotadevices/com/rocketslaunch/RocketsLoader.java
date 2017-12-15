package yotadevices.com.rocketslaunch;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by dmitry.barbashin on 12.12.2017.
 */

public class RocketsLoader extends AsyncTaskLoader<List<Rocket>> {
    private static final String LOG_TAG = RocketsLoader.class.getName();
    private String mUrl;

    public RocketsLoader(Context context,String url){
        super(context);
        mUrl = url;

    }
    @Override
    public List<Rocket> loadInBackground() {
        Log.i(LOG_TAG,"loadInBackground");
        return QueryUtils.fetchEarthquakeData(mUrl);
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG,"onStartLoading");
        forceLoad();
    }
}
