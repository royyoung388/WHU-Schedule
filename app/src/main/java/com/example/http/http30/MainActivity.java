package com.example.http.http30;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText et_name, et_pass, et_code;
    private Button bt_flash, bt_submit;
    private ImageView im_igm;
    final String code = "http://210.42.121.133/servlet/GenImg";//验证码
    final String loginurl =  "http://210.42.121.133/servlet/Login";//登录网站
    final int MESSAGE_SHOW_IMG = 1;
    final int MESSAGE_RESULT_ERR = 2;
    final int Link_ok = 3;
    final int Link_err = 4;
    final int Link_noyzm = 5;
    final int Link_nouser = 6;
    private String userName, userPass, userCode,cookie;
    private String csrf = null;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //加载控件
        setcontrol();
        //子线程加载验证码
        requestNetByThread(code);

        //监听器对应相应动作
        bt_flash.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                et_code.setText(null);
                //子线程加载验证码
                requestNetByThread(code);
            }
        });
        bt_submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //检查之后登录
                check();
                //重置输入和验证码
                et_pass.setText(null);
                et_code.setText(null);

                /***************************************************
                 * 此处刷新验证码有巨坑！！！会导致后面无法获取课表页面
                 * 抓包发现原因可能是获取验证码的请求又发了一遍
                 ***************************************************/
                /*//刷新验证码
                requestNetByThread(code);*/

            }
        });

        /*********************
         * 此处刷新也有坑
         * 最后解决办法只能在message判断处进行刷新
         **********************************/
        /*//刷新验证码
                requestNetByThread(code);*/

        //判断message
        judgemessage();
    }

    //检查输入
    private void check() {
        // 获取用户名
        userName = et_name.getText().toString();
        // 获取用户密码
        userPass = et_pass.getText().toString();
        //获取验证码
        userCode = et_code.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(userPass)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(userCode)) {
            Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "正在连接", Toast.LENGTH_SHORT).show();
            new Thread() {
                public void run() {
                    // 调用loginByGet方法登录
                    loginByPost(loginurl, userName, userPass, userCode);
                };
            }.start();
        }
    }

    //设置回到主界面箭头
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //加载控件
    private void setcontrol() {
        et_name = (EditText) findViewById(R.id.name);
        et_pass = (EditText) findViewById(R.id.password);
        et_code = (EditText) findViewById(R.id.code);
        bt_flash = (Button) findViewById(R.id.flash);
        bt_submit = (Button) findViewById(R.id.submit);
        im_igm = (ImageView) findViewById(R.id.img);
    }

    //子线程加载验证码
    private void requestNetByThread(final String path) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                requestNet(path);
            }
        }.start();
    }
    //定义访问网络获取验证码的方法
    private void requestNet(String path) {
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            int code = connection.getResponseCode();
            if (code == 200) {
                cookie = connection.getHeaderField("set-cookie");
                cookie = cookie.substring(0, cookie.length() - 8);
                Bitmap bitmap = BitmapFactory.decodeStream(connection.getInputStream());
                Message message = new Message();
                message.obj = bitmap;
                message.what = MESSAGE_SHOW_IMG;
                handler.sendMessage(message);
            } else {
                //请求失败,同样也发送消息
                Message message = new Message();
                //3.9 设置消息的类型值
                message.what = MESSAGE_RESULT_ERR;
                //4.0 发送消息到handler
                handler.sendMessage(message);
            }

        } catch (Exception e) {
            e.printStackTrace();
            //失败,同样也发送消息
            Message message = new Message();
            //3.9 设置消息的类型值
            message.what = MESSAGE_RESULT_ERR;
            //4.0 发送消息到handler
            handler.sendMessage(message);
        }
    }

    //message判断
    private void judgemessage() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //super.handleMessage(msg);
                switch (msg.what) {
                    case MESSAGE_SHOW_IMG:
                        im_igm.setImageBitmap((Bitmap) msg.obj);
                        break;
                    case MESSAGE_RESULT_ERR:
                        Toast.makeText(MainActivity.this, "验证码获取失败", Toast.LENGTH_SHORT).show();
                        //刷新验证码
                        requestNetByThread(code);
                        break;
                    case Link_ok:
                        Toast.makeText(MainActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
                        //刷新验证码
                        requestNetByThread(code);
                        break;
                    case Link_noyzm:
                        Toast.makeText(MainActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                        //刷新验证码
                        requestNetByThread(code);
                        break;
                    case Link_err:
                        Toast.makeText(MainActivity.this, "连接出错", Toast.LENGTH_SHORT).show();
                        //刷新验证码
                        requestNetByThread(code);
                        break;
                    case Link_nouser:
                        Toast.makeText(MainActivity.this, "用户名/密码错误", Toast.LENGTH_SHORT).show();
                        //刷新验证码
                        requestNetByThread(code);
                        break;
                }
            }
        };
    }

    //http连接post
    private void  loginByPost(String posturl, String name, String password, String code) {
        try {
            URL url = new URL(posturl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setRequestProperty("Host","210.42.121.133");
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:51.0) Gecko/20100101 Firefox/51.0");
            conn.setRequestProperty("Accept"," text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            conn.setRequestProperty("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
            conn.setRequestProperty("Accept-Encoding", "deflate");
            conn.setRequestProperty("Referer","http://210.42.121.133/");
            conn.setRequestProperty("Cookie",cookie);
            conn.setRequestProperty("Connection","keep-alive");
            conn.setRequestProperty("Upgrade-Insecure-Requests","1");
            conn.connect();

            String user = "id=" + URLEncoder.encode(name, "utf8") + "&pwd=" + URLEncoder.encode(MD5.getInstance().getMD5(password), "utf8") + "&xdvfb=" + URLEncoder.encode(code, "utf8");
            OutputStream os = conn.getOutputStream();
            os.write(user.getBytes("utf8"));
            os.flush();

            //第二种方法进行账号密码输入
            /*String user = "id=" + name + "&pwd=" + MD5.getInstance().getMD5(password) + "&xdvfb=" + code;
            OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            os.write(user);
            os.flush();*/

            //创建输入流对象，接收连接后的网页数据
            InputStream in = conn.getInputStream();

            byte[] data = GetData.read(in);
            String html = new String(data, "gbk");

            //接收源代码的方法二
            //BufferedReader html = new BufferedReader(new InputStreamReader(in, "gbk"));

            //输出结果，校验是否操作成功
            while (html != null) {
                if (html.contains("修改密码")) {
                    //获取csrftoken
                    Analyse analyse = new Analyse("&csrftoken=(.*)','", html);
                    csrf = analyse.getGroup(0);

                    //获取课表页面
                    html = getPage();

                    Intent intent = new Intent(MainActivity.this,Login.class);
                    intent.putExtra("Html", html);
                    startActivity(intent);

                    //发送消息
                    Message message = new Message();
                    message.what = Link_ok;
                    handler.sendMessage(message);
                    break;
                } else if (html.contains("验证码错误")) {
                    Message message = new Message();
                    message.what = Link_noyzm;
                    handler.sendMessage(message);
                    break;
                } else if (html.contains("用户名/密码错误")){
                    Message message = new Message();
                    message.what = Link_nouser;
                    handler.sendMessage(message);
                    break;
                }
            }
            conn.disconnect();
            //注意记得关闭流，不然连接不能结束会抛出异常
            os.close();
            in.close();
        } catch (Exception e) {
            Message message = new Message();
            message.what = Link_err;
            handler.sendMessage(message);
        }
    }

    //获取课表页面
    private String getPage() {

        String ahtml = "";

        try {
            String page = "http://210.42.121.133/servlet/Svlt_QueryStuLsn?action=queryStuLsn&csrftoken=" + csrf;
            URL url = new URL(page);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setRequestProperty("Host", "210.42.121.133");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:51.0) Gecko/20100101 Firefox/51.0");
            conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
            conn.setRequestProperty("Accept-Encoding", "deflate");
            conn.setRequestProperty("Referer", "http://210.42.121.133/stu/stu_course_parent.jsp");
            conn.setRequestProperty("Cookie", cookie);
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
            conn.connect();

            InputStream in = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "gbk"));

            String str = null;

            while ((str = reader.readLine()) != null) {
                ahtml += str;
            }
            conn.disconnect();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ahtml;
    }
}

