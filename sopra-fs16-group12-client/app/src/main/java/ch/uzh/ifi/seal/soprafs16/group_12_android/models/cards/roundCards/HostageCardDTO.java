package ch.uzh.ifi.seal.soprafs16.group_12_android.models.cards.roundCards;

import ch.uzh.ifi.seal.soprafs16.group_12_android.Card;
import ch.uzh.ifi.seal.soprafs16.group_12_android.StationCard;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;

@JsonTypeName("hostageCard")
public class HostageCardDTO extends StationCardDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public Card fetchCard() {
        return StationCard.sc_h;
    }
}
