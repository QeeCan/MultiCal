package com.qeecan.multical.Schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.qeecan.multical.R;
import com.qeecan.multical.Schedule.bean.ScheduleBean;

import java.util.Calendar;

public class ScheduleEditActivity extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    private Button btnTime, btnDate;
    private TextView TimeTv, DateTv;
    private ImageView checkIv;
    private EditText contentEt;
    ScheduleBean sc;
    private int[] dateArray = new int[3];
    private int[] timeArray = new int[2];

    private String old_content = "";
    private String old_time = "";
    private long id = 0;
    private int openModel = 0;


    private boolean timeChange = false;
    Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule);


        init();
        Bundle ob = this.getIntent().getExtras();
        id = ob.getLong("id");
        old_content = ob.getString("content");
        old_time = ob.getString("time");
        openModel = ob.getInt("mode");
        if (openModel == 5) {
            //把内容填充到编辑栏
            contentEt.setText(old_content);
            contentEt.setSelection(old_content.length());
            //用数组装旧时间的切片完成填充
            String[] wholeTime = old_time.split(" ");
            String[] temp = wholeTime[0].split("-");
            String[] temp1 = wholeTime[1].split(":");
            setDateTV(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
            setTimeTV(Integer.parseInt(temp1[0]), Integer.parseInt(temp1[1]));



        }
    }

    private void init() {
        btnTime = findViewById(R.id.alarm_btn_time);
        btnDate = findViewById(R.id.alarm_btn_date);
        TimeTv = findViewById(R.id.alarm_tv_time);
        DateTv = findViewById(R.id.alarm_tv_date);
        checkIv = findViewById(R.id.alarm_edit_iv_check);
        contentEt = findViewById(R.id.alarm_et);
        checkIv.setOnClickListener(onClickListener);
        btnDate.setOnClickListener(onClickListener);
        btnTime.setOnClickListener(onClickListener);
        sc = new ScheduleBean();
        dateArray[0] = sc.getYear();
        dateArray[1] = sc.getMonth() + 1;
        dateArray[2] = sc.getDay();
        timeArray[0] = sc.getHour();
        timeArray[1] = sc.getMinute();
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                setDateTV(year, month + 1, dayOfMonth);

            }
        };
        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                setTimeTV(hourOfDay, minute);
            }
        };
    }

    //日期的显示设置
    private void setDateTV(int y, int m, int d) {
        //update tv and dateArray
        String temp = y + "-";
        if (m < 10) temp += "0";
        temp += (m + "-");
        if (d < 10) temp += "0";
        temp += d;
        DateTv.setText(temp);
        dateArray[0] = y;
        dateArray[1] = m;
        dateArray[2] = d;
    }

    //时间的显示设置
    private void setTimeTV(int h, int m) {
        //update tv and timeArray
        String temp = "";
        if (h < 10) temp += "0";
        temp += (h + ":");
        if (m < 10) temp += "0";
        temp += m;
        TimeTv.setText(temp);
        timeArray[0] = h;
        timeArray[1] = m;
    }


    private boolean canBeSet() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(dateArray[0], dateArray[1] - 1, dateArray[2], timeArray[0], timeArray[1]);
        Calendar cur = Calendar.getInstance();
        if (cur.before(calendar)) return true;
        else {
            Toast.makeText(getApplicationContext(), "无效时间", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.alarm_btn_date://日历选择弹窗
                    DatePickerDialog dialog = new DatePickerDialog(ScheduleEditActivity.this,
                            dateSetListener, dateArray[0], dateArray[1] - 1, dateArray[2]);
                    dialog.show();
                    break;
                case R.id.alarm_btn_time://时间选择弹窗
                    TimePickerDialog dialog1 = new TimePickerDialog(ScheduleEditActivity.this,
                            timeSetListener, timeArray[0], timeArray[1], true);
                    dialog1.show();
                    break;
                case R.id.alarm_edit_iv_check:
                    if (!canBeSet() || contentEt.getText().toString().length() == 0) {
                        Toast.makeText(ScheduleEditActivity.this, "内容或时间有错误，请修改", Toast.LENGTH_LONG).show();
                    } else {
                        //进入模式选择
                        modelSelect();
                        setResult(RESULT_OK, intent);
                        finish();
                    }
            }
        }

    };



    private void modelSelect() {
        if (openModel == 4) {// 新建日程
            Bundle addb = new Bundle();
            addb.putString("content", contentEt.getText().toString());
            addb.putString("time", DateTv.getText().toString() + " " + TimeTv.getText().toString());
            intent.putExtras(addb);

            Toast.makeText(ScheduleEditActivity.this, "新建成功", Toast.LENGTH_LONG).show();

        } else if (openModel == 5) {//修改日程内容
            Bundle upb = new Bundle();
            upb.putString("content", contentEt.getText().toString());
            upb.putString("time", DateTv.getText().toString() + " " + TimeTv.getText().toString());
            //传入id 防止自增新建
            upb.putLong("id", id);
            intent.putExtras(upb);
            Toast.makeText(ScheduleEditActivity.this, "修改成功", Toast.LENGTH_LONG).show();

        } else {
            intent.putExtra("mode", -1);
        }

    }


}


