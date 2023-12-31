package rpc.common;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.IOException;

/**
 * @author knight
 */
public class RpcEncoder extends MessageToByteEncoder {
    private Class<?> clazz;
    private RpcSerializer rpcSerializer;

    public RpcEncoder(Class<?> clazz, RpcSerializer rpcSerializer) {
        this.clazz = clazz;
        this.rpcSerializer = rpcSerializer;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        if (clazz != null && clazz.isInstance(o)) {
            byte[] bytes = rpcSerializer.serialize(o);
            byteBuf.writeInt(bytes.length);
            byteBuf.writeBytes(bytes);
        }
    }

    public static class JSONRpcSerializer implements RpcSerializer {

        @Override
        public byte[] serialize(Object object) throws IOException {
            return JSON.toJSONBytes(object);
        }

        public <T> T deserialize(Class<T> clazz, byte[] bytes) throws IOException {
            return JSON.parseObject(bytes, clazz);
        }
    }
}
