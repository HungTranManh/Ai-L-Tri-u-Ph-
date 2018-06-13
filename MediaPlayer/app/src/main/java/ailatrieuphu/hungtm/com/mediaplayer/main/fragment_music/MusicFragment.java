package ailatrieuphu.hungtm.com.mediaplayer.main.fragment_music;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ailatrieuphu.hungtm.com.mediaplayer.R;
import ailatrieuphu.hungtm.com.mediaplayer.main.fragment_music.album.AlbumFragment;
import ailatrieuphu.hungtm.com.mediaplayer.main.fragment_music.genre.GenreFragment;
import ailatrieuphu.hungtm.com.mediaplayer.main.fragment_music.itemsong.ListMusicFragment;

public class MusicFragment extends Fragment {
    private List<Fragment> fragments;
    private AdapterViewPager adapterViewPager;
    private ViewPager viewPager;
    private TabLayout tab;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_music,container,false);
        fragments=new ArrayList<>();
        addFragment();
        inits(view);
        viewPager.setAdapter(adapterViewPager);
        tab.setupWithViewPager(viewPager);
        return view;
    }
    private  void inits(View view){
        viewPager=view.findViewById(R.id.view_pager_music);
        tab=view.findViewById(R.id.tab_layout_music);
        adapterViewPager=new AdapterViewPager(getChildFragmentManager(),fragments);

    }
    private void addFragment(){
        fragments.add(new ListMusicFragment());
        fragments.add(new AlbumFragment());
        fragments.add(new GenreFragment());
    }
}
