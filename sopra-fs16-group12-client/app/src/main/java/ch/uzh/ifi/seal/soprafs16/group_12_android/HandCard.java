package ch.uzh.ifi.seal.soprafs16.group_12_android;

/**
 * Created by Nico on 29.03.2016.
 */
public abstract class HandCard extends Card {

    public HandCard(String name, int frontSprite) {
        super(name, frontSprite, R.drawable.card_back);
    }
}
