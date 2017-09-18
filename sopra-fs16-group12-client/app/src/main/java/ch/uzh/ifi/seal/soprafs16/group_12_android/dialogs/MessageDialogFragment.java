package ch.uzh.ifi.seal.soprafs16.group_12_android.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import ch.uzh.ifi.seal.soprafs16.group_12_android.Globals;

/**
 * Created by rafael on 11/05/16.
 */
public class MessageDialogFragment extends DialogFragment {

    private static final String ARG_MESSAGE = "ARG_MESSAGE";
    private static final String ARG_COLOR = "ARG_COLOR";
    private static final String[] randomMessages = {"AWESOME","BADASS","ROCKSTAR","GODLIKE","KEEP ON","FLAWLESS"};
    private static final String TAG = "MessageD";

    private Handler dismissHandler;

    public static MessageDialogFragment newInstance(){
        return newInstance(Color.LTGRAY);
    }

    public static MessageDialogFragment newInstance(int color){
        return newInstance(randomMessages[Globals.getInstance().rng.nextInt(randomMessages.length)],color);
    }

    public static MessageDialogFragment newInstance(String message){
        return newInstance(message,Color.LTGRAY);
    }

    public static MessageDialogFragment newInstance(String message, int color){
        Bundle args =  new Bundle();
        args.putInt(ARG_COLOR,color);
        args.putString(ARG_MESSAGE,message);
        MessageDialogFragment dialog = new MessageDialogFragment();
        dialog.setArguments(args);
        return dialog;
    }

    @Deprecated
    public MessageDialogFragment(){
        dismissHandler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                dismiss();
            }
        };
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = getArguments().getString(ARG_MESSAGE);
        int color = getArguments().getInt(ARG_COLOR);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        TextView text = new TextView(getActivity());
        text.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        text.setText(message);
        text.setGravity(Gravity.CENTER);
        text.setTextSize(60f);
        text.setTextColor(Color.BLACK);
        text.setShadowLayer(5f,5f,5f,Color.DKGRAY);
        LinearLayout container = new LinearLayout(getActivity());

        container.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        container.setPadding(25,25,25,25);
        container.setBackground(new ColorDrawable(color));
        container.addView(text);
        builder.setView(container);
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setDimAmount(0f);
        Globals.getInstance().executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Log.w(TAG,"is complaining "+e.getMessage());
                } finally {
                    dismissHandler.obtainMessage().sendToTarget();
                }
            }
        });
    }
}
