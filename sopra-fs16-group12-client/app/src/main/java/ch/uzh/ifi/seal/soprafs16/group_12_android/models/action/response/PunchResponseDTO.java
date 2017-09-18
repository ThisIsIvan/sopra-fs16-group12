package ch.uzh.ifi.seal.soprafs16.group_12_android.models.action.response;

import android.support.annotation.Nullable;
import ch.uzh.ifi.seal.soprafs16.group_12_android.Globals;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.action.ActionResponseDTO;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;

/**
 * Created by Ivan on 19.04.2016.
 */
@JsonTypeName("punchResponseDTO")
public class PunchResponseDTO extends ActionResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long victimId;
    private Long wagonLevelId;

    @Nullable
    private Globals.ItemType itemType;

    public PunchResponseDTO(Long victimId, Long wagonLevelId, Globals.ItemType itemType) {
        this.victimId = victimId;
        this.wagonLevelId = wagonLevelId;
        this.itemType = itemType;
    }

    public Globals.ItemType getItemType() {
        return itemType;
    }

    public Long getWagonLevelId() {
        return wagonLevelId;
    }

    public Long getVictimId() {
        return victimId;
    }

    public void setVictimId(Long victimId) {
        this.victimId = victimId;
    }

    public void setWagonLevelId(Long wagonLevelId) {
        this.wagonLevelId = wagonLevelId;
    }

    public void setItemType(Globals.ItemType itemType) {
        this.itemType = itemType;
    }

}
