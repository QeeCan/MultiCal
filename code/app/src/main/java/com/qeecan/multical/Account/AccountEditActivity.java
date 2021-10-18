package com.qeecan.multical.Account;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.qeecan.multical.Account.adapter.TypeAdapter;
import com.qeecan.multical.Account.bean.TypeBean;

import com.qeecan.multical.Note.NoteEditActivity;
import com.qeecan.multical.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountEditActivity extends AppCompatActivity {
    GridView gv;
    ImageView checkIv, typeIv;
    TextView typeTv;
    EditText moneyEt, remarkEt;
    List<TypeBean> typeBeanList;
    private String old_typename = "";
    private float old_money;
    private String old_remark = "";
    private String old_time = "";
    private int old_kind;
    private long id = 0;
    private int openModel = 0;
    private int kind = -1;
    private int clickPos = 0;
    Intent intent = new Intent();

    int[] imageId = {R.drawable.i_qt_c, R.drawable.i_gz_c, R.drawable.i_jj_c, R.drawable.i_jr_c,
            R.drawable.i_lx_c, R.drawable.i_tz_c, R.drawable.i_es_c, R.drawable.i_yw_c,
            R.drawable.o_qt_c, R.drawable.o_cy_c, R.drawable.o_fs_c, R.drawable.o_jt_c,
            R.drawable.o_ryp_c, R.drawable.o_ls_c, R.drawable.o_w_c, R.drawable.o_xx_c,
            R.drawable.o_yj_c, R.drawable.o_yl_c, R.drawable.o_zz_c, R.drawable.o_tx_c};
    String[] typename = {"其他收入", "工资", "奖金", "借入", "利息", "投资", "二手交易", "意外",
            "其他支出", "餐饮", "服饰", "交通", "日用品", "零食", "娱乐",
            "学习", "烟酒", "医疗", "住宅", "通讯"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        gv = findViewById(R.id.account_gv);
        checkIv = findViewById(R.id.account_edit_iv_check);
        moneyEt = findViewById(R.id.account_et_money);
        remarkEt = findViewById(R.id.account_tv_rm);
        typeIv = findViewById(R.id.account_iv_type);
        typeTv = findViewById(R.id.account_tv_type);
        checkIv.setOnClickListener(onClickListener);

        //创建适配器
        typeBeanList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            typeBeanList.add(new TypeBean(typename[i], imageId[i], 0));
        }
        for (int j = 8; j < 20; j++) {
            typeBeanList.add(new TypeBean(typename[j], imageId[j], 1));
        }
        //设置适配器
        gv.setAdapter(new TypeAdapter(this, typeBeanList));
        setGridListener();

        Bundle ob = this.getIntent().getExtras();
        id = ob.getLong("id");
        old_typename = ob.getString("typename");
        old_money = ob.getFloat("money", 0);
        old_remark = ob.getString("remark");
        old_time = ob.getString("time");
        old_kind = ob.getInt("kind");
        openModel = ob.getInt("mode");
        if (openModel == 7) {//打开已存在的account

            //把内容填充到编辑栏
            typeTv.setText(old_typename);
            getClickPos(old_typename);
            typeIv.setImageResource(imageId[clickPos]);
            moneyEt.setText(String.valueOf(old_money));
            remarkEt.setText(old_remark);

        }
    }
    /*
    *
    * 根据存的name获得其位置进行对应图片的设置
    * */

    public int getClickPos(String oldname) {
        for(int i =0;i<typename.length;i++){
            if(typename[i].equals(oldname)){
                clickPos = i;
            }

        }
        return clickPos;
    }

    private void setGridListener() {
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TypeBean typeBean = typeBeanList.get(position);
                //得到名字
                String tname = typeBean.getTypename();
                typeTv.setText(tname);
                //得到图片
                int cimage = typeBean.getCimageId();
                typeIv.setImageResource(cimage);

                if (position > 7) {
                    kind = 0;
                } else {
                    kind =1;
                }}


        });

    }



    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.account_edit_iv_check) {
                if (moneyEt.getText().toString().length() == 0) {
                    Toast.makeText(AccountEditActivity.this, "钱数不能为空", Toast.LENGTH_LONG).show();
                } else {
                    modelSelect();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }

        }
    };

    private void modelSelect() {
        if (openModel == 6) {//新建
            Bundle addb = new Bundle();
            addb.putString("typename", typeTv.getText().toString());
            addb.putFloat("money", getMoney());
            addb.putString("remark", remarkEt.getText().toString());
            addb.putString("time", dateToStr());
            addb.putInt("kind", (int) kind);
            intent.putExtras(addb);
         //   Toast.makeText(AccountEditActivity.this, addb.toString(), Toast.LENGTH_LONG).show();
            Toast.makeText(AccountEditActivity.this, "新建成功", Toast.LENGTH_LONG).show();
        } else if (openModel == 7) {
            Bundle upb = new Bundle();
            upb.putString("typename", typeTv.getText().toString());
            upb.putFloat("money", getMoney());
            upb.putString("remark", remarkEt.getText().toString());
            upb.putString("time", dateToStr());
            upb.putInt("kind", (int) kind);
            upb.putLong("id", id);
            intent.putExtras(upb);
         //   Toast.makeText(AccountEditActivity.this, upb.toString(), Toast.LENGTH_LONG).show();
            Toast.makeText(AccountEditActivity.this, "修改成功", Toast.LENGTH_LONG).show();
        } else {
            intent.putExtra("mode", -1);
        }

    }

    private float getMoney() {
        return Float.parseFloat(moneyEt.getText().toString());
    }


    public String dateToStr() {
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }


}
