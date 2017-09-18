package ch.uzh.ifi.seal.soprafs16.group_12_android.models;

import ch.uzh.ifi.seal.soprafs16.group_12_android.Globals;

import java.io.Serializable;


/**
 * Created by rafael on 04/04/16.
 */
public class ItemDTO implements Serializable {

    private  Long    id;
    private  Globals.ItemType itemType;
    private  Integer value;

    @Override
    public int hashCode() {
        // overflow risk
        return id.intValue();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Globals.ItemType getItemType() {
        return itemType;
    }

    public void setItemType(Globals.ItemType itemType) {
        this.itemType = itemType;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
