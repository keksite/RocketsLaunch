package yotadevices.com.rocketslaunch;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.squareup.leakcanary.LeakCanary;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    RocketAdapter rocketAdapter;
    ArrayList<Rocket> rockets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        //LeakCanary.install(getApplication());

        setContentView(R.layout.rocket_recycleview);
        rockets = new ArrayList<>();
        rocketAdapter = new RocketAdapter(rockets);

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
        if (savedInstanceState != null) {
            // scroll to existing position which exist before rotation.
            recyclerView.scrollToPosition(savedInstanceState.getInt("position"));
        }
        new GetDataTask(rocketAdapter, /*rockets,*/ "2017"/*,this*/).execute();


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            // scroll to existing position which exist before rotation.
            recyclerView.scrollToPosition(outState.getInt("position"));
        }
        super.onSaveInstanceState(outState);

    }

    private long currentVisiblePosition;
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
        /*finish();
        startActivity(getIntent());*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        final Context mainActivity = this;
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
                String year = spinner.getSelectedItem().toString();
                new GetDataTask(rocketAdapter, /*rockets,*/ year/*,mainActivity*/).execute();
                recyclerView.smoothScrollToPosition(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return true;
    }
}
