package ailatrieuphu.hungtm.com.ailatrieuphu.MoneyQuestion;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ailatrieuphu.hungtm.com.ailatrieuphu.MainActivity;
import ailatrieuphu.hungtm.com.ailatrieuphu.ManagerSound.SoundManager;
import ailatrieuphu.hungtm.com.ailatrieuphu.R;

public class OpenMoneyFragment extends Fragment implements  Runnable,DialogReady.IDialogReady, View.OnClickListener {
    private TextView tv5,tv10,tv15;
    private  Thread thread;
    private Button btnNext;
    private  Handler handler;
    private boolean check;
    private static final int MES0=0;
    private  static final int MES1=1;
    private  static final int MES2=2;
    private  static final int MES3=3;
    private DialogReady dialogReady;
    private SoundManager soundManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_money_question,container,false);
        tv5=view.findViewById(R.id.tv_5);
        tv10=view.findViewById(R.id.tv_10);
        tv15=view.findViewById(R.id.tv_15);
        btnNext=view.findViewById(R.id.btn_next);
        check=true;
        dialogReady=new DialogReady();
        dialogReady.setInter(this);
        soundManager=new SoundManager();
        soundManager.initSound(container.getContext(),R.raw.luatchoi_b);
        soundManager.setOnComplete(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                dialogReady.show(getFragmentManager(),OpenMoneyFragment.class.getName());
            }
        });
        btnNext.setOnClickListener(this);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        initHandler();
        initThread();
    }

    private void initThread(){
        thread=new Thread(this);
        thread.start();

    }
    private void  initHandler(){
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case MES0:
                        soundManager.start();
                        break;
                    case MES1:
                        tv5.setBackgroundResource(R.drawable.bg_true1);
                        break;
                    case MES2:
                        tv10.setBackgroundResource(R.drawable.bg_true1);
                        tv5.setBackground(null);
                        break;
                    case MES3:
                        tv15.setBackgroundResource(R.drawable.bg_true1);
                        tv10.setBackground(null);
                        break;
                }
            }
        };
    }
    @Override
    public void run() {
        for (int i=0;i<4;i++){
            Message message=new Message();
            if (i==0){
                message.what=MES0;
                message.setTarget(handler);
                message.sendToTarget();
                SystemClock.sleep(4500);
            }
            else if(i==1){
                message.what=MES1;
                message.setTarget(handler);
                message.sendToTarget();
                SystemClock.sleep(800);
            }else  if (i==2){
                message.what=MES2;
                message.setTarget(handler);
                message.sendToTarget();
                SystemClock.sleep(1000);
            }
            else if (i==3){
                message.what=MES3;
                message.setTarget(handler);
                message.sendToTarget();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void clickReady() {
        handler.removeCallbacks(thread);
        soundManager.remove();
        if(check){
            check=false;
            ((MainActivity)getActivity()).openFragmentLevel1();
        }
    }

    @Override
    public void onClick(View v) {
        if(check){
            check=false;
            handler.removeCallbacks(thread);
            soundManager.remove();
            ((MainActivity)getActivity()).openFragmentLevel1();
        }
    }
}
