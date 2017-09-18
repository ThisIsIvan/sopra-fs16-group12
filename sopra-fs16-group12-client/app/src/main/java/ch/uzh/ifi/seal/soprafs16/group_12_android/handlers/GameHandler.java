package ch.uzh.ifi.seal.soprafs16.group_12_android.handlers;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import ch.uzh.ifi.seal.soprafs16.group_12_android.Globals;
import ch.uzh.ifi.seal.soprafs16.group_12_android.dialogs.ChooseItemDialogFragment;
import ch.uzh.ifi.seal.soprafs16.group_12_android.dialogs.MessageDialogFragment;
import ch.uzh.ifi.seal.soprafs16.group_12_android.fragments.GameFragment;

import java.util.ArrayList;

/**
 * Created by rafael on 15/05/16.
 */
public class GameHandler extends Handler{

    public static final int UPDATE_GAME = 0;
    public static final int CHOOSE_ITEM_MESSAGE = 1;
    public static final int DRAW_OR_PLAY_MESSAGE = 2;
    public static final int MOVE_MARSHAL_MESSAGE = 3;
    public static final int MOVE_MESSAGE = 4;
    public static final int PUNCH_SELECT_PLAYER_MESSAGE = 5;
    public static final int PUNCH_SELECT_ITEM_MESSAGE = 6;
    public static final int PUNCH_SELECT_WAGON_MESSAGE = 7;
    public static final int SHOOT_SELECT_PLAYER_MESSAGE = 8;
    public static final int START_HIGHLIGHT_ITEMS = 9;
    public static final int STOP_HIGHLIGHT_ITEMS = 10;
    public static final int START_HIGHLIGHT_WAGON_LEVEL = 11;
    public static final int STOP_HIGHLIGHT_WAGON_LEVEL = 12;
    public static final int START_SHOOT_HIGHTLIGHT_FIGURINE = 13;
    public static final int START_PUNCH_HIGHLIGHT_FIGURINE = 14;
    public static final int STOP_HIGHTLIGHT_FIGURINE = 15;
    public static final int PUNCH_CHOOSE_ITEM_DIALOG = 16;
    public static final int RANDOM_MESSAGE = 17;
    public static final int SHOOT_SOUND = 18;
    public static final int PUNCH_SOUND = 19;
    public static final int STEP_SOUND = 20;
    public static final int TRAIN_HONK_SOUND = 21;
    public static final int YAHOO_SOUND = 22;

    public static final String ARG_N_BAGS = "N_BAGS";
    public static final String ARG_N_GEMS = "N_GEMS";
    public static final String ARG_N_CASES = "N_CASES";
    public static final String ARG_NETWORTH = "NETWORTH";
    public static final String ARG_CURRENT_USER_NAME = "CURRENT_USER";
    public static final String ARG_CURRENT_TURN = "CURRENT_TURN";
    public static final String ARG_CURRENT_ROUND = "CURRENT_ROUND";
    public static final String ARG_CURRENT_PHASE = "CURRENT_PHASE";
    public static final String ARG_USER_CIRCLE_SPRITES = "USER_CIRCLE_SPRITES";
    public static final String ARG_HAS_HIDDEN_CARDS = "HAS_HIDDEN_CARDS";

    private GameFragment gameFragment;

    public static GameHandler newInstance(GameFragment gameFragment){
        GameHandler gameHandler = new GameHandler();
        gameHandler.gameFragment = gameFragment;
        return gameHandler;
    }

    private GameHandler(){}

