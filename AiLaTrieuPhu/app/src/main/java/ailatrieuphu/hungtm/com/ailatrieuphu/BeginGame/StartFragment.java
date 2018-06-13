package ailatrieuphu.hungtm.com.ailatrieuphu.BeginGame;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;

import ailatrieuphu.hungtm.com.ailatrieuphu.MainActivity;
import ailatrieuphu.hungtm.com.ailatrieuphu.ManagerSound.Content;
import ailatrieuphu.hungtm.com.ailatrieuphu.ManagerSound.SoundManager;
import ailatrieuphu.hungtm.com.ailatrieuphu.R;


public class StartFragment extends Fragment implements View.OnClickListener {
    private Button btnStart;
    private  boolean isCheckStart;
    private SoundManager soundManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);
        btnStart = view.findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);
        isCheckStart=true;
        soundManager = new SoundManager();
        soundManager.initSound(container.getContext(), R.raw.bgmusic);
        soundManager.start();
        soundManager.loop();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnStart:
                if (isCheckStart){
                    isCheckStart=false;
                    soundManager.remove();
                    ((MainActivity) getActivity()).openFragmentMoneyStart();
                }

                break;
                default:
                    break;
        }

    }


}
