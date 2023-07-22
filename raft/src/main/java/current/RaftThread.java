package current;

public class RaftThread extends Thread{
    private static final UncaughtExceptionHandler UNCAUGHT_EXCEPTION_HANDLER = null;
    public RaftThread(String threadName, Runnable r) {
        super(r, threadName);
        setUncaughtExceptionHandler(UNCAUGHT_EXCEPTION_HANDLER);
    }
}
