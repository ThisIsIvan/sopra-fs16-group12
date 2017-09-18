package ch.uzh.ifi.seal.soprafs16.group_12_android.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.ImageView;
import ch.uzh.ifi.seal.soprafs16.group_12_android.R;

/**
 * Created by rafael on 13/05/16.
 */
public class TutorialDialogFragment extends DialogFragment {

    private TutorialDialogFragment.InteractionListener mListener;

    public static TutorialDialogFragment newInstance() {
        TutorialDialogFragment dialog = new TutorialDialogFragment();
        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (InteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement InteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        ImageView image = new ImageView(getActivity());
        image.setImageResource(R.drawable.tutorial_screen);
        image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onTutorialDialogComplete();
                dismiss();
            }
        });
        image.setClickable(true);
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        builder.setView(image);
        Dialog dialog = builder.create();
        return dialog;
    }

    public interface InteractionListener {
        void onTutorialDialogComplete();
    }
}
