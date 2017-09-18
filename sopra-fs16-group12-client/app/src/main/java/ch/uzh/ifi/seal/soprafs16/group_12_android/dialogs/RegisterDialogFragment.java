package ch.uzh.ifi.seal.soprafs16.group_12_android.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import ch.uzh.ifi.seal.soprafs16.group_12_android.R;

/**
 * Created by rafael on 10/04/16.
 */
public class RegisterDialogFragment extends DialogFragment {

    InteractionListener mListener;

    public static RegisterDialogFragment newInstance() {
        RegisterDialogFragment registerDialogFragment = new RegisterDialogFragment();
        return registerDialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_register, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final EditText nameEditText = (EditText) v.findViewById(R.id.dialog_register_username);
        Button registerButton = ((Button) v.findViewById(R.id.dialog_register_register_button));

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = nameEditText.getText().toString();
                if (username.length() < 3) {
                    Toast.makeText(getActivity(), "Choose an username which has more than 3 characters", Toast.LENGTH_SHORT).show();
                } else {
                    mListener.onRegisterDialogPositiveClick(username);
                    dismiss();
                }
            }
        });

        Button cancelButton = (Button) v.findViewById(R.id.dialog_register_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        builder.setView(v);
        AlertDialog dialog = builder.create();
        return dialog;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (InteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement LoginDialogFragment.InteractionListener");
        }
    }

    public interface InteractionListener {
        void onRegisterDialogPositiveClick(String username);
    }
}
