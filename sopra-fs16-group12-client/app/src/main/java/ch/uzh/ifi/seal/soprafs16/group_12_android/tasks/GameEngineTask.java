package ch.uzh.ifi.seal.soprafs16.group_12_android.tasks;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import ch.uzh.ifi.seal.soprafs16.group_12_android.*;
import ch.uzh.ifi.seal.soprafs16.group_12_android.dialogs.ChooseItemDialogFragment;
import ch.uzh.ifi.seal.soprafs16.group_12_android.fragments.GameFragment;
import ch.uzh.ifi.seal.soprafs16.group_12_android.handlers.GameHandler;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.GameDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.ItemDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.UserDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.WagonDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.action.ActionRequestDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.action.ActionResponseDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.action.request.*;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.action.response.*;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.cards.handCards.ActionCardDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.cards.handCards.BulletCardDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.cards.handCards.HandCardDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.cards.roundCards.RoundCardDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.service.RestService;
import ch.uzh.ifi.seal.soprafs16.group_12_android.views.DeckView;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by rafael on 18/05/16.
 */
public abstract class GameEngineTask implements EngineTask {

    private static final String TAG = "GameET";

    private GameFragment gameFragment;
    private GameHandler gameHandler;
    private Long gameId;
    private Integer initialnUsers;
    private CountDownLatch tutorialCompleteLatch;


    public GameEngineTask(GameFragment gameFragment, GameHandler gameHandler, Long gameId, Integer nUsers) {
        this.gameFragment = gameFragment;
        this.gameHandler = gameHandler;
        this.gameId = gameId;
        this.initialnUsers = nUsers;
        this.tutorialCompleteLatch = new CountDownLatch(1);

    }

