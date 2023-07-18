package rpc.common;

import lombok.Data;
/**
 * @author hyb
 * @date 2023/5/5
 */
@Data
public class RpcRequest {
    /**
     * 请求对象的 ID
     */
    private String requestId;

    /**
     * 类名
     */
    private String className;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 参数类型
     */
    private Class<?>[] parameterTypes;

    /**
     * 参数值
     */
    private Object[] parameters;

}
