package ch.uzh.ifi.seal.soprafs16.group_12_android.models.cards;

import ch.uzh.ifi.seal.soprafs16.group_12_android.Card;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.cards.handCards.HandCardDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.cards.roundCards.RoundCardDTO;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;

@JsonTypeName("card")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value=HandCardDTO.class, name = "handCard"),
        @JsonSubTypes.Type(value=RoundCardDTO.class, name = "roundCard")
})
public abstract class CardDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Card card(){
        Card card = fetchCard();
        card.setId(id);
        return card;
    }

    protected abstract Card fetchCard();
}
