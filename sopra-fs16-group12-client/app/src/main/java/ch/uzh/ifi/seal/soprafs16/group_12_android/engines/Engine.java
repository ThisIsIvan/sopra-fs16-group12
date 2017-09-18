package ch.uzh.ifi.seal.soprafs16.group_12_android.engines;

import android.util.Log;
import ch.uzh.ifi.seal.soprafs16.group_12_android.tasks.EngineTask;

/**
 * Created by rafael on 18/05/16.
 */
public class Engine {

    private static Engine instance = null;

    private final EngineTask defaultWorkerTask = new EngineTask() {
        @Override
        public void execute() throws EngineTask.ExecutionException {
            Log.e(Engine.this.TAG, "no worker task assigned");
        }
    };
    private EngineTask workerTask = defaultWorkerTask;
    private final Runnable engine = new Runnable() {
        @Override
        public void run() {
            Log.d(TAG, "started");
            while (!terminate) {
                if (suspend) {
                    Log.d(TAG, "suspended");
                    while (suspend && !terminate) {
                        try {
                            synchronized (engine) {
                                engine.wait();
                            }
                            Log.d(TAG, "resumed");
                        } catch (InterruptedException ignored) {
                        }
                    }
                } else {
                    try {
                        workerTask.execute();
                        Thread.sleep(executionInterval);
                    } catch (InterruptedException ignored) {
                    } catch (EngineTask.ExecutionException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            }
            Log.d(TAG, "destroyed");
        }
    };
    private Thread engineThread = new Thread(engine, "engineThread");
    private Boolean terminate = true;
    private Boolean suspend = true;
    private static final Long DEFAULT_EXECUTION_INTERVAL = 0L;
    private Long executionInterval = DEFAULT_EXECUTION_INTERVAL;

    private static final String TAG = "Engine";

    /**
     * Returns the Engine instance
     *
     * @return the engine instance
     */
    public static Engine getInstance() {
        if (instance == null) {
            instance = new Engine();
        }
        return instance;
    }

    public void setWorkerTask(EngineTask workerTask) {
        this.workerTask = workerTask == null ? defaultWorkerTask : workerTask;
    }

    /**
     * Starts the engine with the default execution interval
     *
     * @see #start(Long)
     */
    public void start() {
        start(DEFAULT_EXECUTION_INTERVAL);
    }

    /**
     * Starts the engine
     * If engine is terminated, starts it
     * If engine is suspended, resumes it
     *
     * @param executionInterval: the interval in which the engine sleeps between subsequent executions
     */
    public void start(Long executionInterval) {
        if (terminate) {
            Log.d(TAG, "starting");
            this.executionInterval = executionInterval;
            this.terminate = false;
            this.suspend = false;
            engineThread.start();
        } else if (suspend) {
            Log.d(TAG, "resuming");
            this.executionInterval = executionInterval;
            suspend = false;
            synchronized (engine) {
                engine.notify();
            }
        } else {
            Log.d(TAG, "already started");
        }
    }

    /**
     * Suspends the engine
     */
    public void suspend() {
        if (!suspend) {
            Log.d(TAG, "suspending");
            suspend = true;
            engineThread.interrupt();
        } else {
            Log.d(TAG, "already suspended");
        }
    }

    /**
     * Terminates the engine
     */
    public void terminate() {
        if (!suspend) suspend();
        Log.d(TAG, "terminating");
        terminate = true;
        engineThread.interrupt();
    }
}
