package com.example.http.http30;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/5.
 */

public class Login_grid extends AppCompatActivity{

    private Lesson[] mDataset;
    private String week, term, html;
    private TextView txt1, txt2, Mon;
    private Button bt;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid);

        //设置Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.Toolbar_grid);
        toolbar.setTitle("武大课表");
        txt1.setText(week);
        txt2.setText(term);
        setSupportActionBar(toolbar);

        linearLayout = (LinearLayout) findViewById(R.id.column_1);
        TextView textView = new TextView(this);

        bindView();
        initData();
        new Analyse_Item(mDataset);
    }


    //加载控件
    private void bindView() {
        txt1 = (TextView) findViewById(R.id.txt1_gird);
        txt2 = (TextView) findViewById(R.id.txt2_grid);
        Mon = (TextView) findViewById(R.id.Mon);
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
    }

    //对课表页面源代码进行分析，并初始化mDataset
    private void initData() {

        //获取传输来的网页源代码
        Intent intent = getIntent();
        html = intent.getStringExtra("Html");
        week = intent.getStringExtra("Week");
        term = intent.getStringExtra("Term");



        /**
         * 课程名
         * 老师
         * 详细信息
         * 种类
         * 学分
         */

        Lesson lesson = new Lesson(html);
        mDataset = new Lesson[lesson.size()];
        for (int i = 0; i < lesson.size(); i++) {
            mDataset[i] = lesson.getall(lesson, i);
        }
    }

}
