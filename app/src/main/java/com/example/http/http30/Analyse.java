package com.example.http.http30;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/1/31.
 */

public class Analyse {

    private String str1, str2;
    private Matcher matcher;
    private Pattern pattern;

    //分析与初始化
    public Analyse(String str1, String str2) {
        this.str1 = str1.toString();
        this.str2 = str2.toString();
        setData();
    }

    //正则表达式初始化
    private void setData() {
        pattern = Pattern.compile(str1);
        matcher = pattern.matcher(str2);
    }

    //获取所有的group
    public List<String> getGroup() {
        List<String> str = new ArrayList<>();
        //int i = 0;
        while(matcher.find()) {
            str.add(matcher.group(1));
            //i++;
        }
        return str;
    }

    //获取指定的group
    public String getGroup(int index) {
        List<String> str = new ArrayList<>();
        //int i = 0;
        while(matcher.find()) {
            str.add(matcher.group(1));
            //i++;
        }
        return str.get(index);
    }

    //获取group的数据个数
    public int getSize() {
        int j = 0;
        while(matcher.find()) {
            j++;
        }
        return j;
    }
}
