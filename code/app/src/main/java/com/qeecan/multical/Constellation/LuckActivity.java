package com.qeecan.multical.Constellation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qeecan.multical.Constellation.adapter.LuckTypeAdapter;
import com.qeecan.multical.Constellation.bean.LuckBean;
import com.qeecan.multical.Constellation.bean.LuckTypeBean;
import com.qeecan.multical.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http2.Http2Reader;


public class LuckActivity extends AppCompatActivity implements View.OnClickListener {
    ListView luckLv;
    ImageView backIv;
    List<LuckTypeBean> luckTypeList;

    /*name传递上页面GridView中点击获取的星座名称
     *将名称作为参数传递到url中作为请求参数
     * 请求类型默认为今日，key为聚合数据提供API的认证key
     * 返回含参的url作为请求url
     */
    public static String luckURL(String name) {
       String url = "http://web.juhe.cn:8080/constellation/getAll?consName=" + name + "&type=today&key=62a5851b6fddfbbe3e316066992f5408";
        return url;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luck);
        //控件定位
        initView();
        //初始化此集合
        luckTypeList = new ArrayList<>();

        Intent intent = getIntent();
        //使用intent得到上一个页面传递的星座名称
        String name = intent.getStringExtra("name");
        //传参并调用get方法
        get(name);

    }

    //利用okhttp3的get方法进行网络连接
    private void get(String name) {
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(luckURL(name))
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            //将返回的数据体以string形式放到result中
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String result = response.body().string();
                //网络连接需要放到子线程中执行，不能占据主线程，不然会引起阻塞导致闪退
                runOnUiThread(new Runnable(){
                    public void run(){

                        //如果回传结果不为空
                        if (!result.isEmpty()) {
                            //解析数据，利用gson将json转为java类
                            LuckBean luckBean = new Gson().fromJson(result, LuckBean.class);
                            //为了将数据显示在ListView上将其整理为集合，以luckbean的形式得到
                            addDataToLuck(luckBean);
                            //设置适配器，完成数据到页面的显示样式设置
                            luckLv.setAdapter(new LuckTypeAdapter(LuckActivity.this, luckTypeList));
                        }
                    //
                }
            });}

            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable(){
                    public void run() {
                        //失败抛出异常，用toast提醒用户是网络连接的问题
                        Toast.makeText(LuckActivity.this, "Network Connection Error!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    /*整理需要显示的数据到集合当中*/
    private void addDataToLuck(LuckBean luckBean) {
        LuckTypeBean name = new LuckTypeBean("星座名称", luckBean.getName());
        LuckTypeBean day = new LuckTypeBean("日期", luckBean.getDatetime());
        LuckTypeBean all = new LuckTypeBean("综合指数", luckBean.getAll());
        LuckTypeBean health = new LuckTypeBean("健康指数", luckBean.getHealth());
        LuckTypeBean money = new LuckTypeBean("财运指数", luckBean.getMoney());
        LuckTypeBean love = new LuckTypeBean("爱情指数", luckBean.getLove());
        LuckTypeBean work = new LuckTypeBean("工作指数", luckBean.getWork());
        LuckTypeBean color = new LuckTypeBean("幸运色", luckBean.getColor());
        LuckTypeBean num = new LuckTypeBean("幸运数字", luckBean.getNumber());
        LuckTypeBean qfriend = new LuckTypeBean("速配星座", luckBean.getQFriend());
        LuckTypeBean summary = new LuckTypeBean("今日概述", luckBean.getSummary());

        //添加对应类型数据
        luckTypeList.add(name);
        luckTypeList.add(day);
        luckTypeList.add(all);
        luckTypeList.add(health);
        luckTypeList.add(money);
        luckTypeList.add(love);
        luckTypeList.add(work);
        luckTypeList.add(color);
        luckTypeList.add(num);
        luckTypeList.add(qfriend);
        luckTypeList.add(summary);
    }

    private void initView() {
        //查找控件
        luckLv = findViewById(R.id.luck_lv);
        backIv = findViewById(R.id.luck_iv_back);
        backIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //监听返回按钮返回上一页
            case R.id.luck_iv_back:
                finish();
                break;
        }
    }
}