    @Override
    public void execute() throws ExecutionException, InterruptedException {
        try {
            Log.d(TAG, "polling");
            Response<GameDTO> response = RestService.getInstance(gameFragment.getContext()).getGame(gameId).execute();
            Log.d(TAG, "polled");
            GameDTO game = response.body();
            if (BuildConfig.DEBUG) assert game != null;
            Log.d(TAG, "handling received data");
            if (game.getStatus().equals("FINISHED")) {
                onGameFinish(game, gunslingerIds(game.getUsers()), winnerId(game.getUsers()));
                return;
            }
            //Check if any user left
            if (game.getUsers().size() != initialnUsers) {
                onGameFinish(game, new ArrayList<Long>(), winnerId(game.getUsers()));
                return;
            }
            Integer networth = 0;
            Integer nBags = 0;
            Integer nGems = 0;
            Integer nCases = 0;
            Integer nCardsHiddenDeck = 0;
            BulletCardDTO bulletCardDTO = null;
            Deck<HandCard> handDeck = new Deck<>();
            Deck<ActionCard> commonDeck = new Deck<>();
            RoundCardDTO currentRoundCard = game.getRoundCardDeck().getCards().get(game.getCurrentRound());
            Boolean isReverse = ((RoundCard) currentRoundCard.card()).getStringPattern().charAt(game.getCurrentTurn()) == 'R';
            ArrayList<Integer> userQueueSprites = new ArrayList<>(game.getUsers().size());
            Long currentWagonLevelId = null;

            List<UserDTO> users = game.getUsers();
            int userIndex = game.getRoundStarter();
            do {
                UserDTO user = users.get(userIndex);
                userQueueSprites.add(user.getCharacter().circleSprite());
                if (user.getId() == Globals.getInstance().userId) {
                    bulletCardDTO = user.getBulletsDeck().getCards().get(user.getBulletsDeck().getCards().size() - 1);
                    nCardsHiddenDeck = user.getHiddenDeck().getCards().size();
                    for (HandCardDTO cardDTO : user.getHandDeck().getCards()) {
                        handDeck.add((HandCard) cardDTO.card());
                    }
                    for (ItemDTO item : user.getItems()) {
                        switch (item.getItemType()) {
                            case BAG:
                                nBags++;
                                break;
                            case GEM:
                                nGems++;
                                break;
                            case CASE:
                                nCases++;
                                break;
                        }
                        networth += item.getValue();
                    }
                }
                userIndex = (isReverse ? userIndex + users.size() - 1 : userIndex + 1) % users.size();
            } while (userIndex != game.getRoundStarter());


            for (ActionCardDTO cardDTO : game.getCommonDeck().getCards()) {
                commonDeck.add((ActionCard) cardDTO.card());
            }

            for (int i = game.getWagons().size() - 1; i >= 0; i--) {
                WagonDTO wagon = game.getWagons().get(i);

                if (currentWagonLevelId == null) {
                    for (UserDTO user : wagon.getTopLevel().getUsers()) {
                        if (user.getId() == Globals.getInstance().userId) currentWagonLevelId = wagon.getTopLevel().getId();
                    }
                    for (UserDTO user : wagon.getBottomLevel().getUsers()) {
                        if (user.getId() == Globals.getInstance().userId)
                            currentWagonLevelId = wagon.getBottomLevel().getId();
                    }
                }

                gameFragment.prepareWagonLevelView(wagon.getTopLevel());
                gameFragment.prepareWagonLevelView(wagon.getBottomLevel());
            }
            gameFragment.prepareBulletCardView((BulletCard) bulletCardDTO.card());

            gameFragment.prepareRoundCardView((RoundCard) currentRoundCard.card());
            gameFragment.prepareHandDeckView(handDeck);
            gameFragment.prepareCommonDeckView(commonDeck, game.getCurrentPhase() == Globals.GamePhase.PLANNING ? DeckView.Type.COMMON_PLAN : DeckView.Type.COMMON_EXECUTE);

            {
                Message msg = gameHandler.obtainMessage(GameHandler.UPDATE_GAME);
                Bundle args = new Bundle();
                args.putInt(GameHandler.ARG_N_BAGS, nBags);
                args.putInt(GameHandler.ARG_N_GEMS, nGems);
                args.putInt(GameHandler.ARG_N_CASES, nCases);
                args.putInt(GameHandler.ARG_NETWORTH, networth);
                args.putBoolean(GameHandler.ARG_HAS_HIDDEN_CARDS, nCardsHiddenDeck > 0);
                args.putString(GameHandler.ARG_CURRENT_USER_NAME, game.getUsers().get(game.getCurrentPlayer()).getName());
                args.putInt(GameHandler.ARG_CURRENT_TURN, game.getCurrentTurn());
                args.putInt(GameHandler.ARG_CURRENT_ROUND, game.getCurrentRound());
                args.putString(GameHandler.ARG_CURRENT_PHASE, game.getCurrentPhase().toString());
                args.putSerializable(GameHandler.ARG_USER_CIRCLE_SPRITES, userQueueSprites);
                msg.setData(args);
                msg.sendToTarget();
            }
            GameEngineTask.this.tutorialCompleteLatch.await();

            ActionRequestDTO actionRequest = game.getActions().get(game.getActions().size() - 1);

            if (actionRequest.getUserId() == Globals.getInstance().userId) {
                ActionResponseDTO actionResponse = null;

                if (actionRequest instanceof CollectItemRequestDTO) {
                    actionResponse = handleCollectItemRequest((CollectItemRequestDTO) actionRequest, currentWagonLevelId);

                } else if (actionRequest instanceof DrawOrPlayCardRequestDTO) {
                    actionResponse = handleDrawOrPlayRequest((DrawOrPlayCardRequestDTO) actionRequest);

                } else if (actionRequest instanceof MoveMarshalRequestDTO) {
                    actionResponse = handleMoveMarshalRequest((MoveMarshalRequestDTO) actionRequest);

                } else if (actionRequest instanceof MoveRequestDTO) {
                    actionResponse = handleMoveRequest((MoveRequestDTO) actionRequest);

                } else if (actionRequest instanceof PunchRequestDTO) {
                    actionResponse = handlePunchRequest((PunchRequestDTO) actionRequest, game);

                } else if (actionRequest instanceof ShootRequestDTO) {
                    actionResponse = handleShootRequest((ShootRequestDTO) actionRequest);

                } else {
                    Log.e(TAG, actionRequest.getClass().getName() + " unknown, cannot handle");
                }

                actionResponse.setGameId(gameId);
                actionResponse.setUserId(Globals.getInstance().userId);
                Log.d(TAG, "sending action response");
                RestService.getInstance(gameFragment.getContext()).sendAction(gameId, actionResponse, Globals.getInstance().userToken).execute();
                Log.d(TAG, "action response send");
            }
        } catch (InterruptedException e) {
            throw e;
        } catch (Exception e) {
            throw new ExecutionException(e);
        }
    }

    public void onTutorialComplete() {
        this.tutorialCompleteLatch.countDown();
        gameHandler.obtainMessage(GameHandler.TRAIN_HONK_SOUND).sendToTarget();
    }

