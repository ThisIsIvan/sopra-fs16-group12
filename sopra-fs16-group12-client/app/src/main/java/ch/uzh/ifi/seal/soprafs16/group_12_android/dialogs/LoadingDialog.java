package ch.uzh.ifi.seal.soprafs16.group_12_android.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import ch.uzh.ifi.seal.soprafs16.group_12_android.R;

/**
 * Created by rafael on 17/05/16.
 */
public class LoadingDialog extends Dialog{

    public static LoadingDialog newInstance(Context context){
        LoadingDialog loadingDialog = new LoadingDialog(context);
        return loadingDialog;
    }

    public LoadingDialog(Context context) {
        super(context,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_loading,null));
    }
}
