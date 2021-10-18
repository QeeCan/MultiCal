
package com.qeecan.multical.Note;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;


import com.getbase.floatingactionbutton.FloatingActionButton;
import com.qeecan.multical.Account.AccountActivity;
import com.qeecan.multical.Note.adapter.NoteAdapter;
import com.qeecan.multical.Note.bean.NoteBean;
import com.qeecan.multical.Note.db.NoteDao;
import com.qeecan.multical.Note.db.NoteSQLiteHelper;
import com.qeecan.multical.R;

import java.util.ArrayList;
import java.util.List;


public class RecActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private FloatingActionButton fbtn_to_Account, fbtn_note_add;
    private NoteAdapter adapter;
    private NoteSQLiteHelper helper;
    private ListView noteLv;
    private List<NoteBean> noteList = new ArrayList<NoteBean>();
    private Context context = this;
    SearchView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec);
        //悬浮按钮定位及设置监听
        fbtn_to_Account = findViewById(R.id.fbtn_to_Account);
        fbtn_note_add = findViewById(R.id.fbtn_note_add);
        fbtn_to_Account.setOnClickListener(onClickListener);
        fbtn_note_add.setOnClickListener(onClickListener);


        //查找控件
        noteLv = findViewById(R.id.note_lv);
        //创建适配器对象
        adapter = new NoteAdapter(getApplicationContext(), noteList);
        //刷新
        refreshListView();
        //设施适配器
        noteLv.setAdapter(adapter);
        //设置点击事件和长按事件的监听
        noteLv.setOnItemClickListener(this);
        noteLv.setOnItemLongClickListener(this);
        sv = findViewById(R.id.note_sv);
        getSearch();
    }

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
                //Log.d("shishi","nizuihaoshi");
                adapter.getFilter().filter(newText);
                return false;

            }
        });

    }


    /*
     * 按钮控件的跳转监听
     * */
    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.fbtn_to_Account:
                    Intent intent = new Intent();
                    intent.setClass(RecActivity.this, AccountActivity.class);
                    startActivity(intent);
                    break;
                case R.id.fbtn_note_add:
                    Intent intent1 = new Intent();
                    intent1.setClass(RecActivity.this, NoteEditActivity.class);
                    intent1.putExtra("mode", 2);
                    startActivityForResult(intent1, 0);
                    break;
            }

        }
    };

    /*
     * 回传显示笔记相关信息，接收回传的消息判断是更新还是新建
     * */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        if (requestCode == 1 && resultCode == RESULT_OK) {//更新当前笔记
            Bundle ub = data.getExtras();
            assert ub != null;
            //Toast.makeText(this,"?"+ub,Toast.LENGTH_SHORT).show();
            String title = ub.getString("title");
            String content = ub.getString("content");
            String tag = ub.getString("tag");
            String time = ub.getString("time");
            //得到id信息
            long id = ub.getLong("id");
            NoteBean newNote = new NoteBean(title, content, tag, time);
            //保证更改内容为数据库中对应id的内容
            newNote.setId(id);
            NoteDao nd = new NoteDao(context);
            nd.open();
            nd.updateNote(newNote);
            nd.close();

        } else if (requestCode == 0 && resultCode == RESULT_OK) {//新建笔记
            Bundle b = data.getExtras();
            //  Toast.makeText(this, "note" + b, Toast.LENGTH_LONG).show();
            assert b != null;
            String title = b.getString("title");
            String content = b.getString("content");
            String tag = b.getString("tag");
            String time = b.getString("time");
            NoteBean note = new NoteBean(title, content, tag, time);
            NoteDao nd = new NoteDao(context);
            nd.open();
            nd.addNote(note);
            nd.close();
        } else {
        }
        refreshListView();
    }


    /*
     * 列表刷新，使用户能实时看到更新过的内容
     * */
    public void refreshListView() {
        NoteDao nd = new NoteDao(context);
        nd.open();

        if (noteList.size() > 0) noteList.clear();
        noteList.addAll(nd.getAllNotes());
        nd.close();
        adapter.notifyDataSetChanged();

    }

    /*
     * 设置listview的点击事件，能进行更改操作
     * */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        NoteBean cNote = (NoteBean) parent.getItemAtPosition(position);
        Bundle cb = new Bundle();

        cb.putInt("mode",3);
        cb.putLong("id",cNote.getId());
        cb.putString("title",cNote.getTitle());
        cb.putString("content",cNote.getContent());
        cb.putString("tag",cNote.getTag());
        cb.putString("time",cNote.getTime());
        cb.putLong("id",cNote.getId());

        Intent intent = new Intent(RecActivity.this, NoteEditActivity.class);
        intent.putExtras(cb);
     //Toast.makeText(getApplicationContext(),cb.toString(),Toast.LENGTH_LONG).show();
        startActivityForResult(intent, 1);//收到回传码为1，收集更改过的数据
    }


    /*
     * 设置listview的长按事件，进行删除操作
     * */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        final NoteBean note = noteList.get(position);
        new AlertDialog.Builder(RecActivity.this)
                .setMessage("确定删除该条记录?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NoteDao op = new NoteDao(context);
                        op.open();
                        op.removeNote(note);
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


