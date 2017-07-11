package com.ruiduoyi.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ruiduoyi.R;
import com.ruiduoyi.adapter.FailureAnalysisAdapter;
import com.ruiduoyi.adapter.SigleSelectAdapter;
import com.ruiduoyi.model.NetHelper;
import com.ruiduoyi.view.PopupWindowSpinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlfxActivity extends BaseDialogActivity implements View.OnClickListener{
    private Button cancle_btn;
    private Button spinner;//不良产品下拉框
    private ListView listView,listView_register;
    private String jtbh;
    private TextView lab_2,lab_3,lab_4,lab_5,lab_6,lab_7,lab_8,bldm_text,blms_text;
    private Button btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9,btn_0,btn_clear,btn_submit,btn_del;
    private String mod_id,glid;//不良登记表相关、与下拉框相关
    private TextView sub_text;
    private Animation anim;
    private String sub_num;
    private Animation anim_view;
    private String cardId,wkno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blfx);
        initView();
        initData();
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x100:
                    List<List<String>>list_tab=(List<List<String>>)msg.obj;
                    initListView(list_tab);
                    listView.startAnimation(anim);
                    break;
                case 0x101:
                    Toast.makeText(BlfxActivity.this,"服务器异常",Toast.LENGTH_SHORT).show();
                    break;
                case 0x102:
                    List<List<String>>list_lab=(List<List<String>>)msg.obj;
                    if(list_lab.size()<1){
                        Toast.makeText(BlfxActivity.this,"没有数据",Toast.LENGTH_SHORT).show();
                    }else {
                        List<String>item=list_lab.get(0);
                        lab_2.setText(item.get(0));
                        lab_3.setText(item.get(4));
                        lab_4.setText(item.get(5));
                        lab_5.setText(item.get(6));
                        lab_6.setText(item.get(7));
                        lab_7.setText(item.get(2));
                        lab_8.setText(item.get(3));
                        mod_id=item.get(8);
                        new ThreadRight().start();
                        if(item.size()<10){
                            Toast.makeText(BlfxActivity.this,"没有产品可以选取",Toast.LENGTH_SHORT).show();
                        }else {
                            glid=item.get(9);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    List<List<String>>list_cpxq=NetHelper.getQuerysqlResult("PAD_BlfxCpxqDdl '"+glid+"'");
                                    Message msg=handler.obtainMessage();
                                    if (list_cpxq!=null){
                                        msg.what=0x104;
                                        msg.obj=list_cpxq;
                                    }else {
                                        msg.what=0x101;
                                    }
                                    handler.sendMessage(msg);
                                }
                            }).start();
                        }
                    }
                    break;
                case 0x103:
                    List<List<String>>list_register=(List<List<String>>)msg.obj;
                    List<Map<String,String>>data=new ArrayList<>();
                    for (int i=0;i<list_register.size();i++){
                        Map<String,String>map=new HashMap<>();
                        List<String>item=list_register.get(i);
                        map.put("lab_1",item.get(0));
                        map.put("lab_2",item.get(1));
                        map.put("lab_3",item.get(2));
                        map.put("lab_4",item.get(3));
                        data.add(map);
                    }
                    SimpleAdapter adapter=new SimpleAdapter(BlfxActivity.this,data,R.layout.list_item_b8_2,
                            new String[]{"lab_1","lab_2","lab_3","lab_4"},new int[]{R.id.lab_1,R.id.lab_2,
                    R.id.lab_3,R.id.lab_4});
                    listView_register.setAdapter(adapter);
                    listView_register.startAnimation(anim);
                    break;
                case 0x104://初始化
                    List<List<String>>list_spinner=(List<List<String>>)msg.obj;
                    initSpinner(list_spinner);
                    break;
                default:
                    break;
            }
        }
    };


    private void initData(){
        sharedPreferences=getSharedPreferences("info",MODE_PRIVATE);
        jtbh=sharedPreferences.getString("jtbh","");
        thread_tab.start();
        thread_lab.start();
        anim= AnimationUtils.loadAnimation(this,R.anim.sub_num_anim);
        anim_view=AnimationUtils.loadAnimation(this,R.anim.apha_anim);
        Intent intent_from=getIntent();
        cardId=intent_from.getStringExtra("cardId");
        wkno=intent_from.getStringExtra("wkno");
    }

    private void initView(){
        cancle_btn=(Button)findViewById(R.id.cancle_btn);
        spinner=(Button) findViewById(R.id.blcp_spinner);
        listView=(ListView)findViewById(R.id.list_b8);
        sub_text=(TextView)findViewById(R.id.sub_text);
        lab_2=(TextView)findViewById(R.id.lab_2);
        lab_3=(TextView)findViewById(R.id.lab_3);
        lab_4=(TextView)findViewById(R.id.lab_4);
        lab_5=(TextView)findViewById(R.id.lab_5);
        lab_6=(TextView)findViewById(R.id.lab_6);
        lab_7=(TextView)findViewById(R.id.lab_7);
        lab_8=(TextView)findViewById(R.id.lab_8);
        bldm_text=(TextView)findViewById(R.id.bldm_text);
        blms_text=(TextView)findViewById(R.id.blms_text);
        btn_0=(Button)findViewById(R.id.btn_0);
        btn_1=(Button)findViewById(R.id.btn_1);
        btn_2=(Button)findViewById(R.id.btn_2);
        btn_3=(Button)findViewById(R.id.btn_3);
        btn_4=(Button)findViewById(R.id.btn_4);
        btn_5=(Button)findViewById(R.id.btn_5);
        btn_6=(Button)findViewById(R.id.btn_6);
        btn_7=(Button)findViewById(R.id.btn_7);
        btn_8=(Button)findViewById(R.id.btn_8);
        btn_9=(Button)findViewById(R.id.btn_9);
        btn_del=(Button)findViewById(R.id.btn_del);
        btn_submit=(Button)findViewById(R.id.btn_submit);
        btn_clear=(Button)findViewById(R.id.btn_clear);
        listView_register=(ListView)findViewById(R.id.list_b8_2);
        cancle_btn.setOnClickListener(this);
        btn_0.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
        initSpinner(new ArrayList<List<String>>());
    }

    private void initListView(List<List<String>>lists){
        List<Map<String,String>>data=new ArrayList<>();
        for (int i=0;i<lists.size();i++){
            Map<String,String>map=new HashMap<>();
            List<String>item=lists.get(i);
            map.put("lab_1",item.get(0));
            map.put("lab_2",item.get(1));
            map.put("isFocus",1+"");
            data.add(map);
        }
        SigleSelectAdapter adapter1=new SigleSelectAdapter(BlfxActivity.this,data) {
            @Override
            public void onRadioSelectListener(Map<String, String> map) {
                bldm_text.setText(map.get("lab_1"));
                blms_text.setText(map.get("lab_2"));
            }
        };
        FailureAnalysisAdapter adapter=new FailureAnalysisAdapter(BlfxActivity.this,R.layout.list_item_b8,data,bldm_text,blms_text);
        listView.setAdapter(adapter1);
    }


    private void initSpinner(List<List<String>>lists){
        final List<String>data=new ArrayList<>();
        for (int i=0;i<lists.size();i++){
            List<String>item=lists.get(i);
            data.add(item.get(0));
        }
        spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupWindowSpinner popupWindowSpinner=new PopupWindowSpinner(BlfxActivity.this,data,R.layout.spinner_list_b7,R.id.lab_1,450);
                popupWindowSpinner.showDownOn(spinner);
                popupWindowSpinner.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        spinner.setText(data.get(position));
                        popupWindowSpinner.dismiss();
                    }
                });

            }
        });
        /*ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,data);
        spinner.setAdapter(adapter);*/
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancle_btn:
                finish();
                break;
            case R.id.btn_0:
                sub_text.startAnimation(anim);
                sub_num=sub_text.getText().toString();
                if(!sub_num.equals("0")){
                   sub_text.setText(sub_num+"0");
                }
                break;
            case R.id.btn_1:
                sub_text.startAnimation(anim);
                sub_num=sub_text.getText().toString();
                if(!sub_num.equals("0")){
                    sub_text.setText(sub_num+"1");
                }else {
                    sub_text.setText("1");
                }
                break;
            case R.id.btn_2:
                sub_text.startAnimation(anim);
                sub_num=sub_text.getText().toString();
                if(!sub_num.equals("0")){
                    sub_text.setText(sub_num+"2");
                }else {
                    sub_text.setText("2");
                }
                break;
            case R.id.btn_3:
                sub_text.startAnimation(anim);
                sub_num=sub_text.getText().toString();
                if(!sub_num.equals("0")){
                    sub_text.setText(sub_num+"3");
                }else {
                    sub_text.setText("3");
                }
                break;
            case R.id.btn_4:
                sub_text.startAnimation(anim);
                sub_num=sub_text.getText().toString();
                if(!sub_num.equals("0")){
                    sub_text.setText(sub_num+"4");
                }else {
                    sub_text.setText("4");
                }
                break;
            case R.id.btn_5:
                sub_text.startAnimation(anim);
                sub_num=sub_text.getText().toString();
                if(!sub_num.equals("0")){
                    sub_text.setText(sub_num+"5");
                }else {
                    sub_text.setText("5");
                }
                break;
            case R.id.btn_6:
                sub_text.startAnimation(anim);
                sub_num=sub_text.getText().toString();
                if(!sub_num.equals("0")){
                    sub_text.setText(sub_num+"6");
                }else {
                    sub_text.setText("6");
                }
                break;
            case R.id.btn_7:
                sub_text.startAnimation(anim);
                sub_num=sub_text.getText().toString();
                if(!sub_num.equals("0")){
                    sub_text.setText(sub_num+"7");
                }else {
                    sub_text.setText("7");
                }
                break;
            case R.id.btn_8:
                sub_text.startAnimation(anim);
                sub_num=sub_text.getText().toString();
                if(!sub_num.equals("0")){
                    sub_text.setText(sub_num+"8");
                }else {
                    sub_text.setText("8");
                }
                break;
            case R.id.btn_9:
                sub_text.startAnimation(anim);
                sub_num=sub_text.getText().toString();
                if(!sub_num.equals("0")){
                    sub_text.setText(sub_num+"9");
                }else {
                    sub_text.setText("9");
                }
                break;
            case R.id.btn_submit:
               if(bldm_text.getText().toString().equals("")|blms_text.getText().toString().equals("")|sub_text.getText().toString().equals("0")){
                   Toast.makeText(this,"请输入不良信息",Toast.LENGTH_SHORT).show();
               }else {
                   final String bldm=bldm_text.getText().toString();
                   final String sub_num=sub_text.getText().toString();
                   new Thread(new Runnable() {
                       @Override
                       public void run() {
                           List<List<String>>list=NetHelper.getQuerysqlResult("Exec PAD_AddBlmInfo '"+mod_id+"','A','','',\n" +
                                   "'"+jtbh+"','"+bldm+"','"+sub_num+"','"+wkno+"'");
                           if(list==null){
                               Message message=handler.obtainMessage();
                               message.what=0x101;
                               handler.sendMessage(message);
                           }else {
                               new ThreadRight().start();
                           }
                       }
                   }).start();
                   sub_text.setText("0");
                   bldm_text.setText("");
                   blms_text.setText("");
               }
                break;
            case R.id.btn_del:
                sub_text.startAnimation(anim);
                sub_num=sub_text.getText().toString();
                if(sub_num.equals("0")){
                    sub_text.setText("-");
                }
                break;
            case R.id.btn_clear:
                sub_text.startAnimation(anim);
                sub_text.setText("0");
                bldm_text.setText("");
                blms_text.setText("");
                break;
            default:
                break;
        }
    }

    //不良分析表格
    Thread thread_tab=new Thread(new Runnable() {
        @Override
        public void run() {
            List<List<String>>list= NetHelper.getQuerysqlResult("Exec PAD_BlfxTab");
            Message msg=handler.obtainMessage();
            if(list!=null){
                msg.what=0x100;
                msg.obj=list;
            }else {
                msg.what=0x101;
            }
            handler.sendMessage(msg);
        }
    });


    //不良分析文本框
    Thread thread_lab=new Thread(new Runnable() {
        @Override
        public void run() {
            List<List<String>>list= NetHelper.getQuerysqlResult("Exec PAD_BlfxText '"+jtbh+"'");
            Message msg=handler.obtainMessage();
            if(list!=null){
                msg.what=0x102;
                msg.obj=list;
            }else {
                msg.what=0x101;
            }
            handler.sendMessage(msg);
        }
    });
    //右下角的表格

    class ThreadRight extends Thread{
        @Override
        public void run() {
            List<List<String>>list= NetHelper.getQuerysqlResult("Exec PAD_BlfxDjTab '"+mod_id+"'");
            Message msg=handler.obtainMessage();
            if(list!=null){
                msg.what=0x103;
                msg.obj=list;
            }else {
                msg.what=0x101;
            }
            handler.sendMessage(msg);
        }
    }

}
