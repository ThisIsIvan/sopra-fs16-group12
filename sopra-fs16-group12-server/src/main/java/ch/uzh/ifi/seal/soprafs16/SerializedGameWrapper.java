package ch.uzh.ifi.seal.soprafs16;

import ch.uzh.ifi.seal.soprafs16.constant.GameStatus;

/**
 * Created by Christoph on 21.05.2016.
 */
public class SerializedGameWrapper {
    private String serializedGame;

    private GameStatus gameStatus;

    public String getSerializedGame() {
        return serializedGame;
    }

    public void setSerializedGame(String serializedGame) {
        this.serializedGame = serializedGame;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }
}
