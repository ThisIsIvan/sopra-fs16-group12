package ch.uzh.ifi.seal.soprafs16.group_12_android.fragments;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import ch.uzh.ifi.seal.soprafs16.group_12_android.*;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.GameDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.ItemDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.UserDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.WagonLevelDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.action.response.DrawCardResponseDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.views.CardView;
import ch.uzh.ifi.seal.soprafs16.group_12_android.views.DeckView;
import ch.uzh.ifi.seal.soprafs16.group_12_android.views.TrainView;
import ch.uzh.ifi.seal.soprafs16.group_12_android.views.UserQueueView;

/**
 * Created by rafael on 05/04/16.
 */

public class GameFragment extends Fragment {

    private static final String ARG_GAME = "GameFragment.ARG_GAME";
    private static final String TAG = "GameF";

    private InteractionListener mListener;

    private TextView networthTextView;
    private TextView nBagsTextView;
    private TextView nGemsTextView;
    private TextView nCasesTextView;
    private TextView currentPlayerTextView;
    private UserQueueView userQueue;
    private ArrayAdapter<String> logAdapter;
    private TrainView trainView;
    private TextView currentRoundView;
    private TextView currentTurnView;
    private TextView currentPhaseView;
    private CardView roundCardView;
    private DeckView<ActionCard> commonDeckView;
    private DeckView<HandCard> handDeckView;
    private CardView bulletCardView;
    private CardView drawCardView;

    private MediaPlayer shootSound;
    private MediaPlayer punchSound;
    private MediaPlayer stepSound;
    private MediaPlayer trainHonkSound;
    private MediaPlayer yahooSound;

