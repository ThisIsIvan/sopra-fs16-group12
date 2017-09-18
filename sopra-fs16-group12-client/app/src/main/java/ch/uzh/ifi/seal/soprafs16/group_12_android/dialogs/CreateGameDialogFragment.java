package ch.uzh.ifi.seal.soprafs16.group_12_android.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import ch.uzh.ifi.seal.soprafs16.group_12_android.R;

/**
 * Created by Ivan on 31.03.2016.
 */
public class CreateGameDialogFragment extends DialogFragment {

    CreateGameDialogFragment.InteractionListener mListener;

    public static CreateGameDialogFragment newInstance() {
        CreateGameDialogFragment createGameDialogFragment = new CreateGameDialogFragment();
        return createGameDialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_create_game, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(v);
        final EditText nameEditText = (EditText) v.findViewById(R.id.dialog_lobby_name);

        v.findViewById(R.id.dialog_lobby_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = nameEditText.getText().toString();
                if (text.length() < 3) {
                    Toast.makeText(getContext(),"Choose name with more than 3 characters",Toast.LENGTH_SHORT).show();
                } else {
                    dismiss();
                    mListener.onCreateGameRegister(text);
                }
            }
        });
        v.findViewById(R.id.dialog_lobby_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (InteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement NoticeDialogListener");
        }
    }

    public interface InteractionListener {
        void onCreateGameRegister(String gamename);
    }
}
