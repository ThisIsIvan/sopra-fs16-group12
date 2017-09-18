package ch.uzh.ifi.seal.soprafs16.group_12_android.models.characters;

import android.graphics.Color;
import ch.uzh.ifi.seal.soprafs16.group_12_android.*;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;

@JsonTypeName("belle")
public final class Belle extends Character implements Serializable {

    private static final BulletCard[] bulletCards = BulletCard.BELLE;
    private static final CharacterCard characterCard =  CharacterCard.BELLE;

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public int figurineSprite() {
        return R.drawable.figurine_belle;
    }

    @Override
    public int figurineCrosshairSprite() {
        return R.drawable.figurine_belle_crosshair;
    }

    @Override
    public int figurinePunchSprite() {
        return R.drawable.figurine_belle_punch;
    }

    @Override
    public int circleSprite() {
        return R.drawable.circle_belle;
    }

    @Override
    public int color() {
        return Color.rgb(0x7f,0x00,0x7f);
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
        return CardFactory.generateBelleCollect();
    }

    @Override
    public ActionCard generateMarshalCard() {
        return CardFactory.generateBelleMarshal();
    }

    @Override
    public ActionCard generateMoveCard() {
        return CardFactory.generateBelleMove();
    }

    @Override
    public ActionCard generateChangeLevelCard() {
        return CardFactory.generateBelleChangeLevel();
    }

    @Override
    public ActionCard generateShootCard() {
        return CardFactory.generateBelleShoot();
    }

    @Override
    public ActionCard generatePunchCard() {
        return CardFactory.generateBellePunch();
    }

    @Override
    public String name() {
        return "Belle";
    }
}
