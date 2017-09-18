package ch.uzh.ifi.seal.soprafs16.group_12_android.models.cards.handCards;

import ch.uzh.ifi.seal.soprafs16.group_12_android.BulletCard;
import ch.uzh.ifi.seal.soprafs16.group_12_android.Card;
import ch.uzh.ifi.seal.soprafs16.group_12_android.Globals;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;

@JsonTypeName("bulletCard")
public class BulletCardDTO extends HandCardDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Globals.SourceType sourceType;

    private int bulletCounter;

    public Globals.SourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(Globals.SourceType sourceType) {
        this.sourceType = sourceType;
    }

    public int getBulletCounter() {
        return bulletCounter;
    }

    public void setBulletCounter(int bulletCounter) {
        this.bulletCounter = bulletCounter;
    }

    @Override
    protected Card fetchCard() {
        BulletCard bulletCard = null;
        switch (sourceType){
            case MARSHAL: bulletCard = BulletCard.NEUTRAL;
                break;
            case BELLE: bulletCard = BulletCard.BELLE[bulletCounter-1];
                break;
            case CHEYENNE: bulletCard = BulletCard.CHEYENNE[bulletCounter-1];
                break;
            case DJANGO: bulletCard = BulletCard.DJANGO[bulletCounter-1];
                break;
            case DOC: bulletCard = BulletCard.DOC[bulletCounter-1];
                break;
            case GHOST: bulletCard = BulletCard.GHOST[bulletCounter-1];
                break;
            case TUCO: bulletCard = BulletCard.TUCO[bulletCounter-1];
                break;
        }
        return bulletCard;
    }
}
