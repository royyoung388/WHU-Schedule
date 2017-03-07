package com.example.http.http30;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/1.
 */

public class Lesson {

    //查找的内容
    private String[] what = {"lessonName\\s*=\\s*\"(\\S*)\";//课程名", "teacherName\\s*=\\s*\"(\\S*)\"; //任课老师",
            "detail\\s*=\\s*\"(\\S*)\";//课程的详细信息", "day\\s*=\\s*\"(\\d*)\";//"/*周几*/, "planType\\s*=\\s*\"(\\S*)\";"/*种类*/,
            "credit\\s*=\\s*\"(\\S*)\";//课程学分", "var\\sareaName\\s=\\s\"(\\S*)\";"/*大地点*/, "var\\sclassRoom\\s=\\s\"(\\S*)\";//教室",
            "var\\s* beginTime\\s* =\\s* \"(\\w)\";//上课时间，从第几节课开始", "var\\s* endTime\\s* =\\s* \"(\\w)\";//上课时间，到第几节课结束"};
    private List<String> name, teacher, detail, day, type, credit, area, room, start, end;
    private String html;

    //分析传来的html，并初始化
    public Lesson(String html) {
        this.html = html;
        agetname();             //课程名
        agetteacher();          //任课老师
        agetdetail();           //课程详细信息
        agetday();              //周几上课
        agettype();             //课程种类
        agetcredit();           //课程学分
        agetarea();             //上课大地点
        agetroom();             //上课教室
        agetstart();            //开始上课时间
        agetend();              //结束上课时间
    }

    //直接初始化
    public Lesson() {
        name = new ArrayList<>();
        teacher = new ArrayList<>();
        detail = new ArrayList<>();
        day = new ArrayList<>();
        type = new ArrayList<>();
        credit = new ArrayList<>();
        area = new ArrayList<>();
        room = new ArrayList<>();
        start = new ArrayList<>();
        end = new ArrayList<>();
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

    //获取地点
    private void agetarea() {
        Analyse analyse = new Analyse(what[6], html);
        area = analyse.getGroup();
    }
    public String getarea(int i) {
        return area.get(i);
    }

    //获取教室
    private void agetroom() {
        Analyse analyse = new Analyse(what[7], html);
        room = analyse.getGroup();
    }
    public String getroom(int i) {
        return room.get(i);
    }

    //获取开始时间
    private void agetstart() {
        Analyse analyse = new Analyse(what[8], html);
        start = analyse.getGroup();
    }
    public String getstatrt(int i) {
        return start.get(i);
    }

    //获取结束时间
    private void agetend() {
        Analyse analyse = new Analyse(what[9], html);
        end = analyse.getGroup();
    }
    public String getend(int i) {
        return end.get(i);
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
        lesson.area.add(this.getarea(i));
        lesson.room.add(this.getroom(i));
        lesson.start.add(this.getstatrt(i));
        lesson.end.add(this.getend(i));
        return lesson;
    }
}
