package ch.uzh.ifi.seal.soprafs16.group_12_android.models.action.response;

import ch.uzh.ifi.seal.soprafs16.group_12_android.Globals;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.action.ActionResponseDTO;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;

/**
 * Created by Ivan on 19.04.2016.
 */
@JsonTypeName("collectItemResponseDTO")
public class CollectItemResponseDTO extends ActionResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Globals.ItemType collectedItemType;

    public CollectItemResponseDTO(Globals.ItemType collectedItemType) {
        this.collectedItemType = collectedItemType;
    }

    public Globals.ItemType getCollectedItemType() {
        return collectedItemType;
    }

    public void setCollectedItemType(Globals.ItemType collectedItemType) {
        this.collectedItemType = collectedItemType;
    }
}
