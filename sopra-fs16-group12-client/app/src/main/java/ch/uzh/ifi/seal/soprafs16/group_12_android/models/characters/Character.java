package ch.uzh.ifi.seal.soprafs16.group_12_android.models.characters;

import ch.uzh.ifi.seal.soprafs16.group_12_android.ActionCard;
import ch.uzh.ifi.seal.soprafs16.group_12_android.BulletCard;
import ch.uzh.ifi.seal.soprafs16.group_12_android.CharacterCard;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value=Belle.class, name = "belle"),
        @JsonSubTypes.Type(value=Cheyenne.class, name = "cheyenne"),
        @JsonSubTypes.Type(value=Django.class, name = "django"),
        @JsonSubTypes.Type(value=Doc.class, name = "doc"),
        @JsonSubTypes.Type(value=Ghost.class, name = "ghost"),
        @JsonSubTypes.Type(value=Tuco.class, name = "tuco")
})
public abstract class Character implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public abstract int figurineSprite();
    public abstract int figurineCrosshairSprite();
    public abstract int figurinePunchSprite();
    public abstract int circleSprite();
    public abstract int color();
    public abstract BulletCard bulletCard(int nBullets);
    public abstract CharacterCard characterCard();
    public abstract ActionCard generateCollectCard();
    public abstract ActionCard generateMarshalCard();
    public abstract ActionCard generateMoveCard();
    public abstract ActionCard generateChangeLevelCard();
    public abstract ActionCard generateShootCard();
    public abstract ActionCard generatePunchCard();
    public abstract String name();
}
