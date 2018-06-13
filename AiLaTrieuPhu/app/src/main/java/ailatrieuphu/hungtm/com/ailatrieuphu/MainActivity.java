package ailatrieuphu.hungtm.com.ailatrieuphu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import ailatrieuphu.hungtm.com.ailatrieuphu.BeginGame.StartFragment;
import ailatrieuphu.hungtm.com.ailatrieuphu.MoneyQuestion.Level1Fragment;
import ailatrieuphu.hungtm.com.ailatrieuphu.MoneyQuestion.LeverMoneyFragment;
import ailatrieuphu.hungtm.com.ailatrieuphu.MoneyQuestion.OpenMoneyFragment;
import ailatrieuphu.hungtm.com.ailatrieuphu.PlayGame.PlayGameFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openFragmentStart();
    }
    public  void openFragmentStart(){
        StartFragment startFragment=new StartFragment();
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.left_to_right_1,R.anim.left_to_right_2);
        transaction.add(R.id.content,startFragment,StartFragment.class.getName());
        transaction.commit();
    }
    public  void openFragmentPlayGame(String leverMoney){
        PlayGameFragment play=new PlayGameFragment();
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        Bundle bundle=new Bundle();
        bundle.putString("LEVEL_MONEY",leverMoney);
        play.setArguments(bundle);
        transaction.add(R.id.content,play,PlayGameFragment.class.getName());
        transaction.addToBackStack(null);
        transaction.commit();

    }
    public  void openFragmentMoneyStart(){
        OpenMoneyFragment openMoneyFragment=new OpenMoneyFragment();
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.left_to_right_1,R.anim.left_to_right_2);
        transaction.add(R.id.content,openMoneyFragment,OpenMoneyFragment.class.getName());
        transaction.commit();
    }
    public Fragment getCurenFragment(){
        List<Fragment> fragments=getSupportFragmentManager().getFragments();
        for(Fragment fragment:fragments){
            if(fragment!=null&&fragment.isVisible()){
                return fragment;
            }
        }
        return null;
    }
    public void openFragmentLevel1(){
        Level1Fragment lever1Fragment=new Level1Fragment();
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.left_to_right_1,R.anim.left_to_right_2);
        transaction.add(R.id.content,lever1Fragment,Level1Fragment.class.getName());
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public  void openFragmentLevelMoney(int lever){
        LeverMoneyFragment leverMoneyFragment=new LeverMoneyFragment();
        FragmentManager manager=getSupportFragmentManager();
        Bundle bundle=new Bundle();
        bundle.putInt("LEVEL",lever);
        leverMoneyFragment.setArguments(bundle);
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.hide(getCurenFragment());
        transaction.setCustomAnimations(R.anim.left_to_right_1,R.anim.left_to_right_2);
        transaction.add(R.id.content,leverMoneyFragment,LeverMoneyFragment.class.getName());
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void fillData(String levelMoney){
        Fragment fragment=getSupportFragmentManager().findFragmentByTag(PlayGameFragment.class.getName());
        if(fragment!=null){
            ((PlayGameFragment)fragment).fillData(levelMoney);
        }
    }
}
