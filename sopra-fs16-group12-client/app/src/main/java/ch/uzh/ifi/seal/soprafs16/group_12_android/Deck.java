package ch.uzh.ifi.seal.soprafs16.group_12_android;

import java.io.Serializable;
import java.util.LinkedList;


/**
 * A deck of cards.
 *
 * Created by Nico on 29.03.2016.
 */
public class Deck<T extends Card> implements Serializable{
    private LinkedList<T> cards;

    public Deck() {
        this.cards = new LinkedList<T>();
    }

    /**
     *
     * adds an object of generic type T to the deck
     * @param t object to be added to the deck
     *
     */
    public void add(T t){
        cards.add(t);
    }

    /**
     * Returns the object at the given position.
     *
     * @param pos the position of the requested object
     * @return the object at position
     */
    public T getCard(int pos){
        return cards.get(pos);
    }

    /**
     * Removes the fetchCard at given position and returns it
     * @pre pos >= 0 < cards.size()
     * @param pos the position of the fetchCard
     * @return the fetchCard at position pos
     */
    public T remove(int pos){
        return cards.remove(pos);
    }

    public int size(){
        return cards.size();
    }

    public LinkedList<T> getCards() {
        return cards;
    }

    public void setCards(LinkedList<T> cards) {
        this.cards = cards;
    }

}
