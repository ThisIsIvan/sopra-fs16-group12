package ch.uzh.ifi.seal.soprafs16.group_12_android.fragments;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import ch.uzh.ifi.seal.soprafs16.group_12_android.Globals;
import ch.uzh.ifi.seal.soprafs16.group_12_android.R;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.UserDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.characters.*;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.characters.Character;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ivan on 02.04.2016.
 */

/**
 * Shows the character description and the lobby overlay.
 */
public class LobbyFragment extends Fragment {

    private MediaPlayer belleSound;
    private MediaPlayer cheyenneSound;
    private MediaPlayer djangoSound;
    private MediaPlayer docSound;
    private MediaPlayer ghostSound;
    private MediaPlayer tucoSound;

    private LobbyFragment.OnFragmentInteractionListener mListener;

    private UserArrayAdapter userArrayAdapter;

    private Button startGameButton;

    private Map<Class<? extends Character>, ImageView> cardMap;

    public static LobbyFragment newInstance() {
        LobbyFragment fragment = new LobbyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public LobbyFragment() {
        this.cardMap = new HashMap<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.userArrayAdapter = new UserArrayAdapter(getActivity(), new ArrayList<UserDTO>(), "");
        View v = inflater.inflate(R.layout.fragment_lobby, container, false);
        ImageView belleCard = (ImageView) v.findViewById(R.id.fragment_lobby_belle);
        ImageView cheyenneCard = (ImageView) v.findViewById(R.id.fragment_lobby_cheyenne);
        ImageView djangoCard = (ImageView) v.findViewById(R.id.fragment_lobby_django);
        ImageView docCard = (ImageView) v.findViewById(R.id.fragment_lobby_doc);
        ImageView tucoCard = (ImageView) v.findViewById(R.id.fragment_lobby_tuco);
        ImageView ghostCard = (ImageView) v.findViewById(R.id.fragment_lobby_ghost);
        this.startGameButton = (Button) v.findViewById(R.id.fragment_lobby_start_game);
        Button leaveGameButton = (Button) v.findViewById(R.id.fragment_lobby_leave_game);

        this.cardMap.put(Belle.class, belleCard);
        this.cardMap.put(Cheyenne.class, cheyenneCard);
        this.cardMap.put(Django.class, djangoCard);
        this.cardMap.put(Doc.class, docCard);
        this.cardMap.put(Ghost.class, ghostCard);
        this.cardMap.put(Tuco.class, tucoCard);

        this.belleSound = MediaPlayer.create(getContext(), R.raw.belle);
        this.cheyenneSound = MediaPlayer.create(getContext(), R.raw.cheyenne);
        this.djangoSound = MediaPlayer.create(getContext(), R.raw.django);
        this.docSound = MediaPlayer.create(getContext(), R.raw.doc);
        this.ghostSound = MediaPlayer.create(getContext(), R.raw.ghost);
        this.tucoSound = MediaPlayer.create(getContext(), R.raw.tuco);

        ListView userList = (ListView) v.findViewById(R.id.fragment_lobby_user_list);
        userList.setAdapter(userArrayAdapter);
        belleCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnCardClick(Globals.getInstance().belle);
                belleSound.start();
            }
        });
        cheyenneCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnCardClick(Globals.getInstance().cheyenne);
                cheyenneSound.start();
            }
        });
        djangoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnCardClick(Globals.getInstance().django);
                djangoSound.start();
            }
        });
        docCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnCardClick(Globals.getInstance().doc);
                docSound.start();
            }
        });
        tucoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnCardClick(Globals.getInstance().tuco);
                tucoSound.start();
            }
        });
        ghostCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnCardClick(Globals.getInstance().ghost);
                ghostSound.start();
            }
        });
        this.startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnStartGameClick();
            }
        });
        this.startGameButton.setClickable(false);
        leaveGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnLeaveGameClick();
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement LobbyFragment.InteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public Button getStartGameButton() {
        return startGameButton;
    }

    public UserArrayAdapter getUserArrayAdapter() {
        return userArrayAdapter;
    }

    public Map<Class<? extends Character>, ImageView> getCardMap() {
        return cardMap;
    }

    public interface OnFragmentInteractionListener {
        void OnCardClick(Character character);

        void OnStartGameClick();

        void OnLeaveGameClick();
    }

    public class UserArrayAdapter extends ArrayAdapter<UserDTO> {

        String ownerName;

        public UserArrayAdapter(Context context, List<UserDTO> userDTOs, String ownerName) {
            super(context, R.layout.fragment_lobby_user_list_element, userDTOs);
            this.ownerName = ownerName;
        }

        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_lobby_user_list_element, parent, false);
            TextView userName = (TextView) view.findViewById(R.id.fragment_lobby_user_list_element_name);
            ImageView userImage = (ImageView) view.findViewById(R.id.fragment_lobby_user_list_element_image);

            userName.setText(getItem(position).getName());

            try {
                userImage.setImageResource(getItem(position).getCharacter().circleSprite());
                userImage.setVisibility(View.VISIBLE);
            } catch (NullPointerException e) {
                // NO character selected
                userImage.setVisibility(View.INVISIBLE);
            }

            if (getItem(position).getName().equals(this.ownerName)) {
                view.findViewById(R.id.fragment_lobby_user_list_element_owner).setVisibility(View.VISIBLE);
            }

            return view;
        }
    }


}

