package com.ruiduoyi.Fragment;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.Toast;

import com.ruiduoyi.R;
import com.ruiduoyi.activity.SbxxActivity;
import com.ruiduoyi.activity.MjxxActivity;
import com.ruiduoyi.activity.PzglActivity;
import com.ruiduoyi.activity.DialogB5Activty;
import com.ruiduoyi.activity.ScrzActivity;
import com.ruiduoyi.activity.OeeActivity;
import com.ruiduoyi.activity.DialogGActivity;
import com.ruiduoyi.activity.DialogG2Activity;
import com.ruiduoyi.utils.AppUtils;

public class StatusFragment extends Fragment implements View.OnClickListener{
    private CardView cardView_g1,cardView_g2,cardView_g3,cardView_g4,cardView_g5,cardView_g6,
            cardView_g7,cardView_g8,cardView_g9,cardView_g10,cardView_g11,cardView_g12,cardView_g13,
            cardView_g14,cardView_g15,cardView_g16,cardView_g17,cardView_g18,cardView_g19,cardView_g20,cardView_g21,
            cardView_b1,cardView_b2,cardView_b3,cardView_b4,cardView_b5,cardView_b6,cardView_b7,
            cardView_b8;
    private Animation anim;

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
                    Toast.makeText(getContext(),"数据库连接成功",Toast.LENGTH_SHORT).show();
                    break;
                case 0x101:
                    Toast.makeText(getContext(),"数据库连接失败",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


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
                intent_b3.putExtra("title","【工单管理】");
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
                intent_b7.putExtra("title","【异常分析】");
                intent_b7.putExtra("zldm",getContext().getString(R.string.ycfx));
                intent_b7.putExtra("type","DOC");
                startActivity(intent_b7);
                break;
            case R.id.blfx:
                cardView_b7.startAnimation(anim);
                Intent intent_b8=new Intent(getContext(), DialogGActivity.class);
                intent_b8.putExtra("title","【不良分析】");
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
                Intent intent_g1=new Intent(getContext(), DialogGActivity.class);
                intent_g1.putExtra("title","装模升温");
                intent_g1.putExtra("zldm",getContext().getString(R.string.zmsw));
                intent_g1.putExtra("type","OPR");
                startActivity(intent_g1);
                break;
            case  R.id.cxpt:
                cardView_g2.startAnimation(anim);
                Intent intent_g2=new Intent(getContext(), DialogGActivity.class);
                intent_g2.putExtra("title","冲洗炮筒");
                intent_g2.putExtra("zldm",getContext().getString(R.string.cxpt));
                intent_g2.putExtra("type","OPR");
                startActivity(intent_g2);
                break;
            case  R.id.kjsl:
                cardView_g3.startAnimation(anim);
                Intent intent_g3=new Intent(getContext(), DialogGActivity.class);
                intent_g3.putExtra("title","开机试料");
                intent_g3.putExtra("zldm",getContext().getString(R.string.kjsl));
                intent_g3.putExtra("type","OPR");
                startActivity(intent_g3);
                break;
            case  R.id.ds:
                cardView_g4.startAnimation(anim);
                Intent intent_g4=new Intent(getContext(), DialogGActivity.class);
                intent_g4.putExtra("title","定色");
                intent_g4.putExtra("zldm",getContext().getString(R.string.ds));
                intent_g4.putExtra("type","OPR");
                startActivity(intent_g4);
                break;
            case  R.id.sjqc:
                cardView_g5.startAnimation(anim);
                Intent intent_g5=new Intent(getContext(), DialogGActivity.class);
                intent_g5.putExtra("title","三级清场");
                intent_g5.putExtra("zldm",getContext().getString(R.string.sjqc));
                intent_g5.putExtra("type","OPR");
                startActivity(intent_g5);
                break;
            case  R.id.sjjc:
                cardView_g6.startAnimation(anim);
                Intent intent_g6=new Intent(getContext(), DialogGActivity.class);
                intent_g6.putExtra("title","首件检查");
                intent_g6.putExtra("zldm",getContext().getString(R.string.sjjc));
                intent_g6.putExtra("type","OPR");
                startActivity(intent_g6);
                break;
            case  R.id.pzyc:
                cardView_g7.startAnimation(anim);
                Intent intent_g7=new Intent(getContext(), DialogGActivity.class);
                intent_g7.putExtra("title","品质异常");
                intent_g7.putExtra("zldm",getContext().getString(R.string.pzyc));
                intent_g7.putExtra("type","OPR");
                startActivity(intent_g7);
                break;
            case  R.id.tiaoji:
                cardView_g8.startAnimation(anim);
                Intent intent_g8=new Intent(getContext(), DialogGActivity.class);
                intent_g8.putExtra("title","调机");
                intent_g8.putExtra("zldm",getContext().getString(R.string.tiaoji));
                intent_g8.putExtra("type","OPR");
                startActivity(intent_g8);
                break;
            case  R.id.ts:
                cardView_g9.startAnimation(anim);
                Intent intent_g9=new Intent(getContext(), DialogGActivity.class);
                intent_g9.putExtra("title","调色");
                intent_g9.putExtra("zldm",getContext().getString(R.string.ts));
                intent_g9.putExtra("type","OPR");
                startActivity(intent_g9);
                break;
            case  R.id.tingji:
                cardView_g10.startAnimation(anim);
                Intent intent_g10=new Intent(getContext(), DialogGActivity.class);
                intent_g10.putExtra("title","停机");
                intent_g10.putExtra("zldm",getContext().getString(R.string.tingji));
                intent_g10.putExtra("type","OPR");
                startActivity(intent_g10);
                break;
            case  R.id.dl:
                cardView_g11.startAnimation(anim);
                Intent intent_g11=new Intent(getContext(), DialogGActivity.class);
                intent_g11.putExtra("title","待料");
                intent_g11.putExtra("zldm",getContext().getString(R.string.dl));
                intent_g11.putExtra("type","OPR");
                startActivity(intent_g11);
                break;
            case R.id.by:
                cardView_g12.startAnimation(anim);
                Intent intent_g12=new Intent(getContext(), DialogGActivity.class);
                intent_g12.putExtra("title","保养");
                intent_g12.putExtra("zldm",getContext().getString(R.string.by));
                intent_g12.putExtra("type","OPR");
                startActivity(intent_g12);
                break;
            case  R.id.sm:
                cardView_g13.startAnimation(anim);
                Intent intent_g13=new Intent(getContext(), DialogGActivity.class);
                intent_g13.putExtra("title","试模");
                intent_g13.putExtra("zldm",getContext().getString(R.string.sm));
                intent_g13.putExtra("type","OPR");
                startActivity(intent_g13);
                break;
            case  R.id.sl:
                cardView_g14.startAnimation(anim);
                Intent intent_g14=new Intent(getContext(), DialogGActivity.class);
                intent_g14.putExtra("zldm",getContext().getString(R.string.sl));
                intent_g14.putExtra("title","试料");
                intent_g14.putExtra("type","OPR");
                startActivity(intent_g14);
                break;
            case  R.id.mjwx:
                cardView_g15.startAnimation(anim);
                //getActivity().finish();
                Intent intent_g15=new Intent(getContext(), DialogGActivity.class);
                intent_g15.putExtra("zldm",getContext().getString(R.string.mjwx));
                intent_g15.putExtra("title","模具维修");
                intent_g15.putExtra("type","OPR");
                startActivity(intent_g15);
                break;
            case R.id.jtwx:
                cardView_g16.startAnimation(anim);
                Intent intent_g16=new Intent(getContext(), DialogGActivity.class);
                intent_g16.putExtra("zldm",getContext().getString(R.string.jtwx));
                intent_g16.putExtra("title","机台维修");
                intent_g16.putExtra("type","OPR");
                startActivity(intent_g16);
                break;
            case R.id.zyg:
                cardView_g17.startAnimation(anim);
                Intent intent_g17=new Intent(getContext(), DialogGActivity.class);
                intent_g17.putExtra("zldm",getContext().getString(R.string.zyg));
                intent_g17.putExtra("title","粘样盖");
                intent_g17.putExtra("type","OPR");
                startActivity(intent_g17);
                break;
            case R.id.cm:
                cardView_g18.startAnimation(anim);
                Intent intent_g18=new Intent(getContext(), DialogGActivity.class);
                intent_g18.putExtra("zldm",getContext().getString(R.string.cm));
                intent_g18.putExtra("title","拆模");
                intent_g18.putExtra("type","OPR");
                startActivity(intent_g18);
                break;
            case R.id.rysg:
                cardView_g19.startAnimation(anim);
                Intent intent_g19=new Intent(getContext(), DialogGActivity.class);
                intent_g19.putExtra("zldm",getContext().getString(R.string.rysg));
                intent_g19.putExtra("title","人员上岗");
                intent_g19.putExtra("type","OPR");
                startActivity(intent_g19);
                break;
            case R.id.pgxj:
                cardView_g20.startAnimation(anim);
                Intent intent_g20=new Intent(getContext(), DialogGActivity.class);
                intent_g20.putExtra("zldm",getContext().getString(R.string.pgxj));
                intent_g20.putExtra("title","品管巡机");
                intent_g20.putExtra("type","OPR");
                startActivity(intent_g20);
                break;
            case R.id.js:
                cardView_g21.startAnimation(anim);
                Intent intent_g21=new Intent(getContext(), DialogGActivity.class);
                intent_g21.putExtra("zldm",getContext().getString(R.string.js));
                intent_g21.putExtra("title","结束");
                intent_g21.putExtra("type","OPR");
                startActivity(intent_g21);
                break;
        }
    }
}
