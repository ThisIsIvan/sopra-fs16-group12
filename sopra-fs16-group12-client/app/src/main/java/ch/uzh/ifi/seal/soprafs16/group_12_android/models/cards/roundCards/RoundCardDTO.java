package ch.uzh.ifi.seal.soprafs16.group_12_android.models.cards.roundCards;

import ch.uzh.ifi.seal.soprafs16.group_12_android.models.cards.CardDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.turns.TurnDTO;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;
import java.util.List;

@JsonTypeName("roundCard")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = StationCardDTO.class, name = "stationCard"),
        @JsonSubTypes.Type(value = AngryMarshalCardDTO.class, name = "angryMarshalCard"),
        @JsonSubTypes.Type(value = BrakingCardDTO.class,name = "brakingCard"),
        @JsonSubTypes.Type(value = GetItAllCardDTO.class, name = "getItAllCard"),
        @JsonSubTypes.Type(value = PassengerRebellionCardDTO.class, name = "passengerRebellionCard"),
        @JsonSubTypes.Type(value = BlankTunnelCardDTO.class, name = "blankTunnelCard"),
        @JsonSubTypes.Type(value = BlankBridgeCardDTO.class, name = "blankBridgeCard"),
        @JsonSubTypes.Type(value = PivotablePoleCardDTO.class, name = "pivotablePoleCard")
})
public abstract class RoundCardDTO extends CardDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private List<TurnDTO> pattern;

    public List<TurnDTO> getPattern() {
        return pattern;
    }

    public void setPattern(List<TurnDTO> pattern) {
        this.pattern = pattern;
    }

}
