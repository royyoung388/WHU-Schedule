package com.example.http.http30;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/3/5.
 */

public class Login_grid extends AppCompatActivity{

    private Lesson[] mDataset;
    private String week, term, html;
    private TextView txt1, txt2;
    private Button bt;
    private LinearLayout linearLayout1, linearLayout2, linearLayout3, linearLayout4, linearLayout5, linearLayout6, linearLayout7;
    private List<Lesson> Mon, Tus, Wed, Thu, Fri, Sta, Sun;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid);

        bindView();
        initData();
        sortWeek();

        //设置Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.Toolbar_grid);
        toolbar.setTitle("武大课表");
        txt1.setText(week);
        txt2.setText(term);
        setSupportActionBar(toolbar);
    }


    //加载控件
    private void bindView() {
        txt1 = (TextView) findViewById(R.id.txt1_gird);
        txt2 = (TextView) findViewById(R.id.txt2_grid);
        bt = (Button) findViewById(R.id.bt_gird);
        //切换列表模式
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //传数据
                Intent intent = new Intent(Login_grid.this, Login_item.class);
                intent.putExtra("Html", html);
                intent.putExtra("Week", week);
                intent.putExtra("Term", term);
                startActivity(intent);
                finish();
            }
        });

        linearLayout1 = (LinearLayout) findViewById(R.id.column_1);
        linearLayout2 = (LinearLayout) findViewById(R.id.column_2);
        linearLayout3 = (LinearLayout) findViewById(R.id.column_3);
        linearLayout4 = (LinearLayout) findViewById(R.id.column_4);
        linearLayout5 = (LinearLayout) findViewById(R.id.column_5);
        linearLayout6 = (LinearLayout) findViewById(R.id.column_6);
        linearLayout7 = (LinearLayout) findViewById(R.id.column_7);

        Mon = new ArrayList<>();
        Tus = new ArrayList<>();
        Wed = new ArrayList<>();
        Thu = new ArrayList<>();
        Fri = new ArrayList<>();
        Sta = new ArrayList<>();
        Sun = new ArrayList<>();
    }

    //对课表页面源代码进行分析，并初始化mDataset
    private void initData() {

        //获取传输来的网页源代码
        Intent intent = getIntent();
        html = intent.getStringExtra("Html");
        week = intent.getStringExtra("Week");
        term = intent.getStringExtra("Term");

        Lesson lesson = new Lesson(html);
        int a = lesson.size();
        mDataset = new Lesson[lesson.size()];
        for (int i = 0; i < lesson.size(); i++) {
            mDataset[i] = lesson.getall(lesson, i);
        }
    }

    //根据星期分类
    private void sortWeek() {
        for (int i = 0; i < mDataset.length; i++) {
            switch (mDataset[i].getDay(0)) {
                case "1" :
                    Mon.add(mDataset[i]);
                    break;
                case "2":
                    Tus.add(mDataset[i]);
                    break;
                case "3":
                    Wed.add(mDataset[i]);
                    break;
                case "4":
                    Thu.add(mDataset[i]);
                    break;
                case "5":
                    Fri.add(mDataset[i]);
                    break;
                case "6":
                    Sta.add(mDataset[i]);
                    break;
                case "7":
                    Sun.add(mDataset[i]);
                    break;
            }
        }

        setView(linearLayout1, sortLesson(Mon));
        setView(linearLayout2, sortLesson(Tus));
        setView(linearLayout3, sortLesson(Wed));
        setView(linearLayout4, sortLesson(Thu));
        setView(linearLayout5, sortLesson(Fri));
        setView(linearLayout6, sortLesson(Sta));
        setView(linearLayout7, sortLesson(Sun));
    }

    //对指定星期的课程排序
    private Lesson[] sortLesson(List<Lesson> Week) {

        int k = 0;
        Lesson[] lesson = new Lesson[Week.size()];

        for (Lesson L : Week) {
            lesson[k] = L;
            k++;
        }
        //排序
        for (int i = 0; i < (k-1); i++) {
            for (int j = i+1; j < k; j++) {
                int a = Integer.parseInt(lesson[i].getstatrt(0));
                int b = Integer.parseInt(lesson[j].getstatrt(0));
                if (Integer.parseInt(lesson[i].getstatrt(0)) > Integer.parseInt(lesson[j].getstatrt(0)) ) {
                    Lesson alesson = lesson[i];
                    lesson[i] = lesson[j];
                    lesson[j] = alesson;
                }
            }
        }
        return lesson;
    }

    //对指定的星期课程分析，设置上边的margin，和该课程的高度height即几个格子。然后添加TextView
    private void setView(LinearLayout linearLayout, Lesson[] lesson) {
        int last = 0;
        for (int i = 0; i < lesson.length; i++) {
            if (i != 0) {
                last = Integer.parseInt(lesson[i - 1].getend(0));
                if (Integer.parseInt(lesson[i-1].getstatrt(0)) == Integer.parseInt(lesson[i].getstatrt(0))) {
                    continue;
                }
            }

            int margin = 64 * (Integer.parseInt(lesson[i].getstatrt(0)) - last - 1);
            int height = 64 * (Integer.parseInt(lesson[i].getend(0)) - Integer.parseInt(lesson[i].getstatrt(0)) + 1);
            setstyle(linearLayout, lesson[i].getName(0)+"@"+lesson[i].getarea(0)+" "+lesson[i].getroom(0), height - 3, margin);
        }
    }

    //添加一个TextView
    private void setstyle(LinearLayout linearLayout, String string, int height, int margin) {
        TextView textView = new TextView(this);
        textView.setText(string);
        textView.setBackgroundColor(Color.parseColor("#b9b9b9"));
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpTopx(height));
        lp.setMargins(dpTopx(2), dpTopx(margin + 3), dpTopx(2), 0);
        textView.setLayoutParams(lp);
        linearLayout.addView(textView);
    }

    //将dp单位换算为px
    private int dpTopx(int dp) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, this.getResources().getDisplayMetrics());
        return px;
    }

}
