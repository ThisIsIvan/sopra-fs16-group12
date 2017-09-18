package ch.uzh.ifi.seal.soprafs16.group_12_android.tasks;

/**
 * Created by rafael on 18/05/16.
 */
public interface EngineTask {

    void execute() throws ExecutionException,InterruptedException;

    class ExecutionException extends Exception{
        ExecutionException(Exception e){
            super(e);
        }
    }
}
