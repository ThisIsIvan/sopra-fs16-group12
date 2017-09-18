package ch.uzh.ifi.seal.soprafs16.group_12_android.models.characters;


import android.graphics.Color;
import ch.uzh.ifi.seal.soprafs16.group_12_android.*;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;

@JsonTypeName("doc")
public final class Doc extends Character implements Serializable {

    private static final BulletCard[] bulletCards = BulletCard.DOC;
    private static final CharacterCard characterCard =  CharacterCard.DOC;

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public int figurineSprite() {
        return R.drawable.figurine_doc;
    }

    @Override
    public int figurineCrosshairSprite() {
        return R.drawable.figurine_doc_crosshair;
    }

    @Override
    public int figurinePunchSprite() {
        return R.drawable.figurine_doc_punch;
    }

    @Override
    public int circleSprite() {
        return R.drawable.circle_doc;
    }

    @Override
    public int color() {
        return Color.rgb(0xb4,0xff,0xff);
    }

    @Override
    public BulletCard bulletCard(int nBullets) {
        return bulletCards[nBullets-1];
    }

    @Override
    public CharacterCard characterCard() {
        return characterCard;
    }

    @Override
    public ActionCard generateCollectCard() {
        return CardFactory.generateDocCollect();
    }

    @Override
    public ActionCard generateMarshalCard() {
        return CardFactory.generateDocMarshal();
    }

    @Override
    public ActionCard generateMoveCard() {
        return CardFactory.generateDocMove();
    }

    @Override
    public ActionCard generateChangeLevelCard() {
        return CardFactory.generateDocChangelLevel();
    }

    @Override
    public ActionCard generateShootCard() {
        return CardFactory.generateDocShoot();
    }

    @Override
    public ActionCard generatePunchCard() {
        return CardFactory.generateDocPunch();
    }

    @Override
    public String name() {
        return "Doc";
    }
}
