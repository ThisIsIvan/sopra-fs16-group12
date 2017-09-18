package ch.uzh.ifi.seal.soprafs16.group_12_android.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import ch.uzh.ifi.seal.soprafs16.group_12_android.R;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.ItemDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.UserDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafael on 27/04/16.
 */
public class ScoreboardDialogFragment extends DialogFragment{

    private static final String ARG_USERS = "ARG_USERS";
    private static final String ARG_IS_GAME_RUNNING = "ARG_IS_GAME_RUNNING";
    private static final String ARG_WINNER_ID = "ARG_WINNER_ID";
    private static final String ARG_GUNSLINGERS_IDS = "ARG_GUNSLINGER_IDS";

    InteractionListener mListener;

    public static ScoreboardDialogFragment newInstanceRunning(ArrayList<UserDTO> users){
        ScoreboardDialogFragment d = new ScoreboardDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USERS,users);
        args.putBoolean(ARG_IS_GAME_RUNNING, true);
        d.setArguments(args);
        return d;
    }

    public static ScoreboardDialogFragment newInstanceFinished(ArrayList<UserDTO> users, ArrayList<Long> gunslingerIds, Long winnerId){
        ScoreboardDialogFragment d = new ScoreboardDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USERS,users);
        args.putBoolean(ARG_IS_GAME_RUNNING, false);
        args.putSerializable(ARG_GUNSLINGERS_IDS, gunslingerIds);
        args.putLong(ARG_WINNER_ID, winnerId);
        d.setArguments(args);
        return d;
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try {
            mListener = (InteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ScoreboardInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_scoreboard, null);

        ArrayList<UserDTO> userList = (ArrayList<UserDTO>) getArguments().getSerializable(ARG_USERS);
        UserArrayAdapter userArrayAdapter = new UserArrayAdapter(getActivity(),userList);
        ListView scoreBoardListView = (ListView) v.findViewById(R.id.dialog_scoreboard_list);
        scoreBoardListView.setAdapter(userArrayAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);
        v.findViewById(R.id.dialog_scoreboard_button_done).setAlpha(getArguments().getBoolean(ARG_IS_GAME_RUNNING) ? 1f : 0.5f);
        v.findViewById(R.id.dialog_scoreboard_button_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        v.findViewById(R.id.dialog_scoreboard_button_done).setClickable(getArguments().getBoolean(ARG_IS_GAME_RUNNING));
        v.findViewById(R.id.dialog_scoreboard_button_leave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                ScoreboardDialogFragment.this.mListener.onScoreboardGameLeave();
            }
        });

        return builder.create();
    }

    public interface InteractionListener {
        void onScoreboardGameLeave();
    }

    private class UserArrayAdapter extends ArrayAdapter<UserDTO>{

        private UserArrayAdapter(Context context, List<UserDTO> users){
            super(context,-1,users);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            UserDTO user = getItem(position);
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.dialog_scoreboard_element, parent, false);

            FrameLayout circleContainer = (FrameLayout) rowView.findViewById(R.id.dialog_scoreboard_element_circle_container);
            ImageView circle = new ImageView(getContext());
            circle.setImageResource(user.getCharacter().circleSprite());
            circleContainer.addView(circle);

            TextView userNameView = (TextView) rowView.findViewById(R.id.dialog_scoreboard_element_username);
            userNameView.setText(user.getName());

            Integer nBags = 0;
            Integer nGems = 0;
            Integer nCases = 0;
            Integer networth = 0;
            for(ItemDTO item : user.getItems()){
                switch (item.getItemType()){
                    case BAG: nBags++;
                        break;
                    case GEM: nGems++;
                        break;
                    case CASE: nCases++;
                        break;
                }
                networth+=item.getValue();
            }

            TextView nBagsView = (TextView) rowView.findViewById(R.id.dialog_scoreboard_element_bag);
            nBagsView.setText(nBags.toString());

            TextView nGemsView = (TextView) rowView.findViewById(R.id.dialog_scoreboard_element_gem);
            nGemsView.setText(nGems.toString());

            TextView nCasesView = (TextView) rowView.findViewById(R.id.dialog_scoreboard_element_case);
            nCasesView.setText(nCases.toString());

            TextView networthView = (TextView) rowView.findViewById(R.id.dialog_scoreboard_element_networth);
            networthView.setText("$ "+networth.toString());

            if(!getArguments().getBoolean(ARG_IS_GAME_RUNNING)) {
                ImageView gunslingerView = (ImageView) rowView.findViewById(R.id.dialog_scoreboard_element_gunslinger);
                gunslingerView.setVisibility(((ArrayList<Long>) getArguments().getSerializable(ARG_GUNSLINGERS_IDS)).contains(user.getId()) ? View.VISIBLE : View.INVISIBLE);

                if(getArguments().getLong(ARG_WINNER_ID) == user.getId()){
                    rowView.setBackground(new ColorDrawable(0x99EED0D0));
                    ((ImageView)rowView.findViewById(R.id.dialog_scoreboard_element_crown)).setVisibility(View.VISIBLE);
                }
            }

            return rowView;
        }
    }
}
