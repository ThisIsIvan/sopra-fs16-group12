package ch.uzh.ifi.seal.soprafs16.group_12_android.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import ch.uzh.ifi.seal.soprafs16.group_12_android.Globals;
import ch.uzh.ifi.seal.soprafs16.group_12_android.R;


/**
 * Created by Ivan on 27.04.2016.
 */
public class ChooseItemDialogFragment extends DialogFragment {

    public static final String ARG_HAS_BAG = "ChooseItemDialogFragment.HAS_BAG";
    public static final String ARG_HAS_GEM = "ChooseItemDialogFragment.HAS_GEM";
    public static final String ARG_HAS_CASE = "ChooseItemDialogFragment.HAS_CASE";

    public static ChooseItemDialogFragment newInstance(Boolean hasBag, Boolean hasGem, Boolean hasCase) {
        Bundle args = new Bundle();
        args.putBoolean(ARG_HAS_BAG, hasBag);
        args.putBoolean(ARG_HAS_GEM, hasGem);
        args.putBoolean(ARG_HAS_CASE, hasCase);

        ChooseItemDialogFragment d = new ChooseItemDialogFragment();
        d.setArguments(args);
        return d;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_choose_item, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(v);

        boolean[] chooseable = new boolean[3];

        chooseable[0] = getArguments().getBoolean(ARG_HAS_BAG);
        chooseable[1] = getArguments().getBoolean(ARG_HAS_GEM);
        chooseable[2] = getArguments().getBoolean(ARG_HAS_CASE);

        FrameLayout layout = (FrameLayout) v.findViewById(R.id.dialog_choose_item);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(layoutParams);


        FrameLayout bagLayout = (FrameLayout) v.findViewById(R.id.dialog_choose_item_bag);
        bagLayout.setVisibility(chooseable[0] ? View.VISIBLE : View.INVISIBLE);
        bagLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickHelper(Globals.ItemType.BAG);
            }
        });
        FrameLayout gemLayout = (FrameLayout) v.findViewById(R.id.dialog_choose_item_gem);
        gemLayout.setVisibility(chooseable[1] ? View.VISIBLE : View.INVISIBLE);
        gemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickHelper(Globals.ItemType.GEM);
            }
        });
        FrameLayout caseLayout = (FrameLayout) v.findViewById(R.id.dialog_choose_item_case);
        caseLayout.setVisibility(chooseable[2] ? View.VISIBLE : View.INVISIBLE);
        caseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickHelper(Globals.ItemType.CASE);
            }
        });

        setCancelable(false);
        Dialog d = builder.create();
        ColorDrawable color = new ColorDrawable(Color.TRANSPARENT);
        d.getWindow().setBackgroundDrawable(color);
        return d;
    }

    private void onClickHelper(Globals.ItemType itemType) {
        Globals.getInstance().chosenItemType.put(itemType);
        dismiss();
    }
}
