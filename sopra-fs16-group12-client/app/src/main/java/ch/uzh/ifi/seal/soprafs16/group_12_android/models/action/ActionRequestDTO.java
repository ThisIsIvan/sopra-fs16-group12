package ch.uzh.ifi.seal.soprafs16.group_12_android.models.action;

import ch.uzh.ifi.seal.soprafs16.group_12_android.models.action.request.*;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;

/**
 * Created by Ivan on 19.04.2016.
 */
@JsonTypeName("actionRequestDTO")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CollectItemRequestDTO.class, name = "collectItemRequestDTO"),
        @JsonSubTypes.Type(value = DrawOrPlayCardRequestDTO.class, name = "drawOrPlayCardRequestDTO"),
        @JsonSubTypes.Type(value = MoveRequestDTO.class, name = "moveRequestDTO"),
        @JsonSubTypes.Type(value = MoveMarshalRequestDTO.class, name = "moveMarshalRequestDTO"),
        @JsonSubTypes.Type(value = PunchRequestDTO.class, name = "punchRequestDTO"),
        @JsonSubTypes.Type(value = ShootRequestDTO.class, name = "shootRequestDTO")
})
public abstract class ActionRequestDTO extends ActionDTO implements Serializable {

}
