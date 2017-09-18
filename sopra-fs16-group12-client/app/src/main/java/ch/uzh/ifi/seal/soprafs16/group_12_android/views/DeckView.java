package ch.uzh.ifi.seal.soprafs16.group_12_android.views;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import ch.uzh.ifi.seal.soprafs16.group_12_android.Card;
import ch.uzh.ifi.seal.soprafs16.group_12_android.Deck;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * DeckView provides a View that visualizes a deck.
 * DeckView can either be presented as a deck or as a hand.
 *
 * Created by Nico on 29.03.2016.
 */
public abstract class DeckView<T extends Card> extends RelativeLayout {
    private Deck<T> deck;
    private Type type;
    private static final int DEFAULT_HAND_CARD_GAP = 10;
    private static final Integer MAX_COMMON_DECK_CARDS = 5;

    public DeckView(Context context, Type type) {
        super(context);
        this.deck = new Deck<>();
        this.type = type;

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        setLayoutParams(params);

    }

    /**
     * NON UI Thread
     *
     * @param deck
     */
    public void setDeck(Deck<T> deck) {
        this.deck = deck;
    }

    /**
     * NON UI Thread
     *
     * @param type
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Returns a fetchCard from the deck without removing it.
     *
     * @param pos The position of the fetchCard
     * @return The fetchCard of Type T at position pos
     */
    public T get(int pos) {
        if (pos < deck.size()) {
            return deck.getCard(pos);
        }
        return null;
    }

    /**
     * Generates for each fetchCard in the deck a CardView and presents them in a deck.
     * UI Thread
     */
    public DeckView refresh() {
        int maxWidth = ((View) getParent()).getWidth();
        int cardHeight = ((View) getParent()).getHeight();
        int cardWidth = cardHeight * 5 / 7;

        this.removeAllViews();
        final List<T> viewDeck = new LinkedList<>();
        switch (type) {
            case HAND:
                viewDeck.addAll(deck.getCards());
                break;
            case COMMON_EXECUTE:
                viewDeck.addAll(deck.getCards().subList(0, Math.min(MAX_COMMON_DECK_CARDS, deck.getCards().size())));
                Collections.reverse(viewDeck);
                break;
            case COMMON_PLAN:
                viewDeck.addAll(deck.getCards().subList(Math.max(0, (deck.size() - 1) - MAX_COMMON_DECK_CARDS), deck.size()));
                break;
        }


        int card_gap = type==Type.HAND? DEFAULT_HAND_CARD_GAP : 0 - cardWidth * 9 / 10;

        if ((viewDeck.size() * (cardWidth + card_gap) - card_gap) > maxWidth) {
            card_gap = (maxWidth - (viewDeck.size() * cardWidth)) / (viewDeck.size() - 1);
        }
        int offset = (maxWidth - (viewDeck.size() * (cardWidth + card_gap) - card_gap)) / 2;


        for (int i = 0; i < viewDeck.size(); i++) {
            final Card card = viewDeck.get(i);
            if(type==Type.HAND){
                card.setPlayedHidden(false);
            }
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(cardWidth,cardHeight);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            params.leftMargin = offset + i * (cardWidth + card_gap);
            CardView cv = new CardView(getContext(), card) {
                @Override
                public void onClick(Long cardId) {
                    if(type==Type.HAND) DeckView.this.onCardClick(cardId);
                }
            }.refresh();
            addView(cv, params);
        }

        return this;
    }

    /**
     * Indicators for the type
     */
    public enum Type {
        COMMON_EXECUTE,
        COMMON_PLAN,
        HAND
    }

    public abstract void onCardClick(Long cardId);
}
