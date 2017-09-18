package ch.uzh.ifi.seal.soprafs16.group_12_android.models.action.request;

import ch.uzh.ifi.seal.soprafs16.group_12_android.models.action.ActionRequestDTO;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ivan on 27.04.2016.
 */
@JsonTypeName("moveMarshalRequestDTO")
public class MoveMarshalRequestDTO extends ActionRequestDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    private List<Long> movableWagonsLvlIds;

    public List<Long> getMovableWagonsLvlIds() {
        return movableWagonsLvlIds;
    }

    public void setMovableWagonsLvlIds(List<Long> movableWagonsLvlIds) {
        this.movableWagonsLvlIds = movableWagonsLvlIds;
    }
}
