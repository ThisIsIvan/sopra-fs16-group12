package ch.uzh.ifi.seal.soprafs16.group_12_android;


/**
 * Created by Nico on 01.04.2016.
 */
public class RoundCard extends Card {

    public static final RoundCard BACK          = new RoundCard(null,null,R.drawable.rcard_back);
    public static final RoundCard rc_nnnn_B     = new RoundCard("BrakingCard", "NNNN", R.drawable.rcard_nnnn_b);
    public static final RoundCard rc_nntnn_PR   = new RoundCard("PassenderRebellionCard", "NNTNN", R.drawable.rcard_nntnn_pr);
    public static final RoundCard rc_nntr_AM    = new RoundCard("AngryMarshalCard", "NNTR", R.drawable.rcard_nntr_am);
    public static final RoundCard rc_nsn        = new RoundCard("BlankBridgeCard", "NSN", R.drawable.rcard_nsn);
    public static final RoundCard rc_ntnn_PP    = new RoundCard("PivotablePoleCard", "NTNN", R.drawable.rcard_ntnn_p);
    public static final RoundCard rc_ntntn      = new RoundCard("BlankTunnelCard", "NTNTN", R.drawable.rcard_ntntn);
    public static final RoundCard rc_ntsn_GIA   = new RoundCard("GetItAllCard", "NTSN", R.drawable.rcard_ntsn_gia);

    private String stringPattern;

    protected RoundCard(String name, String stringPattern, int frontSprite) {
        super(name, frontSprite, R.drawable.rcard_back);
        this.stringPattern = stringPattern;
    }

    public String getStringPattern(){
        return this.stringPattern;
    }

    /**
     * Returns a specific RoundCard
     * @param type the RoundCard type
     * @return A RoundCard from Type type or BACK if type == null
     */
    @Deprecated
    public static RoundCard getRoundCard(RoundCard.Type type){
        if(type != null) {
            switch (type) {
                case NNNN_B:
                    return rc_nnnn_B;
                case NNTNN_PR:
                    return rc_nntnn_PR;
                case NNTR_AM:
                    return rc_nntr_AM;
                case NSN:
                    return rc_nsn;
                case NTNN_PP:
                    return rc_ntnn_PP;
                case NTNTN:
                    return rc_ntntn;
                case NTSN_GIA:
                    return rc_ntsn_GIA;
            }
        }
        return null;
    }

    /**
     * This enum indicates the RoundCard type. The denouncers are encoded with following abbreviatons:
     * The pattern is ABC[D][E][_X] where A-E indicate TurnTypes and X the RoundAction
     *
     * TurnTypes:
     * * N: normal round
     * * T: tunnel round
     * * R: reverse round
     * * S: speed-up round
     * RoundAction Types
     * * B: Braking
     * * PR: Passengers' Rebellion
     * * AM: Angry Marshal
     * * PP: Pivotable Pole
     * * GIA: Get It All!
     *
     */
    @Deprecated
    public enum Type{
        NNNN_B,
        NNTNN_PR,
        NNTR_AM,
        NSN,
        NTNN_PP,
        NTNTN,
        NTSN_GIA
    }
}