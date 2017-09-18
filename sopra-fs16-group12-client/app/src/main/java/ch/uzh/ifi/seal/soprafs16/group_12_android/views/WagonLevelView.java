package ch.uzh.ifi.seal.soprafs16.group_12_android.views;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import ch.uzh.ifi.seal.soprafs16.group_12_android.Globals;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.ItemDTO;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by rafael on 04/05/16.
 */
public abstract class WagonLevelView extends LinearLayout {

    private final Drawable wagonHighlightColor = new ColorDrawable(0x77000000);
    private final Drawable wagonNoHighlightColor = new ColorDrawable(0x00000000);

    private ItemView bagsView;
    private ItemView gemsView;
    private ItemView casesView;
    private Set<FigurineView> figurineViews;

    public WagonLevelView(Context context) {
        super(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(params);
        this.setGravity(Gravity.CENTER_HORIZONTAL);
        this.setGravity(Gravity.BOTTOM);
        this.figurineViews = new TreeSet<>();
        this.bagsView = new ItemView(getContext(), Globals.ItemType.BAG) {
            @Override
            public void onClick(Globals.ItemType itemType) {
                WagonLevelView.this.onItemClick(itemType);
            }
        };
        this.gemsView = new ItemView(getContext(), Globals.ItemType.GEM) {
            @Override
            public void onClick(Globals.ItemType itemType) {
                WagonLevelView.this.onItemClick(itemType);
            }
        };
        this.casesView = new ItemView(getContext(), Globals.ItemType.CASE) {
            @Override
            public void onClick(Globals.ItemType itemType) {
                WagonLevelView.this.onItemClick(itemType);
            }
        };
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                WagonLevelView.this.onWagonLevelClick();
            }
        });
    }

    /**
     * NON UI Thread
     * @param figurineView
     */
    public void place(FigurineView figurineView){
        figurineViews.add(figurineView);
    }

    /**
     * NON UI Thread
     *
     * @param item
     */
    @Deprecated
    public void place(ItemDTO item) {
        switch (item.getItemType()){
            case BAG:
                bagsView.addOne();
                break;
            case GEM:
                gemsView.addOne();
                break;
            case CASE:
                casesView.addOne();
                break;
        }
    }

    /**
     * NON UI Thread
     * @param itemType
     * @param nItems
     */
    public void place(Globals.ItemType itemType, Integer nItems){
        switch (itemType){
            case BAG:
                bagsView.setnItems(nItems);
                break;
            case GEM:
                gemsView.setnItems(nItems);
                break;
            case CASE:
                casesView.setnItems(nItems);
                break;
        }
    }


    /**
     * NON UI Thread
     */
    @Deprecated
    public void removeAll() {
        this.figurineViews.clear();
        this.bagsView.removeAll();
        this.gemsView.removeAll();
        this.casesView.removeAll();
    }

    /**
     * UI Thread
     */
    public void refresh() {
        this.removeAllViews();

        int width = getWidth();
        int height = getHeight();

        if(width == 0) {
            if (bagsView.getnItems() != 0) {
                bagsView.refresh();
                this.addView(bagsView);
            }
            if (gemsView.getnItems() != 0) {
                gemsView.refresh();
                this.addView(gemsView);
            }
            if (casesView.getnItems() != 0) {
                casesView.refresh();
                this.addView(casesView);
            }
            for (FigurineView view : figurineViews) {
                this.addView(view);
            }
        } else {
            int nItems = (bagsView.getnItems() != 0?1:0) + (bagsView.getnItems() != 0?1:0) + (bagsView.getnItems() != 0?1:0);
            int nFigurines = figurineViews.size();

            int gap = 10;
            int figurineWidth = height;
            int itemWidth = (int) (height * 2f / 5f);

            if ((nItems + nFigurines) > 1 && (nItems * (itemWidth + gap) + (nFigurines * (figurineWidth + gap)) - gap) > width) {
                gap = (int) ((width - ((nItems * itemWidth) + (nFigurines * figurineWidth))) / ((nItems + nFigurines) - 1f));
            }
            int offset = (int) ((width - ((nItems * (itemWidth + gap) + (nFigurines * (figurineWidth + gap)) - gap))) / 2f);

            if (bagsView.getnItems() != 0) {
                bagsView.refresh();
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.leftMargin = offset;
                offset += itemWidth + gap;
                this.addView(bagsView,params);
            }
            if (gemsView.getnItems() != 0) {
                gemsView.refresh();
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.leftMargin = offset;
                offset += itemWidth + gap;
                this.addView(gemsView,params);
            }
            if (casesView.getnItems() != 0) {
                casesView.refresh();
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.leftMargin = offset;
                offset += itemWidth + gap;
                this.addView(casesView,params);
            }
            for (FigurineView view : figurineViews) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.leftMargin = offset;
                offset += figurineWidth + gap;
                this.addView(view,params);
            }
        }
    }

    public void startHighlightWagonLevel() {
        this.setBackground(wagonHighlightColor);
    }

    public void stopHighlightWagonLevel() {
        this.setBackground(wagonNoHighlightColor);
    }

    /**
     * UI Thread
     */
    public void startHighlightItems() {
        bagsView.startHighlight();
        gemsView.startHighlight();
        casesView.startHighlight();
    }

    /**
     * UI Thread
     */
    public void stopHighlightItems() {
        bagsView.stopHighlight();
        gemsView.stopHighlight();
        casesView.stopHighlight();
    }

    public abstract void onWagonLevelClick();
    public abstract void onItemClick(Globals.ItemType itemType);
}
