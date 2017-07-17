package com.ruiduoyi.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ruiduoyi.R;
import com.ruiduoyi.adapter.Jtqsbg1Adapter;
import com.ruiduoyi.adapter.SigleSelectJtjqsbg;
import com.ruiduoyi.adapter.WorkOrderAdapter;
import com.ruiduoyi.model.NetHelper;
import com.ruiduoyi.utils.AppUtils;
import com.ruiduoyi.view.PopupDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JtjqsbgActivity extends BaseActivity implements View.OnClickListener{
    private ListView listView_1,listView_2;
    private String jtbh,sub_num,zzdh;
    private Handler handler;
    private TextView mjbh_text,mjmc_text,mjqs_text,cpbh_text,pmgg_text,sjqs_text,jtbh_text,new_jtbh;
    private EditText sub_text;
    private Button btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9,btn_0,btn_clear,btn_submit,cancle_btn;
    private Animation anim;
    private PopupDialog dialog;
    private PopupDialog tipDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jtjqsbg);
        initData();
        initView();
    }


    private void initView(){
        listView_1=(ListView)findViewById(R.id.list_jtjqsbg_1);
        listView_2=(ListView)findViewById(R.id.list_jtjqsbg_2);
        mjbh_text=(TextView)findViewById(R.id.mjbh);
        mjmc_text=(TextView)findViewById(R.id.mjmc);
        mjqs_text=(TextView)findViewById(R.id.mjqs);
        cpbh_text=(TextView)findViewById(R.id.cpbh);
        pmgg_text=(TextView)findViewById(R.id.pmgg);
        sjqs_text=(TextView)findViewById(R.id.sjqs);
        sub_text=(EditText) findViewById(R.id.new_mjqs);
        jtbh_text=(TextView)findViewById(R.id.jtbh);
        new_jtbh=(TextView)findViewById(R.id.new_jtbh);
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
        cancle_btn.setOnClickListener(this);
        jtbh_text.setText(jtbh);
        new_jtbh.setText(jtbh);



        tipDialog=new PopupDialog(this,400,300);
        tipDialog.getCancle_btn().setVisibility(View.GONE);
        tipDialog.getOkbtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipDialog.dismiss();
            }
        });
        tipDialog.setTitle("提示");
        tipDialog.setMessageTextColor(Color.BLACK);
        tipDialog.getOkbtn().setText("确定");



        dialog=new PopupDialog(this,400,300);
        dialog.setTitle("提示");
        dialog.getCancle_btn().setText("取消");
        dialog.getOkbtn().setText("确定");
        dialog.getOkbtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upLoadData();
            }
        });
        dialog.getCancle_btn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });



        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(sub_text.getWindowToken(),0);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    }

    private void initData(){
        jtbh=sharedPreferences.getString("jtbh","");
        anim= AnimationUtils.loadAnimation(this,R.anim.sub_num_anim);


        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0x100:
                        List<List<String>>list=(List<List<String>>)msg.obj;
                        initListView(list);
                        break;
                    case 0x101:
                        List<List<String>>list1= (List<List<String>>) msg.obj;
                        List<Map<String,String>>data=new ArrayList<>();
                        for (int i=0;i<list1.size();i++){
                            Map<String,String>map=new HashMap<>();
                            map.put("lab_1",list1.get(i).get(0));
                            map.put("lab_2",list1.get(i).get(1));
                            map.put("lab_3",list1.get(i).get(2));
                            map.put("lab_4",list1.get(i).get(3));
                            data.add(map);
                        }
                        SigleSelectJtjqsbg adapter=new SigleSelectJtjqsbg(JtjqsbgActivity.this,R.layout.list_item_jtjqsbg_2,data) {
                            @Override
                            public void onRadioSelectListener(int position, Map<String, String> map) {
                                new_jtbh.setText(map.get("lab_1"));
                            }
                        };
                        listView_2.setAdapter(adapter);
                        break;
                    case 0x102:
                        dialog.dismiss();
                        tipDialog.setMessage("更改失败，请重试");
                        tipDialog.show();
                        break;
                    case 0x103:
                        dialog.dismiss();
                        tipDialog.setMessage("更改成功！");
                        tipDialog.show();
                        mjbh_text.setText("");
                        mjmc_text.setText("");
                        mjqs_text.setText("");
                        cpbh_text.setText("");
                        pmgg_text.setText("");
                        sjqs_text.setText("");
                        jtbh_text.setText("");
                        new_jtbh.setText("");
                        sub_text.setText("");
                        getNetData();
                        break;
                    case 0x104:
                        Map<String,String>map= (Map<String, String>) msg.obj;
                        getList2Data(map.get("mjbh"));
                        mjbh_text.setText(map.get("mjbh"));
                        mjmc_text.setText(map.get("mjmc"));
                        mjqs_text.setText(map.get("mjqs"));
                        cpbh_text.setText(map.get("wldm"));
                        pmgg_text.setText(map.get("pmgg"));
                        sjqs_text.setText(map.get("cpqs"));
                        sub_text.setText(map.get("mjqs"));
                        zzdh=map.get("zzdh");
                        Log.w("zzdh",zzdh);
                        jtbh_text.setText(jtbh);
                        break;
                    default:
                        break;
                }
            }
        };


        getNetData();
    }



    private void  initListView(List<List<String>>lists){
        List<Map<String,String>>data=new ArrayList<>();
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
            map.put("mjqs",item.get(14));
            map.put("cpqs",item.get(15));
            data.add(map);
        }
        Jtqsbg1Adapter adapter_1;
        adapter_1=new Jtqsbg1Adapter(JtjqsbgActivity.this,R.layout.list_item_jtjqsbg1,data,handler);
        listView_1.setAdapter(adapter_1);
    }



    private void getNetData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<List<String>>list= NetHelper.getQuerysqlResult("Exec PAD_Get_MoeDet 'A','"+jtbh+"'");
                if (list!=null){
                    if (list.size()>0){
                        if (list.get(0).size()>15){
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

    private void getList2Data(final String mjbh){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<List<String>>list=NetHelper.getQuerysqlResult("Exec PAD_Get_MjmHgl  '"+mjbh+"'");
                if (list!=null){
                    if (list.size()>0){
                        if (list.get(0).size()>3){
                            Message msg=handler.obtainMessage();
                            msg.what=0x101;
                            msg.obj=list;
                            handler.sendMessage(msg);
                        }
                    }
                }
            }
        }).start();
    }



    private boolean isReady(){
        if (mjbh_text.getText().toString().equals("")){
            tipDialog.setMessage("请先选择模具和产品信息");
            tipDialog.show();
            //Toast.makeText(this,"请先选择模具和产品信息",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (new_jtbh.getText().toString().equals("")){
            tipDialog.setMessage("请先选择新的机台编号");
            tipDialog.show();
            //Toast.makeText(this,"请先选择新的机台编号",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (sub_text.getText().toString().equals(0)){
            tipDialog.setMessage("请先输入新的实际腔数");
            tipDialog.show();
            //Toast.makeText(this,"请先输入新的实际腔数",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Integer.parseInt(mjqs_text.getText().toString())<Integer.parseInt(sub_text.getText().toString())){
            tipDialog.setMessage("输入的腔数不能大于模具腔数");
            tipDialog.show();
            //Toast.makeText(this,"输入的腔数不能大于模具腔数",Toast.LENGTH_SHORT).show();
            return false;
        }
        if ((sjqs_text.getText().toString().equals(sub_text.getText().toString()))&
                (jtbh_text.getText().toString().equals(new_jtbh.getText().toString()))){
            tipDialog.setMessage("机台与腔数未发生变更");
            tipDialog.show();
            //Toast.makeText(this,"机台与腔数未发生变更",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    public void upLoadData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<List<String>>list=NetHelper.getQuerysqlResult("Exec  PAD_Upd_MoeJtXs '"+zzdh+"','"+new_jtbh.getText().toString()+"','"+sub_text.getText().toString()+"'");
                if (list!=null){
                    if (list.size()>0){
                        if (list.get(0).size()>0){
                            if (list.get(0).get(0).equals("OK")){
                                handler.sendEmptyMessage(0x103);
                            }else {
                                handler.sendEmptyMessage(0x102);
                            }
                        }else {
                            handler.sendEmptyMessage(0x102);
                        }
                    }else {
                        handler.sendEmptyMessage(0x102);
                    }
                }else {
                    handler.sendEmptyMessage(0x102);
                }
            }
        }).start();
    }


    @Override
    public void onClick(View v) {
        AppUtils.sendCountdownReceiver(JtjqsbgActivity.this);
        switch (v.getId()){
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
                    dialog.setMessage("实际腔数:"+sjqs_text.getText().toString()+"\t将变更成:"+sub_text.getText().toString()+"\n\n"+
                            "原机台编号:"+jtbh_text.getText().toString()+"\t将变更成:"+new_jtbh.getText().toString());
                    dialog.show();
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
