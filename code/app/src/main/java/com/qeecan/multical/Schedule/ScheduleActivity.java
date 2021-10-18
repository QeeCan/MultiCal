package com.qeecan.multical.Schedule;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.getbase.floatingactionbutton.FloatingActionButton;

import com.qeecan.multical.R;
import com.qeecan.multical.Schedule.adpter.ScheduleAdapter;
import com.qeecan.multical.Schedule.bean.ScheduleBean;
import com.qeecan.multical.Schedule.db.ScheduleDao;
import com.qeecan.multical.Schedule.db.ScheduleSQLiteHelper;

import java.util.ArrayList;

import java.util.Calendar;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private Context context = this;
    private FloatingActionButton ftn;
    private ScheduleAdapter adapter;
    private ScheduleSQLiteHelper helper;
    private ListView scheduleLv;
    private List<ScheduleBean> scheduleList = new ArrayList<ScheduleBean>();
    private SearchView sv;
    private AlarmManager alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        ftn = findViewById(R.id.fbtn_alarm_add);
        ftn.setOnClickListener(onClickListener);

        //定位显示的ListView
        scheduleLv = findViewById(R.id.alarm_lv);
        adapter = new ScheduleAdapter(getApplicationContext(), scheduleList);
        //创建适配器对象并设施适配器
        refreshListView();
        scheduleLv.setAdapter(adapter);
        scheduleLv.setOnItemClickListener(this);
        scheduleLv.setOnItemLongClickListener(this);
        sv = findViewById(R.id.alarm_sv);
        getSearch();

    }

    /*
     * 查找
     * */

    private void getSearch() {
        sv.setQueryHint("search");
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            /*
             * 输入后提交实现搜索
             * */
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            /*
             * 实时搜索
             * */
            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("shishi", "nizuihaoshi");
                adapter.getFilter().filter(newText);
                return false;

            }
        });
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.fbtn_alarm_add) {

                Intent intent = new Intent();
                intent.setClass(ScheduleActivity.this, ScheduleEditActivity.class);
                intent.putExtra("mode", 4);
                startActivityForResult(intent, 0);
            }

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        if (requestCode == 1 && resultCode == RESULT_OK) {//更新当前日程
            Bundle ub = data.getExtras();
            assert ub != null;
            String content = ub.getString("content");
            String time = ub.getString("time");
            long id = ub.getLong("id");
            ScheduleBean newSche = new ScheduleBean(content, time);
            newSche.setId(id);
            ScheduleDao sd = new ScheduleDao(context);
            sd.open();
            sd.updateSchedule(newSche);
            sd.close();

        } else if (requestCode == 0 && resultCode == RESULT_OK) {//新建笔记
            Bundle b = data.getExtras();
            assert b != null;
            String content = b.getString("content");
            String time = b.getString("time");
            ScheduleBean newSche = new ScheduleBean(content, time);
            ScheduleDao sd = new ScheduleDao(context);
            sd.open();
            sd.addSchedule(newSche);
            sd.close();
        } else {
        }
        refreshListView();

    }

    public void refreshListView() {
        ScheduleDao sd = new ScheduleDao(this);
        sd.open();

        if (scheduleList.size() > 0) {
            cleanAlarms(scheduleList);//删除所有闹钟
            scheduleList.clear();
        }
        scheduleList.addAll(sd.getAllSchedule());
        sendAlarms(scheduleList);
        //Log.d("shialarm", "nizuihaoshi");
        sd.close();
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ScheduleBean sche = (ScheduleBean) parent.getItemAtPosition(position);
        Bundle sb = new Bundle();
        sb.putInt("mode", 5);
        sb.putLong("id", sche.getId());
        sb.putString("content", sche.getContent());
        sb.putString("time", sche.getClock());
        Intent intent1 = new Intent(ScheduleActivity.this, ScheduleEditActivity.class);
        intent1.putExtras(sb);
        startActivityForResult(intent1, 1);

    }


    //设置很多提醒
    private void sendAlarms(List<ScheduleBean> sc) {
        for (int i = 0; i < sc.size(); i++) sendAlarm(sc.get(i));
    }

    private void sendAlarm(ScheduleBean s) {
        Calendar c = Calendar.getInstance();
        if (!s.getTime().before(c)) {
            Intent intent1 = new Intent(this, Alarm.class);
            Bundle b = new Bundle();
            b.putLong("id", s.getId());
            b.putString("content", s.getContent());
            intent1.putExtras(b);
            // Toast.makeText(getApplicationContext(), b.toString(), Toast.LENGTH_LONG).show();
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 2, intent1, 0);
            alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
            c.set(Calendar.HOUR_OF_DAY, s.getHour());
            c.set(Calendar.MINUTE, s.getMinute());
            c.set(Calendar.SECOND, 0);
            assert alarm != null;
            alarm.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
            //Toast.makeText(getApplicationContext(), "闹钟设置成功", Toast.LENGTH_LONG).show();

        }
    }


    //取消很多提醒
    private void cleanAlarms(List<ScheduleBean> sc) {
        for (int i = 0; i < sc.size(); i++) cleanAlarm(sc.get(i));
    }

    //取消提醒
    private void cleanAlarm(ScheduleBean s) {
        Intent intent1 = new Intent(this, Alarm.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 2, intent1, 0);
        assert alarm != null;
        alarm.cancel(pendingIntent);

    }

    /*
     * 设置listview的长按事件，进行删除操作
     * */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        final ScheduleBean sch = scheduleList.get(position);
        new AlertDialog.Builder(ScheduleActivity.this)
                .setMessage("确定删除该条记录?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ScheduleDao op = new ScheduleDao(context);
                        op.open();
                        op.removeSchedule(sch);
                        op.close();
                        refreshListView();
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
        return true;
    }

}