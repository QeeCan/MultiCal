package com.qeecan.multical.Note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.qeecan.multical.Note.bean.NoteBean;
import com.qeecan.multical.R;
import com.qeecan.multical.Schedule.ScheduleEditActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteEditActivity extends AppCompatActivity {
    private EditText titleEt, contentEt, tagEt;
    private ImageView checkIv;
    private String old_title = "";
    private String old_content = "";
    private String old_tag = "";
    private String old_time = "";
    private long id = 0;
    private int openModel = 0;

    Intent intent = new Intent();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        titleEt = findViewById(R.id.note_et_title);
        contentEt = findViewById(R.id.note_et_content);
        tagEt = findViewById(R.id.note_et_tag);
        checkIv = findViewById(R.id.note_edit_iv_check);
        checkIv.setOnClickListener(onClickListener);

        //getIntent（）接收上个页面传递过来的bundle数据
        Bundle ob = this.getIntent().getExtras();
        id = ob.getLong("id");
        old_title = ob.getString("title");
        old_content = ob.getString("content");
        old_tag = ob.getString("tag");
        old_time = ob.getString("time");
        openModel = ob.getInt("mode");
         // Toast.makeText(getApplicationContext(),"settext"+ob.toString(), Toast.LENGTH_LONG).show();
        if (openModel == 3) {//打开已存在的note
            //把内容填充到编辑栏
            titleEt.setText(old_title);
            contentEt.setText(old_content);
            tagEt.setText(old_tag);
        }

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.note_edit_iv_check) {
                if (contentEt.getText().toString().length() == 0) {
                    Toast.makeText(NoteEditActivity.this, "内容不能为空", Toast.LENGTH_LONG).show();
                } else {
                modelSelect();
                setResult(RESULT_OK, intent);
                finish();
            }
        }

    }};

    private void modelSelect() {
        if (openModel == 2) {// 新建笔记
            Bundle addb = new Bundle();
            addb.putString("title", titleEt.getText().toString());
            addb.putString("content", contentEt.getText().toString());
            addb.putString("tag", tagEt.getText().toString());
            addb.putString("time", dateToStr());
            intent.putExtras(addb);
            Toast.makeText(NoteEditActivity.this, "新建成功", Toast.LENGTH_LONG).show();

        } else if (openModel == 3) {//修改内容
            Bundle upb = new Bundle();
            upb.putString("title", titleEt.getText().toString());
            upb.putString("content", contentEt.getText().toString());
            upb.putString("tag", tagEt.getText().toString());
            upb.putString("time", dateToStr());
            //传入id 防止自增新建
            upb.putLong("id", id);
            intent.putExtras(upb);
            //Toast.makeText(getApplicationContext(),"update3"+upb.toString(),Toast.LENGTH_SHORT).show();
             Toast.makeText(NoteEditActivity.this, "修改成功", Toast.LENGTH_LONG).show();

        } else {
            intent.putExtra("mode", -1);
        }

    }

    public String dateToStr() {
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }


}