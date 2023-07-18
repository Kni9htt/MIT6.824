package commons.entiy;

public interface LifeCycle {
    /**
     * 初始化
     * @throws Throwable
     */
    void init() throws Throwable;

    /**
     * 超时处理
     * @throws Throwable
     */
    void destroy() throws Throwable;
}
