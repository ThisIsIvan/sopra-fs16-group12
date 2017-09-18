package ch.uzh.ifi.seal.soprafs16.group_12_android;

/**
 * A wrapper for an object which can be transferred if and only if it is requested.
 * Basically a variation of the consumer - producer model.
 * Thread safe implementation.
 *
 * Created by rafael on 03/05/16.
 */
public class TransferableObject<T> {

    private T object = null;
    private Object lock = new Object();
    private Boolean isRequested = false;

    /**
     * Puts object into the TransferableObject and returns if getCard() is being called by another Thread
     * or if the current Thread is being interrupted
     *
     * NON Blocking operation
     * @param object: the object to be inserted
     * @return True is object was accepted
     */
    public Boolean put(T object){
        Boolean isAccepted;
        synchronized (lock) {
            if(isAccepted = isRequested) {
                this.object = object;
                lock.notify();
                isRequested = false;
            }
        }
        return isAccepted;
    }

    /**
     * gets the TransferableObject and returns if put() is being called by another Thread
     * or if the current Thread is being interrupted
     *
     * Blocking operation
     * @return object
     * @throws InterruptedException
     */
    public T request() throws InterruptedException {
        T object;
        synchronized (lock){
            isRequested = true;
            lock.wait();
            object = this.object;
            this.object = null;
        }
        return object;
    }
}
