package com.qeecan.multical.Constellation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.qeecan.multical.Constellation.adapter.ConsAdapter;
import com.qeecan.multical.Constellation.bean.ConsBean;
import com.qeecan.multical.R;

import java.util.ArrayList;
import java.util.List;

public class ConsActivity extends AppCompatActivity {
    private GridView consGv;
    private List<ConsBean> consList;

    //存放相关文字和图片资源的数组
    int[] consimageId = {R.drawable.cons1, R.drawable.cons2, R.drawable.cons3, R.drawable.cons4,
            R.drawable.cons5, R.drawable.cons6, R.drawable.cons7, R.drawable.cons8,
            R.drawable.cons9, R.drawable.cons10, R.drawable.cons11, R.drawable.cons12};
    String[] consnames = {"白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座",
            "天秤座", "天蝎座", "射手座", "摩羯座", "水瓶座", "双鱼座"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cons);

        //获取数据源
        consList = new ArrayList<>();
        //遍历获取两个数组中的资源，载入相应的图和文字
        for (int i = 0; i < 12; i++) {
            consList.add(new ConsBean(consimageId[i], consnames[i]));
        }
        //查找控件
        consGv = findViewById(R.id.cons_gv);
        //创建适配器对象并设施适配器
        consGv.setAdapter(new ConsAdapter(this, consList));
        //设置GridView每一项的监听
        setGridListener();
    }

    //设置监听事件，在点击对应位置时完成名字的传递
    private void setGridListener() {
        consGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //得到点击位置
                ConsBean consBean=consList.get(position);
                //得到名字
                String name = consBean.getConsname();
               // Toast.makeText(ConsActivity.this, name,Toast.LENGTH_LONG).show();
                //intent传递参数name
                Intent intent = new Intent(ConsActivity.this,LuckActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });
    }
}