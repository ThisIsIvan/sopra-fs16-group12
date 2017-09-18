package ch.uzh.ifi.seal.soprafs16.group_12_android.models.action.request;

import ch.uzh.ifi.seal.soprafs16.group_12_android.models.action.ActionRequestDTO;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ivan on 19.04.2016.
 */
@JsonTypeName("punchRequestDTO")
public class PunchRequestDTO extends ActionRequestDTO implements Serializable {


    private static final long serialVersionUID = 1L;

    private List<Long> punchableUserIds;
    private List<Boolean> hasGem;
    private List<Boolean> hasBag;
    private List<Boolean> hasCase;
    private List<Long> movable;


    public List<Long> getPunchableUserIds() {
        return punchableUserIds;
    }

    public void setPunchableUserIds(List<Long> punchableUserIds) {
        this.punchableUserIds = punchableUserIds;
    }

    public List<Boolean> getHasGem() {
        return hasGem;
    }

    public void setHasGem(List<Boolean> hasGem) {
        this.hasGem = hasGem;
    }

    public List<Boolean> getHasBag() {
        return hasBag;
    }

    public void setHasBag(List<Boolean> hasBag) {
        this.hasBag = hasBag;
    }

    public List<Boolean> getHasCase() {
        return hasCase;
    }

    public void setHasCase(List<Boolean> hasCase) {
        this.hasCase = hasCase;
    }

    public List<Long> getMovable() {
        return movable;
    }

    public void setMovable(List<Long> movable) {
        this.movable = movable;
    }
}
