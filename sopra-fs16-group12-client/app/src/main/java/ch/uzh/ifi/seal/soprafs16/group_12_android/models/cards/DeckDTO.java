package ch.uzh.ifi.seal.soprafs16.group_12_android.models.cards;

import java.io.Serializable;
import java.util.List;

public class DeckDTO<T extends CardDTO> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long id;

    private List<T> cards;

    public Long getId() {
        return id;
    }

    public List<T> getCards() {
        return cards;
    }

    public void setCards(List<T> cards) {
        this.cards = cards;
    }
}
