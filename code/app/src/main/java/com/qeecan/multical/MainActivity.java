package com.qeecan.multical;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.qeecan.multical.Constellation.ConsActivity;
import com.qeecan.multical.Note.RecActivity;
import com.qeecan.multical.Schedule.ScheduleActivity;
import com.qeecan.multical.Schedule.bean.ScheduleBean;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private ImageView imgbtn1,imgbtn2,imgbtn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgbtn1=(ImageView)findViewById(R.id.imgbtn_cal);
        imgbtn2=(ImageView)findViewById(R.id.imgbtn_rec);
        imgbtn3=(ImageView)findViewById(R.id.imgbtn_cons);


        imgbtn1.setImageResource(R.drawable.bb1);
        imgbtn2.setImageResource(R.drawable.bb2);
        imgbtn3.setImageResource(R.drawable.bb3);

        imgbtn1.setOnClickListener(onClickListener);
        imgbtn2.setOnClickListener(onClickListener);
        imgbtn3.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener=new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            switch (v.getId()) {
                case R.id.imgbtn_cal:
                    intent.setClass(MainActivity.this, CalActivity.class);
                    break;
                case R.id.imgbtn_rec:
                    intent.setClass(MainActivity.this, RecActivity.class);
                    break;
                case R.id.imgbtn_cons:
                    intent.setClass(MainActivity.this, ConsActivity.class);
                    break;
            }
            startActivity(intent);
        }
    };


}