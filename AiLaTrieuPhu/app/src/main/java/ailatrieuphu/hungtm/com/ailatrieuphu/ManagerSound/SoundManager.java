package ailatrieuphu.hungtm.com.ailatrieuphu.ManagerSound;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.IOException;

public class SoundManager {
    private MediaPlayer mediaPlayer;

    public void initSound(Context context, int id){
        if(mediaPlayer!=null) {
            mediaPlayer.release();
        }
        mediaPlayer=MediaPlayer.create(context,id);
    }
    public  void prepare(){
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  void start(){

            mediaPlayer.start();
    }
    public  void stop(){
        mediaPlayer.stop();
    }
    public void pause(){
        mediaPlayer.pause();
    }
    public  void loop(){
        mediaPlayer.setLooping(true);
    }
    public  void remove(){
        mediaPlayer.release();
    }
    public  int getCurrentDuration(){
        return mediaPlayer.getCurrentPosition();
    }
    public  int getDuration(){
        return mediaPlayer.getDuration();
    }
    public void setOnComplete(MediaPlayer.OnCompletionListener onComplete){
        if ( mediaPlayer != null ) {
            mediaPlayer.setOnCompletionListener(onComplete);
        }
    }
    public boolean isExist(){
        if(mediaPlayer!=null){
            return true;
        }
        else
        {
            return false;
        }
    }
}
