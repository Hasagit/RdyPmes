package com.ruiduoyi.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ruiduoyi.R;
import com.ruiduoyi.adapter.EasyArrayAdapter;
import com.ruiduoyi.adapter.WorkOrderAdapter;
import com.ruiduoyi.model.NetHelper;
import com.ruiduoyi.utils.AppUtils;
import com.ruiduoyi.view.PopupDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HddjActivity extends BaseActivity implements View.OnClickListener{
    private Button cancle_btn,btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9,btn_0,btn_clear,btn_submit;
    private ListView listView_gd,listView_hd;
    private TextView zzdh_text,gddh_text,scph_text,cpbh_text,pmgg_text,jhsl_text,ksdu_text,
            jsds_text,sub_text;
    private Handler handler;
    private String sub_num;
    private Animation anim;
    private String wkno;
    private PopupDialog dialog,delTipDialog;
    private EasyArrayAdapter adapter_mx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hddj);
        initData();
        initView();
    }

    private void initView(){
        gddh_text=(TextView)findViewById(R.id.gddh);
        zzdh_text=(TextView)findViewById(R.id.zzdh);
        scph_text=(TextView)findViewById(R.id.scph);
        cpbh_text=(TextView)findViewById(R.id.cpbh);
        pmgg_text=(TextView)findViewById(R.id.pmgg);
        jhsl_text=(TextView)findViewById(R.id.jhsl);
        ksdu_text=(TextView)findViewById(R.id.ksds);
        jsds_text=(TextView)findViewById(R.id.jsds);
        listView_gd=(ListView)findViewById(R.id.list_1);
        listView_hd=(ListView)findViewById(R.id.list_2);


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
        btn_submit=(Button)findViewById(R.id.btn_submit);
        btn_clear=(Button)findViewById(R.id.btn_clear);
        cancle_btn=(Button)findViewById(R.id.cancle_btn);


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
        btn_submit.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
        ksdu_text.setOnClickListener(this);
        jsds_text.setOnClickListener(this);

        sub_text=ksdu_text;

        dialog=new PopupDialog(this,400,360);
        dialog.setTitle("提示");
        dialog.getCancle_btn().setVisibility(View.GONE);
        dialog.getOkbtn().setText("确定");
        dialog.setCancelable(true);
        dialog.getOkbtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.sendCountdownReceiver(HddjActivity.this);
                dialog.dismiss();
            }
        });
        delTipDialog=new PopupDialog(this,400,360);
        delTipDialog.setTitle("提示");
        delTipDialog.getCancle_btn().setText("取消");
        delTipDialog.getOkbtn().setText("确定");

    }


    private void initData(){
        anim= AnimationUtils.loadAnimation(this,R.anim.sub_num_anim);
        Intent intent_from=getIntent();
        wkno=intent_from.getStringExtra("wkno");


        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0x100:
                        List<List<String>>list=(List<List<String>>)msg.obj;
                        if (list.get(0).size()>5){
                            initListView(list);
                        }
                        break;
                    case 0x101:
                        initHdmxListView((List<List<String>>) msg.obj);
                        break;
                    case 0x102:
                        List<List<String>>list_zh= (List<List<String>>) msg.obj;
                        if (list_zh.get(0).size()>1){
                            if (!list_zh.get(0).get(0).equals("0")){
                                ksdu_text.setText(list_zh.get(0).get(0));
                                jsds_text.setText(list_zh.get(0).get(1));
                                ksdu_text.setEnabled(false);
                                ksdu_text.setBackgroundColor(Color.WHITE);
                                jsds_text.setBackgroundColor(getResources().getColor(R.color.small));
                                sub_text=jsds_text;
                            }else {
                                ksdu_text.setText(list_zh.get(0).get(0));
                                jsds_text.setText(list_zh.get(0).get(1));
                                ksdu_text.setEnabled(true);
                            }
                        }
                        break;
                    case 0x103:
                        getHdmxData(zzdh_text.getText().toString(),"A",0x101);
                        /*dialog.setMessageTextColor(Color.BLACK);
                        dialog.setMessage("提交成功");
                        dialog.show();*/
                        break;
                    case 0x104:
                        dialog.setMessageTextColor(Color.RED);
                        dialog.setMessage((String) msg.obj);
                        dialog.show();
                        break;
                    case 0x105:
                        getHdmxData(zzdh_text.getText().toString(),"A",0x101);
                        /*dialog.setMessageTextColor(Color.BLACK);
                        dialog.setMessage("删除成功");
                        dialog.show();*/
                        break;
                }
            }
        };

        getNetData();
    }


    private void getNetData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<List<String>>list= NetHelper.getQuerysqlResult("Exec PAD_Get_MoeDet 'A','"+jtbh+"'");
                if (list!=null){
                    if (list.size()>0){
                        if (list.get(0).size()>13){
                            Message msg=handler.obtainMessage();
                            msg.what=0x100;
                            msg.obj=list;
                            handler.sendMessage(msg);
                        }
                    }
                }else {
                    AppUtils.uploadNetworkError("Exec PAD_Get_MoeDet",jtbh,sharedPreferences.getString("mac",""));
                }
            }
        }).start();
    }


    private void  initListView(List<List<String>>lists){
        final List<Map<String,String>>data=new ArrayList<>();
        for (int i=0;i<lists.size();i++){
            List<String>item=lists.get(i);
            Map<String,String>map=new HashMap<>();
            map.put("moeid",item.get(0));
            map.put("scrq",item.get(1));
            map.put("scxh",item.get(2));
            map.put("zzdh",item.get(3));
            map.put("sodh",item.get(4));
            map.put("ph",item.get(5));
            map.put("mjbh",item.get(6));
            map.put("mjmc",item.get(7));
            map.put("wldm",item.get(8));
            map.put("pmgg",item.get(9));
            map.put("wgrq",item.get(10));
            map.put("scsl",item.get(11));
            map.put("lpsl",item.get(12));
            map.put("ztbz",item.get(13));
            data.add(map);
        }
        EasyArrayAdapter adapter=new EasyArrayAdapter(HddjActivity.this,R.layout.list_item_hddj_gd,data) {
            @Override
            public View getEasyView(int position, View convertView, ViewGroup parent) {
                View view;
                if (convertView!=null){
                    view=convertView;
                }else {
                    view= LayoutInflater.from(HddjActivity.this).inflate(R.layout.list_item_hddj_gd,null);
                }
                final Map<String,String>map=data.get(position);
                final TextView lab_4=(TextView)view.findViewById(R.id.lab_4);
                TextView lab_5=(TextView)view.findViewById(R.id.lab_5);
                TextView lab_6=(TextView)view.findViewById(R.id.lab_6);
                TextView lab_7=(TextView)view.findViewById(R.id.lab_7);
                TextView lab_8=(TextView)view.findViewById(R.id.lab_8);
                TextView lab_9=(TextView)view.findViewById(R.id.lab_9);
                TextView lab_10=(TextView)view.findViewById(R.id.lab_10);
                TextView lab_11=(TextView)view.findViewById(R.id.lab_11);
                TextView lab_12=(TextView)view.findViewById(R.id.lab_12);
                TextView lab_13=(TextView)view.findViewById(R.id.lab_13);
                TextView lab_14=(TextView)view.findViewById(R.id.lab_14);
                TextView lab_15=(TextView)view.findViewById(R.id.lab_15);
                LinearLayout backgroup=(LinearLayout)view.findViewById(R.id.bg);
                final Button select_btn=(Button)view.findViewById(R.id.select_btn);
                final int index=position+1;
                select_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppUtils.sendCountdownReceiver(getContext());
                        zzdh_text.setText(map.get("zzdh"));
                        gddh_text.setText(map.get("sodh"));
                        scph_text.setText(map.get("ph"));
                        cpbh_text.setText(map.get("wldm"));
                        pmgg_text.setText(map.get("pmgg"));
                        jhsl_text.setText(map.get("scsl"));
                        getHdmxData(map.get("zzdh"),"A",0x101);
                        getHdmxData(map.get("zzdh"),"B",0x102);

                    }
                });
                lab_4.setText(map.get("scrq"));
                lab_5.setText(map.get("scxh"));
                lab_6.setText(map.get("zzdh"));
                lab_7.setText(map.get("sodh"));
                lab_8.setText(map.get("ph"));
                lab_9.setText(map.get("mjbh"));
                lab_10.setText(map.get("mjmc"));
                lab_11.setText(map.get("wldm"));
                lab_12.setText(map.get("pmgg"));
                lab_13.setText(map.get("wgrq"));
                lab_14.setText(map.get("scsl"));
                lab_15.setText(map.get("lpsl"));

                return view;
            }
        };
        listView_gd.setAdapter(adapter);
    }

    private void getHdmxData(final String zzdh, final String type, final int what){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<List<String>>list=NetHelper.getQuerysqlResult("Exec PAD_Get_DllInf '"+type+"','"+zzdh+"'");
                if (list!=null){
                    if (list.size()>0){
                            Message msg=handler.obtainMessage();
                            msg.what=what;
                            msg.obj=list;
                            handler.sendMessage(msg);
                    }
                    if (what==0x101){
                        Message msg=handler.obtainMessage();
                        msg.what=what;
                        msg.obj=list;
                        handler.sendMessage(msg);
                    }
                }
            }
        }).start();
    }


    private void initHdmxListView(List<List<String>>lists){
        final List<Map<String,String>>data=new ArrayList<>();
        for (int i=0;i<lists.size();i++){
            if (lists.get(0).size()<7){
                break;
            }
            Map<String,String>map=new HashMap<>();
            map.put("lab_1",lists.get(i).get(1));//机台编号
            map.put("lab_2",lists.get(i).get(2));//开始度数
            map.put("lab_3",lists.get(i).get(3));//终止度数
            map.put("zzdh",lists.get(i).get(0));//制造单号
            map.put("lab_4",lists.get(i).get(4));//耗电度数
            map.put("id",lists.get(i).get(5));//id
            map.put("lab_5",lists.get(i).get(6));
            map.put("lab_6",lists.get(i).get(7));
            data.add(map);
        }
        adapter_mx=new EasyArrayAdapter(this,R.layout.list_item_hddj_mx,data) {
            @Override
            public View getEasyView(int position, View convertView, ViewGroup parent) {
                View view;
                if (convertView!=null){
                    view=convertView;
                }else {
                    view=LayoutInflater.from(HddjActivity.this).inflate(R.layout.list_item_hddj_mx,null);
                }
                Map<String,String>map=data.get(position);
                TextView lab_1=(TextView)view.findViewById(R.id.lab_1);
                final TextView lab_2=(TextView)view.findViewById(R.id.lab_2);
                final TextView lab_3=(TextView)view.findViewById(R.id.lab_3);
                TextView lab_4=(TextView)view.findViewById(R.id.lab_4);
                TextView lab_5=(TextView)view.findViewById(R.id.lab_5);
                TextView lab_6=(TextView)view.findViewById(R.id.lab_6);
                Button del_btn=(Button)view.findViewById(R.id.del_btn);
                lab_1.setText(map.get("lab_1"));
                lab_2.setText(map.get("lab_2"));
                lab_3.setText(map.get("lab_3"));
                lab_4.setText(map.get("lab_4"));
                lab_5.setText(map.get("lab_5"));
                lab_6.setText(map.get("lab_6"));

                del_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AppUtils.sendCountdownReceiver(HddjActivity.this);
                        delData(lab_2.getText().toString(),lab_3.getText().toString());
                    }
                });
                return view;
            }
        };
        listView_hd.setAdapter(adapter_mx);

    }


    private boolean isReady(){
        if (zzdh_text.getText().toString().equals("")){
            dialog.setMessageTextColor(Color.RED);
            dialog.setMessage("请先选择工单信息");
            dialog.show();
            return false;
        }
        if (ksdu_text.getText().toString().equals("")|jsds_text.getText().toString().equals("")){
            dialog.setMessageTextColor(Color.RED);
            dialog.setMessage("请先输入开始和结束度数");
            dialog.show();
            return false;
        }
        if ((Integer.parseInt(jsds_text.getText().toString())<=Integer.parseInt(ksdu_text.getText().toString()))
                &&!jsds_text.getText().toString().equals("0")){
            dialog.setMessageTextColor(Color.RED);
            dialog.setMessage("结束度数一定要大于开始度数");
            dialog.show();
            return false;
        }
        return true;
    }

    private void delData(final String ksds, final String jsds){
        delTipDialog.setMessage("确定要删除这条记录？\n"+"开始度数:"+ksds+"\n结束度数:"+jsds);
        delTipDialog.getOkbtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delTipDialog.dismiss();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<List<String>>list=NetHelper.getQuerysqlResult("Exec  PAD_Up_Dlllist " +
                                "  'B','"+zzdh_text.getText().toString()+"','"+jtbh+"',"+ksds+"," +
                                jsds+",'"+wkno+"'");
                        if (list!=null){
                            if (list.size()>0){
                                if (list.get(0).size()>0){
                                    if (list.get(0).get(0).equals("OK")){
                                        handler.sendEmptyMessage(0x105);
                                    }else {
                                        Message msg=handler.obtainMessage();
                                        msg.what=0x104;
                                        msg.obj=list.get(0).get(0);
                                        handler.sendMessage(msg);
                                    }
                                }
                            }
                        }else {
                            AppUtils.uploadNetworkError("Exec  PAD_Up_Dlllist 'A'",jtbh,sharedPreferences.getString("mac",""));
                        }
                    }
                }).start();
            }
        });
        delTipDialog.show();

    }


    private void addData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<List<String>>list=NetHelper.getQuerysqlResult("Exec  PAD_Up_Dlllist " +
                        "  'A','"+zzdh_text.getText().toString()+"','"+jtbh+"',"+ksdu_text.getText().toString()+"," +
                        jsds_text.getText().toString()+",'"+wkno+"'");
                if (list!=null){
                    if (list.size()>0){
                        if (list.get(0).size()>0){
                            if (list.get(0).get(0).equals("OK")){
                                handler.sendEmptyMessage(0x103);
                            }else {
                                Message msg=handler.obtainMessage();
                                msg.what=0x104;
                                msg.obj=list.get(0).get(0);
                                handler.sendMessage(msg);
                            }
                        }
                    }
                }else {
                    AppUtils.uploadNetworkError("Exec  PAD_Up_Dlllist 'A'",jtbh,sharedPreferences.getString("mac",""));
                }
            }
        }).start();
    }


    @Override
    public void onClick(View v) {
        AppUtils.sendCountdownReceiver(HddjActivity.this);
        switch (v.getId()){
            case R.id.ksds:
                ksdu_text.setBackgroundColor(getResources().getColor(R.color.small));
                jsds_text.setBackgroundColor(Color.WHITE);
                sub_text=ksdu_text;
                break;
            case R.id.jsds:
                jsds_text.setBackgroundColor(getResources().getColor(R.color.small));
                ksdu_text.setBackgroundColor(Color.WHITE);
                sub_text=jsds_text;
                break;
            case R.id.cancle_btn:
                finish();
                break;
            case R.id.btn_0:
                sub_text.startAnimation(anim);
                sub_num=sub_text.getText().toString();
                if((!sub_num.equals("0"))&&(!sub_num.equals("-"))){
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
               if (isReady()){
                    addData();
                }
                break;
            case R.id.btn_clear:
                sub_text.startAnimation(anim);
                sub_text.setText("0");
                break;
            default:
                break;
        }
    }
}
