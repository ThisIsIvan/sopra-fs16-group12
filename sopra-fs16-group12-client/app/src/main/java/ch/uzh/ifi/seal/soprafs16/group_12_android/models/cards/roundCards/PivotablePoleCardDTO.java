package ch.uzh.ifi.seal.soprafs16.group_12_android.models.cards.roundCards;

import ch.uzh.ifi.seal.soprafs16.group_12_android.Card;
import ch.uzh.ifi.seal.soprafs16.group_12_android.RoundCard;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;

@JsonTypeName("pivotablePoleCard")
public class PivotablePoleCardDTO extends RoundCardDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public Card fetchCard() {
        return RoundCard.rc_ntnn_PP;
    }
}
