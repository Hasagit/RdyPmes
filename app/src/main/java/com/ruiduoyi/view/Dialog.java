package com.ruiduoyi.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.ruiduoyi.R;
import com.ruiduoyi.activity.BaseDialogActivity;

public class Dialog extends BaseDialogActivity{
    private String msg_str,title_str,ok_str,cancle_str;
    private TextView msg_text,title_text;
    private Button ok_btn,cancle_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        initData();
        initView();
    }


    private void initView(){
        ok_btn=(Button) findViewById(R.id.ok_btn);
        cancle_btn=(Button)findViewById(R.id.cancle_btn);
        title_text=(TextView)findViewById(R.id.title);
        msg_text=(TextView)findViewById(R.id.ok_btn);
        ok_btn.setText(ok_str);
        cancle_btn.setText(cancle_str);
        title_text.setText(title_str);
        msg_text.setText(msg_str);
    }

    private void initData(){
        Intent intent_from=getIntent();
        msg_str=intent_from.getStringExtra("msg");
        title_str=intent_from.getStringExtra("title");
        ok_str=intent_from.getStringExtra("ok");
        cancle_str=intent_from.getStringExtra("cancle");
        if (msg_str==null){
            msg_str="";
        }
        if (title_str==null){
            title_str="";
        }
        if (ok_str==null){
            ok_str="";
        }
        if (cancle_str==null){
            cancle_str="";
        }
    }

}
