package ch.uzh.ifi.seal.soprafs16.group_12_android;

import java.io.Serializable;

/**
 * Created by Nico on 29.03.2016.
 */
public abstract class Card implements Serializable{

    private Long id = null;
    private int frontSprite;
    private int backSprite;
    private Boolean playedHidden = null;



    private Long playedByUserId = null;
    private String name;

    public final static Card BACK = new Card("back",R.drawable.card_back, R.drawable.card_back){};

    public Card(String name, int frontSprite, int backSprite) {
        this.name = name;
        this.frontSprite = frontSprite;
        this.backSprite = backSprite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFrontSprite() {
        return frontSprite;
    }

    public int getBackSprite() {
        return backSprite;
    }

    public boolean isPlayedHidden() {
        return playedHidden;
    }

    public Card setPlayedHidden(boolean playedHidden) {
        this.playedHidden = playedHidden;
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVisibleSprite(){
        return playedHidden==null ? frontSprite : playedHidden ? backSprite : frontSprite;
    }

    public Long getPlayedByUserId() {
        return playedByUserId;
    }

    public void setPlayedByUserId(Long playedByUserId) {
        this.playedByUserId = playedByUserId;
    }
}
