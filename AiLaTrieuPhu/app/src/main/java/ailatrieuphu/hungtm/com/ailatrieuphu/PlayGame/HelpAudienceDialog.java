package ailatrieuphu.hungtm.com.ailatrieuphu.PlayGame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ailatrieuphu.hungtm.com.ailatrieuphu.MainActivity;
import ailatrieuphu.hungtm.com.ailatrieuphu.R;

public class HelpAudienceDialog extends DialogFragment {
    private  CustomHelpAudience customHelpAudience;

    public  static HelpAudienceDialog newInstance(boolean a, boolean b, boolean c, boolean d, int trueCase, int level){
        HelpAudienceDialog helpAudienceDialog=new HelpAudienceDialog();
        Bundle bundle=new Bundle();
        bundle.putBoolean("A",a);
        bundle.putBoolean("B",b);
        bundle.putBoolean("C",c);
        bundle.putBoolean("D",d);
        bundle.putInt("TRUECASE",trueCase);
        bundle.putInt("LEVEL",level);
        helpAudienceDialog.setArguments(bundle);
        return helpAudienceDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_help_audience,null);
        builder.setView(view);
        Bundle bundle=getArguments();
        boolean a=bundle.getBoolean("A");
        boolean b=bundle.getBoolean("B");
        boolean c=bundle.getBoolean("C");
        boolean d=bundle.getBoolean("D");
        int trueCase=bundle.getInt("TRUECASE");
        int level=bundle.getInt("LEVEL");
        customHelpAudience=view.findViewById(R.id.help_audience);
        customHelpAudience.setUpdateChart(a,b,c,d,trueCase,level);
        builder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return builder.create();
    }


}
