package ch.uzh.ifi.seal.soprafs16.group_12_android.handlers;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import ch.uzh.ifi.seal.soprafs16.group_12_android.fragments.LobbyFragment;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.UserDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.characters.Character;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by rafael on 17/05/16.
 */
public class LobbyHandler extends Handler {

    public static final int ENABLE_START_GAME_BUTTON = 0;
    public static final int DISABLE_START_GAME_BUTTON = 1;
    public static final int ENABLE_CHARACTER_CARDS = 2;
    public static final int DISABLE_CHARACTER_CARDS = 3;
    public static final int UPDATE_USER_LIST = 4;
    public static final String ARG_GAME_USERS = "LobbyHandler.ARG_GAME_USERS";
    public static final String ARG_GAME_OWNER = "LobbyHandler.ARG_GAME_OWNER";
    public static final String ARG_CHARACTER_LIST = "LobbyHandler.ARG_CHARACTER_LIST";

    private LobbyFragment lobbyFragment;

    public static LobbyHandler newInstance(LobbyFragment lobbyFragment){
        LobbyHandler lobbyHandler = new LobbyHandler();
        lobbyHandler.lobbyFragment = lobbyFragment;
        return lobbyHandler;
    }

    private LobbyHandler(){}

    @Override
    public void handleMessage(Message msg) {
        Bundle args = msg.getData();
        switch (msg.what){
            case ENABLE_START_GAME_BUTTON:
                LobbyHandler.this.lobbyFragment.getStartGameButton().setClickable(true);
                LobbyHandler.this.lobbyFragment.getStartGameButton().setAlpha(1f);
                break;
            case DISABLE_START_GAME_BUTTON:
                LobbyHandler.this.lobbyFragment.getStartGameButton().setClickable(false);
                LobbyHandler.this.lobbyFragment.getStartGameButton().setAlpha(0.5f);
                break;
            case ENABLE_CHARACTER_CARDS:
                for(Character character : (HashSet<Character>)args.getSerializable(ARG_CHARACTER_LIST)){
                    ImageView card = LobbyHandler.this.lobbyFragment.getCardMap().get(character.getClass());
                    card.setClickable(true);
                    card.setAlpha(1f);
                }
                 break;
            case DISABLE_CHARACTER_CARDS:
                for(Character character : (HashSet<Character>)args.getSerializable(ARG_CHARACTER_LIST)){
                    ImageView card = LobbyHandler.this.lobbyFragment.getCardMap().get(character.getClass());
                    card.setClickable(false);
                    card.setAlpha(0.5f);
                }
                break;
            case UPDATE_USER_LIST:
                LobbyHandler.this.lobbyFragment.getUserArrayAdapter().clear();
                LobbyHandler.this.lobbyFragment.getUserArrayAdapter().addAll((ArrayList<UserDTO>)args.getSerializable(ARG_GAME_USERS));
                LobbyHandler.this.lobbyFragment.getUserArrayAdapter().setOwnerName(args.getString(ARG_GAME_OWNER));
                break;
        }
    }
}
