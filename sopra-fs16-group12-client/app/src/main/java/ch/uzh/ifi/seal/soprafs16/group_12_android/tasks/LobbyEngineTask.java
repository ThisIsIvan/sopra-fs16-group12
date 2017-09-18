package ch.uzh.ifi.seal.soprafs16.group_12_android.tasks;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import ch.uzh.ifi.seal.soprafs16.group_12_android.Globals;
import ch.uzh.ifi.seal.soprafs16.group_12_android.handlers.LobbyHandler;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.GameDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.UserDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.characters.Character;
import ch.uzh.ifi.seal.soprafs16.group_12_android.service.RestService;
import retrofit2.Response;

import java.io.IOException;
import java.util.HashSet;

/**
 * Created by rafael on 18/05/16.
 */
public abstract class LobbyEngineTask implements EngineTask {

    private static final String TAG = "LobbyET";

    private Long gameId;
    private LobbyHandler lobbyHandler;
    private Context context;

    public LobbyEngineTask(Long gameId, LobbyHandler lobbyHandler, Context context) {
        this.gameId = gameId;
        this.lobbyHandler = lobbyHandler;
        this.context = context;
    }

    @Override
    public void execute() throws ExecutionException, InterruptedException {
        try {
            Log.d(TAG, "polling");
            Response<GameDTO> response = RestService.getInstance(context).getGame(gameId).execute();
            Log.d(TAG, "polled");
            GameDTO game = response.body();
            handleLobby(game);
        } catch (IOException e) {
            throw new ExecutionException(e);
        }
    }

    private void handleLobby(GameDTO gameDTO) {
        if (gameDTO.getStatus().equals("RUNNING")) {
            LobbyEngineTask.this.OnGameRunning(gameDTO);
        }

        HashSet<Character> toEnable = new HashSet<>();
        toEnable.add(Globals.getInstance().belle);
        toEnable.add(Globals.getInstance().cheyenne);
        toEnable.add(Globals.getInstance().django);
        toEnable.add(Globals.getInstance().doc);
        toEnable.add(Globals.getInstance().tuco);
        toEnable.add(Globals.getInstance().ghost);
        HashSet<Character> toDisable = new HashSet<>();

        Boolean allHaveSelectedCharacter = gameDTO.getUsers().size() > 0;
        for (UserDTO user : gameDTO.getUsers()) {
            Character character = user.getCharacter();
            if (character != null) {
                toEnable.remove(character);
                toDisable.add(character);
            } else {
                allHaveSelectedCharacter = false;
            }
        }
        if (gameDTO.getUsers().size() > 1 && allHaveSelectedCharacter && Globals.getInstance().userUsername.equals(gameDTO.getOwner())) {
            lobbyHandler.obtainMessage(LobbyHandler.ENABLE_START_GAME_BUTTON).sendToTarget();
        } else {
            lobbyHandler.obtainMessage(LobbyHandler.DISABLE_START_GAME_BUTTON).sendToTarget();
        }

        Bundle updateUserListArgs = new Bundle();
        updateUserListArgs.putSerializable(LobbyHandler.ARG_GAME_USERS, gameDTO.getUsers());
        updateUserListArgs.putString(LobbyHandler.ARG_GAME_OWNER, gameDTO.getOwner());
        Message updateUserListMessage = lobbyHandler.obtainMessage(LobbyHandler.UPDATE_USER_LIST);
        updateUserListMessage.setData(updateUserListArgs);
        updateUserListMessage.sendToTarget();

        Bundle enableCharacterCardArgs = new Bundle();
        enableCharacterCardArgs.putSerializable(LobbyHandler.ARG_CHARACTER_LIST, toEnable);
        Message enableCharacterCardMessage = lobbyHandler.obtainMessage(LobbyHandler.ENABLE_CHARACTER_CARDS);
        enableCharacterCardMessage.setData(enableCharacterCardArgs);
        enableCharacterCardMessage.sendToTarget();

        Bundle disableCharacterCardArgs = new Bundle();
        disableCharacterCardArgs.putSerializable(LobbyHandler.ARG_CHARACTER_LIST, toDisable);
        Message disableCharacterCardMessage = lobbyHandler.obtainMessage(LobbyHandler.DISABLE_CHARACTER_CARDS);
        disableCharacterCardMessage.setData(disableCharacterCardArgs);
        disableCharacterCardMessage.sendToTarget();
    }

    protected abstract void OnGameRunning(GameDTO game);
}