    /**
     * NON UI Thread
     *
     * @param actionRequest:       the action request
     * @param currentWagonLevelId: the id of the wagon level in which the current user is on
     * @return an action response
     * @throws InterruptedException
     */
    private ActionResponseDTO handleCollectItemRequest(CollectItemRequestDTO actionRequest, Long currentWagonLevelId) throws InterruptedException {
        Log.d(TAG, "collect item");

        gameHandler.obtainMessage(GameHandler.CHOOSE_ITEM_MESSAGE).sendToTarget();
        Globals.ItemType chosenItemType = null;
        do {
            if (chosenItemType != null)
                Log.w(TAG, "violation in collect item, user chose a non-selectable item");
            gameHandler.obtainMessage(GameHandler.START_HIGHLIGHT_ITEMS, currentWagonLevelId).sendToTarget();
            chosenItemType = Globals.getInstance().chosenItemType.request();
        }
        while (!((chosenItemType == Globals.ItemType.BAG && actionRequest.getHasBag())
                || chosenItemType == Globals.ItemType.GEM && actionRequest.getHasGem()
                || chosenItemType == Globals.ItemType.CASE && actionRequest.getHasCase()));
        gameHandler.obtainMessage(GameHandler.STOP_HIGHLIGHT_ITEMS, currentWagonLevelId).sendToTarget();

        Log.d(TAG, "collect item complete");
        return new CollectItemResponseDTO(chosenItemType);
    }

    /**
     * @param actionRequest: the action request
     * @return an action response
     * @throws InterruptedException
     */
    private ActionResponseDTO handleDrawOrPlayRequest(DrawOrPlayCardRequestDTO actionRequest) throws InterruptedException {
        Log.i(TAG, "draw or play");
        gameHandler.obtainMessage(GameHandler.DRAW_OR_PLAY_MESSAGE).sendToTarget();
        Long chosenCardId = null;
        do {
            if (chosenCardId != null)
                Log.w(TAG, "violation in draw or play, user chose a non-playable card");
            chosenCardId = Globals.getInstance().chosenCardId.request();
        }
        while (!actionRequest.getPlayableCardsId().contains(chosenCardId) && chosenCardId != DrawCardResponseDTO.DRAW_CARD);
        if (BuildConfig.DEBUG) assert (chosenCardId != null);
        Log.d(TAG, "draw or play complete");
        return chosenCardId == DrawCardResponseDTO.DRAW_CARD ? new DrawCardResponseDTO() : new PlayCardResponseDTO(chosenCardId);
    }