    public static GameFragment newInstance(GameDTO game) {
        GameFragment gameFragment = new GameFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_GAME, game);
        gameFragment.setArguments(args);
        return gameFragment;
    }

    public GameFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_game, container, false);
        GameFragment.this.shootSound = MediaPlayer.create(getContext(), R.raw.shot);
        GameFragment.this.punchSound = MediaPlayer.create(getContext(), R.raw.punch);
        GameFragment.this.stepSound = MediaPlayer.create(getContext(), R.raw.step);
        GameFragment.this.trainHonkSound = MediaPlayer.create(getContext(), R.raw.train_honk);
        GameFragment.this.yahooSound = MediaPlayer.create(getContext(), R.raw.yahoo);

        GameDTO gameDTO = (GameDTO) getArguments().getSerializable(GameFragment.ARG_GAME);

        Globals.getInstance().characterMap.clear();
        for (int i = 0; i < gameDTO.getUsers().size(); i++) {
            UserDTO user = gameDTO.getUsers().get(i);
            Globals.getInstance().characterMap.put(user.getId(), user.getCharacter());
        }

        /**
         * Networth
         */
        GameFragment.this.networthTextView = (TextView) v.findViewById(R.id.fragment_game_networth);
        GameFragment.this.networthTextView.setText("$ 0");

        /**
         * Items
         */
        GameFragment.this.nBagsTextView = (TextView) v.findViewById(R.id.fragment_game_n_bags);
        GameFragment.this.nGemsTextView = (TextView) v.findViewById(R.id.fragment_game_n_gems);
        GameFragment.this.nCasesTextView = (TextView) v.findViewById(R.id.fragment_game_n_cases);
        GameFragment.this.nBagsTextView.setText("1");
        GameFragment.this.nGemsTextView.setText("0");
        GameFragment.this.nCasesTextView.setText("0");

        /**
         * Current Player
         */
        GameFragment.this.currentPlayerTextView = (TextView) v.findViewById(R.id.fragment_game_current_player);
        GameFragment.this.currentPlayerTextView.setText("");

        /**
         * Player Queue
         */
        FrameLayout userQueueContainer = (FrameLayout) v.findViewById(R.id.fragment_game_player_queue_container);
        GameFragment.this.userQueue = new UserQueueView(getContext(), gameDTO.getUsers().size());
        userQueueContainer.addView(GameFragment.this.userQueue);

        /**
         * Scoreboard
         */
        Button scoreboardButton = (Button) v.findViewById(R.id.fragment_game_scoreboard_button);
        scoreboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameFragment.this.mListener.onScoreBoardButtonClick();
            }
        });

        /**
         * Manual
         */
        Button manualButton = (Button) v.findViewById(R.id.fragment_game_manual_button);
        manualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onManualButtonClick();
            }
        });

        /**
         * Log
         */
        GameFragment.this.logAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_item, R.id.list_item_text);
        ListView logView = new ListView(getActivity());
        logView.setAdapter(logAdapter);
        FrameLayout logContainer = (FrameLayout) v.findViewById(R.id.fragment_game_log_container);
        logContainer.addView(logView);

        /**
         * Train
         */

        trainView = new TrainView(getActivity(), gameDTO.getWagons()) {
            @Override
            public void onFigurineClick(final Long userId) {
                GameFragment.this.mListener.onFigurineClick(userId);
            }

            @Override
            public void onWagonLevelClick(Long wagonLevelId) {
                GameFragment.this.mListener.onWagonLevelClick(wagonLevelId);
            }

            @Override
            public void onItemClick(Long wagonLevelId, Globals.ItemType itemType) {
                GameFragment.this.mListener.onItemClick(wagonLevelId, itemType);
            }
        };
        v.findViewById(R.id.train_scroll_container).setLayerType(View.LAYER_TYPE_SOFTWARE, null); //disable hardware acceleration for train view
        ((HorizontalScrollView) v.findViewById(R.id.train_scroll_container)).addView(trainView);

        /**
         * Common Deck
         */
        GameFragment.this.commonDeckView = new DeckView<ActionCard>(getActivity(), DeckView.Type.COMMON_PLAN) {
            @Override
            public void onCardClick(Long cardId) {
                // DO NOTHING
            }
        };
        FrameLayout gameDeckContainer = (FrameLayout) v.findViewById(R.id.fragment_game_game_deck_container);
        gameDeckContainer.addView(commonDeckView);

        /**
         * Draw Deck
         */
        FrameLayout drawLayout = (FrameLayout) v.findViewById(R.id.fragment_game_player_draw_container);
        GameFragment.this.drawCardView = new CardView(getActivity(), Card.BACK) {
            @Override
            public void onClick(Long cardId) {
                if (Globals.getInstance().chosenCardId.put(DrawCardResponseDTO.DRAW_CARD)) {
                    Log.d(TAG, "draw card chosen");
                }
            }
        }.refresh();
        drawLayout.addView(drawCardView);

        /**
         * Bullet Card
         */
        FrameLayout bulletCardLayout = (FrameLayout) v.findViewById(R.id.fragment_game_bullet_card_container);
        GameFragment.this.bulletCardView = new CardView(getActivity(), Card.BACK) {
            @Override
            public void onClick(Long cardId) {
                // DO NOTHING
            }
        }.refresh();
        GameFragment.this.bulletCardView.setClickable(false);
        bulletCardLayout.addView(bulletCardView);

        /**
         * Hand Deck
         */
        FrameLayout handDeckContainer = (FrameLayout) v.findViewById(R.id.user_deck_container);
        GameFragment.this.handDeckView = new DeckView<HandCard>(getActivity(), DeckView.Type.HAND) {
            @Override
            public void onCardClick(Long cardId) {
                if (Globals.getInstance().chosenCardId.put(cardId)) {
                    Log.d(TAG, "card " + cardId + " chosen");
                }
            }
        };
        handDeckContainer.addView(handDeckView);

        /**
         * Round Info
         */
        GameFragment.this.currentRoundView = (TextView) v.findViewById(R.id.fragment_game_round_number);
        GameFragment.this.currentTurnView = (TextView) v.findViewById(R.id.fragment_game_turn_number);
        GameFragment.this.currentPhaseView = (TextView) v.findViewById(R.id.fragment_game_phase);
        GameFragment.this.roundCardView = new CardView(getActivity(), RoundCard.BACK) {
            @Override
            public void onClick(Long cardId) {
                // DO NOTHING
            }
        };
        GameFragment.this.roundCardView.setClickable(false);
        FrameLayout roundCardContainer = (FrameLayout) v.findViewById(R.id.fragment_game_round_card);
        roundCardContainer.addView(GameFragment.this.roundCardView);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (InteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement InteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public CardView getDrawCardView() {
        return drawCardView;
    }

    public TextView getNetworthTextView() {
        return networthTextView;
    }

    public TextView getnBagsTextView() {
        return nBagsTextView;
    }

    public TextView getnGemsTextView() {
        return nGemsTextView;
    }

    public TextView getnCasesTextView() {
        return nCasesTextView;
    }

    public TextView getCurrentPlayerTextView() {
        return currentPlayerTextView;
    }

    public UserQueueView getUserQueue() {
        return userQueue;
    }

    public ArrayAdapter<String> getLogAdapter() {
        return logAdapter;
    }

    public TrainView getTrainView() {
        return trainView;
    }

    public TextView getCurrentRoundView() {
        return currentRoundView;
    }

    public TextView getCurrentTurnView() {
        return currentTurnView;
    }

    public TextView getCurrentPhaseView() {
        return currentPhaseView;
    }

    public CardView getRoundCardView() {
        return roundCardView;
    }

    public DeckView<ActionCard> getCommonDeckView() {
        return commonDeckView;
    }

    public DeckView<HandCard> getHandDeckView() {
        return handDeckView;
    }

    public CardView getBulletCardView() {
        return bulletCardView;
    }

    public MediaPlayer getShootSound() {
        return shootSound;
    }

    public MediaPlayer getPunchSound() {
        return punchSound;
    }

    public MediaPlayer getStepSound() {
        return stepSound;
    }

    public MediaPlayer getTrainHonkSound() {
        return trainHonkSound;
    }

    public MediaPlayer getYahooSound() {
        return yahooSound;
    }

    public interface InteractionListener {
        void onManualButtonClick();

        void onScoreBoardButtonClick();

        void onFigurineClick(Long userId);

        void onWagonLevelClick(Long wagonLevelId);

        void onItemClick(Long wagonLevelId, Globals.ItemType itemType);
    }

    /**
     * NON UI Thread
     *
     * @param wagonLevel
     */
    public void prepareWagonLevelView(WagonLevelDTO wagonLevel) {
        trainView.removeAll(wagonLevel.getId());
        for (ItemDTO item : wagonLevel.getItems()) {
            trainView.place(wagonLevel.getId(), item);
        }
        for (UserDTO user : wagonLevel.getUsers()) {
            trainView.place(wagonLevel.getId(), user);
        }
        if (wagonLevel.getMarshal() != null) {
            trainView.placeMarshall(wagonLevel.getId());
        }
    }

    /**
     * NON UI Thread
     *
     * @param bulletCard
     */
    public void prepareBulletCardView(BulletCard bulletCard) {
        GameFragment.this.bulletCardView.setCard(bulletCard);
    }

    /**
     * NON UI Thread
     *
     * @param roundCard
     */
    public void prepareRoundCardView(RoundCard roundCard) {
        GameFragment.this.roundCardView.setCard(roundCard);

    }

    /**
     * NON UI Thread
     *
     * @param handDeck
     */
    public void prepareHandDeckView(Deck<HandCard> handDeck) {
        GameFragment.this.handDeckView.setDeck(handDeck);
    }

    /**
     * NON UI Thread
     *
     * @param commonDeck
     */
    public void prepareCommonDeckView(Deck<ActionCard> commonDeck, DeckView.Type type) {
        GameFragment.this.commonDeckView.setDeck(commonDeck);
        GameFragment.this.commonDeckView.setType(type);
    }
}