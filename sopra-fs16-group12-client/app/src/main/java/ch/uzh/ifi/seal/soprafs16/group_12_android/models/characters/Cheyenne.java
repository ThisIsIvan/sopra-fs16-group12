package ch.uzh.ifi.seal.soprafs16.group_12_android.models.characters;


import android.graphics.Color;
import ch.uzh.ifi.seal.soprafs16.group_12_android.*;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;

@JsonTypeName("cheyenne")
public final class Cheyenne extends Character implements Serializable {

    private static final BulletCard[] bulletCards = BulletCard.CHEYENNE;
    private static final CharacterCard characterCard =  CharacterCard.CHEYENNE;

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public int figurineSprite() {
        return R.drawable.figurine_cheyenne;
    }

    @Override
    public int figurineCrosshairSprite() {
        return R.drawable.figurine_cheyenne_crosshair;
    }

    @Override
    public int figurinePunchSprite() {
        return R.drawable.figurine_cheyenne_punch;
    }

    @Override
    public int circleSprite() {
        return R.drawable.circle_cheyenne;
    }

    @Override
    public int color() {
        return Color.rgb(0x3e,0x9b,0x3e);
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
        return CardFactory.generateCheyenneCollect();
    }

    @Override
    public ActionCard generateMarshalCard() {
        return CardFactory.generateCheyenneMarshal();
    }

    @Override
    public ActionCard generateMoveCard() {
        return CardFactory.generateCheyenneMove();
    }

    @Override
    public ActionCard generateChangeLevelCard() {
        return CardFactory.generateCheyenneChangeLevel();
    }

    @Override
    public ActionCard generateShootCard() {
        return CardFactory.generateCheyenneShoot();
    }

    @Override
    public ActionCard generatePunchCard() {
        return CardFactory.generateCheyennePunch();
    }

    @Override
    public String name() {
        return "Cheyenne";
    }
}
