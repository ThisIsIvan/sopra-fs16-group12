package ch.uzh.ifi.seal.soprafs16.group_12_android;

/**
 * Created by Nico on 03.04.2016.
 */
public class StationCard extends RoundCard{

    public static final StationCard sc_h = new StationCard("HostageCard","NNTN",R.drawable.scard_h);
    public static final StationCard sc_mr = new StationCard("MarshalsRevengeCard","NNTN",R.drawable.scard_mr);
    public static final StationCard sc_pp = new StationCard("PickPocketingCard","NNTN",R.drawable.scard_pp);

    public StationCard(String name, String stringPattern, int frontSprite) {
        super(name, stringPattern, frontSprite);
    }
}
