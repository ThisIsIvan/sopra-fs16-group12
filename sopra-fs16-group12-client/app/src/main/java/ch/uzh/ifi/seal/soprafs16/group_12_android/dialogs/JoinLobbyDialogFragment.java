package ch.uzh.ifi.seal.soprafs16.group_12_android.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import ch.uzh.ifi.seal.soprafs16.group_12_android.R;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.GameDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.service.RestService;
import ch.uzh.ifi.seal.soprafs16.group_12_android.views.UserQueueView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 02.04.2016.
 */

/**
 * A dialog that displays the on the server existing Lobbies.
 */
public class JoinLobbyDialogFragment extends DialogFragment {

    private static final String ARG_LOBBIES = "ARG_LOBBIES";
    private static final String TAG = "JoinLobbyD";

    private Handler handler;

    InteractionListener mListener;

    private Long currentlySelectedGameId;
    private LobbyArrayAdapter lobbyArrayAdapter;
    private ListView lobbyListView;

    public static JoinLobbyDialogFragment newInstance(ArrayList<GameDTO> lobbies){
        JoinLobbyDialogFragment d = new JoinLobbyDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_LOBBIES,lobbies);
        d.setArguments(args);
        return d;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_join_lobby, null);

        this.currentlySelectedGameId = null;
        this.handler = new Handler();

        ArrayList<GameDTO> lobbyList = (ArrayList<GameDTO>) getArguments().getSerializable(ARG_LOBBIES);
        this.lobbyArrayAdapter = new LobbyArrayAdapter(getActivity(),lobbyList);
        this.lobbyListView = (ListView) v.findViewById(R.id.dialog_joinlobby_list);
        this.lobbyListView.setAdapter(lobbyArrayAdapter);
        this.lobbyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JoinLobbyDialogFragment.this.currentlySelectedGameId = JoinLobbyDialogFragment.this.lobbyArrayAdapter.getItem(position).getId();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);
        v.findViewById(R.id.dialog_join_lobby_join).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (JoinLobbyDialogFragment.this.currentlySelectedGameId != null) {
                    mListener.onJoinLobbyDialogJoinClick(currentlySelectedGameId);
                    dismiss();
                } else {
                    Toast.makeText(getContext(),"You didn't select a lobby.", Toast.LENGTH_LONG).show();
                }
            }
        });
        v.findViewById(R.id.dialog_join_lobby_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JoinLobbyDialogFragment.this.handler.post(new LobbyRefresher());
            }
        });
        v.findViewById(R.id.dialog_join_lobby_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try {
            mListener = (InteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement LeaveDialogListener");
        }
    }

    public interface InteractionListener {
        void onJoinLobbyDialogJoinClick(Long gameId);
    }

    private class LobbyArrayAdapter extends ArrayAdapter<GameDTO>{

        public LobbyArrayAdapter(Context context, List<GameDTO> gameDTOs) {
            super(context, -1, gameDTOs);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = LayoutInflater.from(getContext()).inflate(R.layout.lobby_list_element, parent, false);
            GameDTO game = getItem(position);

            TextView lobbyName = (TextView) rowView.findViewById(R.id.lobby_list_element_name);
            lobbyName.setText(game.getName());

            TextView lobbyNPlayers = (TextView) rowView.findViewById(R.id.lobby_list_elements_n_players);
            lobbyNPlayers.setText(game.getUsers().size()+"/4");

            UserQueueView userView = new UserQueueView(getContext(),game.getUsers().size(),75);
            userView.setQueue(game.getUsers(),0,false);
            FrameLayout userViewContainer = (FrameLayout) rowView.findViewById(R.id.dialog_join_lobby_element_character_container);
            userViewContainer.addView(userView);
            userView.refresh();

            return rowView;
        }
    }

    private class LobbyRefresher extends Thread{

        @Override
        public void run() {
            super.run();
            RestService.getInstance(getActivity()).getLobbies().enqueue(new Callback<List<GameDTO>>() {
                @Override
                public void onResponse(Call<List<GameDTO>> call, Response<List<GameDTO>> response) {
                    JoinLobbyDialogFragment.this.lobbyArrayAdapter.clear();
                    JoinLobbyDialogFragment.this.lobbyArrayAdapter.addAll(response.body());
                    Log.d(TAG,"successful updated lobbies");
                }

                @Override
                public void onFailure(Call<List<GameDTO>> call, Throwable t) {
                    Log.e("LobbyR",t.getMessage());
                }
            });
        }
    }
}
