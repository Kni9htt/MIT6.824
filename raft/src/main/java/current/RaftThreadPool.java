package current;

import java.util.concurrent.*;

public class RaftThreadPool {
    // 获取可用的CPU核数
    private static final int CPU = Runtime.getRuntime().availableProcessors();
    // 设置线程池的最大容量 IO密集型：corePoolSize = CPU核数 * 2（大量的网络请求）
    private static final int MAX_POOL_SIZE = CPU * 2;

    // 等待队列的大小
    private static final int MAX_QUEUE_SIZE = 1024;

    // 线程等待时间
    private static final long KEEP_TIME = 1000 * 60;

    // 等待时间的单位
    private static final TimeUnit KEEP_TIME_UINT = TimeUnit.MILLISECONDS;

    // 设置为类方法且为私有，只能在类内调用
    private static ThreadPoolExecutor te = getThreadPool();

    /**
     * 新建线程池
     * @return
     */
    private static ThreadPoolExecutor getThreadPool() {
        return new ThreadPoolExecutor(
                CPU,
                MAX_POOL_SIZE,
                KEEP_TIME,
                KEEP_TIME_UINT,
                new LinkedBlockingQueue<>(MAX_QUEUE_SIZE),
                new NameThreadFactory());
    }

    /**
     * 提交实现Callable方法的线程到线程池
     * @param c
     * @return
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    public static <T>Future<T> submit(Callable c) {
        return te.submit(c);
    }

    /**
     * 静态内部类：设置线程名
     */
    static class NameThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new RaftThread("Raft thread", r);
            t.setDaemon(true);
            t.setPriority(5);
            return t;
        }
    }
}
