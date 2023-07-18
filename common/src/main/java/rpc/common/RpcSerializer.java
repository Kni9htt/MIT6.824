package rpc.common;

import java.io.IOException;

public interface RpcSerializer {
    byte[] serialize(Object object) throws IOException;

    <T> T deserialize(Class<T> clazz, byte[] bytes) throws IOException;
}
