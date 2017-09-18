package ch.uzh.ifi.seal.soprafs16.group_12_android.models.characters;

import android.graphics.Color;
import ch.uzh.ifi.seal.soprafs16.group_12_android.*;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;

@JsonTypeName("django")
public final class Django extends Character implements Serializable {

    private static final BulletCard[] bulletCards = BulletCard.DJANGO;
    private static final CharacterCard characterCard =  CharacterCard.DJANGO;

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public int figurineSprite() {
        return R.drawable.figurine_django;
    }

    @Override
    public int figurineCrosshairSprite() {
        return R.drawable.figurine_django_crosshair;
    }

    @Override
    public int figurinePunchSprite() {
        return R.drawable.figurine_django_punch;
    }

    @Override
    public int circleSprite() {
        return R.drawable.circle_django;
    }

    @Override
    public int color() {
        return Color.GRAY;
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
        return CardFactory.generateDjangoCollect();
    }

    @Override
    public ActionCard generateMarshalCard() {
        return CardFactory.generateDjangoMarshal();
    }

    @Override
    public ActionCard generateMoveCard() {
        return CardFactory.generateDjangoMove();
    }

    @Override
    public ActionCard generateChangeLevelCard() {
        return CardFactory.generateDjangoChangeLevel();
    }

    @Override
    public ActionCard generateShootCard() {
        return CardFactory.generateDjangoShoot();
    }

    @Override
    public ActionCard generatePunchCard() {
        return CardFactory.generateDjangoPunch();
    }

    @Override
    public String name() {
        return "Django";
    }
}
