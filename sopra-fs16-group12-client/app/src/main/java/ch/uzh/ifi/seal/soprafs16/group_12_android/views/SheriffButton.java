package ch.uzh.ifi.seal.soprafs16.group_12_android.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import ch.uzh.ifi.seal.soprafs16.group_12_android.Globals;
import ch.uzh.ifi.seal.soprafs16.group_12_android.R;

/**
 * Created by rafael on 12/05/16.
 */
public abstract class SheriffButton extends Button {

    private static final String TAG = "SheriffButton";
    private static final int normalSprite = R.drawable.sheriff_badge;
    private static final int[] shotSprites = {R.drawable.sheriff_badge_shot1, R.drawable.sheriff_badge_shot2, R.drawable.sheriff_badge_shot3, R.drawable.sheriff_badge_shot4, R.drawable.sheriff_badge_shot5, R.drawable.sheriff_badge_shot6, R.drawable.sheriff_badge_shot7};
    private static final int SHOTGUN_SOUND = 0;
    private static final int CHANGE_SPRITE = 1;
    private static final int ON_CLICK = 2;

    private MediaPlayer shootgun_shoot_sound;
    private Handler uiHandler;

    public SheriffButton(Context context, String text) {
        super(context);
        uiHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SHOTGUN_SOUND:
                        shootgun_shoot_sound.start();
                        break;
                    case CHANGE_SPRITE:
                        SheriffButton.this.setBackgroundResource(shotSprites[Globals.getInstance().rng.nextInt(shotSprites.length)]);
                        break;
                    case ON_CLICK:
                        SheriffButton.this.onClick();
                        break;
                }
            }
        };
        shootgun_shoot_sound = MediaPlayer.create(context, R.raw.shot);
        setTypeface(Typeface.SERIF);
        setTextColor(Color.DKGRAY);
        setTypeface(Typeface.createFromAsset(context.getAssets(),"RioGrande.ttf"));
        setTextSize(27f);
        setBackgroundResource(normalSprite);
        setSoundEffectsEnabled(false);
        setText(text);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals.getInstance().executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            uiHandler.obtainMessage(SHOTGUN_SOUND).sendToTarget();
                            Thread.sleep(200);
                            uiHandler.obtainMessage(CHANGE_SPRITE).sendToTarget();
                            Thread.sleep(100);
                            uiHandler.obtainMessage(ON_CLICK).sendToTarget();
                        } catch (InterruptedException e) {
                            Log.w(TAG, "is complaining");
                        }
                    }
                });
            }
        });
    }

    public abstract void onClick();

}
