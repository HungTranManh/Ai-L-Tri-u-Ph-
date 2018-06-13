package ailatrieuphu.hungtm.com.ailatrieuphu.MoneyQuestion;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import ailatrieuphu.hungtm.com.ailatrieuphu.MainActivity;
import ailatrieuphu.hungtm.com.ailatrieuphu.ManagerSound.SoundManager;
import ailatrieuphu.hungtm.com.ailatrieuphu.R;

public class DialogReady extends DialogFragment {
    private SoundManager soundManager1;
    private IDialogReady iDialogReady;

    public void setInter(IDialogReady inter) {
        iDialogReady = inter;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        soundManager1 = new SoundManager();
        builder.setTitle("ready?");
        builder.setIcon(R.drawable.icon_question);
        builder.setMessage("Bạn đã sẵn sàng chưa?");
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((MainActivity)getActivity()).openFragmentStart();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Sẵn Sàng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                soundManager1.initSound(builder.getContext(),R.raw.gofind);
                soundManager1.setOnComplete(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        iDialogReady.clickReady();
                        dismiss();
                    }
                });
                soundManager1.start();

            }
        });
        return  builder.create();
    }
    public interface IDialogReady{
        void clickReady();
    }
}
