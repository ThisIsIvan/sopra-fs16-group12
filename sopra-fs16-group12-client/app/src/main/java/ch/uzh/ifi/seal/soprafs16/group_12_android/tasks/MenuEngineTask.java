package ch.uzh.ifi.seal.soprafs16.group_12_android.tasks;

import ch.uzh.ifi.seal.soprafs16.group_12_android.Globals;
import ch.uzh.ifi.seal.soprafs16.group_12_android.handlers.MenuHandler;

/**
 * Created by rafael on 24/05/16.
 */
public class MenuEngineTask implements EngineTask {

    private MenuHandler menuHandler;

    public MenuEngineTask(MenuHandler menuHandler) {
        this.menuHandler = menuHandler;
    }

    @Override
    public synchronized void execute() throws ExecutionException, InterruptedException {
        try {
            menuHandler.obtainMessage(Globals.getInstance().isLoggedIn ? MenuHandler.LOGIN : MenuHandler.LOGOUT).sendToTarget();
        } catch (Exception e) {
            throw new EngineTask.ExecutionException(e);
        }
    }
}
