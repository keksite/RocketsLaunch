package yotadevices.com.rocketslaunch;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by keksi on 27.11.2017.
 */

public class RocketAdapter extends RecyclerView.Adapter<RocketAdapter.ViewHolder> {
    private Context context;
    private List<Rocket> rockets;


    RocketAdapter(List<Rocket> rockets) {
        this.rockets = rockets;
    }

    public void swap(ArrayList<Rocket> datas) {
        rockets.clear();
        Collections.reverse(datas);
        rockets.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main, parent, false);
        ViewHolder vh = new ViewHolder(v);
        context = parent.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Rocket rocket = rockets.get(position);
        Picasso.with(context)
                .load(rocket.getRocketIcon())
                .fit().centerInside()
                .into(holder.rocketIcon);
        /*Glide.with(this.context)
                .load(rocket.getRocketIcon())
                .crossFade()
                .into(holder.rocketIcon);*/
        holder.rocketName.setText(rocket.getRocketNAme());
        holder.launchDate.setText(String.valueOf(rocket.getLaunchData()));
        holder.details.setText(rocket.getDetails());
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {
                    Toast.makeText(context, "#" + position + " - " + rockets.get(position).getLaunchData() + " (Long click)", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(context, RocketActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    String message = rockets.get(position).getDetails();
                    String imageURL = rockets.get(position).getRocketIcon();
                    intent.putExtra(Intent.EXTRA_TEXT, message);
                    intent.putExtra("Image", imageURL);
                    context.startActivity(intent);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return rockets.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        private ImageView rocketIcon;
        private TextView rocketName;
        private TextView launchDate;
        private TextView details;
        private ItemClickListener clickListener;

        public ViewHolder(final View itemView) {
            super(itemView);
            this.cv = (CardView) itemView.findViewById(R.id.cv);
            this.rocketIcon = (ImageView) itemView.findViewById(R.id.rocket_icon);
            this.rocketName = (TextView) itemView.findViewById(R.id.rocket_name);
            this.launchDate = (TextView) itemView.findViewById(R.id.launch_date);
            this.details = (TextView) itemView.findViewById(R.id.details);
            itemView.setOnClickListener(this);
        }


        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition(), false);

        }
    }
}
