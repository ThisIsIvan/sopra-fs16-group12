package ch.uzh.ifi.seal.soprafs16.group_12_android.models;

import android.support.annotation.Nullable;
import ch.uzh.ifi.seal.soprafs16.group_12_android.Globals;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.action.ActionRequestDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.cards.DeckDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.cards.handCards.ActionCardDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.cards.roundCards.RoundCardDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafael on 30/03/16.
 */
public class GameDTO implements Serializable{

    @Nullable
    private  Long id;

    private  String name;

    private  String owner;

    @Nullable
    private  String status;

    @Nullable
    private  Integer currentPlayer;

    @Nullable
    private  ArrayList<UserDTO> users;

    @Nullable
    private  List<WagonDTO> wagons;

    @Nullable
    private  Integer currentRound;

    @Nullable
    private  Integer currentTurn;

    @Nullable
    private Globals.GamePhase currentPhase;

    @Nullable
    private DeckDTO<RoundCardDTO> roundCardDeck;

    @Nullable
    private DeckDTO<ActionCardDTO> commonDeck;

    @Nullable
    private Integer roundStarter;

    @Nullable
    private List<ActionRequestDTO> actions;

    @Nullable
    public Long getId() {
        return id;
    }

    public void setId(@Nullable Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Nullable
    public String getStatus() {
        return status;
    }

    public void setStatus(@Nullable String status) {
        this.status = status;
    }

    @Nullable
    public Integer getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(@Nullable Integer currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    @Nullable
    public ArrayList<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(@Nullable ArrayList<UserDTO> users) {
        this.users = users;
    }

    @Nullable
    public List<WagonDTO> getWagons() {
        return wagons;
    }

    public void setWagons(@Nullable List<WagonDTO> wagons) {
        this.wagons = wagons;
    }

    @Nullable
    public Integer getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(@Nullable Integer currentRound) {
        this.currentRound = currentRound;
    }

    @Nullable
    public Integer getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(@Nullable Integer currentTurn) {
        this.currentTurn = currentTurn;
    }

    @Nullable
    public Globals.GamePhase getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(@Nullable Globals.GamePhase currentPhase) {
        this.currentPhase = currentPhase;
    }

    @Nullable
    public DeckDTO<RoundCardDTO> getRoundCardDeck() {
        return roundCardDeck;
    }

    public void setRoundCardDeck(@Nullable DeckDTO<RoundCardDTO> roundCardDeck) {
        this.roundCardDeck = roundCardDeck;
    }

    @Nullable
    public DeckDTO<ActionCardDTO> getCommonDeck() {
        return commonDeck;
    }

    public void setCommonDeck(@Nullable DeckDTO<ActionCardDTO> commonDeck) {
        this.commonDeck = commonDeck;
    }

    @Nullable
    public Integer getRoundStarter() {
        return roundStarter;
    }

    public void setRoundStarter(@Nullable Integer roundStarter) {
        this.roundStarter = roundStarter;
    }

    @Nullable
    public List<ActionRequestDTO> getActions() {
        return actions;
    }

    public void setActions(@Nullable List<ActionRequestDTO> actions) {
        this.actions = actions;
    }
}
