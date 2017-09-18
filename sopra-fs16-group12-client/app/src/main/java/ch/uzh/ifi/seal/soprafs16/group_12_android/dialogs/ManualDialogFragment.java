package ch.uzh.ifi.seal.soprafs16.group_12_android.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import ch.uzh.ifi.seal.soprafs16.group_12_android.R;

/**
 * Created by rafael on 13/05/16.
 */
public class ManualDialogFragment extends DialogFragment {

    public static ManualDialogFragment newInstance() {
        return new ManualDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_manual,null);
        view.findViewById(R.id.fragment_manual_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        builder.setView(view);
        builder.setCancelable(false);
        Dialog dialog = builder.create();
        return dialog;
    }
}
