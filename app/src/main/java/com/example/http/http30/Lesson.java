package com.example.http.http30;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/1.
 */

public class Lesson {

    //查找的内容
    private String[] what = {"lessonName\\s*=\\s*\"(\\S*)\";//课程名", "teacherName\\s*=\\s*\"(\\S*)\";//任课老师",
            "detail\\s*=\\s*\"(\\S*)\";//课程的详细信息", "day\\s*=\\s*\"(\\d*)\";//", "planType\\s*=\\s*\"(\\S*)\";",
            "credit\\s*=\\s*\"(\\S*)\";//课程学分"};
    private List<String> name, teacher, detail, day, type, credit;
    private String html;

    //分析传来的html，并初始化
    public Lesson(String html) {
        this.html = html;
        agetname();
        agetteacher();
        agetdetail();
        agetday();
        agettype();
        agetcredit();
    }

    //直接初始化
    public Lesson() {
        name = new ArrayList<>();
        teacher = new ArrayList<>();
        detail = new ArrayList<>();
        day = new ArrayList<>();
        type = new ArrayList<>();
        credit = new ArrayList<>();
    }

    //获取课程名
    private void agetname() {
        Analyse analyse = new Analyse(what[0], html);
        name = analyse.getGroup();
    }
    public String getName(int i) {
        return name.get(i);
    }

    //获取老师
    private void agetteacher() {
        Analyse analyse = new Analyse(what[1], html);
        teacher = analyse.getGroup();
    }
    public String getTeacher(int i) {
        return teacher.get(i);
    }

    //获取详细信息
    private void agetdetail() {
        Analyse analyse = new Analyse(what[2], html);
        detail = analyse.getGroup();
    }
    public String getDetail(int i) {
        return detail.get(i);
    }

    //获取周几
    private void agetday() {
        Analyse analyse = new Analyse(what[3], html);
        day = analyse.getGroup();
    }
    public String getDay(int i) {
        return day.get(i);
    }

    //获取种类
    private void agettype() {
        Analyse analyse = new Analyse(what[4], html);
        type = analyse.getGroup();
    }
    public String getType(int i) {
        return type.get(i);
    }

    //获取学分
    private void agetcredit() {
        Analyse analyse = new Analyse(what[5], html);
        credit = analyse.getGroup();
    }
    public String getCredit(int i) {
        return credit.get(i);
    }

    //获取长度
    public int size() {
        Analyse analyse = new Analyse(what[0], html);
        return analyse.getSize();
    }

    //获取第i节课的所有信息
    public Lesson getall(Lesson l, int i) {
        Lesson lesson = new Lesson();
        lesson.name.add(this.getName(i));
        lesson.teacher.add(this.getTeacher(i));
        lesson.detail.add(this.getDetail(i));
        lesson.day.add(this.getDay(i));
        lesson.type.add(this.getType(i));
        lesson.credit.add(this.getCredit(i));
        return lesson;
    }
}
