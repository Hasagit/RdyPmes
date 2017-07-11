package com.ruiduoyi.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ruiduoyi.R;
import com.ruiduoyi.adapter.YichangfenxiAdapter;
import com.ruiduoyi.model.NetHelper;
import com.ruiduoyi.utils.AppUtils;
import com.ruiduoyi.view.PopupWindowSpinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YcfxActivity extends BaseActivity implements View.OnClickListener{
    private Button cancle_btn,sub_btn;
    private ListView listView;
    private Button spinner_2;
    private Button spinner_1;
    private TextView text_1,text_2,text_3,text_4,text_5,text_6,text_7,text_8,text_9,text_10,text_11,text_key;
    private String jtbh;
    String yylb="";
    String yymsId="";
    String yymstext="";
    String wkno="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ycfx);
        initView();
        initData();
    }

    private void initView(){
        cancle_btn=(Button)findViewById(R.id.cancle_btn);
        sub_btn=(Button)findViewById(R.id.sub_btn);
        text_1=(TextView)findViewById(R.id.text_1);
        text_2=(TextView)findViewById(R.id.text_2);
        text_3=(TextView)findViewById(R.id.text_3);
        text_4=(TextView)findViewById(R.id.text_4);
        text_5=(TextView)findViewById(R.id.text_5);
        text_6=(TextView)findViewById(R.id.text_6);
        text_7=(TextView)findViewById(R.id.text_7);
        text_8=(TextView)findViewById(R.id.text_8);
        text_9=(TextView)findViewById(R.id.text_9);
        text_10=(TextView)findViewById(R.id.text_10);
        text_11=(TextView)findViewById(R.id.text_11);
        text_key=(TextView)findViewById(R.id.text_key);
        spinner_1=(Button) findViewById(R.id.spinner_1);
        spinner_2=(Button) findViewById(R.id.spinner_2);
        listView=(ListView)findViewById(R.id.list_b7);
        cancle_btn.setOnClickListener(this);
        sub_btn.setOnClickListener(this);
    }

    private void initData(){
        Intent intent_from=getIntent();
        wkno=intent_from.getStringExtra("wkno");
        sharedPreferences=getSharedPreferences("info",MODE_PRIVATE);
        jtbh=sharedPreferences.getString("jtbh","");
        thread_1.start();
        thread_2.start();
    }

    private void initListView(List<List<String>>list){
        List<Map<String,String>>data=new ArrayList<>();
        for (int i=0;i<list.size();i++){
            List<String>item=list.get(i);
            Map<String,String>map=new HashMap<>();
            map.put("lab_1",item.get(1));
            map.put("lab_2",item.get(2));
            map.put("lab_3",item.get(3));
            map.put("lab_4",item.get(4));
            map.put("lab_5",item.get(5));
            map.put("lab_6",item.get(6));
            map.put("lab_7",item.get(7));
            map.put("lab_8",item.get(8));
            map.put("lab_9",item.get(9));
            map.put("lab_10",item.get(10));
            map.put("lab_11",item.get(11));
            map.put("lab_12",item.get(12));
            if (item.size()>12){
                map.put("lab_13",item.get(13));
            }else {
                map.put("lab_13","");
            }
            data.add(map);
        }
        List<TextView>list_text=new ArrayList<>();
        list_text.add(text_1);
        list_text.add(text_2);
        list_text.add(text_3);
        list_text.add(text_4);
        list_text.add(text_5);
        list_text.add(text_6);
        list_text.add(text_7);
        list_text.add(text_8);
        list_text.add(text_9);
        list_text.add(text_10);
        list_text.add(text_11);
        list_text.add(text_key);
        YichangfenxiAdapter adapter=new YichangfenxiAdapter(YcfxActivity.this,R.layout.list_item_b7,data,list_text);
        listView.setAdapter(adapter);
    }


    private void initSpinner_1(List<List<String>>list, final Button spinner){
        final List<String>data=new ArrayList<>();
        final List<String>lbId=new ArrayList<>();
        for (int i=0;i<list.size();i++){
            List<String>item=list.get(i);
            if (item.size()>0){
                data.add(item.get(1));
                lbId.add(item.get(0));
            }else {
                data.add("");
                lbId.add("");
            }
        }
        spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupWindowSpinner popupWindowSpinner=new PopupWindowSpinner(YcfxActivity.this,data,R.layout.spinner_list_b7,R.id.lab_1,340);
                popupWindowSpinner.showUpOn(spinner);
                popupWindowSpinner.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        if (position>0){
                            yylb=lbId.get(position);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String key=data.get(position).substring(0,3);
                                    List<List<String>>list= NetHelper.getQuerysqlResult("Exec PAD_YcmInfMS '"+key+"'");
                                    Message msg=handler.obtainMessage();
                                    if(list!=null){
                                        msg.what=0x103;
                                        msg.obj=list;
                                    }else {
                                        msg.what=0x101;
                                    }
                                    handler.sendMessage(msg);
                                }
                            }).start();
                        }
                        spinner.setText(data.get(position));
                        popupWindowSpinner.dismiss();
                    }
                });
            }
        });
        /*ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,data);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
               if (position>0){
                   yylb=lbId.get(position);
                   new Thread(new Runnable() {
                       @Override
                       public void run() {
                           String key=data.get(position).substring(0,3);
                           List<List<String>>list= NetHelper.getQuerysqlResult("Exec PAD_YcmInfMS '"+key+"'");
                           Message msg=handler.obtainMessage();
                           if(list!=null){
                               msg.what=0x103;
                               msg.obj=list;
                           }else {
                               msg.what=0x101;
                           }
                           handler.sendMessage(msg);
                       }
                   }).start();
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
    }

    private void initSpinner_2(List<List<String>>list, final Button spinner){
        final List<String>data=new ArrayList<>();
        final List<String>Id=new ArrayList<>();
        for (int i=0;i<list.size();i++){
            List<String>item=list.get(i);
            data.add(item.get(1));
            Id.add(item.get(0));
        }
        spinner.setText(data.get(0));
        spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.sendCountdownReceiver(YcfxActivity.this);
                final PopupWindowSpinner popupWindowSpinner=new PopupWindowSpinner(YcfxActivity.this,data,R.layout.spinner_list_b7,R.id.lab_1,340);
                popupWindowSpinner.showUpOn(spinner);
                popupWindowSpinner.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        yymstext=data.get(position);
                        yymsId=Id.get(position);
                        spinner.setText(data.get(position));
                        popupWindowSpinner.dismiss();
                    }
                });
            }
        });
        /*ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,data);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yymstext=data.get(position);
                yymsId=Id.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x100:
                    List<List<String>>list_1=(List<List<String>>)msg.obj;
                    if (list_1.size()>0){
                        initListView(list_1);
                    }else {
                        Toast.makeText(YcfxActivity.this,"没有数据",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 0x101:
                    Toast.makeText(YcfxActivity.this,"服务器异常",Toast.LENGTH_SHORT).show();
                    break;
                case 0x102://原因类别
                    List<List<String>>list_2=(List<List<String>>)msg.obj;
                    if (list_2.size()>0){
                        initSpinner_1(list_2,spinner_1);

                    }else {
                        Toast.makeText(YcfxActivity.this,"没有数据",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 0x103://原因类别
                    List<List<String>>list_3=(List<List<String>>)msg.obj;
                    if (list_3.size()>0){
                        initSpinner_2(list_3,spinner_2);

                    }else {
                        Toast.makeText(YcfxActivity.this,"没有数据",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 0x104:
                    Toast.makeText(YcfxActivity.this,"操作成功",Toast.LENGTH_SHORT).show();
                    text_1.setText("");
                    text_2.setText("");
                    text_3.setText("");
                    text_4.setText("");
                    text_5.setText("");
                    text_6.setText("");
                    text_7.setText("");
                    text_8.setText("");
                    text_9.setText("");
                    text_10.setText("");
                    text_11.setText("");
                    text_key.setText("");
                    break;
                case 0x105:
                    Toast.makeText(YcfxActivity.this,"请先完善信息",Toast.LENGTH_SHORT).show();
                    break;
                case 0x106:
                    Toast.makeText(YcfxActivity.this,"操作失败",Toast.LENGTH_SHORT).show();
                    /*List<List<String>>list_4=(List<List<String>>)msg.obj;
                    if (list_4.get(0).get(0).equals("No data can be found.")){
                        Toast.makeText(YcfxActivity.this,"操作成功",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(YcfxActivity.this,list_4.get(0).get(0),Toast.LENGTH_SHORT).show();
                    }*/
                    break;

            }
        }
    };


    //请求表格数据
    Thread thread_1=new Thread(new Runnable() {
        @Override
        public void run() {
            List<List<String>>list= NetHelper.getQuerysqlResult("Exec PAD_YcmInf '"+jtbh+"'");
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

    //请求原因类别数据
    Thread thread_2=new Thread(new Runnable() {
        @Override
        public void run() {
            List<List<String>>list= NetHelper.getQuerysqlResult("Exec PAD_YcmInfYY");
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


    @Override
    public void onClick(View v) {
        AppUtils.sendCountdownReceiver(YcfxActivity.this);
        switch (v.getId()){
            case R.id.cancle_btn:
                finish();
                break;
            case R.id.sub_btn:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String[] temp=text_2.getText().toString().split("\t");
                        String zlId=temp[0];
                        String key=text_key.getText().toString();
                        if (zlId.equals("")|yylb.equals("")|yymsId.equals("")|yymstext.equals("")|key.equals("")|wkno.equals("")){
                            handler.sendEmptyMessage(0x105);
                        }else {
                            boolean result= NetHelper.getRunsqlResult("Exec PAD_YcmInfButton" +
                                    " '"+zlId+"','"+yylb+"','"+yymsId+"','"+yymstext+"','"+key+"','"+wkno+"'");
                            Message msg=handler.obtainMessage();
                            if(result){
                                msg.what=0x104;
                            }else {
                                msg.what=0x106;
                            }
                            handler.sendMessage(msg);
                        }
                    }
                }).start();
                break;
            default:
                break;
        }
    }
}
