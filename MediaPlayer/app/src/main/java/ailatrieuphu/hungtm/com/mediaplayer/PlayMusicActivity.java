package ailatrieuphu.hungtm.com.mediaplayer;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;

import ailatrieuphu.hungtm.com.mediaplayer.media_player.PlayerManage;
import ailatrieuphu.hungtm.com.mediaplayer.service_music.MusicService;
import ailatrieuphu.hungtm.com.mediaplayer.service_music.MyBinder;
import ak.sh.ay.musicwave.MusicWave;
import de.hdodenhof.circleimageview.CircleImageView;

public class PlayMusicActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private TextView tvNameSong, tvTimeTotal, tvCurrentTime;
    private MusicService musicService;
    private Visualizer mVisualizer;
    private MusicWave musicWave;
    private int currentPosition;
    private CircleImageView civAvatar;
    private Button btnPrevious, btnPause, btnNext;
    private Animation animation;
    private PlayerManage playerManage;
    private ServiceConnection conn;
    private Thread thread;
    private SeekBar seekBar;
    private BroadCastMusic broadCastMusic;
    private int currentTime,currentMinute,currentSeconds;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        Intent intent = getIntent();
        currentPosition = intent.getIntExtra("POSITION", 0);
        broadCastMusicRegister();
        connectMusicService();

    }

    public void connectMusicService() {
        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MyBinder binder = (MyBinder) service;
                musicService = binder.getMusicService();
                inits();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                musicService = null;
            }
        };
        Intent intent = new Intent();
        intent.setClass(this, MusicService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    private void unBindService() {
        unbindService(conn);
    }

    private void broadCastMusicRegister() {
        broadCastMusic = new BroadCastMusic();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.COMPLETION);
        intentFilter.addAction(Constant.PLAY_ACTION);
        intentFilter.addAction(Constant.NEXT_ACTION);
        intentFilter.addAction(Constant.PREV_ACTION);
        registerReceiver(broadCastMusic, intentFilter);
    }

    private void unBroadCastMusicRegister() {
        unregisterReceiver(broadCastMusic);
    }

    public void loadUI() {
        setImage(musicService.getItem(currentPosition).getPathImage());
        tvNameSong.setText(musicService.getItem(currentPosition).getNameSong());
        tvTimeTotal.setText(playerManage.getDuration() / 60000 + ":" + (playerManage.getDuration() % 60000) / 1000);
        prepareVisualizer();
    }


    private void inits() {
        playerManage = musicService.getPlayerManage();
        animation = AnimationUtils.loadAnimation(this, R.anim.rotate_avata);
        seekBar = findViewById(R.id.seek_bar);
        tvNameSong = findViewById(R.id.tv_name_song);
        tvNameSong.setText(musicService.getItem(currentPosition).getNameSong());
        tvTimeTotal = findViewById(R.id.tv_time_total);
        tvCurrentTime = findViewById(R.id.tv_current_time);
        tvTimeTotal.setText(playerManage.getDuration() / 60000 + ":" + (playerManage.getDuration() % 60000) / 1000);
        musicWave = findViewById(R.id.musicwave);
        btnPrevious = findViewById(R.id.btn_previous);
        btnNext = findViewById(R.id.btn_next);
        btnPause = findViewById(R.id.btn_pause);
        civAvatar = findViewById(R.id.civ_avatar);
        if (musicService.getItem(currentPosition).getPathImage() != null) {
            Picasso.with(this).load(new File(musicService.getItem(currentPosition).getPathImage())).into(civAvatar);
        } else {
            Picasso.with(this).load(R.drawable.backgroud_play_media).into(civAvatar);
        }
        civAvatar.setAnimation(animation);
        btnPrevious.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);
        playerManage.setOnCompletion(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                next();
            }
        });
        prepareVisualizer();
        thread=new Thread(runnable);
        thread.start();
    }

    private void setImage(String path) {
        if (path != null) {
            Picasso.with(this).load(new File(path)).into(civAvatar);
        } else {
            Picasso.with(this).load(R.drawable.backgroud_play_media).into(civAvatar);
        }
    }
    private void updateProgessBar(){
        try {
            currentTime = playerManage.getCurrentTime();
            currentMinute = currentTime / 60000;
            currentSeconds = (currentTime % 60000) / 1000;
            seekBar.setMax(playerManage.getDuration());
            seekBar.setProgress(currentTime);
            tvCurrentTime.post(new Runnable() {
                @Override
                public void run() {
                    tvCurrentTime.setText(currentMinute + ":"+currentSeconds);
                }
            });
        }
        catch (IllegalStateException ex){
        }

    }
    private void prepareVisualizer() {
        mVisualizer = new Visualizer(musicService.getPlayerManage().getAudioSessionId());
        mVisualizer.setEnabled(false);
        mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
        mVisualizer.setDataCaptureListener(
                new Visualizer.OnDataCaptureListener() {
                    public void onWaveFormDataCapture(Visualizer visualizer,
                                                      byte[] bytes, int samplingRate) {
                        musicWave.updateVisualizer(bytes);
                    }

                    public void onFftDataCapture(Visualizer visualizer,
                                                 byte[] bytes, int samplingRate) {
                    }
                }, Visualizer.getMaxCaptureRate() / 2, true, false);
        mVisualizer.setEnabled(true);
    }

//    public void cancelService() {
//        musicService.stopForeground(true);
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
               next();
                break;
            case R.id.btn_pause:
                musicService.pauseMusic();
                break;
            case R.id.btn_previous:
                previous();
                break;
            default:
                break;

        }

    }

    public void previous() {
        if (currentPosition > 0) {
            currentPosition -= 1;
            musicService.previousMusic();
            loadUI();
        }
    }

    public void next() {
        if (currentPosition > (musicService.getCount() - 2)) {
            playerManage.stop();
            return;
        }
        musicService.nextMusic();
        currentPosition += 1;
        loadUI();
    }

    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            while (true){
                updateProgessBar();
                SystemClock.sleep(500);
            }
        }
    };

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(playerManage!=null&&fromUser){
            seekBar.setProgress(progress);
            playerManage.seekto(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    public class BroadCastMusic extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Constant.COMPLETION:
                    if (currentPosition<(musicService.getCount() - 2)) {
                        currentPosition+=1;
                    }
                    loadUI();
                    break;
                case Constant.NEXT_ACTION:
                    if (currentPosition<(musicService.getCount() - 2)) {
                        currentPosition+=1;
                    }
                    loadUI();
                    break;
                case Constant.PREV_ACTION:
                    if(currentPosition>0){
                        currentPosition-=1;
                    }
                    loadUI();
                    break;
                    default:
                        break;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
      mVisualizer.release();
        unBindService();
        unBroadCastMusicRegister();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PlayMusicActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

}
