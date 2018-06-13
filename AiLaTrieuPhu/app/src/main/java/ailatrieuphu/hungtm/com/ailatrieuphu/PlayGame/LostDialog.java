package ailatrieuphu.hungtm.com.ailatrieuphu.PlayGame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import ailatrieuphu.hungtm.com.ailatrieuphu.MainActivity;
import ailatrieuphu.hungtm.com.ailatrieuphu.ManagerSound.SoundManager;
import ailatrieuphu.hungtm.com.ailatrieuphu.R;

public class LostDialog extends DialogFragment {
    private EditText edtName;
    private ILostDialog iLostDialog;
    private SoundManager manager;
    private TextView tvMoney,tvLevel;
    public  static LostDialog newInstance(String money,int level){
        LostDialog lostDialog=new LostDialog();
        Bundle bundle=new Bundle();
        bundle.putString("MONEY",money);
        bundle.putInt("LEVEL",level);
        lostDialog.setArguments(bundle);
        return  lostDialog;
    }
    public  void setILostDialog(ILostDialog iLostDialog){
        this.iLostDialog=iLostDialog;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_lost,null);
        manager=new SoundManager();
        manager.initSound(getContext(),R.raw.lose2);
        manager.start();
        builder.setView(view);
        edtName=view.findViewById(R.id.edt_name);
        tvMoney=view.findViewById(R.id.tv_money);
        tvLevel=view.findViewById(R.id.tv_level);
        Bundle bundle=getArguments();
        final String money=bundle.getString("MONEY");
        final int level=bundle.getInt("LEVEL");
        tvMoney.setText(money+" VND");
        tvLevel.setText(level+"");
        builder.setNegativeButton("Lưu Điểm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((MainActivity)getActivity()).openFragmentStart();
                iLostDialog.saveScore(edtName.getText().toString()+" ",
                        parseScore(money),level,money);
                manager.remove();
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((MainActivity)getActivity()).openFragmentStart();
                manager.remove();
                dialog.cancel();
            }
        });
        return builder.create();
    }
    private int parseScore(String s){
        s=s.trim();
        int index=s.indexOf(".");
        String s1=s.substring(0,index)+s.substring(index+1);
        int score;
        try {
             score=(int)Long.parseLong(s1)/1000;
        }
        catch (NumberFormatException ex){
            score=0;
        }
       return score;

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        manager.remove();
    }
    public  interface ILostDialog{
        void saveScore(String name,int score,int level,String money);
    }
}
