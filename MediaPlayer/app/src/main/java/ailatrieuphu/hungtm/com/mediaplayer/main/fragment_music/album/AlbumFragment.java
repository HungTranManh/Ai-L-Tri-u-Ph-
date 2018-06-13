package ailatrieuphu.hungtm.com.mediaplayer.main.fragment_music.album;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ailatrieuphu.hungtm.com.mediaplayer.R;
import ailatrieuphu.hungtm.com.mediaplayer.main.fragment_music.itemsong.ItemData;
import ailatrieuphu.hungtm.com.mediaplayer.service_music.MusicService;
import ailatrieuphu.hungtm.com.mediaplayer.service_music.MyBinder;

public class AlbumFragment extends Fragment implements DataAdapterRecycleViewAlbum.IDataRecycleViewAlbum {
    private RecyclerView recyclerView;
    private List<ItemData> itemDatas;
    private ServiceConnection conn;
    private List<ItemDataAlbum> itemDataAlbums;
    private MusicService musicService;
    private DataAdapterRecycleViewAlbum adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_list_album,container,false);
        inits(view);
        connectService();
        return view;
    }
    private void inits(View view){
        recyclerView=view.findViewById(R.id.recycler_view_album);

        adapter=new DataAdapterRecycleViewAlbum(this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
    private void connectService(){
        conn=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MyBinder binder=(MyBinder)service;
                musicService=binder.getMusicService();
                itemDatas=musicService.getItemDatas();
                initDataAlbum();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                musicService=null;
            }
        };
        Intent intent=new Intent();
        intent.setClass(getContext(),MusicService.class);
        getContext().bindService(intent,conn, Context.BIND_AUTO_CREATE);
    }
    private void initDataAlbum(){
        itemDataAlbums=new ArrayList<>();
        Cursor c=getContext().getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                null,null,null,null);
        int indexIdAlbum=c.getColumnIndex(MediaStore.Audio.Albums._ID);
        int indexNameAlbum=c.getColumnIndex(MediaStore.Audio.Albums.ALBUM);
        int indexNameSinger=c.getColumnIndex(MediaStore.Audio.Albums.ARTIST);
        int indexPathImage=c.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
        c.moveToFirst();
        while (!c.isAfterLast()){
            Long idAlbum=c.getLong(indexIdAlbum);
            String nameAlbum=c.getString(indexNameAlbum);
            String nameSinger=c.getString(indexNameSinger);
            String pathImage=c.getString(indexPathImage);
            ItemDataAlbum itemDataAlbum=new ItemDataAlbum();
            itemDataAlbum.setNameAlbum(nameAlbum);
            itemDataAlbum.setPathImageAlbum(pathImage);
            itemDataAlbum.setNameSinger(nameSinger);
            itemDataAlbum.setIdAlbum(idAlbum);
            int count=0;
            for(ItemData itemData:itemDatas){
                if(idAlbum==itemData.getIdAlbum()){
                    count++;
                }
            }
            itemDataAlbum.setSizeItems(count);
            itemDataAlbums.add(itemDataAlbum);
            c.moveToNext();
        }
        c.close();
    }
    @Override
    public void clickItem(int position) {

    }

    @Override
    public ItemDataAlbum getItemDataAlbum(int position) {
        if(musicService==null){
            return null;
        }
        return itemDataAlbums.get(position);
    }

    @Override
    public int getCount() {
        if(musicService==null){
            return 0;
        }
        return itemDataAlbums.size();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unbindService(conn);
    }
}
