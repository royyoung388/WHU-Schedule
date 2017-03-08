package com.example.http.http30;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/2/1.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private List<Lesson> mDataset;

    public MyAdapter(List<Lesson> mDataSet) {
        this.mDataset = mDataSet;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, teacher, detail, day, type, credit;

        public ViewHolder(View v) {
            super (v);
            name = (TextView) v.findViewById(R.id.name);
            teacher = (TextView) v.findViewById(R.id.teacher);
            detail = (TextView) v.findViewById(R.id.detail);
            day = (TextView) v.findViewById(R.id.day);
            type = (TextView) v.findViewById(R.id.type);
            credit = (TextView) v.findViewById(R.id.credit);
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        Lesson lesson = mDataset.get(position);
        holder.name.setText(lesson.getName(0));
        holder.teacher.setText("老师: " + lesson.getTeacher(0));
        holder.detail.setText(lesson.getDetail(0));
        holder.day.setText("星期: " + lesson.getDay(0));
        holder.type.setText(lesson.getType(0));
        holder.credit.setText("学分: " + lesson.getCredit(0));
    }

    public int getItemCount() {
        return mDataset.size();
    }
}
