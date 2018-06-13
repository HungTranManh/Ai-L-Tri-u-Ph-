package ailatrieuphu.hungtm.com.ailatrieuphu.MoneyQuestion;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import ailatrieuphu.hungtm.com.ailatrieuphu.MainActivity;
import ailatrieuphu.hungtm.com.ailatrieuphu.ManagerSound.SoundManager;
import ailatrieuphu.hungtm.com.ailatrieuphu.R;

public class LeverMoneyFragment extends Fragment implements MediaPlayer.OnCompletionListener {
    private int lever;
    private TextView tv;
    private SoundManager soundManager2;
    private String moneyLever;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_money_question1, container, false);
        Bundle bundle = getArguments();
        lever = bundle.getInt("LEVEL");
        soundManager2 = new SoundManager();
        inits(view);
        soundManager2.setOnComplete(this);
        soundManager2.start();
        return view;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        ((MainActivity) getActivity()).fillData(moneyLever);
        getActivity().onBackPressed();
    }
    private void inits(View view) {
            switch (lever) {
            case 2:
                showLevelCurrent(view,R.id.tv_2,R.raw.ques2);
                break;
            case 3:
                showLevelCurrent(view,R.id.tv_3,R.raw.ques3);

                break;
            case 4:
                showLevelCurrent(view,R.id.tv_4,R.raw.ques4);

                break;
            case 5:
                showLevelCurrent(view,R.id.tv_5,R.raw.ques5);
                break;
            case 6:
                showLevelCurrent(view,R.id.tv_6,R.raw.ques6);

                break;
            case 7:
                showLevelCurrent(view,R.id.tv_7,R.raw.ques7);

                break;
            case 8:
                showLevelCurrent(view,R.id.tv_8,R.raw.ques8);

                break;
            case 9:
                showLevelCurrent(view,R.id.tv_9,R.raw.ques9);
                break;
            case 10:
                showLevelCurrent(view,R.id.tv_10,R.raw.ques10);

                break;
            case 11:
                showLevelCurrent(view,R.id.tv_11,R.raw.ques11);

                break;
            case 12:
                showLevelCurrent(view,R.id.tv_12,R.raw.ques12);

                break;
            case 13:
                showLevelCurrent(view,R.id.tv_13,R.raw.ques13);

                break;
            case 14:
                showLevelCurrent(view,R.id.tv_14,R.raw.ques14);
                break;
            case 15:
                showLevelCurrent(view,R.id.tv_15,R.raw.ques15);

                break;
            default:
                break;
        }

    }
    private  void showLevelCurrent(View view,int idTextView,int idRaw){
        tv = view.findViewById(idTextView);
        tv.setBackgroundResource(R.drawable.bg_true1);
        moneyLever = stringProcessing((String) tv.getText());
        soundManager2.initSound(view.getContext(), idRaw);
        soundManager2.start();
    }
    private String stringProcessing(String s) {
        int indexSpace = s.indexOf(" ");
        return s.substring(indexSpace);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        soundManager2.remove();
    }
}