    /**
     * @param actionRequest: the action request
     * @return an action response
     * @throws InterruptedException
     */
    private ActionResponseDTO handleMoveMarshalRequest(MoveMarshalRequestDTO actionRequest) throws InterruptedException {
        Log.d(TAG, "move marshall");
        gameHandler.obtainMessage(GameHandler.MOVE_MARSHAL_MESSAGE).sendToTarget();
        List<Long> selectableWagonLevelIds = actionRequest.getMovableWagonsLvlIds();
        Long selectedWagonLevelId = null;
        do {
            if (selectedWagonLevelId != null)
                Log.w(TAG, "violation in move marshall, user chose a non-moveable wagonLevel");
            gameHandler.obtainMessage(GameHandler.START_HIGHLIGHT_WAGON_LEVEL, selectableWagonLevelIds.toArray(new Long[selectableWagonLevelIds.size()])).sendToTarget();
            selectedWagonLevelId = Globals.getInstance().chosenWagonLevelId.request();
        } while (!selectableWagonLevelIds.contains(selectedWagonLevelId));
        gameHandler.obtainMessage(GameHandler.STOP_HIGHLIGHT_WAGON_LEVEL, selectableWagonLevelIds.toArray(new Long[selectableWagonLevelIds.size()])).sendToTarget();
        Globals.getInstance().executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    gameHandler.obtainMessage(GameHandler.STEP_SOUND).sendToTarget();
                    Thread.sleep(1000);
                    gameHandler.obtainMessage(GameHandler.STEP_SOUND).sendToTarget();
                    Thread.sleep(1000);
                    gameHandler.obtainMessage(GameHandler.STEP_SOUND).sendToTarget();
                } catch (InterruptedException e) {

                }
            }
        });
        return new MoveMarshalResponseDTO(selectedWagonLevelId);
    }

    /**
     * @param actionRequest: the action request
     * @return an action response
     * @throws InterruptedException
     */
    private ActionResponseDTO handleMoveRequest(MoveRequestDTO actionRequest) throws InterruptedException {
        Log.d(TAG, "move");
        gameHandler.obtainMessage(GameHandler.MOVE_MESSAGE).sendToTarget();
        List<Long> selectableWagonLevelIds = actionRequest.getMovableWagonsLvlIds();
        Long selectedWagonLevelId = null;
        do {
            if (selectedWagonLevelId != null)
                Log.w(TAG, "violation in move, user chose a non-moveable wagonLevel");
            gameHandler.obtainMessage(GameHandler.START_HIGHLIGHT_WAGON_LEVEL, selectableWagonLevelIds.toArray(new Long[selectableWagonLevelIds.size()])).sendToTarget();
            selectedWagonLevelId = Globals.getInstance().chosenWagonLevelId.request();
        } while (!selectableWagonLevelIds.contains(selectedWagonLevelId));
        gameHandler.obtainMessage(GameHandler.STOP_HIGHLIGHT_WAGON_LEVEL, selectableWagonLevelIds.toArray(new Long[selectableWagonLevelIds.size()])).sendToTarget();
        Globals.getInstance().executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    gameHandler.obtainMessage(GameHandler.STEP_SOUND).sendToTarget();
                    Thread.sleep(1000);
                    gameHandler.obtainMessage(GameHandler.STEP_SOUND).sendToTarget();
                    Thread.sleep(1000);
                    gameHandler.obtainMessage(GameHandler.STEP_SOUND).sendToTarget();
                } catch (InterruptedException e) {

                }
            }
        });
        return new MoveResponseDTO(selectedWagonLevelId);
    }

    /**
     * @param actionRequest: the action request
     * @param game:          the gameDTO
     * @return an action response
     * @throws InterruptedException
     */
    private ActionResponseDTO handlePunchRequest(PunchRequestDTO actionRequest, GameDTO game) throws InterruptedException {
        Log.d(TAG, "punch");
        Log.d(TAG, "selecting other player");
        gameHandler.obtainMessage(GameHandler.PUNCH_SELECT_PLAYER_MESSAGE).sendToTarget();
        List<Long> selectableUserIds = actionRequest.getPunchableUserIds();
        gameHandler.obtainMessage(GameHandler.START_PUNCH_HIGHLIGHT_FIGURINE, selectableUserIds.toArray(new Long[selectableUserIds.size()])).sendToTarget();
        Long selectedUserId = Globals.getInstance().chosenUserId.request();
        gameHandler.obtainMessage(GameHandler.STOP_HIGHTLIGHT_FIGURINE, selectableUserIds.toArray(new Long[selectableUserIds.size()])).sendToTarget();
        int position = selectableUserIds.indexOf(selectedUserId);
        Globals.ItemType selectedItemType = null;
        int nSelectableItems = (actionRequest.getHasBag().get(position) ? 1 : 0) + (actionRequest.getHasGem().get(position) ? 1 : 0) + (actionRequest.getHasCase().get(position) ? 1 : 0);
        if (nSelectableItems > 1) {
            Log.d(TAG, "selecting item to toss");
            {
                Bundle args = new Bundle();
                args.putBoolean(ChooseItemDialogFragment.ARG_HAS_BAG, actionRequest.getHasBag().get(position));
                args.putBoolean(ChooseItemDialogFragment.ARG_HAS_GEM, actionRequest.getHasGem().get(position));
                args.putBoolean(ChooseItemDialogFragment.ARG_HAS_CASE, actionRequest.getHasCase().get(position));
                Message msg = gameHandler.obtainMessage(GameHandler.PUNCH_CHOOSE_ITEM_DIALOG);
                msg.setData(args);
                msg.sendToTarget();
            }
            selectedItemType = Globals.getInstance().chosenItemType.request();
        } else if (nSelectableItems == 1) {
            if (actionRequest.getHasBag().get(position)) {
                Log.d(TAG, "selecting bag");
                selectedItemType = Globals.ItemType.BAG;
            } else if (actionRequest.getHasGem().get(position)) {
                Log.d(TAG, "selecting gem");
                selectedItemType = Globals.ItemType.GEM;
            } else if (actionRequest.getHasCase().get(position)) {
                Log.d(TAG, "selecting case");
                selectedItemType = Globals.ItemType.CASE;
            }
        } else {
            Log.d(TAG, "no item to toss");
            selectedItemType = null;
        }

        Log.d(TAG, "selecting wagon to move punchable target");
        gameHandler.obtainMessage(GameHandler.PUNCH_SELECT_WAGON_MESSAGE).sendToTarget();
        List<Long> selectableWagonLevelIds = actionRequest.getMovable();
        gameHandler.obtainMessage(GameHandler.START_HIGHLIGHT_WAGON_LEVEL, selectableWagonLevelIds.toArray(new Long[selectableWagonLevelIds.size()])).sendToTarget();
        Long selectedWagonLevelId = Globals.getInstance().chosenWagonLevelId.request();
        gameHandler.obtainMessage(GameHandler.STOP_HIGHLIGHT_WAGON_LEVEL, selectableWagonLevelIds.toArray(new Long[selectableWagonLevelIds.size()])).sendToTarget();
        gameHandler.obtainMessage(GameHandler.PUNCH_SOUND).sendToTarget();
        return new PunchResponseDTO(selectedUserId, selectedWagonLevelId, selectedItemType);
    }

    /**
     * @param actionRequest: the action request
     * @return an action response
     * @throws InterruptedException
     */
    private ActionResponseDTO handleShootRequest(ShootRequestDTO actionRequest) throws InterruptedException {
        Log.d(TAG, "selecting player to shoot");
        gameHandler.obtainMessage(GameHandler.SHOOT_SELECT_PLAYER_MESSAGE).sendToTarget();
        List<Long> selectableUserIds = actionRequest.getShootableUserIds();
        gameHandler.obtainMessage(GameHandler.START_SHOOT_HIGHTLIGHT_FIGURINE, selectableUserIds.toArray(new Long[selectableUserIds.size()])).sendToTarget();
        Long selectedUserId = Globals.getInstance().chosenUserId.request();
        gameHandler.obtainMessage(GameHandler.STOP_HIGHTLIGHT_FIGURINE, selectableUserIds.toArray(new Long[selectableUserIds.size()])).sendToTarget();
        gameHandler.obtainMessage(GameHandler.SHOOT_SOUND).sendToTarget();
        return new ShootResponseDTO(selectedUserId);
    }

    /**
     * Calculates the gunslinger's Id
     *
     * @param users: the list of users
     * @return id of the gunslinger
     */
    private ArrayList<Long> gunslingerIds(List<UserDTO> users) {
        ArrayList<Long> gunslingerIds = new ArrayList<>();
        Integer gunslingerBullets = 7;

        for (UserDTO user : users) {
            int userBullets = user.getBulletsDeck().getCards().get(user.getBulletsDeck().getCards().size() - 1).getBulletCounter();
            if (userBullets < gunslingerBullets) {
                gunslingerIds.clear();
                gunslingerIds.add(user.getId());
                gunslingerBullets = userBullets;
            } else if (userBullets == gunslingerBullets) {
                gunslingerIds.add(user.getId());
            }
        }

        return gunslingerIds;
    }

    /**
     * Calculates the winner's id
     *
     * @param users: the list of users
     * @return id of the winner
     */
    private Long winnerId(List<UserDTO> users) {
        Long winnerId = null;
        List<UserDTO> tieUsers = new ArrayList<>();
        Integer winnerNetworth = 0;

        for (UserDTO user : users) {
            int networth = 0;
            for (ItemDTO item : user.getItems()) {
                networth += item.getValue();
            }
            if (networth == winnerNetworth) {
                tieUsers.add(user);
                winnerNetworth = networth;
            } else if (networth > winnerNetworth) {
                tieUsers.clear();
                tieUsers.add(user);
                winnerNetworth = networth;
            }
        }

        if (tieUsers.size() == 1) {
            winnerId = tieUsers.get(0).getId();
        } else {
            Integer minReceivedBulletCards = Integer.MAX_VALUE;
            for (UserDTO tieUser : tieUsers) {
                int nReceivedBulletsCards = 0;
                for (HandCardDTO card : tieUser.getHandDeck().getCards()) {
                    if (card instanceof BulletCardDTO) {
                        nReceivedBulletsCards++;
                    }
                }
                if (nReceivedBulletsCards < minReceivedBulletCards) {
                    minReceivedBulletCards = nReceivedBulletsCards;
                    winnerId = tieUser.getId();
                }
            }
        }
        return winnerId;
    }

    /**
     * Triggered when FINNISH status detected
     *
     * @param game: the gameDTO
     */
    protected abstract void onGameFinish(GameDTO game, ArrayList<Long> gunslingerUserIds, Long winnerId);
}
