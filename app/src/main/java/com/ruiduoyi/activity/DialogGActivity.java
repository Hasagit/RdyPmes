package com.ruiduoyi.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ruiduoyi.R;
import com.ruiduoyi.model.NetHelper;
import com.ruiduoyi.utils.AppUtils;

import java.util.List;

public class DialogGActivity extends BaseDialogActivity implements View.OnClickListener{
    private Intent intent_from;
    private Button ok_btn,cancle_btn,yylb_spinner,yymc_spinner;
    private TextView title_text,tip_text;
    private EditText num_edit;
    private String num;
    private String title,type,zldm,jtbh;
    private FrameLayout yy_bg;
    private boolean isFromBlyyfx;
    private LinearLayout ds_bg;
    private RadioGroup radioGroup;

    //接收刷卡串口广播
    private BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            num=intent.getStringExtra("num");
            num_edit.setText(num);
            if (sharedPreferences.getString("doIsFinish","OK").equals("OK")){
                setIsFinishNo();
                getNetData(0x100);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_g);
        initView();
        initDate();
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x100:
                    String readCardResult= (String) msg.obj;
                    final String two=readCardResult.substring(0,2);
                    if (readCardResult.substring(0,2).equals("OK")){
                        if (type.equals("OPR")){//执行指令操作
                            execOPR();
                        }else {//执行文档操作
                            String wkno=readCardResult.substring(2,readCardResult.length());
                            Intent intent;
                            if (zldm.equals(getResources().getString(R.string.gdgl))){
                                intent=new Intent(DialogGActivity.this,GdglActivity.class);
                                intent.putExtra("wkno",wkno);
                                startActivity(intent);
                            }else if(zldm.equals(getResources().getString(R.string.ycfx))){
                                intent=new Intent(DialogGActivity.this,YcfxActivity.class);
                                intent.putExtra("wkno",wkno);
                                startActivity(intent);
                            }else if(zldm.equals(getResources().getString(R.string.blfx))){
                                intent=new Intent(DialogGActivity.this,BlfxActivity.class);
                                intent.putExtra("wkno",wkno);
                                startActivity(intent);
                            }else if(zldm.equals(getResources().getString(R.string.jtjqsbg))){
                                intent=new Intent(DialogGActivity.this,JtjqsbgActivity.class);
                                intent.putExtra("wkno",wkno);
                                startActivity(intent);
                            }else if (zldm.equals(getResources().getString(R.string.pgxj))){
                                intent=new Intent(DialogGActivity.this,PgxjActivity.class);
                                intent.putExtra("wkno",wkno);
                                startActivity(intent);
                            }else if(zldm.equals(getResources().getString(R.string.hddj))){
                                intent=new Intent(DialogGActivity.this,HddjActivity.class);
                                intent.putExtra("wkno",wkno);
                                startActivity(intent);
                            }else if(zldm.equals(getResources().getString(R.string.sbdj))){
                                intent=new Intent(DialogGActivity.this,SbdjActivity.class);
                                intent.putExtra("wkno",wkno);
                                startActivity(intent);
                            }else if(zldm.equals(getResources().getString(R.string.slcs))){
                                intent=new Intent(DialogGActivity.this,SlcsActivity.class);
                                intent.putExtra("wkno",wkno);
                                startActivity(intent);
                            }else if(zldm.equals(getResources().getString(R.string.qsfh))){
                                intent=new Intent(DialogGActivity.this,QsfhActivity.class);
                                intent.putExtra("wkno",wkno);
                                startActivity(intent);
                            }else if (zldm.equals(getResources().getString(R.string.sbxx))){
                                intent=new Intent(DialogGActivity.this,SbxxActivity.class);
                                startActivity(intent);
                            }else if (zldm.equals(getResources().getString(R.string.mjxx))){
                                intent=new Intent(DialogGActivity.this,MjxxActivity.class);
                                startActivity(intent);
                            }else if (zldm.equals(getResources().getString(R.string.pzgl))){
                                intent=new Intent(DialogGActivity.this,PzglActivity.class);
                                startActivity(intent);
                            }else if (zldm.equals(getResources().getString(R.string.scrz))){
                                intent=new Intent(DialogGActivity.this,ScrzActivity.class);
                                startActivity(intent);
                            }else if (zldm.equals(getResources().getString(R.string.gycs))){
                                intent=new Intent(DialogGActivity.this,GycsActivity.class);
                                startActivity(intent);
                            }else if (zldm.equals(getResources().getString(R.string.zyzd))){
                                intent=new Intent(DialogGActivity.this,ZyzdActivity.class);
                                startActivity(intent);
                            }
                            setIsFinishOk();
                            finish();

                        }
                    }else {
                        tip_text.setText(readCardResult);
                        tip_text.setTextColor(Color.RED);
                    }
                    break;
                case 0x101:
                    String result= (String) msg.obj;
                    String wkno=result.substring(2,result.length());
                    Intent intent=new Intent();
                    intent.putExtra("wkno",wkno);
                    setResult(0,intent);
                    finish();
                    break;
                case 0x102://操作失败
                    String tip_str=(String)msg.obj;
                    tip_text.setText(tip_str);
                    tip_text.setTextColor(Color.RED);
                    break;
                default:
                    break;
            }
        }
    };

    private void initView(){
        ok_btn=(Button)findViewById(R.id.ok_btn);
        cancle_btn=(Button)findViewById(R.id.cancle_btn);
        num_edit=(EditText) findViewById(R.id.num_text);
        title_text=(TextView)findViewById(R.id.title_text);
        tip_text=(TextView)findViewById(R.id.tip);
        intent_from=getIntent();
        /*yy_bg=(FrameLayout)findViewById(R.id.yy_bg);
        yylb_spinner=(Button)findViewById(R.id.yylb);
        yymc_spinner=(Button)findViewById(R.id.yymc);*/
        title=intent_from.getStringExtra("title");
        zldm=intent_from.getStringExtra("zldm");
        type=intent_from.getStringExtra("type");
        isFromBlyyfx=intent_from.getBooleanExtra("isFromBlyyfx",false);
        title_text.setText(title);
        ok_btn.setOnClickListener(this);
        cancle_btn.setOnClickListener(this);
        ds_bg=(LinearLayout)findViewById(R.id.ds_bg);
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(num_edit.getWindowToken(),0);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);



        if (zldm.equals(getResources().getString(R.string.ds))){
            ds_bg.setVisibility(View.VISIBLE);
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.radio_ye:
                        title="定色";
                        type="OPR";
                        zldm=getResources().getString(R.string.ds);
                        title_text.setText(title);
                        break;
                    case R.id.radio_no:
                        title="腔数变更";
                        type="DOC";
                        zldm=getResources().getString(R.string.jtjqsbg);
                        title_text.setText(title);
                        break;
                }
            }
        });

    }

    private void initDate(){
        sharedPreferences=getSharedPreferences("info",MODE_PRIVATE);
        jtbh=sharedPreferences.getString("jtbh","");
        IntentFilter receiverfilter=new IntentFilter();
        receiverfilter.addAction("SerialPortNum");
        registerReceiver(receiver,receiverfilter);
        setIsFinishOk();
    }


    private void execOPR(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<List<String>>list=NetHelper.getQuerysqlResult("Exec PAD_SrvCon '"+jtbh+"','"+zldm+"','"+num+"',''");
                if (list!=null){
                    if (list.size()>0){
                        if (list.get(0).size()>0){
                            if (list.get(0).get(0).trim().equals("OK")){
                                if (isFromBlyyfx){//从BlyyfxActivity启动来的
                                    type="DOC";
                                    getNetData(0x101);
                                }else {//从statusFragment启动来的
                                    Intent intent=new Intent();
                                    intent.setAction("UpdateInfoFragment");
                                    sendBroadcast(intent);
                                    if (!(zldm.equals(getResources().getString(R.string.js))|
                                            zldm.equals(getResources().getString(R.string.rysg))|
                                            zldm.equals(getResources().getString(R.string.pgxj)))){
                                        Intent intent2=new Intent();
                                        intent2.setAction("com.Ruiduoyi.returnToInfoReceiver");
                                        sendBroadcast(intent2);
                                    }
                                    setIsFinishOk();
                                    finish();
                                }
                            }else {
                                Message msg=handler.obtainMessage();
                                msg.what=0x102;
                                msg.obj=list.get(0).get(0);
                                handler.sendMessage(msg);
                                setIsFinishOk();
                            }
                        }else {
                            setIsFinishOk();
                        }
                    }else {
                        setIsFinishOk();
                    }
                }else {
                    setIsFinishOk();
                }
            }
        }).start();
    }

    private void getNetData(final int what){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<List<String>>list=NetHelper.getQuerysqlResult("Exec PAD_Read_CardID '"
                        +type+"','"+zldm+"','"+num+"'");
                if (list!=null){
                    if (list.size()>0){
                        if (list.get(0).size()>0){
                            Message msg=handler.obtainMessage();
                            msg.what=what;
                            msg.obj=list.get(0).get(0);
                            handler.sendMessage(msg);
                        }else {
                            setIsFinishOk();
                        }
                    }else {
                        setIsFinishOk();
                    }
                }else {
                    setIsFinishOk();
                    AppUtils.uploadNetworkError("PAD_Read_CardID",jtbh,sharedPreferences.getString("mac",""));
                }
            }
        }).start();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ok_btn:
                Intent intent=new Intent();
                setResult(1,intent);
                AppUtils.sendCountdownReceiver(DialogGActivity.this);
                finish();
                break;
            case R.id.cancle_btn:
                Intent intent2=new Intent();
                setResult(1,intent2);
                AppUtils.sendCountdownReceiver(DialogGActivity.this);
                finish();
                break;
        }
    }

    private void setIsFinishOk(){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("doIsFinish","OK");
        editor.commit();
    }

    private void setIsFinishNo(){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("doIsFinish","NO");
        editor.commit();
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

}
