package ch.uzh.ifi.seal.soprafs16.group_12_android.models.cards.handCards;

import ch.uzh.ifi.seal.soprafs16.group_12_android.models.cards.CardDTO;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;


@JsonTypeName("handCard")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value=BulletCardDTO.class, name = "bulletCard"),
        @JsonSubTypes.Type(value = ActionCardDTO.class, name = "actionCard")
})
public abstract class HandCardDTO extends CardDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

}
