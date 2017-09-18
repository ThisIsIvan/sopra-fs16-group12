package ch.uzh.ifi.seal.soprafs16.group_12_android.views;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.UserDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafael on 19/04/16.
 */
public class UserQueueView extends LinearLayout{

    private static final int DEFAULT_CIRCLE_SIZE = 32;

    List<UserQueueElementView> userQueueElements;

    public UserQueueView(Context context, int nPlayers){
        this(context,nPlayers, DEFAULT_CIRCLE_SIZE);
    }

    public UserQueueView(Context context, int nPlayers, int circle_size) {
        super(context);
        this.setOrientation(HORIZONTAL);
        this.setGravity(Gravity.LEFT);
        this.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        userQueueElements = new ArrayList<>(nPlayers);
        while(nPlayers-- != 0){
            userQueueElements.add(new UserQueueElementView(context,circle_size));
        }
    }

    public void refresh(){
        this.removeAllViews();
        for(UserQueueElementView elementView : userQueueElements){
            this.addView(elementView);
        }
    }

    public void setQueue(List<UserDTO> userDTOs, int starting, boolean isReverse){
        int i = starting;
        do{
            if(userDTOs.get(i).getCharacter() != null){
                this.userQueueElements.get(i).setImageResource(userDTOs.get(i).getCharacter().circleSprite());
            }
            i=(isReverse?i-1:i+1)%userDTOs.size();
        }while(i != starting);
    }

    public void setQueue(List<Integer> sprites){
        for(int i =0; i < sprites.size(); i++){
            this.userQueueElements.get(i).setImageResource(sprites.get(i));
        }
    }

    private class UserQueueElementView extends ImageView{

        private UserQueueElementView(Context context, int circle_size) {
            super(context);
            LinearLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            this.setLayoutParams(params);
            this.setMaxWidth(circle_size);
            this.setMaxHeight(circle_size);
            this.setAdjustViewBounds(true);
            this.setPadding(5,5,5,5);
        }
    }
}
