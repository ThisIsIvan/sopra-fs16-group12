package ch.uzh.ifi.seal.soprafs16.group_12_android.views;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by rafael on 02/05/16.
 */
public abstract class FigurineView extends FrameLayout implements Comparable<FigurineView>{

    ImageView figurine;

    private int normalSprite;
    private int shootHighlightSprite;
    private int punchHighlightSprite;

    public FigurineView(Context context, final Long userId, int normalSprite, int shootHighlightSprite, int punchHighlightSprite) {
        super(context);

        this.normalSprite = normalSprite;
        this.shootHighlightSprite = shootHighlightSprite;
        this.punchHighlightSprite = punchHighlightSprite;

        figurine = new ImageView(context);
        figurine.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FigurineView.this.onClick(userId);
            }
        });
        figurine.setImageResource(normalSprite);
        figurine.setMaxWidth(150);
        figurine.setAdjustViewBounds(true);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FigurineView.this.onClick(userId);
            }
        });
        this.addView(figurine);
    }

    /**
     * Replaces the current sprite with the "shootable" version
     */
    public void startShootHighlight() {
        FigurineView.this.figurine.setImageResource(shootHighlightSprite);
    }

    /**
     * Replaces the current sprite with the "punchable" version
     */
    public void startPunchHighlight(){
        FigurineView.this.figurine.setImageResource(punchHighlightSprite);
    }

    /**
     * Replaces the current sprite with the normal version
     */
    public void stopHighlight() {
        FigurineView.this.figurine.setImageResource(normalSprite);
    }

    /**
     * Triggered when figurine or hand on top is clicked
     * @param userId: the id of the figurine's user
     */
    public abstract void onClick(Long userId);

    @Override
    public int compareTo(FigurineView another) {
        return normalSprite < another.normalSprite ? -1 : (normalSprite == another.normalSprite ? 0 : 1);
    }
}
