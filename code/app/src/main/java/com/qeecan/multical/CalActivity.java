package com.qeecan.multical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.othershe.calendarview.bean.DateBean;
import com.othershe.calendarview.listener.OnPagerChangeListener;
import com.othershe.calendarview.listener.OnSingleChooseListener;
import com.othershe.calendarview.utils.CalendarUtil;
import com.othershe.calendarview.weiget.CalendarView;
import com.qeecan.multical.Schedule.ScheduleActivity;
import com.qeecan.multical.Schedule.ScheduleEditActivity;


public class CalActivity extends AppCompatActivity {
    private CalendarView calendarView;
    private TextView chooseDate, title;
    private FloatingActionButton fbtn_alarm_today,  fbtn_alarm_all;
    //将当前的日期存放到数组中
    private int[] cDate = CalendarUtil.getCurrentDate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal);

        title = (TextView) findViewById(R.id.title);
        chooseDate = findViewById(R.id.choose_date);
        calendarView = (CalendarView) findViewById(R.id.calview);
        fbtn_alarm_today = (FloatingActionButton) findViewById(R.id.fbtn_today);
        fbtn_alarm_all = (FloatingActionButton) findViewById(R.id.fbtn_alarm_all);
        fbtn_alarm_today.setOnClickListener(onClickListener);
        fbtn_alarm_all.setOnClickListener(onClickListener);
        //初始化日历起始范围
        calendarView.setStartEndDate("2016.1", "2028.12")
                //禁用范围
                .setDisableStartEndDate("2015.10.10", "2029.10.10")
                //初始显示的年月
                .setInitDate(cDate[0] + "." + cDate[1])
                //单选时默认的选中日期
                .setSingleDate(cDate[0] + "." + cDate[1] + "." + cDate[2])
                //根据设定的日期范围计算日历的页数
                .init();

        //显示日历头的年月
        title.setText(cDate[0] + "年" + cDate[1] + "月");
        chooseDate.setText("当前选中的日期：" + cDate[0] + "年" + cDate[1] + "月" + cDate[2] + "日");
        //设置月份切换的显示
        calendarView.setOnPagerChangeListener(new OnPagerChangeListener() {
            @Override
            public void onPagerChanged(int[] date) {
                title.setText(date[0] + "年" + date[1] + "月");
            }
        });

        calendarView.setOnSingleChooseListener(new OnSingleChooseListener() {
            @Override
            public void onSingleChoose(View view, DateBean date) {
                //如果当前类型为本月
                if (date.getType() == 1) {
                    chooseDate.setText("当前选中的日期：" + date.getSolar()[0] + "年" + date.getSolar()[1] + "月" + date.getSolar()[2] + "日");
                }

            }
        });

    }

    //监听几个floatingactionbutton按钮进行跳转操作
    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            switch (v.getId()) {
                case R.id.fbtn_today:
                    calendarView.today();
                    chooseDate.setText("当前选中的日期：" + cDate[0] + "年" + cDate[1] + "月" + cDate[2] + "日");
                    break;
                case R.id.fbtn_alarm_all:
                    intent.setClass(CalActivity.this, ScheduleActivity.class);
                    startActivity(intent);
                    break;

            }

        }
    };
}