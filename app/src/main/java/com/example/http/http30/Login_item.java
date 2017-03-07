package com.example.http.http30;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/24.
 */

public class Login_item extends AppCompatActivity{

    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private List<Lesson> mDataset;
    private String week, term, html;
    private TextView txt1, txt2;
    private Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        /*//设置返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        bindView();

        initData();

        //设置Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.Toolbar_item);
        toolbar.setTitle("武大课表");
        txt1.setText(week);
        txt2.setText(term);
        setSupportActionBar(toolbar);

        //设置RecyclerView
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

    //加载控件
    private void bindView() {
        txt1 = (TextView) findViewById(R.id.txt1_item);
        txt2 = (TextView) findViewById(R.id.txt2_item);
        bt = (Button) findViewById(R.id.bt_item);
        //切换周历模式
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //传数据
                Intent intent = new Intent(Login_item.this, Login_grid.class);
                intent.putExtra("Html", html);
                intent.putExtra("Week", week);
                intent.putExtra("Term", term);
                startActivity(intent);
                finish();
            }
        });
    }

    //对课表页面源代码进行分析，并初始化mDataset
    private List<Lesson> initData() {

        //获取传输来的网页源代码和其他信息
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

        mDataset = new ArrayList<>();
        Lesson lesson = new Lesson(html);
        for (int i = 0; i < lesson.size(); i++) {
            mDataset.add(lesson.getall(lesson, i));
        }

        return mDataset;
    }
}
