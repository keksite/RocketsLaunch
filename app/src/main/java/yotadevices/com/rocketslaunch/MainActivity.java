package yotadevices.com.rocketslaunch;


import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.leakcanary.LeakCanary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<List<Rocket>> {

    private RecyclerView recyclerView;
    RocketAdapter rocketAdapter;
    ArrayList<Rocket> rockets;
    private static final String ROCKETS_REQUEST_URL =
            "https://api.spacexdata.com/v2/launches";
    private static final String LOG_TAG = MainActivity.class.getName();
    TextView mEmptyView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(getApplication());

        setContentView(R.layout.rocket_recycleview);
        rockets = new ArrayList<>();
        rocketAdapter = new RocketAdapter(rockets);
        rocketAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                Log.i(LOG_TAG,"registerAdapterDataObserver.onChanged");
                super.onChanged();
                checkAdapterIsEmpty();
            }
        });

        mEmptyView = (TextView) (findViewById(R.id.empty_view));
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setAdapter(rocketAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(itemAnimator);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        if(isOnline()){
            LoaderManager loaderManager = getLoader();
            init(loaderManager);
        }
        else {
            checkAdapterIsEmpty();
        }

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).








    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    private void checkAdapterIsEmpty () {
        if (rockets.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
        }
    }

    public LoaderManager getLoader(){
        Log.i(LOG_TAG,"getLoader");
        return getLoaderManager();
    }

    public void init(LoaderManager loaderManager){
        Log.i(LOG_TAG,"initLoader");
        loaderManager.initLoader(1, null, this);
    }

    /*@Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            // scroll to existing position which exist before rotation.
            recyclerView.scrollToPosition(outState.getInt("position"));
        }
        super.onSaveInstanceState(outState);

    }*/

   /* private long currentVisiblePosition;
    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.getLayoutManager().scrollToPosition((int)currentVisiblePosition);
        currentVisiblePosition = 0;
    }


    @Override
    protected void onPause() {
        super.onPause();
        currentVisiblePosition = 0;
        currentVisiblePosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        *//*finish();
        startActivity(getIntent());*//*
    }*/

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //final Context mainActivity = this;
        String[] data = new String[]{"2017", "2016", "2015"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getMenuInflater().inflate(R.menu.spinner_menu, menu);
        MenuItem item = menu.findItem(R.id.spinner);
        final Spinner spinner = (Spinner) MenuItemCompat.getActionView(item); // get the spinner
        spinner.setAdapter(spinnerAdapter);
        int initialSelectedPosition=spinner.getSelectedItemPosition();
        spinner.setSelection(initialSelectedPosition, false); //clear selection
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View arg1,
                                       int arg2, long arg3) {
                if(isOnline()) {
                    String year = spinner.getSelectedItem().toString();
                    setYear(year);
                    getLoaderManager().restartLoader(1, null, MainActivity.this);
                    recyclerView.smoothScrollToPosition(0);
                }
                else {
                    spinner.setSelection(Arrays.);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return true;
    }


    String year = "2017";
    public void setYear(String year){
        this.year = year;
    }
    public String getYear(){
        return year;
    }

    @Override
    public Loader<List<Rocket>> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG,"onCreateLoader");
        Uri baseUri = Uri.parse(ROCKETS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("launch_year", getYear());
        Log.d(getCallingPackage(),uriBuilder.toString());
        return new RocketsLoader(this, uriBuilder.toString());
    }



    @Override
    public void onLoadFinished(Loader<List<Rocket>> loader, List<Rocket> data) {
        Log.i(LOG_TAG,"onLoadFinished");
        rocketAdapter.swap(data);
        checkAdapterIsEmpty();

    }

    @Override
    public void onLoaderReset(Loader<List<Rocket>> loader) {
        Log.i(LOG_TAG,"onLoaderReset");
        rocketAdapter.swap(rockets);
        checkAdapterIsEmpty();

    }
}
