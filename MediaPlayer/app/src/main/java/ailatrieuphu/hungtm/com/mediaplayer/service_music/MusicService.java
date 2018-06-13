package ailatrieuphu.hungtm.com.mediaplayer.service_music;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ailatrieuphu.hungtm.com.mediaplayer.Constant;
import ailatrieuphu.hungtm.com.mediaplayer.SeachMusicOnline.ItemDataOnline;
import ailatrieuphu.hungtm.com.mediaplayer.main.fragment_music.itemsong.ItemData;
import ailatrieuphu.hungtm.com.mediaplayer.media_player.PlayerManage;
import ailatrieuphu.hungtm.com.mediaplayer.R;
import ailatrieuphu.hungtm.com.mediaplayer.MainActivity;

public class MusicService extends Service {
    private static final String TAG=MusicService.class.getSimpleName();
    private List<ItemData> itemDatas;
    private PlayerManage playerManage;
    private List<ItemDataOnline> itemDataOnlines;
    private int currentPosition;
    private boolean isPause = false;
    private Notification notification;
    private AsyncTask<String, Void, Void> asyncTask;

    @Override
    public void onCreate() {
        super.onCreate();
        currentPosition = -1;
        playerManage = new PlayerManage();
        initDataBase();
    }

    public PlayerManage getPlayerManage() {
        return playerManage;
    }

    public List<ItemData> getItemDatas() {
        return itemDatas;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Constant.PLAY_ACTION.equals(intent.getAction())) {
            pauseMusic();
            Intent intent1 = new Intent();
            intent1.setAction(Constant.PLAY_ACTION);
            sendBroadcast(intent1);
        } else if (Constant.PREV_ACTION.equals(intent.getAction())) {
            previousMusic();
            Intent intent2 = new Intent();
            intent2.setAction(Constant.PREV_ACTION);
            sendBroadcast(intent2);
        } else if (Constant.NEXT_ACTION.equals(intent.getAction())) {
            nextMusic();
            Intent intent3 = new Intent();
            intent3.setAction(Constant.NEXT_ACTION);
            sendBroadcast(intent3);
        } else if (Constant.STOPFOREGROUND_ACTION.equals(intent.getAction())) {
            stopForeground(true);
            stopSelf();
        }

