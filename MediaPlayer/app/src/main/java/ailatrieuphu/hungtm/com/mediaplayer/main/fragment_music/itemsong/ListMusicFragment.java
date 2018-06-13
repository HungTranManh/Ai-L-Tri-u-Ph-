package ailatrieuphu.hungtm.com.mediaplayer.main.fragment_music.itemsong;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;

import ailatrieuphu.hungtm.com.mediaplayer.R;
import ailatrieuphu.hungtm.com.mediaplayer.MainActivity;
import ailatrieuphu.hungtm.com.mediaplayer.service_music.MusicService;
import ailatrieuphu.hungtm.com.mediaplayer.service_music.MyBinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class ListMusicFragment extends Fragment implements DataAdapterRecycleView.IDataAdapter{
    private RecyclerView recyclerView;
    private DataAdapterRecycleView dataAdapter;
    private MusicService musicService;
    private ServiceConnection conn;
    private AsyncTask<String, Bitmap, Void> asyncTask;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_music, container, false);
        inits(view);
        connectionSerVice();
        return view;
    }

    private void inits(View view) {
        recyclerView = view.findViewById(R.id.rcv_music);
        dataAdapter = new DataAdapterRecycleView(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(dataAdapter);

    }

    @Override
    public void clickItem(int position) {
        musicService.playMusicOffline(position);
        ((MainActivity) getActivity()).openActivityPlayMusic(position);

    }


    private void connectionSerVice() {
        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MyBinder binder = (MyBinder) service;
                musicService = binder.getMusicService();
                dataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                musicService = null;
            }
        };
        Intent intent = new Intent();
        intent.setClass(getContext(), MusicService.class);
        getContext().bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    public int getCount() {
        if (musicService == null) {
            return 0;
        }
        return musicService.getCount();
    }

    @Override
    public ItemData getItem(int position) {
        if (musicService == null) {
            return null;
        }
        return musicService.getItem(position);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unbindService(conn);
    }
}
