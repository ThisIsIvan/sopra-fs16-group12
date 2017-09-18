package ch.uzh.ifi.seal.soprafs16.group_12_android.models.characters;

import android.graphics.Color;
import ch.uzh.ifi.seal.soprafs16.group_12_android.*;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;

@JsonTypeName("tuco")
public final class Tuco extends Character implements Serializable {

    private static final BulletCard[] bulletCards = BulletCard.TUCO;
    private static final CharacterCard characterCard =  CharacterCard.TUCO;

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public int figurineSprite() {
        return R.drawable.figurine_tuco;
    }

    @Override
    public int figurineCrosshairSprite() {
        return R.drawable.figurine_tuco_crosshair;
    }

    @Override
    public int figurinePunchSprite() {
        return R.drawable.figurine_tuco_punch;
    }

    @Override
    public int circleSprite() {
        return R.drawable.circle_tuco;
    }

    @Override
    public int color() {
        return Color.rgb(0xfe,0x5a,0x5a);
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
        return CardFactory.generateTucoCollect();
    }

    @Override
    public ActionCard generateMarshalCard() {
        return CardFactory.generateTucoMarshal();
    }

    @Override
    public ActionCard generateMoveCard() {
        return CardFactory.generateTucoMove();
    }

    @Override
    public ActionCard generateChangeLevelCard() {
        return CardFactory.generateTucoChangeLevel();
    }

    @Override
    public ActionCard generateShootCard() {
        return CardFactory.generateTucoShoot();
    }

    @Override
    public ActionCard generatePunchCard() {
        return CardFactory.generateTucoPunch();
    }

    @Override
    public String name() {
        return "Tuco";
    }
}
