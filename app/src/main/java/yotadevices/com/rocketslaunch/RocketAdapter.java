package yotadevices.com.rocketslaunch;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by keksi on 27.11.2017.
 */

public class RocketAdapter extends RecyclerView.Adapter<RocketAdapter.ViewHolder> {
    private Context context;
    private List<Rocket> rockets;

    RocketAdapter(Context context, List<Rocket> rockets) {
        this.context = context;
        this.rockets = rockets;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Rocket rocket = rockets.get(position);
        Picasso.with(this.context)
                .load(rocket.getRocketIcon())
                .fit().centerInside()
                .into(holder.rocketIcon);
        holder.rocketName.setText(rocket.getRocketNAme());
        holder.launchDate.setText(String.valueOf(rocket.getLaunchData()));
        holder.details.setText(rocket.getDetails());
        holder.cv.setRadius(25);
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence text = "Hello toast!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return rockets.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        private ImageView rocketIcon;
        private TextView rocketName;
        private  TextView launchDate;
        private TextView details;

        public ViewHolder(View itemView) {
            super(itemView);
            this.cv = (CardView) itemView.findViewById(R.id.cv);
            this.rocketIcon = (ImageView) itemView.findViewById(R.id.rocket_icon);
            this.rocketName = (TextView) itemView.findViewById(R.id.rocket_name);
            this.launchDate = (TextView) itemView.findViewById(R.id.launch_date);
            this.details = (TextView) itemView.findViewById(R.id.details);
        }
    }
}
