package ailatrieuphu.hungtm.com.ailatrieuphu.MoneyQuestion;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ailatrieuphu.hungtm.com.ailatrieuphu.MainActivity;
import ailatrieuphu.hungtm.com.ailatrieuphu.ManagerSound.SoundManager;
import ailatrieuphu.hungtm.com.ailatrieuphu.R;

public class Level1Fragment extends Fragment {
    private SoundManager soundManager;
    private TextView tv1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_money_question1,container,false);
        soundManager=new SoundManager();
        soundManager.initSound(container.getContext(),R.raw.ques1);
        inits(view);
        soundManager.setOnComplete(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                ((MainActivity) getActivity()).openFragmentPlayGame("200 000");
            }
        });
        return  view;
    }
    private  void inits(View view){
        tv1=view.findViewById(R.id.tv_1);
        tv1.setBackgroundResource(R.drawable.bg_true1);
        soundManager.start();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        soundManager.remove();
    }
}
