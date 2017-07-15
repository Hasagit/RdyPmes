package com.ruiduoyi.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.ruiduoyi.R;
import com.ruiduoyi.activity.BlYyfxActivity;
import com.ruiduoyi.activity.SbxxActivity;
import com.ruiduoyi.activity.MjxxActivity;
import com.ruiduoyi.activity.PzglActivity;
import com.ruiduoyi.activity.DialogB5Activty;
import com.ruiduoyi.activity.ScrzActivity;
import com.ruiduoyi.activity.OeeActivity;
import com.ruiduoyi.activity.DialogGActivity;
import com.ruiduoyi.activity.DialogG2Activity;
import com.ruiduoyi.model.NetHelper;
import com.ruiduoyi.utils.AppUtils;

import java.util.List;

public class StatusFragment extends Fragment implements View.OnClickListener{
    private CardView cardView_g1,cardView_g2,cardView_g3,cardView_g4,cardView_g5,cardView_g6,
            cardView_g7,cardView_g8,cardView_g9,cardView_g10,cardView_g11,cardView_g12,cardView_g13,
            cardView_g14,cardView_g15,cardView_g16,cardView_g17,cardView_g18,cardView_g19,cardView_g20,cardView_g21,
            cardView_b1,cardView_b2,cardView_b3,cardView_b4,cardView_b5,cardView_b6,cardView_b7,
            cardView_b8;
    private String startType,startZldm,startZlmc;
    private Animation anim;
    private SharedPreferences sharedPreferences;
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


