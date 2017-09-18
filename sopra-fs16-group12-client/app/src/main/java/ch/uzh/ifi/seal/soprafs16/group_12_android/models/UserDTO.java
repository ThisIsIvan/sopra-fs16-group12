package ch.uzh.ifi.seal.soprafs16.group_12_android.models;

import android.support.annotation.Nullable;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.cards.DeckDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.cards.handCards.BulletCardDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.cards.handCards.HandCardDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.characters.Character;

import java.io.Serializable;
import java.util.ArrayList;

public class UserDTO implements Serializable{

    @Nullable
    private  Long id;

    private  String name;

    private  String username;

    @Nullable
    private  UserDTO.Status status;

    @Nullable
    private ArrayList<ItemDTO> items;

    @Nullable
    private  Character character;

    @Nullable
    private DeckDTO<HandCardDTO> handDeck;

    @Nullable
    private DeckDTO<HandCardDTO> hiddenDeck;

    @Nullable
    private DeckDTO<BulletCardDTO> bulletsDeck;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Nullable
    public Status getStatus() {
        return status;
    }

    public void setStatus(@Nullable Status status) {
        this.status = status;
    }

    @Nullable
    public ArrayList<ItemDTO> getItems() {
        return items;
    }

    public void setItems(@Nullable ArrayList<ItemDTO> items) {
        this.items = items;
    }

    @Nullable
    public Character getCharacter() {
        return character;
    }

    public void setCharacter(@Nullable Character character) {
        this.character = character;
    }

    @Nullable
    public DeckDTO<HandCardDTO> getHandDeck() {
        return handDeck;
    }

    public void setHandDeck(@Nullable DeckDTO<HandCardDTO> handDeck) {
        this.handDeck = handDeck;
    }

    @Nullable
    public DeckDTO<HandCardDTO> getHiddenDeck() {
        return hiddenDeck;
    }

    public void setHiddenDeck(@Nullable DeckDTO<HandCardDTO> hiddenDeck) {
        this.hiddenDeck = hiddenDeck;
    }

    @Nullable
    public DeckDTO<BulletCardDTO> getBulletsDeck() {
        return bulletsDeck;
    }

    public void setBulletsDeck(@Nullable DeckDTO<BulletCardDTO> bulletsDeck) {
        this.bulletsDeck = bulletsDeck;
    }

    public enum Status {
        ONLINE,
        OFFLINE
    }
}

