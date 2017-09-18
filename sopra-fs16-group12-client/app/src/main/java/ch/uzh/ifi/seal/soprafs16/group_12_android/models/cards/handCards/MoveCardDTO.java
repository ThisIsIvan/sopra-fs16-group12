package ch.uzh.ifi.seal.soprafs16.group_12_android.models.cards.handCards;

import ch.uzh.ifi.seal.soprafs16.group_12_android.Card;
import ch.uzh.ifi.seal.soprafs16.group_12_android.Globals;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;

@JsonTypeName("moveCard")
public class MoveCardDTO extends ActionCardDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected Card fetchCard() {
        return Globals.getInstance().characterMap.get(super.getPlayedByUserId()).generateMoveCard().setPlayedHidden(super.isPlayedHidden());
    }
}
