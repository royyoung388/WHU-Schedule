package com.example.http.http30;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/24.
 */

public class Login extends AppCompatActivity{

    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private List<Lesson> mDataset;
    private String week, term;
    private TextView txt1, txt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        /*//设置返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        txt1 = (TextView) findViewById(R.id.txt1);
        txt2 = (TextView) findViewById(R.id.txt2);
        initData();

        Toolbar toolbar = (Toolbar) findViewById(R.id.Toolbar2);
        toolbar.setTitle("武大课表");
        txt1.setText(week);
        txt2.setText(term);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        mAdapter = new MyAdapter(mDataset);
        recyclerView.setAdapter(mAdapter);
    }

    /*//设置返回主界面
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }*/

    //对课表页面源代码进行分析，并初始化mDataset
    private List<Lesson> initData() {

        //获取传输来的网页源代码
        Intent intent = getIntent();
        String html = intent.getStringExtra("Html");
        week = intent.getStringExtra("Week");
        term = intent.getStringExtra("Term");



        /**
         * 课程名
         * 老师
         * 详细信息
         * 种类
         * 学分
         */

        mDataset = new ArrayList<>();
        Lesson lesson = new Lesson(html);
        for (int i = 0; i < lesson.size(); i++) {
            mDataset.add(lesson.getall(lesson, i));
        }

        return mDataset;
    }
}
