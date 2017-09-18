package ch.uzh.ifi.seal.soprafs16.group_12_android.models.action;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;

/**
 * Created by Ivan on 19.04.2016.
 */
@JsonTypeName("actionDTO")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @Type(value = ActionRequestDTO.class, name = "actionRequestDTO"),
        @Type(value = ActionResponseDTO.class, name = "actionResponseDTO")
})
public abstract class ActionDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    private Long gameId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @JsonProperty("spielId")
    public Long getGameId() {
        return gameId;
    }

    @JsonProperty("spielId")
    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }
}
