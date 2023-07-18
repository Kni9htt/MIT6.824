package rpc.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class RpcDecoder extends ByteToMessageDecoder {
    private Class<?> clazz;
    private RpcSerializer rpcSerializer;

    public RpcDecoder(Class<?> clazz, RpcSerializer rpcSerializer) {
        this.clazz = clazz;
        this.rpcSerializer = rpcSerializer;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf,
        List<Object> list) throws Exception {

        // ��ȡ���͹�������Ϣ�ĳ���
        int dataLength = byteBuf.readInt();
        // ���Ƕ�������Ϣ�峤��Ϊ 0�����ǲ�Ӧ�ó��ֵ���������������������ر����ӡ�
        if (dataLength < 0) {
            channelHandlerContext.close();
        }

        //ȡ������
        byte[] bytes = new byte[dataLength];
        byteBuf.readBytes(bytes);  //
        //�� byte ����ת��Ϊ������Ҫ�Ķ���
        Object o = rpcSerializer.deserialize(clazz, bytes);
        list.add(o);
    }
}
