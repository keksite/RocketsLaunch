package yotadevices.com.rocketslaunch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rocket_recycleview);

        List<Rocket> rockets = new ArrayList<>();
        populateRecords(rockets);


        final RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        RocketAdapter rocketAdapter= new RocketAdapter(rockets);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        recyclerView.setAdapter(rocketAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(itemAnimator);
    }

    private void populateRecords(List<Rocket> rockets){
        for (int i = 0; i<50; i++){
            Rocket rocket = new Rocket("Item №" + i + 1,new Date(20021987 + i),"Detail" + i + 1);
            rockets.add(rocket);
        }
    }
}
