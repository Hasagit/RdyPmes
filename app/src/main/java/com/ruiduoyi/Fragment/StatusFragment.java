package com.ruiduoyi.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.ruiduoyi.R;
import com.ruiduoyi.activity.BlYyfxActivity;
import com.ruiduoyi.activity.SbxxActivity;
import com.ruiduoyi.activity.MjxxActivity;
import com.ruiduoyi.activity.PzglActivity;
import com.ruiduoyi.activity.ScrzActivity;
import com.ruiduoyi.activity.DialogGActivity;
import com.ruiduoyi.activity.ZyzdActivity;
import com.ruiduoyi.model.NetHelper;
import com.ruiduoyi.utils.AppUtils;
import com.ruiduoyi.view.PopupDialog;

import java.util.List;

public class StatusFragment extends Fragment implements View.OnClickListener{
    private CardView cardView_g1,cardView_g2,cardView_g3,cardView_g4,cardView_g5,cardView_g6,
            cardView_g7,cardView_g8,cardView_g9,cardView_g10,cardView_g11,cardView_g12,cardView_g13,
            cardView_g14,cardView_g15,cardView_g16,cardView_g17,cardView_g18,cardView_g19,cardView_g20,cardView_g21,
            cardView_b1,cardView_b2,cardView_b3,cardView_b4,cardView_b5,cardView_b6,cardView_b7,
            cardView_b8,cardView_b9,cardView_b10,cardView_b11,cardView_b12;
    private String startType,startZldm,startZlmc;
    private Animation anim;
    private SharedPreferences sharedPreferences;
    private PopupDialog dialog;
    public StatusFragment() {

    }


