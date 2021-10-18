package com.qeecan.multical.Schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.qeecan.multical.R;
import com.qeecan.multical.Schedule.bean.ScheduleBean;

public class Alarm extends AppCompatActivity {
    private long id;
    private String alarm_content="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_alarm);

        Bundle alb = this.getIntent().getExtras();
        id = alb.getLong("id");
        alarm_content = alb.getString("content");
        Toast.makeText(getApplicationContext(),alb.toString(),Toast.LENGTH_LONG).show();

        AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setIcon(R.drawable.ic_baseline_access_alarms_24);
        alert.setTitle("日程提醒");
        alert.setMessage(alarm_content);
        alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        });
        alert.show();
    }
}