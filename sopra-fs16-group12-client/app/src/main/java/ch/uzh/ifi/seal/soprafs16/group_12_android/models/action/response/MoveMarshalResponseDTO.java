package ch.uzh.ifi.seal.soprafs16.group_12_android.models.action.response;

import ch.uzh.ifi.seal.soprafs16.group_12_android.models.action.ActionResponseDTO;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;

/**
 * Created by Ivan on 27.04.2016.
 */
@JsonTypeName("moveMarshalResponseDTO")
public class MoveMarshalResponseDTO extends ActionResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long wagonLevelId;

    public MoveMarshalResponseDTO(Long wagonLevelId) {
        this.wagonLevelId = wagonLevelId;
    }

    public void setWagonLevelId(Long wagonLevelId) {
        this.wagonLevelId = wagonLevelId;
    }

    public Long getWagonLevelId() {
        return wagonLevelId;
    }
}
