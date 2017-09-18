package ch.uzh.ifi.seal.soprafs16.group_12_android.models.cards.roundCards;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;

@JsonTypeName("stationCard")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PickPocketingCardDTO.class, name = "pickPocketingCard"),
        @JsonSubTypes.Type(value = MarshallsRevengeCardDTO.class, name = "marshallsRevengeCard"),
        @JsonSubTypes.Type(value = HostageCardDTO.class, name = "hostageCard")
})
public abstract class StationCardDTO extends RoundCardDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

}
