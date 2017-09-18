package ch.uzh.ifi.seal.soprafs16.group_12_android.views;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import ch.uzh.ifi.seal.soprafs16.group_12_android.R;

/**
 * Created by rafael on 01/05/16.
 */
public class WagonView extends FrameLayout {

    Boolean isLocomotive;
    WagonLevelView topFloor;
    View topFloorSpring;
    View space1;
    WagonLevelView bottomFloor;
    View bottomFloorSpring;
    View space2;

    RelativeLayout container;

    public WagonView(Context context, Boolean isLocomotive, WagonLevelView topFloor, WagonLevelView bottomFloor) {
        super(context);
        this.isLocomotive = isLocomotive;
        this.topFloor = topFloor;
        this.bottomFloor = bottomFloor;
        this.space1 = new View(context);
        this.space2 =  new View(context);
        this.topFloorSpring =  new View(context);
        this.bottomFloorSpring = new View(context);
        ImageView image = new ImageView(context);
        image.setImageResource(isLocomotive? R.drawable.locomotive : R.drawable.wagon);
        image.setAdjustViewBounds(true);
        this.addView(image);
        this.container = new RelativeLayout(context);
        this.addView(container);
    }

    /**
     * UI Thread
     */
    public void refresh(){

        topFloor.refresh();
        topFloor.addView(topFloorSpring);
        topFloorSpring.getLayoutParams().height = 710;
        bottomFloor.refresh();
        bottomFloor.addView(bottomFloorSpring);
        bottomFloorSpring.getLayoutParams().height = 710;

        container.removeAllViews();

        int maxHeight = ((View)container.getParent()).getHeight();
        int maxWidth = ((View)container.getParent()).getWidth();

        RelativeLayout.LayoutParams topFloorParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
        topFloorParams.topMargin = 0;
        topFloorParams.bottomMargin = (int) (maxHeight * 24.9f / 40.9f);
        topFloorParams.leftMargin = (int) (maxWidth * (isLocomotive ? 0.4123f : 0.069f));
        topFloorParams.width = (int) (maxWidth * (isLocomotive ? 0.53f : 0.865f));
        container.addView(topFloor,0,topFloorParams);

        RelativeLayout.LayoutParams bottomFloorParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
        bottomFloorParams.topMargin = (int) (maxHeight * 20.f / 40.9f);
        bottomFloorParams.bottomMargin = (int) (maxHeight * 4.9f / 40.9f);
        bottomFloorParams.leftMargin = (int) (maxWidth * (isLocomotive ? 0.4123f : 0.069f));
        bottomFloorParams.width = (int) (maxWidth * (isLocomotive ? 0.53f : 0.865f));
        container.addView(bottomFloor,0,bottomFloorParams);
    }
}
