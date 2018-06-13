package ailatrieuphu.hungtm.com.mediaplayer.media_player;

import android.media.MediaPlayer;
import android.os.AsyncTask;

import java.io.IOException;

public class PlayerManage {
    private MediaPlayer mediaPlayer;

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void init(String path){
        if(mediaPlayer!=null){
            mediaPlayer.release();
        }
        mediaPlayer=new MediaPlayer();
        try {
            mediaPlayer.setDataSource(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void  prepareAsysn() {
        AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mediaPlayer.prepareAsync();
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
        }.execute();
    }
    public void seekto(int time){
        mediaPlayer.seekTo(time);
    }
    public int getCurrentTime(){
        if (mediaPlayer!=null){
            return mediaPlayer.getCurrentPosition();
        }
        else
            return 0;

    }
    public   void prepare(){
        if (mediaPlayer!=null) {
            try {
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void setOnCompletion(MediaPlayer.OnCompletionListener onCompletionListener){
        if (mediaPlayer!=null){
           mediaPlayer.setOnCompletionListener(onCompletionListener);
            }
        }
        public int getDuration(){
        if(mediaPlayer!=null){
            return mediaPlayer.getDuration();
        }else return 0;
        }
    public void loop(){
        mediaPlayer.setLooping(true);
    }
    public  void start(){
        mediaPlayer.start();
    }
    public  void pause(){
        mediaPlayer.pause();
    }
    public  void stop(){
        mediaPlayer.stop();
    }
    public void remove(){
        mediaPlayer.release();
    }
    public int getAudioSessionId() {
        return mediaPlayer.getAudioSessionId();
    }

}