    public static StatusFragment newInstance() {
        StatusFragment fragment = new StatusFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        anim= AnimationUtils.loadAnimation(getContext(),R.anim.scale_anim);
        sharedPreferences=getContext().getSharedPreferences("info", Context.MODE_PRIVATE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_status, container, false);
        initView(view);
        return view;
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x100:
                    List<String>list=(List<String>)msg.obj;
                    startType=list.get(2);
                    startZldm=list.get(0);
                    startZlmc=list.get(1);
                    break;
                case 0x101:
                    break;
            }
        }
    };


    private void exitEven(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String zldming;
                String zlmcing;
                String waring;

                //请求当前指令及是否超时信息
                List<List<String>>list2= NetHelper.getQuerysqlResult("Exec PAD_Get_JtmZtInfo '"+sharedPreferences.getString("jtbh","")+"'");
                if(list2!=null) {
                    if (list2.size() > 0) {
                        if (list2.get(0).size() > 11) {
                            zldming= list2.get(0).get(1);
                            waring = list2.get(0).get(5);
                        }else {
                            return;
                        }
                    }else {
                        return;
                    }
                }else {
                    return;
                }

                List<List<String>>list= NetHelper.getQuerysqlResult("Exec PAD_Get_ZlmYywh 'A','"+sharedPreferences.getString("jtbh","")+"','"+zldming+"'");

                if (list!=null){
                    if (list.size()>0){
                        if (list.get(0).size()>2){
                            String startType=list.get(0).get(2);
                            zlmcing=list.get(0).get(1);
                            switch (startType) {
                                case "A":
                                    Intent intent_g21 = new Intent(getContext(), DialogGActivity.class);
                                    intent_g21.putExtra("zldm", getContext().getString(R.string.js));
                                    intent_g21.putExtra("title", "结束");
                                    intent_g21.putExtra("type", "OPR");
                                    startActivity(intent_g21);
                                    break;
                                case "B":
                                    Intent intent1 = new Intent(getContext(), BlYyfxActivity.class);
                                    intent1.putExtra("title", zlmcing);
                                    intent1.putExtra("zldm",zldming);
                                    startActivity(intent1);
                                    break;
                                case "C":
                                    Intent intent2 = new Intent(getContext(), BlYyfxActivity.class);
                                    intent2.putExtra("title", zlmcing);
                                    intent2.putExtra("zldm", zldming);
                                    startActivity(intent2);
                                    break;
                                case "S":
                                    if (waring.equals("1")){//如果超时了则必须要弹出蓝框
                                        Intent intent_blyyfx=new Intent(getContext(),BlYyfxActivity.class);
                                        intent_blyyfx.putExtra("title",zlmcing);
                                        intent_blyyfx.putExtra("zldm",zldming);
                                        startActivity(intent_blyyfx);
                                    }else {//如果没有超时则直接启动结束
                                        Intent intent_js = new Intent(getContext(), DialogGActivity.class);
                                        intent_js.putExtra("zldm", getContext().getString(R.string.js));
                                        intent_js.putExtra("title", "结束");
                                        intent_js.putExtra("type", "OPR");
                                        startActivity(intent_js);
                                    }
                                    break;
                                default:
                                    Intent intent_g3 = new Intent(getContext(), DialogGActivity.class);
                                    intent_g3.putExtra("zldm", getContext().getString(R.string.js));
                                    intent_g3.putExtra("title", "结束");
                                    intent_g3.putExtra("type", "OPR");
                                    startActivity(intent_g3);
                                    break;
                            }
                        }
                    }
                }
            }
        }).start();
    }

    public void initView(View view){
        cardView_b1=(CardView)view.findViewById(R.id.gdgl);
        cardView_b2=(CardView)view.findViewById(R.id.blfx);
        cardView_b3=(CardView)view.findViewById(R.id.ycfx);
        cardView_b4=(CardView)view.findViewById(R.id.jtjqsbg);
        cardView_b5=(CardView)view.findViewById(R.id.hddj);
        cardView_b6=(CardView)view.findViewById(R.id.gycs);
        cardView_b7=(CardView)view.findViewById(R.id.djby);
        cardView_b8=(CardView)view.findViewById(R.id.sbxx);
        cardView_b9=(CardView)view.findViewById(R.id.mjxx);
        cardView_b10=(CardView)view.findViewById(R.id.pzgl);
        cardView_b11=(CardView)view.findViewById(R.id.scrz);
        cardView_b12=(CardView)view.findViewById(R.id.zyzd);
        cardView_g1=(CardView)view.findViewById(R.id.zmsw);
        cardView_g2=(CardView)view.findViewById(R.id.cxpt);
        cardView_g3=(CardView)view.findViewById(R.id.kjsl);
        cardView_g4=(CardView)view.findViewById(R.id.ds);
        cardView_g5=(CardView)view.findViewById(R.id.sjqc);
        cardView_g6=(CardView)view.findViewById(R.id.sjjc);
        cardView_g7=(CardView)view.findViewById(R.id.pzyc);
        cardView_g8=(CardView)view.findViewById(R.id.tiaoji);
        cardView_g9=(CardView)view.findViewById(R.id.ts);
        cardView_g10=(CardView)view.findViewById(R.id.tingji);
        cardView_g11=(CardView)view.findViewById(R.id.dl);
        cardView_g12=(CardView)view.findViewById(R.id.by);
        cardView_g13=(CardView)view.findViewById(R.id.sm);
        cardView_g14=(CardView)view.findViewById(R.id.sl);
        cardView_g15=(CardView)view.findViewById(R.id.mjwx);
        cardView_g16=(CardView)view.findViewById(R.id.jtwx);
        cardView_g17=(CardView)view.findViewById(R.id.zyg) ;
        cardView_g18=(CardView)view.findViewById(R.id.cm) ;
        cardView_g19=(CardView)view.findViewById(R.id.rysg) ;
        cardView_g20=(CardView)view.findViewById(R.id.pgxj) ;
        cardView_g21=(CardView)view.findViewById(R.id.js) ;
        cardView_g1.setOnClickListener(this);
        cardView_g2.setOnClickListener(this);
        cardView_g3.setOnClickListener(this);
        cardView_g4.setOnClickListener(this);
        cardView_g5.setOnClickListener(this);
        cardView_g6.setOnClickListener(this);
        cardView_g7.setOnClickListener(this);
        cardView_g8.setOnClickListener(this);
        cardView_g9.setOnClickListener(this);
        cardView_g10.setOnClickListener(this);
        cardView_g11.setOnClickListener(this);
        cardView_g12.setOnClickListener(this);
        cardView_g13.setOnClickListener(this);
        cardView_g14.setOnClickListener(this);
        cardView_g15.setOnClickListener(this);
        cardView_g16.setOnClickListener(this);
        cardView_g17.setOnClickListener(this);
        cardView_g18.setOnClickListener(this);
        cardView_g19.setOnClickListener(this);
        cardView_g20.setOnClickListener(this);
        cardView_g21.setOnClickListener(this);
        cardView_b1.setOnClickListener(this);
        cardView_b2.setOnClickListener(this);
        cardView_b3.setOnClickListener(this);
        cardView_b4.setOnClickListener(this);
        cardView_b5.setOnClickListener(this);
        cardView_b6.setOnClickListener(this);
        cardView_b7.setOnClickListener(this);
        cardView_b8.setOnClickListener(this);
        cardView_b9.setOnClickListener(this);
        cardView_b10.setOnClickListener(this);
        cardView_b11.setOnClickListener(this);
        cardView_b12.setOnClickListener(this);
        dialog=new PopupDialog(getActivity(),400,350);
        dialog.setTitle("提示");
        dialog.getCancle_btn().setVisibility(View.GONE);
        dialog.getOkbtn().setText("确定");
        dialog.getOkbtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setMessage("正在指令状态下，请先结束指令");
    }

    private void startActivityByNetResult(final String zldm, final String title, final String type){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<List<String>>list= NetHelper.getQuerysqlResult("Exec PAD_Get_ZlmYywh 'A',"+sharedPreferences.getString("jtbh","")+",'"+zldm+"'");
                if (list!=null){
                    if (list.size()>0){
                        if (list.get(0).size()>2){
                            Message msg=handler.obtainMessage();
                            msg.obj=list.get(0);
                            msg.what=0x100;
                            handler.sendMessage(msg);
                            Intent intent;
                            switch (list.get(0).get(2)){
                                case "A":
                                    intent=new Intent(getContext(), BlYyfxActivity.class);
                                    intent.putExtra("title",title);
                                    intent.putExtra("zldm",zldm);
                                    startActivity(intent);
                                    break;
                                case "B":
                                    intent=new Intent(getContext(), DialogGActivity.class);
                                    intent.putExtra("title",title);
                                    intent.putExtra("zldm",zldm);
                                    intent.putExtra("type",type);
                                    startActivity(intent);
                                    break;
                                case "C":
                                    intent=new Intent(getContext(), BlYyfxActivity.class);
                                    intent.putExtra("title",title);
                                    intent.putExtra("zldm",zldm);
                                    startActivity(intent);
                                    break;
                                case "S":
                                    //请求当前指令及是否超时信息
                                    List<List<String>>list2= NetHelper.getQuerysqlResult("Exec PAD_Get_JtmZtInfo '"+sharedPreferences.getString("jtbh","")+"'");
                                    if(list2!=null) {
                                        if (list2.size() > 0) {
                                            if (list2.get(0).size() > 11) {
                                                String waring = list2.get(0).get(5);
                                                if (waring.equals("1")){//如果超时了则必须要弹出蓝框
                                                    Intent intent_blyyfx=new Intent(getContext(),BlYyfxActivity.class);
                                                    intent_blyyfx.putExtra("title",zldm);
                                                    intent_blyyfx.putExtra("zldm",title);
                                                    startActivity(intent_blyyfx);
                                                }else {//如果没有超时则直接启动结束
                                                    Intent intent_js = new Intent(getContext(), DialogGActivity.class);
                                                    intent_js.putExtra("zldm", zldm);
                                                    intent_js.putExtra("title", title);
                                                    intent_js.putExtra("type", "OPR");
                                                    startActivity(intent_js);
                                                }
                                            }
                                        }
                                    }
                                    break;
                                default:
                                    intent=new Intent(getContext(), DialogGActivity.class);
                                    intent.putExtra("title",title);
                                    intent.putExtra("zldm",zldm);
                                    intent.putExtra("type",type);
                                    startActivity(intent);
                                    break;
                            }
                        }
                    }
                }
            }
        }).start();
    }


    private boolean isReady(){
        String zldming=sharedPreferences.getString("zldm_ss","");
        if (zldming.equals("50")|zldming.equals("51")|zldming.equals("52")|zldming.equals("53")|zldming.equals("54")|
                zldming.equals("55")|zldming.equals("56")|zldming.equals("57")|zldming.equals("58")|
                zldming.equals("59")|zldming.equals("60")|zldming.equals("61")|zldming.equals("62")|
                zldming.equals("63")|zldming.equals("64")|zldming.equals("65")|zldming.equals("66")|
                zldming.equals("67")|zldming.equals("68")|zldming.equals("69")|zldming.equals("70")){
            dialog.show();
            return false;
        }
        return true;
    }


    @Override
    public void onClick(View v) {
        AppUtils.sendCountdownReceiver(getContext());
        switch (v.getId()){
            case R.id.sbxx://设备信息
                cardView_b8.startAnimation(anim);
                Intent intent=new Intent(getContext(), SbxxActivity.class);
                startActivity(intent);
                break;
            case R.id.mjxx:
                cardView_b9.startAnimation(anim);
                Intent intent_b2=new Intent(getContext(), MjxxActivity.class);
                startActivity(intent_b2);
                break;
            case R.id.gdgl:
                cardView_b1.startAnimation(anim);
                Intent intent_b3=new Intent(getContext(),DialogGActivity.class );
                intent_b3.putExtra("title","工单管理");
                intent_b3.putExtra("zldm",getContext().getString(R.string.gdgl));
                intent_b3.putExtra("type","DOC");
                startActivity(intent_b3);
                break;
            case R.id.pzgl:
                cardView_b10.startAnimation(anim);
                Intent intent_b4=new Intent(getContext(), PzglActivity.class);
                startActivity(intent_b4);
                break;
            case R.id.scrz:
                cardView_b11.startAnimation(anim);
                Intent intent_b6=new Intent(getContext(), ScrzActivity.class);
                startActivity(intent_b6);
                break;
            case R.id.ycfx:
                cardView_b3.startAnimation(anim);
                Intent intent_b7=new Intent(getContext(),DialogGActivity.class );
                intent_b7.putExtra("title","异常分析");
                intent_b7.putExtra("zldm",getContext().getString(R.string.ycfx));
                intent_b7.putExtra("type","DOC");
                startActivity(intent_b7);
                break;
            case R.id.blfx:
                cardView_b2.startAnimation(anim);
                Intent intent_b8=new Intent(getContext(), DialogGActivity.class);
                intent_b8.putExtra("title","不良分析");
                intent_b8.putExtra("zldm",getContext().getString(R.string.blfx));
                intent_b8.putExtra("type","DOC");
                startActivity(intent_b8);
                break;
            case R.id.jtjqsbg:
                cardView_b4.startAnimation(anim);
                Intent intent_10=new Intent(getContext(), DialogGActivity.class);
                intent_10.putExtra("title","腔数变更");
                intent_10.putExtra("zldm",getResources().getString(R.string.jtjqsbg));
                intent_10.putExtra("type","DOC");
                startActivity(intent_10);
                break;
            case R.id.hddj:
                cardView_b5.startAnimation(anim);
                Intent intent_hddj=new Intent(getContext(), DialogGActivity.class);
                intent_hddj.putExtra("title","耗电登记");
                intent_hddj.putExtra("zldm",getResources().getString(R.string.hddj));
                intent_hddj.putExtra("type","DOC");
                startActivity(intent_hddj);
                break;
            case R.id.gycs:
                cardView_b6.startAnimation(anim);
                break;
            case R.id.zyzd:
                cardView_b12.startAnimation(anim);
                Intent intent_zyzd=new Intent(getContext(), ZyzdActivity.class);
                startActivity(intent_zyzd);
                break;
            case R.id.djby:
                cardView_b7.startAnimation(anim);
                Intent intent_djby=new Intent(getContext(), DialogGActivity.class);
                intent_djby.putExtra("title","设备点检");
                intent_djby.putExtra("type","DOC");
                intent_djby.putExtra("zldm",getResources().getString(R.string.sbdj));
                startActivity(intent_djby);
                break;




            case  R.id.zmsw:
                if (isReady()){
                    cardView_g1.startAnimation(anim);
                    startActivityByNetResult(getContext().getString(R.string.zmsw),"装模升温","OPR");
                }
                break;
            case  R.id.cxpt:
                if (isReady()){
                    cardView_g2.startAnimation(anim);
                    startActivityByNetResult(getContext().getString(R.string.cxpt),"冲洗炮筒","OPR");
                }
                break;
            case  R.id.kjsl:
                if (isReady()){
                    cardView_g3.startAnimation(anim);
                    startActivityByNetResult(getContext().getString(R.string.kjsl),"开机试料","OPR");
                }
                break;
            case  R.id.ds:
                if (isReady()){
                    cardView_g4.startAnimation(anim);
                    startActivityByNetResult(getContext().getString(R.string.ds),"定色","OPR");
                }
                break;
            case  R.id.sjqc:
                if (isReady()){
                    cardView_g5.startAnimation(anim);
                    startActivityByNetResult(getContext().getString(R.string.sjqc),"三级清场","OPR");
                }
                break;
            case  R.id.sjjc:
                if (isReady()){
                    cardView_g6.startAnimation(anim);
                    startActivityByNetResult(getContext().getString(R.string.sjjc),"首件检查","OPR");
                }
                break;
            case  R.id.pzyc:
                if (isReady()){
                    cardView_g7.startAnimation(anim);
                    startActivityByNetResult(getContext().getString(R.string.pzyc),"品质异常","OPR");
                }
                break;
            case  R.id.tiaoji:
                if (isReady()){
                    cardView_g8.startAnimation(anim);
                    startActivityByNetResult(getContext().getString(R.string.tiaoji),"调机","OPR");
                }
                break;
            case  R.id.ts:
                if (isReady()){
                    cardView_g9.startAnimation(anim);
                    startActivityByNetResult(getContext().getString(R.string.ts),"调色","OPR");
                }
                break;
            case  R.id.tingji:
                if (isReady()){
                    cardView_g10.startAnimation(anim);
                    startActivityByNetResult(getContext().getString(R.string.tingji),"停机","OPR");
                }
                break;
            case  R.id.dl:
                if (isReady()){
                    cardView_g11.startAnimation(anim);
                    startActivityByNetResult(getContext().getString(R.string.dl),"待料","OPR");
                }
                break;
            case R.id.by:
                if (isReady()){
                    cardView_g12.startAnimation(anim);
                    startActivityByNetResult(getContext().getString(R.string.by),"保养","OPR");
                }
                break;
            case  R.id.sm:
                if (isReady()){
                    cardView_g13.startAnimation(anim);
                    startActivityByNetResult(getContext().getString(R.string.sm),"试模","OPR");
                }
                break;
            case  R.id.sl:
                if (isReady()){
                    cardView_g14.startAnimation(anim);
                    startActivityByNetResult(getContext().getString(R.string.sl),"试料","OPR");
                }
                break;
            case  R.id.mjwx:
                if (isReady()){
                    cardView_g15.startAnimation(anim);
                    //getActivity().finish();
                    startActivityByNetResult(getContext().getString(R.string.mjwx),"模具维修","OPR");
                }
                break;
            case R.id.jtwx:
                if (isReady()){
                    cardView_g16.startAnimation(anim);
                    startActivityByNetResult(getContext().getString(R.string.jtwx),"机台维修","OPR");
                }
                break;
            case R.id.zyg:
               if (isReady()){
                   cardView_g17.startAnimation(anim);
                   startActivityByNetResult(getContext().getString(R.string.zyg),"粘样盖","OPR");
               }
                break;
            case R.id.cm:
               if (isReady()){
                   cardView_g18.startAnimation(anim);
                   startActivityByNetResult(getContext().getString(R.string.cm),"拆模","OPR");
               }
                break;
            case R.id.rysg:
                cardView_g19.startAnimation(anim);
                startActivityByNetResult(getContext().getString(R.string.rysg),"人员上岗","OPR");
                break;
            case R.id.pgxj:
                cardView_g20.startAnimation(anim);
                Intent intent_g20=new Intent(getContext(),DialogGActivity.class);
                intent_g20.putExtra("zldm",getResources().getString(R.string.pgxj));
                intent_g20.putExtra("title","品管巡机");
                intent_g20.putExtra("type","DOC");
                startActivity(intent_g20);
                //startActivityByNetResult(getContext().getString(R.string.pgxj),"品管巡机","OPR");
                break;
            case R.id.js:
                cardView_g21.startAnimation(anim);
                exitEven();
                break;
        }
    }


    private String getZlmcByZldm(String zldm){
        if (zldm.equals(getResources().getString(R.string.zmsw))){
            return "装模升温";
        }else if (zldm.equals(getResources().getString(R.string.cxpt))){
            return "冲洗炮筒";
        }else if (zldm.equals(getResources().getString(R.string.kjsl))){
            return "开机试料";
        }else if (zldm.equals(getResources().getString(R.string.ds))){
            return "定色";
        }else if (zldm.equals(getResources().getString(R.string.sjqc))){
            return "三级清场";
        }else if (zldm.equals(getResources().getString(R.string.sjjc))){
            return "首件检查";
        }else if (zldm.equals(getResources().getString(R.string.pzyc))){
            return "品质异常";
        }else if (zldm.equals(getResources().getString(R.string.tiaoji))){
            return "调机";
        }else if (zldm.equals(getResources().getString(R.string.ts))){
            return "调色";
        }else if (zldm.equals(getResources().getString(R.string.tingji))){
            return "停机";
        }else if (zldm.equals(getResources().getString(R.string.dl))){
            return "待料";
        }else if (zldm.equals(getResources().getString(R.string.by))){
            return "保养";
        }else if (zldm.equals(getResources().getString(R.string.sm))){
            return "试模";
        }else if (zldm.equals(getResources().getString(R.string.sl))){
            return "试料";
        }else if (zldm.equals(getResources().getString(R.string.mjwx))){
            return "模具维修";
        }else if (zldm.equals(getResources().getString(R.string.jtwx))){
            return "机台维修";
        }else if (zldm.equals(getResources().getString(R.string.zyg))){
            return "粘样盖";
        }else if (zldm.equals(getResources().getString(R.string.cm))){
            return "拆模";
        }else if (zldm.equals(getResources().getString(R.string.rysg))){
            return "人员上岗";
        }else if (zldm.equals(getResources().getString(R.string.pgxj))){
            return "品管巡机";
        }else if (zldm.equals(getResources().getString(R.string.js))){
            return "结束";
        }
        return "";
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dialog!=null){
            dialog.dismiss();
        }
    }
}



