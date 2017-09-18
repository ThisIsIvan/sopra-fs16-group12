package ch.uzh.ifi.seal.soprafs16.group_12_android;

import java.util.concurrent.Semaphore;

/**
 * @author rafaelkallis
 *
 * Threadsafe Lock
 *
 * Can be used as a limiter: throws an exception if current point of execution requests a already acquired lock,
 * thus discarding th rest of the execution and continuing from the catch clause.
 *
 * Useful for preventing call storms to the server, or preventing endless toasts after rapidly pressing buttons
 */
public class TimeoutMutex {
    private Semaphore isAcquiredMutex;
    private Boolean isAcquired;
    private Long lastAcquireTimestamp;
    private Long timeoutInterval;

    private Object lock = new Object();

    private static final Long TIMEOUT = 5000L;
    public static final Long NO_TIMEOUT = Long.MAX_VALUE;

    public TimeoutMutex(){
        this(TIMEOUT);
    }

    public TimeoutMutex(Long timeout){
        isAcquired = false;
        isAcquiredMutex = new Semaphore(1);
        this.lastAcquireTimestamp = 0L;
        this.timeoutInterval = timeout;
    }

    /**
     *
     * @return the timestamp of acquisition of the lock
     * @throws AcquiredException if the lock is already acquired
     */
    public Long acquire() throws AcquiredException{
        Long timestamp;
        synchronized (lock){
            if(System.currentTimeMillis() - this.lastAcquireTimestamp >= timeoutInterval || !isAcquired){
                isAcquired = true;
                timestamp = this.lastAcquireTimestamp = System.currentTimeMillis();
            }else{
                throw new AcquiredException();
            }
        }
        return timestamp;
//        if(System.currentTimeMillis() - this.lastAcquireTimestamp >= timeoutInterval || !isAcquired){
//            isAcquired = true;
//            timestamp = this.lastAcquireTimestamp = System.currentTimeMillis();
//            this.isAcquiredMutex.release();
//            return timestamp;
//        }else{
//            this.isAcquiredMutex.release();
//            throw new AcquiredException();
//        }
    }

    /**
     *
     * @param acquireTimestamp timestamp of acquisition of the lock
     * @throws TimeoutException, if release() has been called after the timeout interval has finished
     */
    public void release(Long acquireTimestamp) throws TimeoutException{
        synchronized (lock){
            if(System.currentTimeMillis() - acquireTimestamp < timeoutInterval){ // no timeout
                isAcquired = false;
            }else{
                throw new TimeoutException();
            }
        }

//        isAcquiredMutex.acquireUninterruptibly();
//        if(System.currentTimeMillis() - acquireTimestamp < timeoutInterval){ // no timeout
//            isAcquired = false;
//            isAcquiredMutex.release();
//        }else{
//            isAcquiredMutex.release();
//            throw new TimeoutException();
//        }
    }

    public class AcquiredException extends Exception{
        public AcquiredException(){
            super("Requested lock is already acquired, wait until it gets released");
        }
    }

    public class TimeoutException extends Exception{
        public TimeoutException(){
            super("The request has resulted in a timeout");
        }
    }
}
