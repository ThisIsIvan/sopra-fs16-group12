package ch.uzh.ifi.seal.soprafs16.group_12_android.models.cards.handCards;

import ch.uzh.ifi.seal.soprafs16.group_12_android.Card;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;

@JsonTypeName("actionCard")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ShootCardDTO.class, name = "shootCard"),
        @JsonSubTypes.Type(value = CollectCardDTO.class, name = "collectCard"),
        @JsonSubTypes.Type(value = MarshalCardDTO.class, name = "marshalCard"),
        @JsonSubTypes.Type(value = MoveCardDTO.class, name = "moveCard"),
        @JsonSubTypes.Type(value = ChangeLevelCardDTO.class, name = "changeLevelCard"),
        @JsonSubTypes.Type(value = PunchCardDTO.class, name = "punchCard")
})
public abstract class ActionCardDTO extends HandCardDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long playedByUserId;

    private boolean playedHidden;

    public Long getPlayedByUserId() {
        return playedByUserId;
    }

    public void setPlayedByUserId(Long playedByUserId) {
        this.playedByUserId = playedByUserId;
    }

    public boolean isPlayedHidden() {
        return playedHidden;
    }

    public void setPlayedHidden(boolean playedHidden) {
        this.playedHidden = playedHidden;
    }

    @Override
    public Card card(){
        Card card = super.card();
        card.setPlayedHidden(playedHidden);
        card.setPlayedByUserId(playedByUserId);
        return card;
    }
}
