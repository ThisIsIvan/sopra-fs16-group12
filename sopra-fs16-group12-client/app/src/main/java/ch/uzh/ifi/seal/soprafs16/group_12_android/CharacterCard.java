package ch.uzh.ifi.seal.soprafs16.group_12_android;

/**
 * Created by rafael on 31/03/16.
 */
public class CharacterCard extends Card {

    public static final CharacterCard GHOST     = new CharacterCard("Ghost",R.drawable.ccard_ghost);
    public static final CharacterCard CHEYENNE  = new CharacterCard("Cheyenne",R.drawable.ccard_cheyenne);
    public static final CharacterCard DJANGO    = new CharacterCard("Django",R.drawable.ccard_django);
    public static final CharacterCard BELLE     = new CharacterCard("Belle",R.drawable.ccard_belle);
    public static final CharacterCard TUCO      = new CharacterCard("Tuco",R.drawable.ccard_tuco);
    public static final CharacterCard DOC       = new CharacterCard("Tuco",R.drawable.ccard_doc);

    private CharacterCard(String characterName,int frontRecource){
        super(characterName,frontRecource,R.drawable.card_back);
    }
}
