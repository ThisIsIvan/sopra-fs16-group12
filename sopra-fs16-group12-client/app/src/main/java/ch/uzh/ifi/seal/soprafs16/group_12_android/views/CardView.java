package ch.uzh.ifi.seal.soprafs16.group_12_android.views;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import ch.uzh.ifi.seal.soprafs16.group_12_android.Card;

/**
 * CardView is the visual representation of a fetchCard.
 *
 * Created by Nico on 29.03.2016.
 *
 */
public abstract class CardView extends ImageView{

    private static final String TAG = "CardView";

    private Card card;

    /**
     * @pre fetchCard.getId() != null
     * @param context: the context
     * @param card: the fetchCard model
     */
    public CardView(Context context, final Card card) {
        super(context);
        this.card = card;
    }

    public void setCard(Card card){
        this.card = card;
    }

    public CardView refresh(){
        setImageResource(this.card.getVisibleSprite());
        final Long cardId = card.getId();
        final String name = card.getName();
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"card clicked: "+name+" "+cardId);
                CardView.this.onClick(cardId);
            }
        });
        return this;
    }

    public abstract void onClick(Long cardId);
}