        return START_NOT_STICKY;
    }

    private void initDataBase() {
        itemDatas = new ArrayList<>();
        Cursor c = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null
                , MediaStore.MediaColumns.DATE_ADDED + " DESC");
        if (c != null) {
            int indexPath = c.getColumnIndex(MediaStore.Audio.Media.DATA);
            int indexNameSong = c.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
            int indexNameSigle = c.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int indexId = c.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
            int indexTime = c.getColumnIndex(MediaStore.Audio.Media.DURATION);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                String namePath = c.getString(indexPath);
                String nameSong = c.getString(indexNameSong);
                String nameSigle = c.getString(indexNameSigle);
                Long alburmID = Long.valueOf(c.getLong(indexId));
                Long time = c.getLong(indexTime);
                Cursor cursorAlbum = getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI
                        , new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                        MediaStore.Audio.Albums._ID + "=" + alburmID, null, null);
                ItemData itemData = new ItemData();
                itemData.setPath(namePath);
                if (cursorAlbum != null && cursorAlbum.moveToFirst()) {
                    String pathImage= cursorAlbum.getString(cursorAlbum.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                    itemData.setPathImage(pathImage);
                }
                itemData.setIdAlbum(alburmID);
               itemData.setNameSong(subNameSong(nameSong));
                itemData.setNameSinger(nameSigle);
                itemData.setTime(time);
                itemDatas.add(itemData);
                c.moveToNext();
            }
        }
        c.close();
    }
    public void fillDataOnline(final String query) {
        itemDataOnlines=new ArrayList<>();
        String link = "https://mp3.zing.vn/tim-kiem/bai-hat.html?q=" + progressQuery(query);
        asyncTask = new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... strings) {
                try {
                    dowloadItemSong(strings[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }.execute(link);

    }

    private String progressQuery(String query) {
        return query.replaceAll(" ", "+");
    }


    private void dowloadItemSong(String s) throws IOException {
        ItemDataOnline itemDataOnline=new ItemDataOnline();
        Document document = Jsoup.connect(s).timeout(20000).get();
        Elements itemSongs = document.select("div.item-song");
        for (Element itemSong : itemSongs) {
            String dataId = itemSong.attr("data-id");
            itemDataOnline.setDataId(dataId);
            String dataCode = itemSong.attr("data-code");
            itemDataOnline.setDataCode(dataCode);
            try {
                String linkImg =
                        itemSong.selectFirst("img").attr("src");
                itemDataOnline.setLinkImage(linkImg);
            } catch (NullPointerException e) {

            }

            try {
                String nameSong =
                        itemSong.selectFirst("a").attr("title");
                itemDataOnline.setNameSong(nameSong);
            } catch (NullPointerException e) {

            }

            try {
                String url =
                        itemSong.selectFirst("a").attr("href");

            } catch (NullPointerException e) {

            }
            Log.d(TAG, "dowloadItemSong: ____"+itemDataOnline.toString());
            itemDataOnlines.add(itemDataOnline);
        }
    }
    public int getCount() {
        if (itemDatas == null) {
            return 0;
        }
        return itemDatas.size();
    }

    public ItemData getItem(int position) {
        if (itemDatas == null) {
            return null;
        }
        return itemDatas.get(position);
    }

    public void playMusicOffline(int position) {
        currentPosition = position;
        playerManage.init(itemDatas.get(position).getPath());
        playerManage.prepare();
        playerManage.start();
        showNotification(position);
        playerManage.setOnCompletion(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                nextMusic();
                Intent intent = new Intent();
                intent.setAction(Constant.COMPLETION);
                sendBroadcast(intent);
            }
        });
    }

    private void showNotification(int currentPosition) {
        RemoteViews views = new RemoteViews(getPackageName(), R.layout.status_bar);
        String pathBitmap = itemDatas.get(currentPosition).getPathImage();
        if (pathBitmap != null) {
            views.setImageViewBitmap(R.id.status_bar_album_art, BitmapFactory.decodeFile(pathBitmap));
        } else {
            views.setImageViewBitmap(R.id.status_bar_album_art, BitmapFactory.decodeResource(getResources(), R.drawable.not_nhac));
        }
        views.setTextViewText(R.id.tv_name_song, itemDatas.get(currentPosition).getNameSong());
        views.setTextViewText(R.id.tv_name_singer, itemDatas.get(currentPosition).getNameSinger());
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setAction(Constant.MAIN_ACTION);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent previousIntent = new Intent(this, MusicService.class);
        previousIntent.setAction(Constant.PREV_ACTION);
        PendingIntent ppreviousIntent = PendingIntent.getService(this, 0,
                previousIntent, 0);

        Intent playIntent = new Intent(this, MusicService.class);
        playIntent.setAction(Constant.PLAY_ACTION);
        PendingIntent pplayIntent = PendingIntent.getService(this, 0,
                playIntent, 0);

        Intent nextIntent = new Intent(this, MusicService.class);
        nextIntent.setAction(Constant.NEXT_ACTION);
        PendingIntent pnextIntent = PendingIntent.getService(this, 0,
                nextIntent, 0);

        Intent closeIntent = new Intent(this, MusicService.class);
        closeIntent.setAction(Constant.STOPFOREGROUND_ACTION);
        PendingIntent pcloseIntent = PendingIntent.getService(this, 0,
                closeIntent, 0);
        views.setOnClickPendingIntent(R.id.status_bar_play, pplayIntent);
        views.setOnClickPendingIntent(R.id.status_bar_previous, ppreviousIntent);
        views.setOnClickPendingIntent(R.id.status_bar_next, pnextIntent);
        views.setOnClickPendingIntent(R.id.status_bar_collapse, pcloseIntent);
        notification = new Notification.Builder(this).build();
        notification.contentView = views;
        notification.icon = R.drawable.not_nhac;
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        notification.contentIntent = pendingIntent;
        startForeground(Constant.FOREGROUND_SERVICE, notification);
    }

//    private void createNotification(ItemData itemData) {
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
//        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.not_nhac));
//        builder.setSmallIcon(R.drawable.ic_notifications_white_24dp);
//        builder.setContentTitle(itemData.getNameSong());
//        builder.setContentText(itemData.getNameSinger());
//        Intent intent = new Intent();
//        intent.putExtra("id", 1);
//        intent.setClass(this, MainActivity.class);
//        //neu activity dang song thi no se vao pt onNewIntent neu nó đã chết thì sẽ vào pt oncreat củra activity
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(pendingIntent);
//        startForeground(1, builder.build());
//    }

    private String subNameSong(String name) {
        int index = name.lastIndexOf(".");
        return name.substring(0, index);
    }

    public void nextMusic() {
        if (currentPosition > (getCount() - 2)) {
            playerManage.stop();
            return;
        }
        currentPosition += 1;
        playMusicOffline(currentPosition);
    }

    public void previousMusic() {
        if (currentPosition > 0) {
            currentPosition -= 1;
            playMusicOffline(currentPosition);
        }
    }

    public void pauseMusic() {
        isPause = !isPause;
        if (isPause) {
            playerManage.pause();
        } else {
            playerManage.start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        playerManage.remove();
    }
}
