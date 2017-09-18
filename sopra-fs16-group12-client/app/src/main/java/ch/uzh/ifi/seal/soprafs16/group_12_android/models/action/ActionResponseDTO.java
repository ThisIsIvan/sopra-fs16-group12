package ch.uzh.ifi.seal.soprafs16.group_12_android.models.action;

import ch.uzh.ifi.seal.soprafs16.group_12_android.models.action.response.*;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;

/**
 * Created by Ivan on 19.04.2016.
 */
@JsonTypeName("actionResponseDTO")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CollectItemResponseDTO.class, name = "collectItemResponseDTO"),
        @JsonSubTypes.Type(value = DrawCardResponseDTO.class, name = "drawCardResponseDTO"),
        @JsonSubTypes.Type(value = MoveMarshalResponseDTO.class, name = "moveMarshalResponseDTO"),
        @JsonSubTypes.Type(value = MoveResponseDTO.class, name = "moveResponseDTO"),
        @JsonSubTypes.Type(value = PlayCardResponseDTO.class, name = "playCardResponseDTO"),
        @JsonSubTypes.Type(value = PunchResponseDTO.class, name = "punchResponseDTO"),
        @JsonSubTypes.Type(value = ShootResponseDTO.class, name = "shootResponseDTO")
})
public abstract class ActionResponseDTO extends ActionDTO implements Serializable {

}
