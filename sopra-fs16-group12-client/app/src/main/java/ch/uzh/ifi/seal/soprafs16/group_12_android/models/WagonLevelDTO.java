package ch.uzh.ifi.seal.soprafs16.group_12_android.models;

import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rafael on 04/04/16.
 */

public class WagonLevelDTO implements Serializable{

    @Nullable
    private Long id;

    private List<ItemDTO> items;

    private Level levelType;

    private MarshalDTO marshal;

    private List<UserDTO> users;

    @Nullable
    public Long getId() {
        return id;
    }

    public void setId(@Nullable Long id) {
        this.id = id;
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }

    public Level getLevelType() {
        return levelType;
    }

    public void setLevelType(Level levelType) {
        this.levelType = levelType;
    }

    public MarshalDTO getMarshal() {
        return marshal;
    }

    public void setMarshal(MarshalDTO marshal) {
        this.marshal = marshal;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    public enum Level {
        TOP,
        BOTTOM
    }
}
