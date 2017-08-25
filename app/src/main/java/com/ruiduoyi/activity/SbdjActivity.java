package com.ruiduoyi.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ruiduoyi.R;
import com.ruiduoyi.adapter.EasyArrayAdapter;
import com.ruiduoyi.model.NetHelper;
import com.ruiduoyi.utils.AppUtils;
import com.ruiduoyi.view.PopupDialog;
import com.ruiduoyi.view.PopupWindowSpinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SbdjActivity extends BaseActivity implements View.OnClickListener{
    private Button spinner_btn,cancle_btn,add_btn,save_btn;
    private ListView listView_sb,listView_dj;
    private RadioGroup radioGroup;
    private RadioButton radio_jt,radio_gy;
    private TextView djbh_text,sbbh_text,sbmc_text;
    private String jtbh,wkno;
    private SharedPreferences sharedPreferences;
    private Handler handler;
    private String spinner_select_lb;
    private PopupWindowSpinner spinner_list;
    private PopupDialog dialog;
    private List<Map<String,String>>djData;
    private List<Map<String,String>>allDjData;
    private EasyArrayAdapter djAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_djby);
        initData();
        initView();
    }

    private void initData(){
        sharedPreferences=getSharedPreferences("info",MODE_PRIVATE);
        jtbh=sharedPreferences.getString("jtbh","");
        Intent intent_from=getIntent();
        wkno=intent_from.getStringExtra("wkno");






        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0x100:
                        initSbList((List<List<String>>) msg.obj);
                        break;
                    case 0x101:
                        djbh_text.setText((String) msg.obj);
                        spinner_btn.setText("");
                        //getDjListData((String) msg.obj);
                        break;
                    case 0x102:
                        initSpinnerList((List<List<String>>) msg.obj);
                        break;
                    case 0x103:
                        List<List<String>>lists= (List<List<String>>) msg.obj;
                        allDjData=new ArrayList<>();
                        for (int i=0;i<lists.size();i++){
                            Map<String,String>map=new HashMap<>();
                            map.put("lab_1",lists.get(i).get(0));
                            map.put("lab_2",lists.get(i).get(1));
                            map.put("lab_3",lists.get(i).get(2));
                            map.put("lab_4",lists.get(i).get(3));
                            map.put("djbh",lists.get(i).get(4));
                            map.put("bnlid",lists.get(i).get(5));
                            allDjData.add(map);
                        }
                        initDjListView(allDjData);
                        break;
                    case 0x104:
                        djbh_text.setText((String)msg.obj);
                        break;
                    case 0x105:
                        dialog.setMessageTextColor(Color.RED);
                        dialog.setMessage((String) msg.obj);
                        dialog.show();
                        break;
                    case 0x106:
                        dialog.setMessageTextColor(Color.RED);
                        dialog.setMessage((String) msg.obj);
                        dialog.show();
                        break;
                    case 0x107:
                        dialog.setMessage("请先新增点检编号");
                        dialog.setMessageTextColor(Color.RED);
                        dialog.show();
                       break;
                    default:
                        break;
                }
            }
        };

        getSbListData(jtbh);
    }

    private void initView(){
        radio_jt=(RadioButton)findViewById(R.id.radio_jt);
        radio_gy=(RadioButton)findViewById(R.id.radio_gy);
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        listView_sb=(ListView)findViewById(R.id.list_1);
        listView_dj=(ListView)findViewById(R.id.list_2);
        djbh_text=(TextView)findViewById(R.id.djbh);
        sbbh_text=(TextView)findViewById(R.id.sbbh);
        sbmc_text=(TextView)findViewById(R.id.sbmc);
        spinner_btn=(Button)findViewById(R.id.spinner_btn);
        add_btn=(Button)findViewById(R.id.add_btn);
        save_btn=(Button)findViewById(R.id.save_btn);
        cancle_btn=(Button)findViewById(R.id.cancle_btn);
        spinner_btn.setOnClickListener(this);
        add_btn.setOnClickListener(this);
        save_btn.setOnClickListener(this);
        cancle_btn.setOnClickListener(this);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId==R.id.radio_jt){
                    getSbListData(jtbh);
                }else {
                    getSbListData("");
                }
            }
        });

        dialog=new PopupDialog(this,400,360);
        dialog.getCancle_btn().setVisibility(View.GONE);
        dialog.getOkbtn().setText("确定");
        dialog.getOkbtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.sendCountdownReceiver(SbdjActivity.this);
                dialog.dismiss();
            }
        });
        dialog.setTitle("提示");

    }

    private void getSbListData(final String sbbh){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<List<String>>list= NetHelper.getQuerysqlResult("Exec PAD_Get_DjmInf 'A','','"+sbbh+"','',''");
                if (list!=null){
                    Message msg=handler.obtainMessage();
                    msg.what=0x100;
                    msg.obj=list;
                    handler.sendMessage(msg);
                }else {
                    AppUtils.uploadNetworkError("Exec PAD_Get_DjmInf 'A'",jtbh,sharedPreferences.getString("mac",""));
                }
            }
        }).start();

    }

    private void initSbList(List<List<String>>lists){
        final List<Map<String,String>>data=new ArrayList<>();
        for (int i=0;i<lists.size();i++){
            if (lists.get(0).size()<3){
                break;
            }
            Map<String,String>map=new HashMap<>();
            map.put("lab_1",lists.get(i).get(0));
            map.put("lab_2",lists.get(i).get(1));
            map.put("lab_3",lists.get(i).get(2));
            map.put("isSelect","0");
            data.add(map);
        }

        EasyArrayAdapter adapter=new EasyArrayAdapter(this,R.layout.list_item_sbdj_sb,data) {
            @Override
            public View getEasyView(final int position, View convertView, ViewGroup parent) {
                View view;
                if (convertView!=null){
                    view=convertView;
                }else {
                    view= LayoutInflater.from(SbdjActivity.this).inflate(R.layout.list_item_sbdj_sb,null);
                }
                final Map<String,String>map=data.get(position);
                TextView lab_1=(TextView)view.findViewById(R.id.lab_1);
                TextView lab_2=(TextView)view.findViewById(R.id.lab_2);
                LinearLayout bg=(LinearLayout)view.findViewById(R.id.bg);
                lab_1.setText(map.get("lab_1"));
                lab_2.setText(map.get("lab_2"));
                if (map.get("isSelect").equals("0")){
                    bg.setBackgroundColor(Color.WHITE);
                }else {
                    bg.setBackgroundColor(getResources().getColor(R.color.small));
                }
                bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppUtils.sendCountdownReceiver(SbdjActivity.this);
                        djbh_text.setText("");
                        initDjListView(new ArrayList<Map<String, String>>());
                        map.put("isSelect","1");
                        sbbh_text.setText(map.get("lab_1"));
                        sbmc_text.setText(map.get("lab_2"));
                        getSpinnerData(map.get("lab_1"),map.get("lab_3"));
                        for (int i=0;i<data.size();i++){
                            if (i!=position){
                                data.get(i).put("isSelect","0");
                            }
                        }
                        notifyDataSetChanged();
                    }
                });
                return view;
            }
        };
        listView_sb.setAdapter(adapter);
    }

    private void getSpinnerData(final String sbbh, final String sbtype){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<List<String>>list=NetHelper.getQuerysqlResult("Exec PAD_Get_DjmInf 'B','','','"+sbbh+"','"+sbtype+"'");
                if (list!=null){
                    if (list.size()>0){
                        if (list.get(0).size()>1){
                            Message msg=handler.obtainMessage();
                            msg.what=0x102;
                            msg.obj=list;
                            handler.sendMessage(msg);
                        }
                    }else {
                        Message msg=handler.obtainMessage();
                        msg.what=0x102;
                        msg.obj="";
                        handler.sendMessage(msg);
                    }
                }
            }
        }).start();
    }


    private void initSpinnerList(final List<List<String>>lists){
        spinner_select_lb=null;
        final List<String>data=new ArrayList<>();
        final List<String>list_spinner_dm=new ArrayList<>();
        for (int i=0;i<lists.size();i++){
            data.add(lists.get(i).get(1));
            list_spinner_dm.add(lists.get(i).get(0));
        }
        spinner_list=new PopupWindowSpinner(this,data,R.layout.spinner_list_b7,R.id.lab_1,410);
        spinner_list.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppUtils.sendCountdownReceiver(SbdjActivity.this);
                initDjListView(new ArrayList<Map<String, String>>());
                spinner_btn.setText(data.get(position));
                spinner_select_lb=list_spinner_dm.get(position);
                spinner_list.dismiss();
                getDjDh(lists.get(0).get(0),sbbh_text.getText().toString());
            }
        });
       //默认选中第一个
        if (lists.size()>0){
            spinner_btn.setText(data.get(0));
            spinner_select_lb=list_spinner_dm.get(0);
            spinner_list.dismiss();
            //selectByDjlb(lists.get(0).get(0));
            getDjDh(lists.get(0).get(0),sbbh_text.getText().toString());
        }
    }


    private void getDjDh(final String djlb, final String sbdm){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (spinner_select_lb==null){
                    Message msg=handler.obtainMessage();
                    msg.what=0x105;
                    msg.obj="点检类别为空";
                    handler.sendMessage(msg);
                    return;
                }
                List<List<String>>list=NetHelper.getQuerysqlResult("Exec PAD_Get_DjmInf 'C','','','"+djlb+"','"+sbdm+"'");
                if (list!=null){
                    if (list.size()>0){
                        if (list.get(0).size()>2){
                           if (list.get(0).get(0).trim().equals("")){
                               /*Message msg=handler.obtainMessage();
                               msg.what=0x105;
                               msg.obj="请先新增点检编号";
                               handler.sendMessage(msg);*/
                           }else {
                               Message msg=handler.obtainMessage();
                               msg.what=0x104;
                               msg.obj=list.get(0).get(0);
                               handler.sendMessage(msg);
                               List<List<String>>list_dj=NetHelper.getQuerysqlResult("Exec PAD_Get_DjmInf  'D',"+list.get(0).get(0)+",'','"+spinner_select_lb+"',''");
                               Message msg2=handler.obtainMessage();
                               msg2.what=0x103;
                               msg2.obj=list_dj;
                               handler.sendMessage(msg2);

                           }
                        }
                    }
                }
            }
        }).start();
    }


    private void initDjListView(final List<Map<String,String>>djData){
        djAdapter=new EasyArrayAdapter(this,R.layout.list_item_sbdj_dj,djData) {
            @Override
            public View getEasyView(final int position, View convertView, ViewGroup parent) {
                View view;
                if (convertView!=null){
                    view=convertView;
                }else {
                    view=LayoutInflater.from(getContext()).inflate(R.layout.list_item_sbdj_dj,null);
                }
                final Map<String,String>map=djData.get(position);
                TextView lab_1=(TextView)view.findViewById(R.id.lab_1);
                TextView lab_2=(TextView)view.findViewById(R.id.lab_2);
                TextView lab_3=(TextView)view.findViewById(R.id.lab_3);
                final LinearLayout bg=(LinearLayout)view.findViewById(R.id.bg);
                RadioGroup radioGroup_list=(RadioGroup)view.findViewById(R.id.radioGroup_list);
                RadioButton radio_ok=(RadioButton)view.findViewById(R.id.radio_ok);
                RadioButton radio_ng=(RadioButton)view.findViewById(R.id.radio_ng);
                RadioButton radio_o=(RadioButton)view.findViewById(R.id.radio_o);
                RadioButton radio_dpd=(RadioButton)view.findViewById(R.id.radio_dpd);
                lab_1.setText(map.get("lab_1"));
                lab_2.setText(map.get("lab_2"));
                lab_3.setText(map.get("lab_3"));
                radioGroup_list.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        AppUtils.sendCountdownReceiver(SbdjActivity.this);
                        switch (checkedId){
                            case R.id.radio_ok:
                                djData.get(position).put("lab_4","√");
                                //bg.setBackgroundColor(getResources().getColor(R.color.large));
                                break;
                            case R.id.radio_ng:
                                djData.get(position).put("lab_4","X");
                                //bg.setBackgroundColor(getResources().getColor(R.color.color_7));
                                break;
                            case R.id.radio_dpd:
                                djData.get(position).put("lab_4","");
                                //bg.setBackgroundColor(Color.WHITE);
                                break;
                            case R.id.radio_o:
                                djData.get(position).put("lab_4","◎");
                                //bg.setBackgroundColor(getResources().getColor(R.color.fragment_bg));
                                break;

                        }
                    }
                });
                switch (map.get("lab_4")){
                    case "√":
                        //bg.setBackgroundColor(getResources().getColor(R.color.large));
                        radio_ok.setChecked(true);
                        break;
                    case "X":
                        //bg.setBackgroundColor(getResources().getColor(R.color.color_7));
                        radio_ng.setChecked(true);
                        break;
                    case "◎":
                        //bg.setBackgroundColor(getResources().getColor(R.color.fragment_bg));
                        radio_o.setChecked(true);
                        break;
                    case "":
                        //bg.setBackgroundColor(Color.WHITE);
                        radio_dpd.setChecked(true);
                        break;
                }
                return view;
            }
        };
        listView_dj.setAdapter(djAdapter);
    }

    private void addDj(final String djbh, final String sbdm){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (spinner_select_lb==null){
                    Message msg=handler.obtainMessage();
                    msg.what=0x105;
                    msg.obj="点检类别为空";
                    handler.sendMessage(msg);
                    return;
                }
                List<List<String>>list=NetHelper.getQuerysqlResult("Exec PAD_Up_DjmMstr" +
                        " 'A','"+djbh+"','"+sbdm+"','"+spinner_select_lb+"','','','"+wkno+"'");
                if (list!=null){
                    if (list.size()>0){
                        if (list.get(0).size()>0){
                            if (list.get(0).get(0).equals("OK")){
                                handler.sendEmptyMessage(0x104);
                                getDjDh(spinner_select_lb,sbdm);
                            }else {
                                Message msg=handler.obtainMessage();
                                msg.what=0x105;
                                msg.obj=list.get(0).get(0);
                                handler.sendMessage(msg);
                            }
                        }
                    }
                }
            }
        }).start();
    }

    private boolean isAddReady(){
        if (sbbh_text.getText().toString().equals("")){
            dialog.setMessage("请先选择设备信息");
            dialog.setMessageTextColor(Color.RED);
            dialog.show();
            return false;
        }
        return true;
    }

    private void uploadData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (spinner_select_lb==null){
                    Message msg=handler.obtainMessage();
                    msg.what=0x105;
                    msg.obj="点检类别为空";
                    handler.sendMessage(msg);
                    return;
                }
                List<String>result=new ArrayList<String>();
                for (int i=0;i<allDjData.size();i++){
                    Map<String,String>map=allDjData.get(i);
                    List<List<String>>list=NetHelper.getQuerysqlResult("Exec PAD_Up_DjmMstr 'B','"+djbh_text.getText().toString()+
                            "','"+sbbh_text.getText().toString()+"','"+spinner_select_lb+"','"+map.get("bnlid")+"','"+map.get("lab_4")+"','"+wkno+"'");
                    if (list!=null){
                        if (list.size()>0){
                            if (list.get(0).size()>0){
                                if (!list.get(0).get(0).equals("OK")){
                                    result.add(list.get(0).get(0));
                                }
                            }else {
                                Message msg=handler.obtainMessage();
                                msg.what=0x106;
                                msg.obj="提交失败";
                                handler.sendMessage(msg);
                            }
                        }else {
                            Message msg=handler.obtainMessage();
                            msg.what=0x106;
                            msg.obj="提交失败";
                            handler.sendMessage(msg);
                        }
                    }else {
                        Message msg=handler.obtainMessage();
                        msg.what=0x106;
                        msg.obj="提交失败";
                        handler.sendMessage(msg);
                    }
                }
                if (result.size()>0){
                    String error="";
                    for (int i=0;i<result.size();i++){
                        error=error+result.get(i)+"\n";
                    }
                    Message msg=handler.obtainMessage();
                    msg.what=0x106;
                    msg.obj=error;
                    handler.sendMessage(msg);
                }else {
                    //handler.sendEmptyMessage(0x107);
                }

            }
        }).start();
    }

    private boolean isUpoadReady(){
        if (sbbh_text.getText().toString().equals("")){
            dialog.setMessageTextColor(Color.RED);
            dialog.setMessage("请先选择设备信息");
            dialog.show();
            return false;
        }

        if (djbh_text.getText().toString().equals("")){
            dialog.setMessageTextColor(Color.RED);
            dialog.setMessage("请先新增点检记录");
            dialog.show();
            return false;
        }
        if (allDjData==null){
            dialog.setMessageTextColor(Color.RED);
            dialog.setMessage("数据为空");
            dialog.show();
            return false;
        }
        return true;
    }


    @Override
    public void onClick(View v) {
        AppUtils.sendCountdownReceiver(SbdjActivity.this);
        switch (v.getId()){
            case R.id.cancle_btn:
                finish();
                break;
            case R.id.spinner_btn:
                if (spinner_list!=null){
                    spinner_list.showDownOn(spinner_btn);
                }
                break;
            case R.id.save_btn:
                if (isUpoadReady()){
                    uploadData();
                }
                break;
            case R.id.add_btn:
                if (isAddReady()){
                    addDj(djbh_text.getText().toString(),sbbh_text.getText().toString());
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog.isShow()){
            dialog.dismiss();
        }
    }
}
