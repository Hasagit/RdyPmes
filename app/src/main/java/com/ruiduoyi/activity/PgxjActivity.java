package com.ruiduoyi.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ruiduoyi.R;
import com.ruiduoyi.adapter.EasyArrayAdapter;
import com.ruiduoyi.adapter.Jtqsbg1Adapter;
import com.ruiduoyi.adapter.YyfxAdapter;
import com.ruiduoyi.model.NetHelper;
import com.ruiduoyi.utils.AppUtils;
import com.ruiduoyi.view.PopupDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PgxjActivity extends BaseActivity implements View.OnClickListener{
    private Button save_btn,cancle_btn;
    private RadioButton radio_ok,radio_ng;
    private RadioGroup radioGroup;
    private TextView zzdh_text,gddh_text,scph_text,wgrq_text,cpbh_text,pmgg_text,jhsl_text,lpsl_text,mjbh_text,mjmc_text,cpxs_text,sjxs_text;
    private ListView congDanListView,yuanYinLisView;
    private Handler handler;
    private String wkno,jtbh;
    private List<Integer>num_list=new ArrayList<>();
    private List<Map<String,String>>data_cong;
    private YyfxAdapter adapter_yy;
    private PopupDialog dialog;
    private List<Map<String,String>>upload_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pgxj);
        initData();
        initView();
    }

    private void initData(){
        Intent inten_from=getIntent();
        wkno=inten_from.getStringExtra("wkno");
        sharedPreferences=getSharedPreferences("info",MODE_PRIVATE);
        jtbh=sharedPreferences.getString("jtbh","");




        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0x100://初始化工单信息
                        List<List<String>>list=(List<List<String>>)msg.obj;
                        initGongDanList(list);
                        break;
                    case 0x101://从工单信息
                        initCongDanList((List<List<String>>) msg.obj);
                        break;
                    case 0x102:
                        dialog.setMessage((String) msg.obj);
                        dialog.show();
                        break;
                    case 0x103://原因列表
                        List<List<String>>list1= (List<List<String>>) msg.obj;
                        List<Map<String,String>>data1=new ArrayList<>();
                        for (int i=0;i<list1.size();i++){
                            Map<String,String>map=new HashMap<>();
                            map.put("lab_1",list1.get(i).get(0));
                            map.put("lab_2",list1.get(i).get(1));
                            data1.add(map);
                        }
                        adapter_yy=new YyfxAdapter(PgxjActivity.this,R.layout.list_item_pgxj_yy,data1);
                        yuanYinLisView.setAdapter(adapter_yy);
                        break;
                    case 0x104://工单信息选择发回来的信息
                        Map<String,String>map= (Map<String, String>) msg.obj;
                        getCongListData(map.get("zzdh"));
                        num_list.clear();
                        Log.w("num_list.clear",num_list.toString());
                        break;
                    default:
                        break;
                }
            }
        };

        getNetData();
    }

    private void initView(){
        cancle_btn=(Button)findViewById(R.id.cancle_btn);
        save_btn=(Button)findViewById(R.id.save_btn);
        radio_ok=(RadioButton)findViewById(R.id.ok_btn);
        radio_ng=(RadioButton)findViewById(R.id.ng_btn);
        congDanListView=(ListView)findViewById(R.id.list_2);
        yuanYinLisView=(ListView)findViewById(R.id.list_3);
        zzdh_text=(TextView)findViewById(R.id.dq_1);
        gddh_text=(TextView)findViewById(R.id.dq_2);
        scph_text=(TextView)findViewById(R.id.dq_3);
        wgrq_text=(TextView)findViewById(R.id.dq_4);
        cpbh_text=(TextView)findViewById(R.id.dq_5);
        pmgg_text=(TextView)findViewById(R.id.dq_6);
        jhsl_text=(TextView)findViewById(R.id.dq_7);
        lpsl_text=(TextView)findViewById(R.id.dq_8);
        mjbh_text=(TextView)findViewById(R.id.dq_9);
        mjmc_text=(TextView)findViewById(R.id.dq_10);
        cpxs_text=(TextView)findViewById(R.id.dq_11);
        sjxs_text=(TextView)findViewById(R.id.dq_12);
        save_btn.setOnClickListener(this);
        cancle_btn.setOnClickListener(this);
        dialog=new PopupDialog(this,400,300);
        dialog.getCancle_btn().setVisibility(View.GONE);
        dialog.getOkbtn().setText("确定");
        dialog.getOkbtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.ok_btn:
                        yuanYinLisView.setVisibility(View.GONE);
                        break;
                    case R.id.ng_btn:
                        yuanYinLisView.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

    private void getNetData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //工单信息表
                List<List<String>>list= NetHelper.getQuerysqlResult("Exec PAD_Get_MoeDet 'C','"+jtbh+"'");
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

                //原因列表
                List<List<String>>list_yy=NetHelper.getQuerysqlResult("Exec PAD_Get_XjlInf  'B',''");
                if (list_yy!=null){
                    if (list_yy.size()>0){
                        if (list_yy.get(0).size()>1){
                            Message msg=handler.obtainMessage();
                            msg.what=0x103;
                            msg.obj=list_yy;
                            handler.sendMessage(msg);
                        }
                    }
                }else {
                    AppUtils.uploadNetworkError("Exec PAD_Get_XjlInf  'B',''",jtbh,sharedPreferences.getString("mac",""));
                }

            }
        }).start();

    }


    private void getCongListData(final String zzdh){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //从工单信息
                List<List<String>>list6= NetHelper.getQuerysqlResult("Exec PAD_Get_XjlInf  'A','"+zzdh+"'");
                if (list6!=null){
                    if (list6.size()>0){
                        if (list6.get(0).size()>8){
                            Message msg=handler.obtainMessage();
                            msg.what=0x101;
                            msg.obj=list6;
                            handler.sendMessage(msg);
                        }
                    }
                }else {
                    AppUtils.uploadNetworkError("Exec PAD_Get_XjlInf  'A'",jtbh,sharedPreferences.getString("mac",""));
                }

            }
        }).start();
    }

    private void  initGongDanList(List<List<String>>lists){
        List<String>list=lists.get(0);
        zzdh_text.setText(list.get(3));
        gddh_text.setText(list.get(4));
        scph_text.setText(list.get(5));
        wgrq_text.setText(list.get(10));
        cpbh_text.setText(list.get(8));
        pmgg_text.setText(list.get(9));
        jhsl_text.setText(list.get(11));
        lpsl_text.setText(list.get(12));
        mjbh_text.setText(list.get(6));
        mjmc_text.setText(list.get(7));
        cpxs_text.setText(list.get(14));
        sjxs_text.setText(list.get(15));

        getCongListData(zzdh_text.getText().toString());
    }

    private void initCongDanList(List<List<String>>lists){
        data_cong=new ArrayList<>();
        for (int i=0;i<lists.size();i++){
            List<String>item=lists.get(i);
            Map<String,String>map=new HashMap<>();
            map.put("lab_1",item.get(1));
            map.put("lab_2",item.get(2));
            map.put("lab_3",item.get(3));
            map.put("lab_4",item.get(4));
            map.put("lab_5",item.get(5));
            map.put("lab_6",item.get(6));
            map.put("lab_7",item.get(7));
            map.put("lab_8",item.get(8));
            map.put("zzdh",item.get(0));
            data_cong.add(map);
        }
        EasyArrayAdapter adapter=new EasyArrayAdapter(this,R.layout.list_item_pgxj_cong,data_cong) {
            @Override
            public View getEasyView(final int position, View convertView, ViewGroup parent) {
                View view;
                if (convertView!=null){
                    view=convertView;
                }else {
                    view= LayoutInflater.from(PgxjActivity.this).inflate(R.layout.list_item_pgxj_cong,null);
                }
                TextView lab_1=(TextView)view.findViewById(R.id.lab_1);
                TextView lab_2=(TextView)view.findViewById(R.id.lab_2);
                TextView lab_3=(TextView)view.findViewById(R.id.lab_3);
                TextView lab_4=(TextView)view.findViewById(R.id.lab_4);
                TextView lab_5=(TextView)view.findViewById(R.id.lab_5);
                TextView lab_6=(TextView)view.findViewById(R.id.lab_6);
                final TextView lab_7=(TextView)view.findViewById(R.id.lab_7);
                final TextView lab_8=(TextView)view.findViewById(R.id.lab_8);
                Button add_btn=(Button)view.findViewById(R.id.add);
                Button del_btn=(Button)view.findViewById(R.id.delete);
                final Map<String,String>map=data_cong.get(position);
                lab_1.setText(map.get("lab_1"));
                lab_2.setText(map.get("lab_2"));
                lab_3.setText(map.get("lab_3"));
                lab_4.setText(map.get("lab_4"));
                lab_5.setText(map.get("lab_5"));
                lab_6.setText(map.get("lab_6"));
                lab_7.setText(map.get("lab_7"));
                lab_8.setText(map.get("lab_8"));
                add_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int temp=Integer.parseInt(lab_8.getText().toString())+1;
                        lab_8.setText(temp+"");
                        data_cong.get(position).put("lab_8",temp+"");
                    }
                });
                del_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int temp=Integer.parseInt(lab_8.getText().toString())-1;
                        if (!(temp<0)){
                            lab_8.setText(temp+"");
                            data_cong.get(position).put("lab_8",temp+"");
                        }
                        Log.w("num_list",data_cong.toString());
                    }
                });
                return view;
            }
        };
        congDanListView.setAdapter(adapter);
    }


    private boolean isReady(){
        if (!(radio_ng.isChecked()|radio_ok.isChecked())){
            dialog.setMessage("请先选择判定结果");
            dialog.show();
            return false;
        }
        if (radio_ok.isChecked()){
            upload_data=new ArrayList<>();
            for (int i=0;i<data_cong.size();i++){
                if (!data_cong.get(i).get("lab_7").equals(data_cong.get(i).get("lab_8"))){
                    upload_data.add(data_cong.get(i));
                }
            }
            if (upload_data.size()>0){
                dialog.setMessage("实际腔数与巡查腔数不一致，判定结果不能选择OK");
                dialog.show();
                return false;
            }
        }
        if (radio_ng.isChecked()){
            if (!(adapter_yy.getSelectData().size()>0)){
                dialog.setMessage("请先选择原因");
                dialog.show();
                return false;
            }


            upload_data=new ArrayList<>();
            for (int i=0;i<data_cong.size();i++){
                if (!data_cong.get(i).get("lab_7").equals(data_cong.get(i).get("lab_8"))){
                    upload_data.add(data_cong.get(i));
                }
            }
            if (!(upload_data.size()>0)){
                dialog.setMessage("腔数未发生改变");
                dialog.show();
                return false;
            }
        }
        return true;
    }


    private void uploadData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (radio_ok.isChecked()){//Ok情况下
                    for (int i=0;i<data_cong.size();i++){
                        Map<String,String>map=data_cong.get(i);

                        List<List<String>>list=NetHelper.getQuerysqlResult(
                                "Exec PAD_Up_Xjllist   'A','"+jtbh+"','"+zzdh_text.getText().toString()+"','"+map.get("lab_7")+"'," +
                                        "'"+map.get("lab_8")+"','OK','','"+wkno+"'");
                        if (list!=null){
                            if (list.size()>0){
                                if (list.get(0).size()>0){
                                    if (list.get(0).get(0).equals("OK")){
                                        finish();
                                    }else {
                                        Message msg=handler.obtainMessage();
                                        msg.what=0x102;
                                        msg.obj="品质异常发出错误："+list.get(0).get(0);
                                        handler.sendMessage(msg);
                                    }
                                }
                            }
                        }else {
                            AppUtils.uploadNetworkError("Exec PAD_Up_Xjllist",jtbh,sharedPreferences.getString("mac",""));
                        }
                    }
                }else if (radio_ng.isChecked()){//NG情况下
                    String yydms="";
                    for (int i=0;i<adapter_yy.getSelectData().size();i++){
                        yydms=yydms+adapter_yy.getSelectData().get(i).get("lab_1")+";";
                    }
                    for (int i=0;i<data_cong.size();i++){
                        Map<String,String>map=data_cong.get(i);

                        List<List<String>>list=NetHelper.getQuerysqlResult(
                                "Exec PAD_Up_Xjllist  'A','"+jtbh+"','"+zzdh_text.getText().toString()+"','"+map.get("lab_7")+"'," +
                                        "'"+map.get("lab_8")+"','NG','"+yydms+"','"+wkno+"'");
                        if (list!=null){
                            if (list.size()>0){
                                if (list.get(0).size()>0){
                                    if (list.get(0).get(0).equals("OK")){
                                        List<List<String>>list2=NetHelper.getQuerysqlResult(
                                                "Exec PAD_Up_Xjllist  'B','"+jtbh+"','',''," +
                                                        "'','','','"+wkno+"'");
                                        if (list2!=null){
                                            if (list2.size()>0){
                                                if (list2.get(0).size()>0){
                                                    if (list2.get(0).get(0).equals("OK")){
                                                        AppUtils.sendUpdateInfoFragmentReceiver(PgxjActivity.this);
                                                        AppUtils.sendReturnToInfoReceiver(PgxjActivity.this);
                                                        finish();
                                                    }else {
                                                        Message msg=handler.obtainMessage();
                                                        msg.what=0x102;
                                                        msg.obj="品质异常发出错误："+list2.get(0).get(0);
                                                        handler.sendMessage(msg);
                                                    }
                                                }
                                            }
                                        }
                                    }else {
                                        Message msg=handler.obtainMessage();
                                        msg.what=0x102;
                                        msg.obj="品质异常发出错误："+list.get(0).get(0);
                                        handler.sendMessage(msg);
                                    }
                                }
                            }
                        }else {
                            AppUtils.uploadNetworkError("Exec PAD_Up_Xjllist",jtbh,sharedPreferences.getString("mac",""));
                        }
                    }
                }
            }
        }).start();
    }




    @Override
    public void onClick(View v) {
        AppUtils.sendCountdownReceiver(PgxjActivity.this);
        switch (v.getId()){
            case R.id.cancle_btn:
                finish();
                break;
            case R.id.save_btn:
                if (isReady()){
                    uploadData();
                }
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
    }
}
