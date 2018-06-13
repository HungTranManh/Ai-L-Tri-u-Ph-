package ailatrieuphu.hungtm.com.mediaplayer.main.fragment_music;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class AdapterViewPager extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public AdapterViewPager(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "List Item Song";

            case 1:
                return "Album";

            case 2:
                return "Genre ";
            default:
                break;
        }
        return null;
    }
}
