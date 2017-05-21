package com.example.darkknight.ait_20.Adapters;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.darkknight.ait_20.Activities.HomeScreen;
import com.example.darkknight.ait_20.Fragments.Main.Results;
import com.example.darkknight.ait_20.Pojo.Newsfeed_Object;
import com.example.darkknight.ait_20.R;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by DARKKNIGHT on 5/19/2017.
 */

public class NewsFeed_Adapter extends RecyclerView.Adapter<NewsFeed_Adapter.RecyclerViewHolder> {

    ArrayList<Newsfeed_Object> newsfeed_objects;
    Context context;

    public NewsFeed_Adapter(Context context, ArrayList<Newsfeed_Object> newsfeed_objects) {
        this.newsfeed_objects = newsfeed_objects;
        this.context = context;

    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        CardView newsfeed_cv;

        ImageView newsfeed_iv;

        TextView newsfeed_subject_tv, newsfeed_description_tv;


        RecyclerViewHolder(View itemView) {
            super(itemView);

            newsfeed_cv = (CardView) itemView.findViewById(R.id.newsfeed_cv);
            newsfeed_iv = (ImageView) itemView.findViewById(R.id.newsfeed_iv);
            newsfeed_description_tv = (TextView) itemView.findViewById(R.id.newsfeed_description_tv);
            newsfeed_subject_tv = (TextView) itemView.findViewById(R.id.newsfeed_subject_tv);
        }

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_newsfeed, viewGroup, false);

        return new RecyclerViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {


        holder.newsfeed_subject_tv.setText(newsfeed_objects.get(position).getSubject());
        holder.newsfeed_description_tv.setText(newsfeed_objects.get(position).getDescription());

        String type = newsfeed_objects.get(position).getType();

        switch (type) {
            case "web": {
                holder.newsfeed_iv.setImageResource(R.drawable.rsz_web2);
                holder.newsfeed_cv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String link_strg = newsfeed_objects.get(position).getLink();
                        //Toast.makeText(getApplicationContext(), item, Toast.LENGTH_LONG).show();
                        Uri uri = Uri.parse(link_strg); // missing 'http://' will cause crashed
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        v.getContext().startActivity(intent);
                    }
                });

                break;
            }
            case "pdf": {
                holder.newsfeed_iv.setImageResource(R.drawable.pdf);
                holder.newsfeed_cv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String link_strg2 = newsfeed_objects.get(position).getLink();
                        Log.d("pdf_log", link_strg2);
                        // Toast.makeText(getApplicationContext(), item, Toast.LENGTH_LONG).show();

                        Uri uri = Uri.parse(link_strg2); // missing 'http://' will cause crashed
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        v.getContext().startActivity(intent);
                    }
                });
                break;

            }
            case "result": {
                holder.newsfeed_iv.setImageResource(R.drawable.result);
                holder.newsfeed_cv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switchContent("result");
                    }
                });
                break;

            }
            case "sched": {
                holder.newsfeed_iv.setImageResource(R.drawable.sched321);
                holder.newsfeed_cv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switchContent("sched");
                    }
                });
                break;

            }
        }


    }


    @Override
    public int getItemCount() {
        return newsfeed_objects.size();
    }

    public void switchContent(String whereTo) {
        if (context == null)
            return;
        if (context instanceof HomeScreen) {
            HomeScreen homeScreen = (HomeScreen) context;
            if(Objects.equals(whereTo, "result")) {
                homeScreen.goToResults();
            }else if(Objects.equals(whereTo, "sched")){
                homeScreen.goToScheduler();
            }
        }
    }

}