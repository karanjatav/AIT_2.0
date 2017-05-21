package com.example.darkknight.ait_20.Adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.darkknight.ait_20.Pojo.Result_Object;
import com.example.darkknight.ait_20.R;

import java.util.List;
import java.util.Objects;

/**
 * Created by DARKKNIGHT on 5/19/2017.
 */

public class Results_Adapter  extends RecyclerView.Adapter<Results_Adapter.RecyclerViewHolder> {

    String sub_code,sub_name,max_marks,obt_marks;


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView sub_code_tv,sub_name_tv,max_marks_tv,obt_marks_tv;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            sub_code_tv=(TextView) itemView.findViewById(R.id.sub_code_xtv);
            sub_name_tv=(TextView) itemView.findViewById(R.id.sub_name_xtv);
            max_marks_tv=(TextView) itemView.findViewById(R.id.max_marks_xtv);
            obt_marks_tv=(TextView) itemView.findViewById(R.id.obt_marks_xtv);



        }
    }

    List<Result_Object> result_objects;

    public Results_Adapter(List<Result_Object> result_objects) {
        this.result_objects = result_objects;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_result, viewGroup, false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder recyclerViewHolder, int position) {
        recyclerViewHolder.sub_code_tv.setText(result_objects.get(position).getSub_code());
        recyclerViewHolder.sub_name_tv.setText(result_objects.get(position).getSub_name());
        recyclerViewHolder.max_marks_tv.setText(result_objects.get(position).getMax_marks());
        recyclerViewHolder.obt_marks_tv.setText(result_objects.get(position).getObt_marks());

        if(Objects.equals(result_objects.get(position).getPass_fail(), "fail")){
            recyclerViewHolder.obt_marks_tv.setTextColor(Color.RED);
        }
        else if(Objects.equals(result_objects.get(position).getPass_fail(), "pass")){
            recyclerViewHolder.obt_marks_tv.setTextColor(Color.parseColor("#009688"));
        }

    }

    @Override
    public int getItemCount() {
        return result_objects.size();
    }


}




