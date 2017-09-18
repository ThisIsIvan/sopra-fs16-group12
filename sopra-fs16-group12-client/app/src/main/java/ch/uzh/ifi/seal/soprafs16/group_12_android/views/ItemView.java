package ch.uzh.ifi.seal.soprafs16.group_12_android.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import ch.uzh.ifi.seal.soprafs16.group_12_android.Globals;
import ch.uzh.ifi.seal.soprafs16.group_12_android.R;

/**
 * Created by rafael on 01/05/16.
 */
public abstract class ItemView extends FrameLayout{

    private Integer nItems;
    private TextView nItemsText;
    private ImageView itemImageView;

    private ImageView beacon;

    public ItemView(Context context, final Globals.ItemType itemType) {
        super(context);
        LinearLayout container = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.item,null);
        this.addView(container);
        this.nItemsText = (TextView) container.findViewById(R.id.item_nItems);
        this.nItems = 0;
        this.nItemsText.setText(nItems.toString());
        this.itemImageView = (ImageView) container.findViewById(R.id.item_item);
        this.itemImageView.setImageResource(itemType.getSprite());
        this.itemImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemView.this.onClick(itemType);
            }
        });

        this.beacon = (ImageView) container.findViewById(R.id.item_arrow);
        this.beacon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemView.this.onClick(itemType);
            }
        });
    }

    /**
     * NON UI Thread
     */
    @Deprecated
    public void removeAll(){
        this.nItems = 0;
    }

    /**
     * NON UI Thread
     */
    @Deprecated
    public void addOne(){
        this.nItems++;
    }

    /**
     * NON UI Thread
     * @return
     */
    public Integer getnItems() {
        return nItems;
    }

    /**
     * NON UI Thread
     * @param nItems
     */
    public void setnItems(Integer nItems) {
        this.nItems = nItems;
    }

    /**
     * UI Thread
     */
    public void startHighlight() {
        ItemView.this.beacon.setVisibility(VISIBLE);
    }

    /**
     * UI Thread
     */
    public void stopHighlight(){
        ItemView.this.beacon.setVisibility(INVISIBLE);
    }

    /**
     * UI Thread
     */
    public void refresh(){
        this.nItemsText.setText((this.nItems).toString());
    }

    public abstract void onClick(Globals.ItemType itemType);
}
