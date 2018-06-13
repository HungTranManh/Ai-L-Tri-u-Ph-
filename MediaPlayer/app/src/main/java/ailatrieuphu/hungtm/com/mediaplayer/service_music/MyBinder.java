package ailatrieuphu.hungtm.com.mediaplayer.service_music;

import android.os.Binder;

import ailatrieuphu.hungtm.com.mediaplayer.service_music.MusicService;

public class MyBinder extends Binder {
    private MusicService musicService;

    public MyBinder(MusicService musicService) {
        this.musicService = musicService;
    }

    public MusicService getMusicService() {
        return musicService;
    }
}
