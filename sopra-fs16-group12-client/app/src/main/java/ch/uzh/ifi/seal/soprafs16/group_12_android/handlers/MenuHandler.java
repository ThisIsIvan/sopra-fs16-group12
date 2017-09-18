package ch.uzh.ifi.seal.soprafs16.group_12_android.handlers;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import ch.uzh.ifi.seal.soprafs16.group_12_android.fragments.MenuFragment;

/**
 * Created by rafael on 24/05/16.
 */
public class MenuHandler extends Handler{

    public static final int LOGIN = 0;
    public static final int LOGOUT = 1;

    private MenuFragment menuFragment;

    public static MenuHandler newInstance(MenuFragment menuFragment){
        MenuHandler menuHandler = new MenuHandler();
        menuHandler.menuFragment = menuFragment;
        return menuHandler;
    }

    private MenuHandler(){}

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){
            case LOGIN:
                menuFragment.getRegisterButton().setVisibility(View.INVISIBLE);
                menuFragment.getLogoutButton().setVisibility(View.VISIBLE);
                break;
            case LOGOUT:
                menuFragment.getRegisterButton().setVisibility(View.VISIBLE);
                menuFragment.getLogoutButton().setVisibility(View.INVISIBLE);
                break;
        }
    }

}
