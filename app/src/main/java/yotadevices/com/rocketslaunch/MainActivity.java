package yotadevices.com.rocketslaunch;

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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Rocket> rockets;
    private RocketAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(outState != null){
            // scroll to existing position which exist before rotation.
            recyclerView.scrollToPosition(outState.getInt("position"));
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rocket_recycleview);
        /*Toolbar tb = (Toolbar) findViewById(R.id.toolbar_actionbar);
        getActionBar().setCustomView(tb);*/

        rockets = new ArrayList<>();
        adapter = new RocketAdapter(this,rockets);
       // adapter.setHasStableIds(true);

        recyclerView = (RecyclerView)findViewById(R.id.rv);
       /* recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this.getApplicationContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        CharSequence text = "Hello toast!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                        toast.show();
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        CharSequence text = "Hello toast long!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                        toast.show();
                    }
                })
        );*/
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(itemAnimator);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        if(savedInstanceState != null){
            // scroll to existing position which exist before rotation.
            recyclerView.scrollToPosition(savedInstanceState.getInt("position"));
        }
        new GetDataTask(adapter, rockets, "2017").execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        String[] arraySpinner;

        getMenuInflater().inflate(R.menu.android_action_bar_spinner_menu, menu);

        MenuItem item = menu.findItem(R.id.spinner);

        final Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
        arraySpinner = new String[] {
                "2017", "2016", "2015", "2014", "2013"
        };
        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, arraySpinner);

        spinner.setAdapter(spinnerAdapter);
        spinner.setDropDownWidth(500);
        spinner.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
        spinner.setDropDownVerticalOffset(100);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //new GetDataTask(adapter,rockets,spinner.getSelectedItem().toString());
                String ss = spinner.getSelectedItem().toString();
                new GetDataTask(adapter, rockets, ss).execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return true;
    }



}