    private void getStartType(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.w("zldm_ss",sharedPreferences.getString("zldm_ss",""));
                List<List<String>>list= NetHelper.getQuerysqlResult("Exec PAD_Get_ZlmYywh 'A','"+sharedPreferences.getString("zldm_ss","")+"'");
                if (list!=null){
                    if (list.size()>0){
                        if (list.get(0).size()>2){
                            String startType=list.get(0).get(2);
                            String startZlmc=list.get(0).get(1);
                            String startZldm=list.get(0).get(0);
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
                                    intent1.putExtra("title", startZlmc);
                                    intent1.putExtra("zldm", startZldm);
                                    startActivity(intent1);
                                    break;
                                case "C":
                                    Intent intent2 = new Intent(getContext(), BlYyfxActivity.class);
                                    intent2.putExtra("title", startZlmc);
                                    intent2.putExtra("zldm", startZldm);
                                    startActivity(intent2);
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
        cardView_b1=(CardView)view.findViewById(R.id.sbxx);
        cardView_b2=(CardView)view.findViewById(R.id.mjxx);
        cardView_b3=(CardView)view.findViewById(R.id.gdgl);
        cardView_b4=(CardView)view.findViewById(R.id.pzgl);
        cardView_b5=(CardView)view.findViewById(R.id.scrz);
        cardView_b6=(CardView)view.findViewById(R.id.ycfx);
        cardView_b7=(CardView)view.findViewById(R.id.blfx);
        cardView_b8=(CardView)view.findViewById(R.id.oee);
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
    }

    private void startActivityByNetResult(final String zldm, final String title, final String type){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<List<String>>list= NetHelper.getQuerysqlResult("Exec PAD_Get_ZlmYywh 'A','"+zldm+"'");
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

    @Override
    public void onClick(View v) {
        AppUtils.sendCountdownReceiver(getContext());
        switch (v.getId()){
            case R.id.sbxx://设备信息
                cardView_b1.startAnimation(anim);
                Intent intent=new Intent(getContext(), SbxxActivity.class);
                startActivity(intent);
                break;
            case R.id.mjxx:
                cardView_b2.startAnimation(anim);
                Intent intent_b2=new Intent(getContext(), MjxxActivity.class);
                startActivity(intent_b2);
                break;
            case R.id.gdgl:
                cardView_b3.startAnimation(anim);
                Intent intent_b3=new Intent(getContext(),DialogGActivity.class );
                intent_b3.putExtra("title","工单管理");
                intent_b3.putExtra("zldm",getContext().getString(R.string.gdgl));
                intent_b3.putExtra("type","DOC");
                startActivity(intent_b3);
                break;
            case R.id.pzgl:
                cardView_b4.startAnimation(anim);
                Intent intent_b4=new Intent(getContext(), PzglActivity.class);
                startActivity(intent_b4);
                break;
            case R.id.scrz:
                cardView_b5.startAnimation(anim);
                Intent intent_b6=new Intent(getContext(), ScrzActivity.class);
                startActivity(intent_b6);
                break;
            case R.id.ycfx:
                cardView_b6.startAnimation(anim);
                Intent intent_b7=new Intent(getContext(),DialogGActivity.class );
                intent_b7.putExtra("title","异常分析");
                intent_b7.putExtra("zldm",getContext().getString(R.string.ycfx));
                intent_b7.putExtra("type","DOC");
                startActivity(intent_b7);
                break;
            case R.id.blfx:
                cardView_b7.startAnimation(anim);
                Intent intent_b8=new Intent(getContext(), DialogGActivity.class);
                intent_b8.putExtra("title","不良分析");
                intent_b8.putExtra("zldm",getContext().getString(R.string.blfx));
                intent_b8.putExtra("type","DOC");
                startActivity(intent_b8);
                break;
            case R.id.oee:

                cardView_b8.startAnimation(anim);
                Intent intent_b9=new Intent(getContext(), OeeActivity.class);
                startActivity(intent_b9);
                break;
            case  R.id.zmsw:
                cardView_g1.startAnimation(anim);
                startActivityByNetResult(getContext().getString(R.string.zmsw),"装模升温","OPR");
                break;
            case  R.id.cxpt:
                cardView_g2.startAnimation(anim);
                startActivityByNetResult(getContext().getString(R.string.cxpt),"冲洗炮筒","OPR");
                break;
            case  R.id.kjsl:
                cardView_g3.startAnimation(anim);
                startActivityByNetResult(getContext().getString(R.string.kjsl),"开机试料","OPR");
                break;
            case  R.id.ds:
                cardView_g4.startAnimation(anim);
                startActivityByNetResult(getContext().getString(R.string.ds),"定色","OPR");
                break;
            case  R.id.sjqc:
                cardView_g5.startAnimation(anim);
                startActivityByNetResult(getContext().getString(R.string.sjqc),"三级清场","OPR");
                break;
            case  R.id.sjjc:
                cardView_g6.startAnimation(anim);
                startActivityByNetResult(getContext().getString(R.string.sjjc),"首件检查","OPR");
                break;
            case  R.id.pzyc:
                cardView_g7.startAnimation(anim);
                startActivityByNetResult(getContext().getString(R.string.pzyc),"品质异常","OPR");
                break;
            case  R.id.tiaoji:
                cardView_g8.startAnimation(anim);
                startActivityByNetResult(getContext().getString(R.string.tiaoji),"调机","OPR");
                break;
            case  R.id.ts:
                cardView_g9.startAnimation(anim);
                startActivityByNetResult(getContext().getString(R.string.ts),"调色","OPR");
                break;
            case  R.id.tingji:
                cardView_g10.startAnimation(anim);
                startActivityByNetResult(getContext().getString(R.string.tingji),"停机","OPR");
                break;
            case  R.id.dl:
                cardView_g11.startAnimation(anim);
                startActivityByNetResult(getContext().getString(R.string.dl),"待料","OPR");
                break;
            case R.id.by:
                cardView_g12.startAnimation(anim);
                startActivityByNetResult(getContext().getString(R.string.by),"保养","OPR");
                break;
            case  R.id.sm:
                cardView_g13.startAnimation(anim);
                startActivityByNetResult(getContext().getString(R.string.sm),"试模","OPR");
                break;
            case  R.id.sl:
                cardView_g14.startAnimation(anim);
                startActivityByNetResult(getContext().getString(R.string.sl),"试料","OPR");
                break;
            case  R.id.mjwx:
                cardView_g15.startAnimation(anim);
                //getActivity().finish();
                startActivityByNetResult(getContext().getString(R.string.mjwx),"模具维修","OPR");
                break;
            case R.id.jtwx:
                cardView_g16.startAnimation(anim);
                startActivityByNetResult(getContext().getString(R.string.jtwx),"机台维修","OPR");
                break;
            case R.id.zyg:
                cardView_g17.startAnimation(anim);
                startActivityByNetResult(getContext().getString(R.string.zyg),"粘样盖","OPR");
                break;
            case R.id.cm:
                cardView_g18.startAnimation(anim);
                startActivityByNetResult(getContext().getString(R.string.cm),"拆模","OPR");
                break;
            case R.id.rysg:
                cardView_g19.startAnimation(anim);
                startActivityByNetResult(getContext().getString(R.string.rysg),"人员上岗","OPR");
                break;
            case R.id.pgxj:
                startActivityByNetResult(getContext().getString(R.string.pgxj),"品管巡机","OPR");
                break;
            case R.id.js:
                cardView_g21.startAnimation(anim);
                if (startType!=null){
                    switch (startType){
                        case "A":
                            Intent intent_g21=new Intent(getContext(), DialogGActivity.class);
                            intent_g21.putExtra("zldm",getContext().getString(R.string.js));
                            intent_g21.putExtra("title","结束");
                            intent_g21.putExtra("type","OPR");
                            startActivity(intent_g21);
                            break;
                        case "B":
                            Intent intent1=new Intent(getContext(), BlYyfxActivity.class);
                            intent1.putExtra("title",startZlmc);
                            intent1.putExtra("zldm",startZldm);
                            startActivity(intent1);
                            break;
                        case "C":
                            Intent intent2=new Intent(getContext(), BlYyfxActivity.class);
                            intent2.putExtra("title",startZlmc);
                            intent2.putExtra("zldm",startZldm);
                            startActivity(intent2);
                            break;
                        default:
                            Intent intent_g3=new Intent(getContext(), DialogGActivity.class);
                            intent_g3.putExtra("zldm",getContext().getString(R.string.js));
                            intent_g3.putExtra("title","结束");
                            intent_g3.putExtra("type","OPR");
                            startActivity(intent_g3);
                            break;
                    }
                }else {
                    getStartType();
                }
                break;
        }
    }
}
