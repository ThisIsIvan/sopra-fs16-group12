package ch.uzh.ifi.seal.soprafs16.group_12_android.models.characters;


import android.graphics.Color;
import ch.uzh.ifi.seal.soprafs16.group_12_android.*;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;

@JsonTypeName("ghost")
public final class Ghost extends Character implements Serializable {

    private static final BulletCard[] bulletCards = BulletCard.GHOST;
    private static final CharacterCard characterCard =  CharacterCard.GHOST;

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public int figurineSprite() {
        return R.drawable.figurine_ghost;
    }

    @Override
    public int figurineCrosshairSprite() {
        return R.drawable.figurine_ghost_crosshair;
    }

    @Override
    public int figurinePunchSprite() {
        return R.drawable.figurine_ghost_punch;
    }

    @Override
    public int circleSprite() {
        return R.drawable.circle_ghost;
    }

    @Override
    public int color() {
        return Color.WHITE;
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
        return CardFactory.generateGhostCollect();
    }

    @Override
    public ActionCard generateMarshalCard() {
        return CardFactory.generateGhostMarshal();
    }

    @Override
    public ActionCard generateMoveCard() {
        return CardFactory.generateGhostMove();
    }

    @Override
    public ActionCard generateChangeLevelCard() {
        return CardFactory.generateGhostChangeLevel();
    }

    @Override
    public ActionCard generateShootCard() {
        return CardFactory.generateGhostShoot();
    }

    @Override
    public ActionCard generatePunchCard() {
        return CardFactory.generateGhostPunch();
    }

    @Override
    public String name() {
        return "Ghost";
    }
}
