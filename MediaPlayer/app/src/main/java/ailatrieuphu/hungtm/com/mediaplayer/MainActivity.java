package ailatrieuphu.hungtm.com.mediaplayer;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;

import ailatrieuphu.hungtm.com.mediaplayer.main.fragment_music.MusicFragment;
import ailatrieuphu.hungtm.com.mediaplayer.main.fragment_music.itemsong.ListMusicFragment;
import ailatrieuphu.hungtm.com.mediaplayer.service_music.MusicService;
import ailatrieuphu.hungtm.com.mediaplayer.service_music.MyBinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.support.v4.view.MenuItemCompat.getActionView;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, View.OnClickListener {
    private DrawerLayout drawerLayout;
    private ActionBar actionBar;
    private MusicService musicService;
    private ServiceConnection conn;
    private Animation animation;
    private LinearLayout llCurrentPlay;
    private CircleImageView civAvatar;
    private TextView tvNameSong;
    private static final String TAG = MainActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout
                , R.string.open, R.string.close);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        llCurrentPlay = findViewById(R.id.ll_current_play_music);
        llCurrentPlay.setVisibility(View.GONE);
        drawerToggle.syncState();
        init();
        startService();
        bindService();
        addFragmentMenu();
    }

    private void bindService() {
        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MyBinder binder = (MyBinder) service;
                musicService = binder.getMusicService();
                setViewGone();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                musicService = null;
            }
        };
        Intent intent = new Intent();
        intent.setClass(this, MusicService.class);
        bindService(intent, conn, BIND_AUTO_CREATE);
    }
    private void unBindService(){
        unbindService(conn);
    }
    private void setViewGone() {
        if (musicService.getPlayerManage() != null && musicService.getCurrentPosition() > -1) {
            llCurrentPlay.setVisibility(View.VISIBLE);
            animation = AnimationUtils.loadAnimation(this, R.anim.rotate_avata);
            setImage(musicService.getItem(musicService.getCurrentPosition()).getPathImage());
            tvNameSong.setText(musicService.getItem(musicService.getCurrentPosition()).getNameSong());
            civAvatar.startAnimation(animation);
            llCurrentPlay.setOnClickListener(this);
        }
    }

    private void setImage(String path) {
        if(path!=null){
            Picasso.with(this).load(new File(path)).into(civAvatar);
        }
        else {
            Picasso.with(this).load(R.drawable.backgroud_play_media).into(civAvatar);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    private void init() {
        tvNameSong = findViewById(R.id.tv_name_song);
        civAvatar = findViewById(R.id.civ_avatar);
        drawerLayout = findViewById(R.id.drawer_layout);
    }
    public void addFragmentMenu() {
        MusicFragment fragment = new MusicFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.content, fragment, MusicFragment.class.getName());
        transaction.commit();
    }

    public void openActivityPlayMusic(int position) {
        Intent intent = new Intent(MainActivity.this, PlayMusicActivity.class);
        intent.putExtra("POSITION", position);
        startActivity(intent);
        finish();

    }

    private void startService() {
        Intent intent = new Intent();
        intent.setClass(this, MusicService.class);
        startService(intent);
    }



    @Override
    protected void onNewIntent(Intent intent) {
        NotificationManager notification = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notification.cancel(Constant.FOREGROUND_SERVICE);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        musicService.fillDataOnline(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d(TAG, "onQueryTextChange2: _____" + newText);
        if (TextUtils.isEmpty(newText)) {
            Log.d(TAG, "onQueryTextChange1: _____" + newText);
        } else {
            Log.d(TAG, "onQueryTextChange: _____" + newText);
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_current_play_music:
               openActivityPlayMusic(musicService.getCurrentPosition());
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBindService();

    }

}
