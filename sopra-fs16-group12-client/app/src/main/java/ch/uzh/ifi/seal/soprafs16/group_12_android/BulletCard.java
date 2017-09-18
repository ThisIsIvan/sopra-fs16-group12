package ch.uzh.ifi.seal.soprafs16.group_12_android;

/**
 * Created by Nico on 01.04.2016.
 */
public class BulletCard extends HandCard {

    public static final BulletCard[]    BELLE      = generateBelleBCards();
    public static final BulletCard[]    CHEYENNE   = generateCheyenneBCards();
    public static final BulletCard[]    DJANGO     = generateDjangoBCards();
    public static final BulletCard[]    DOC        = generateDocBCards();
    public static final BulletCard[]    GHOST      = generateGhostBCards();
    public static final BulletCard[]    TUCO       = generateTucoBCards();
    public static final BulletCard      NEUTRAL    = generateNeutralBulletCard();

    private BulletCard(String name, int frontSprite) {
        super(name, frontSprite);
    }

    /**
     * Returns the deck of bullet cards for Belle.
     *
     * @return An array containing all BulletCards for Belle
     */
    private static BulletCard[] generateBelleBCards(){
        BulletCard belle_bullet_1 = new BulletCard("bullet_1", R.drawable.bcard_belle_1);
        BulletCard belle_bullet_2 = new BulletCard("bullet_2", R.drawable.bcard_belle_2);
        BulletCard belle_bullet_3 = new BulletCard("bullet_3", R.drawable.bcard_belle_3);
        BulletCard belle_bullet_4 = new BulletCard("bullet_4", R.drawable.bcard_belle_4);
        BulletCard belle_bullet_5 = new BulletCard("bullet_5", R.drawable.bcard_belle_5);
        BulletCard belle_bullet_6 = new BulletCard("bullet_6", R.drawable.bcard_belle_6);

        return new BulletCard[] {belle_bullet_1, belle_bullet_2, belle_bullet_3, belle_bullet_4, belle_bullet_5, belle_bullet_6};
    }

    /**
     * Returns the deck of bullet cards for Cheyenne.
     *
     * @return An array containing all BulletCards for Cheyenne
     */
    private static BulletCard[] generateCheyenneBCards(){
        BulletCard cheyenne_bullet_1 = new BulletCard("bullet_1", R.drawable.bcard_cheyenne_1);
        BulletCard cheyenne_bullet_2 = new BulletCard("bullet_2", R.drawable.bcard_cheyenne_2);
        BulletCard cheyenne_bullet_3 = new BulletCard("bullet_3", R.drawable.bcard_cheyenne_3);
        BulletCard cheyenne_bullet_4 = new BulletCard("bullet_4", R.drawable.bcard_cheyenne_4);
        BulletCard cheyenne_bullet_5 = new BulletCard("bullet_5", R.drawable.bcard_cheyenne_5);
        BulletCard cheyenne_bullet_6 = new BulletCard("bullet_6", R.drawable.bcard_cheyenne_6);

        return new BulletCard[] {cheyenne_bullet_1, cheyenne_bullet_2, cheyenne_bullet_3, cheyenne_bullet_4, cheyenne_bullet_5, cheyenne_bullet_6};
    }
    /**
     * Returns the deck of bullet cards for Django.
     *
     * @return An array containing all BulletCards for Django
     */

    private static BulletCard[] generateDjangoBCards(){
        BulletCard django_bullet_1 = new BulletCard("bullet_1", R.drawable.bcard_django_1);
        BulletCard django_bullet_2 = new BulletCard("bullet_2", R.drawable.bcard_django_2);
        BulletCard django_bullet_3 = new BulletCard("bullet_3", R.drawable.bcard_django_3);
        BulletCard django_bullet_4 = new BulletCard("bullet_4", R.drawable.bcard_django_4);
        BulletCard django_bullet_5 = new BulletCard("bullet_5", R.drawable.bcard_django_5);
        BulletCard django_bullet_6 = new BulletCard("bullet_6", R.drawable.bcard_django_6);

        return new BulletCard[] {django_bullet_1, django_bullet_2, django_bullet_3, django_bullet_4, django_bullet_5, django_bullet_6};
    }

    /**
     *
     *
     * @return
     */
    private static BulletCard[] generateDocBCards(){
        BulletCard doc_bullet_1 = new BulletCard("bullet_1", R.drawable.bcard_doc_1);
        BulletCard doc_bullet_2 = new BulletCard("bullet_2", R.drawable.bcard_doc_2);
        BulletCard doc_bullet_3 = new BulletCard("bullet_3", R.drawable.bcard_doc_3);
        BulletCard doc_bullet_4 = new BulletCard("bullet_4", R.drawable.bcard_doc_4);
        BulletCard doc_bullet_5 = new BulletCard("bullet_5", R.drawable.bcard_doc_5);
        BulletCard doc_bullet_6 = new BulletCard("bullet_6", R.drawable.bcard_doc_6);

        return new BulletCard[] {doc_bullet_1, doc_bullet_2, doc_bullet_3, doc_bullet_4, doc_bullet_5, doc_bullet_6};
    }

    /**
     * Returns the deck of bullet cards for Ghost.
     *
     * @return An array containing all BulletCards for Ghost
     */
    private static BulletCard[] generateGhostBCards() {
        BulletCard ghost_bullet_1 = new BulletCard("bullet_1", R.drawable.bcard_ghost_1);
        BulletCard ghost_bullet_2 = new BulletCard("bullet_2", R.drawable.bcard_ghost_2);
        BulletCard ghost_bullet_3 = new BulletCard("bullet_3", R.drawable.bcard_ghost_3);
        BulletCard ghost_bullet_4 = new BulletCard("bullet_4", R.drawable.bcard_ghost_4);
        BulletCard ghost_bullet_5 = new BulletCard("bullet_5", R.drawable.bcard_ghost_5);
        BulletCard ghost_bullet_6 = new BulletCard("bullet_6", R.drawable.bcard_ghost_6);

        return new BulletCard[] {ghost_bullet_1, ghost_bullet_2, ghost_bullet_3, ghost_bullet_4, ghost_bullet_5, ghost_bullet_6};
    }

    /**
     * Returns the deck of bullet cards for Tuco.
     *
     * @return An array containing all BulletCards for Tuco
     */
    private static BulletCard[] generateTucoBCards(){
        BulletCard tuco_bullet_1 = new BulletCard("bullet_1", R.drawable.bcard_tuco_1);
        BulletCard tuco_bullet_2 = new BulletCard("bullet_2", R.drawable.bcard_tuco_2);
        BulletCard tuco_bullet_3 = new BulletCard("bullet_3", R.drawable.bcard_tuco_3);
        BulletCard tuco_bullet_4 = new BulletCard("bullet_4", R.drawable.bcard_tuco_4);
        BulletCard tuco_bullet_5 = new BulletCard("bullet_5", R.drawable.bcard_tuco_5);
        BulletCard tuco_bullet_6 = new BulletCard("bullet_6", R.drawable.bcard_tuco_6);

        return new BulletCard[] {tuco_bullet_1, tuco_bullet_2, tuco_bullet_3, tuco_bullet_4, tuco_bullet_5, tuco_bullet_6};
    }

    /**
     * Generates a neutral bullet fetchCard
     * @return a neutral BulletCardDTO
     */
    private static BulletCard generateNeutralBulletCard(){
        return new BulletCard("neutral_bullet", R.drawable.bcard_neutral);
    }
}
