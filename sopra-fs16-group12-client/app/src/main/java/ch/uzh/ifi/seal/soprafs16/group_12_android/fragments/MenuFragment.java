package ch.uzh.ifi.seal.soprafs16.group_12_android.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import ch.uzh.ifi.seal.soprafs16.group_12_android.R;
import ch.uzh.ifi.seal.soprafs16.group_12_android.views.SheriffButton;

/**
 * Created by Ivan on 30.03.2016.
 */

/**
 * The starting Fragment that displays the title, "Create Lobby"-Button,
 * "Join Lobby"-button and "Manual"-button.
 */
public class MenuFragment extends Fragment {

    private InteractionListener mListener;

    private Button registerButton;
    private Button logoutButton;

    public static MenuFragment newInstance(){
        MenuFragment fragment = new MenuFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_menu, container, false);
        ((FrameLayout) v.findViewById(R.id.fragment_menu_create_game_container)).addView(new SheriffButton(getContext(),"CREATE") {
            @Override
            public void onClick() {
                mListener.onCreateGameButtonClick();
            }
        });

        ((FrameLayout) v.findViewById(R.id.fragment_menu_join_game_container)).addView(new SheriffButton(getContext(),"JOIN") {
            @Override
            public void onClick() {
                mListener.onJoinGameButtonClick();
            }
        });

        ((FrameLayout) v.findViewById(R.id.fragment_menu_manual_container)).addView(new SheriffButton(getContext(),"MANUAL") {
            @Override
            public void onClick() {
                MenuFragment.this.mListener.onManualButtonClick();
            }
        });


        registerButton = (Button) v.findViewById(R.id.register);
        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onRegisterButtonClick();
            }
        });

        logoutButton = (Button) v.findViewById(R.id.logout);
        logoutButton.setVisibility(View.INVISIBLE);

        logoutButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onLogoutButtonClick();
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try {
            mListener = (InteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +  " must implement InteractionListener");
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mListener = null;
    }

    public Button getRegisterButton() {
        return registerButton;
    }

    public Button getLogoutButton() {
        return logoutButton;
    }

    public interface InteractionListener {
        void onCreateGameButtonClick();
        void onJoinGameButtonClick();
        void onRegisterButtonClick();
        void onLogoutButtonClick();
        void onManualButtonClick();
    }
}