    @Override
    public void handleMessage(Message msg) {

        int color = Globals.getInstance().characterMap.get(Globals.getInstance().userId).color();
        switch (msg.what) {
            case CHOOSE_ITEM_MESSAGE:
                MessageDialogFragment.newInstance("Choose Item", color).show(GameHandler.this.gameFragment.getFragmentManager(), "choose item message");
                break;
            case DRAW_OR_PLAY_MESSAGE:
                MessageDialogFragment.newInstance("Draw or Play", color).show(GameHandler.this.gameFragment.getFragmentManager(), "draw or play message");
                break;
            case MOVE_MARSHAL_MESSAGE:
                MessageDialogFragment.newInstance("Move the Marshall", color).show(GameHandler.this.gameFragment.getFragmentManager(), "move marshal message");
                break;
            case MOVE_MESSAGE:
                MessageDialogFragment.newInstance("Move", color).show(GameHandler.this.gameFragment.getFragmentManager(), "move message");
                break;
            case PUNCH_SELECT_PLAYER_MESSAGE:
                MessageDialogFragment.newInstance("Punch", color).show(GameHandler.this.gameFragment.getFragmentManager(), "punch message");
                break;
            case PUNCH_SELECT_ITEM_MESSAGE:
                MessageDialogFragment.newInstance("Select Item to toss", color).show(GameHandler.this.gameFragment.getFragmentManager(), "select item to toss message");
                break;
            case PUNCH_SELECT_WAGON_MESSAGE:
                MessageDialogFragment.newInstance("Select Wagon to punch into", color).show(GameHandler.this.gameFragment.getFragmentManager(), "select wagon to punch");
                break;
            case SHOOT_SELECT_PLAYER_MESSAGE:
                MessageDialogFragment.newInstance("Select Player to Shoot", color).show(GameHandler.this.gameFragment.getFragmentManager(), "select player to shoot");
                break;
            case RANDOM_MESSAGE:
                MessageDialogFragment.newInstance(color).show(GameHandler.this.gameFragment.getFragmentManager(), "random message");
                break;
            case START_HIGHLIGHT_ITEMS:
                GameHandler.this.gameFragment.getTrainView().startHighlightItems((Long) msg.obj);
                break;
            case STOP_HIGHLIGHT_ITEMS:
                GameHandler.this.gameFragment.getTrainView().stopHighlightItems((Long) msg.obj);
                break;
            case START_HIGHLIGHT_WAGON_LEVEL:
                GameHandler.this.gameFragment.getTrainView().startHighlightWagonLevels((Long[]) msg.obj);
                break;
            case STOP_HIGHLIGHT_WAGON_LEVEL:
                GameHandler.this.gameFragment.getTrainView().stopHighlightWagonLevels((Long[]) msg.obj);
                break;
            case START_PUNCH_HIGHLIGHT_FIGURINE:
                GameHandler.this.gameFragment.getTrainView().startPunchHighlightFigurines((Long[]) msg.obj);
                break;
            case START_SHOOT_HIGHTLIGHT_FIGURINE:
                GameHandler.this.gameFragment.getTrainView().startShootHighlightFigurines((Long[]) msg.obj);
                break;
            case STOP_HIGHTLIGHT_FIGURINE:
                GameHandler.this.gameFragment.getTrainView().stopHighlightFigurines((Long[]) msg.obj);
                break;
            case SHOOT_SOUND:
                GameHandler.this.gameFragment.getShootSound().start();
                break;
            case PUNCH_SOUND:
                GameHandler.this.gameFragment.getPunchSound().start();
                break;
            case STEP_SOUND:
                GameHandler.this.gameFragment.getStepSound().start();
                break;
            case TRAIN_HONK_SOUND:
                GameHandler.this.gameFragment.getTrainHonkSound().start();
                break;
            case YAHOO_SOUND:
                GameHandler.this.gameFragment.getYahooSound().start();
                break;
            case PUNCH_CHOOSE_ITEM_DIALOG: {
                Bundle args = msg.getData();
                ChooseItemDialogFragment d = ChooseItemDialogFragment.newInstance(
                        args.getBoolean(ChooseItemDialogFragment.ARG_HAS_BAG),
                        args.getBoolean(ChooseItemDialogFragment.ARG_HAS_GEM),
                        args.getBoolean(ChooseItemDialogFragment.ARG_HAS_CASE));
                d.show(GameHandler.this.gameFragment.getFragmentManager(), "choose item to toss");
            }
            break;
            case UPDATE_GAME: {
                Bundle args = msg.getData();
                Integer nBags = args.getInt(GameHandler.ARG_N_BAGS);
                Integer nGems = args.getInt(GameHandler.ARG_N_GEMS);
                Integer nCases = args.getInt(GameHandler.ARG_N_CASES);
                Integer networth = args.getInt(GameHandler.ARG_NETWORTH);
                Boolean hasHiddenCards = args.getBoolean(GameHandler.ARG_HAS_HIDDEN_CARDS);
                String currentUserName = args.getString(GameHandler.ARG_CURRENT_USER_NAME);
                Integer currentTurn = args.getInt(GameHandler.ARG_CURRENT_TURN);
                Integer currentRound = args.getInt(GameHandler.ARG_CURRENT_ROUND);
                String currentPhase = args.getString(GameHandler.ARG_CURRENT_PHASE);
                ArrayList<Integer> userQueueSprites = (ArrayList<Integer>) args.getSerializable(GameHandler.ARG_USER_CIRCLE_SPRITES);

                GameHandler.this.gameFragment.getCurrentPlayerTextView().setText(currentUserName);
                GameHandler.this.gameFragment.getUserQueue().setQueue(userQueueSprites);
                GameHandler.this.gameFragment.getUserQueue().refresh();
                GameHandler.this.gameFragment.getCurrentRoundView().setText("Round " + (currentRound + 1));
                GameHandler.this.gameFragment.getCurrentTurnView().setText("Turn " + (currentTurn + 1));
                GameHandler.this.gameFragment.getCurrentPhaseView().setText(currentPhase + " Phase");
                GameHandler.this.gameFragment.getRoundCardView().refresh();
                GameHandler.this.gameFragment.getNetworthTextView().setText("$ " + networth.toString());
                GameHandler.this.gameFragment.getnBagsTextView().setText(nBags.toString());
                GameHandler.this.gameFragment.getnGemsTextView().setText(nGems.toString());
                GameHandler.this.gameFragment.getnCasesTextView().setText(nCases.toString());
                GameHandler.this.gameFragment.getDrawCardView().setVisibility(hasHiddenCards ? View.VISIBLE : View.INVISIBLE);
                GameHandler.this.gameFragment.getTrainView().refresh();
                GameHandler.this.gameFragment.getCommonDeckView().refresh();
                GameHandler.this.gameFragment.getHandDeckView().refresh();
                GameHandler.this.gameFragment.getBulletCardView().refresh();
            }
            break;
        }
    }
}
