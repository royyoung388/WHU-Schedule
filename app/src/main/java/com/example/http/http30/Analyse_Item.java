package com.example.http.http30;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/6.
 */

public class Analyse_Item extends AppCompatActivity{

    private Lesson[] Data;
    private List<Lesson> Mon, Tus, Wed, Thu, Fri, Sta, Sun;
    private LinearLayout linearLayout1, linearLayout2, linearLayout3, linearLayout4, linearLayout5, linearLayout6, linearLayout7;

    //构造器.获取周数，用于判断双周的课
    //每一个mDatatset都是有一个Lesson来存储一门课的信息
    public Analyse_Item(Lesson[] Data) {
        this.Data = Data;
        Mon = new ArrayList<>();
        Tus = new ArrayList<>();
        Wed = new ArrayList<>();
        Thu = new ArrayList<>();
        Fri = new ArrayList<>();
        Sta = new ArrayList<>();
        Sun = new ArrayList<>();
        bindView();
        sortWeek();
    }

    //初始化View
    private void bindView() {
        linearLayout1 = (LinearLayout) findViewById(R.id.column_1);
        linearLayout2 = (LinearLayout) findViewById(R.id.column_2);
        linearLayout3 = (LinearLayout) findViewById(R.id.column_3);
        linearLayout4 = (LinearLayout) findViewById(R.id.column_4);
        linearLayout5 = (LinearLayout) findViewById(R.id.column_5);
        linearLayout6 = (LinearLayout) findViewById(R.id.column_6);
        linearLayout7 = (LinearLayout) findViewById(R.id.column_7);
    }

    //根据星期分类
    private void sortWeek() {
        for (int i = 0; i < Data.length; i++) {
            switch (Data[i].getDay(0)) {
                case "1" :
                    Mon.add(Data[i]);
                    break;
                case "2":
                    Tus.add(Data[i]);
                    break;
                case "3":
                    Wed.add(Data[i]);
                    break;
                case "4":
                    Thu.add(Data[i]);
                    break;
                case "5":
                    Fri.add(Data[i]);
                    break;
                case "6":
                    Sta.add(Data[i]);
                    break;
                case "7":
                    Sun.add(Data[i]);
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
            for (int j = 0; j < k; j++) {
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
        int last = 1;
        for (int i = 0; i < lesson.length; i++) {
            if (i != 0) {
                last = Integer.parseInt(lesson[i - 1].getend(0));
            }
            int margin = 64 * (Integer.parseInt(lesson[i].getstatrt(0)) - last);
            int height = 64 * (Integer.parseInt(lesson[i].getend(0)) - Integer.parseInt(lesson[i].getstatrt(0)) + 1);
            setstyle(linearLayout, lesson[i].getName(0)+"@"+lesson[i].getarea(0)+" "+lesson[i].getroom(0), height, margin);
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
        lp.setMargins(0, dpTopx(margin), 0, 0);
        textView.setLayoutParams(lp);
        linearLayout.addView(textView);
    }

    //将dp单位换算为px
    private int dpTopx(int dp) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, this.getResources().getDisplayMetrics());
        return px;
    }
}
