package ch.uzh.ifi.seal.soprafs16.group_12_android;

import ch.uzh.ifi.seal.soprafs16.group_12_android.models.characters.*;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.characters.Character;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by rafael on 14/04/16.
 */
public final class Globals {

    private static Globals instance;

    private Globals(){}

    public static Globals getInstance(){
        if(instance == null){
            instance = new Globals();
        }
        return instance;
    }

    public final String ARG_GAME_ID = "Globals.ARG_GAME_ID";

    public Boolean isLoggedIn    = false;
    public Long userId           = null;
    public String userToken      = null;
    public String userUsername   = null;

    public final Random rng = new Random();
    public final ExecutorService executor = Executors.newFixedThreadPool(5);

    public final HashMap<Long, Character> characterMap = new HashMap<>();
    public final TransferableObject<Long> chosenCardId = new TransferableObject<>();
    public final TransferableObject<ItemType> chosenItemType = new TransferableObject<>();
    public final TransferableObject<Long> chosenWagonLevelId = new TransferableObject<>();
    public final TransferableObject<Long> chosenUserId = new TransferableObject<>();

    public final Character belle = new Belle();
    public final Character cheyenne = new Cheyenne();
    public final Character django = new Django();
    public final Character doc = new Doc();
    public final Character ghost = new Ghost();
    public final Character tuco = new Tuco();

    public enum SourceType {
        BELLE, CHEYENNE, DJANGO, DOC, GHOST, TUCO, MARSHAL
    }

    public enum ItemType{
        CASE,
        GEM,
        BAG;

        public int getSprite(){
            switch (this){
                case BAG:
                    return R.drawable.item_bag;
                case GEM:
                    return R.drawable.item_gem;
                case CASE:
                    return R.drawable.item_case;
                default:
                    return 0;
            }
        }
    }

    public enum GamePhase {
        PLANNING,
        EXECUTION;

        @Override
        public String toString(){
            switch (this){
                case PLANNING: return "Planning";
                case EXECUTION: return "Executing";
                default: return "";
            }
        }
    }
}
