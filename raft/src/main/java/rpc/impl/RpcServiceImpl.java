package rpc.impl;

import rpc.RpcService;
import rpc.common.RpcReponse;
import rpc.common.RpcRequest;
import rpc.io.RpcNode;

public class RpcServiceImpl implements RpcService {
    private final RpcNode node;

    public RpcServiceImpl(int port, RpcNode node) {
        this.node = node;
        try{
            node.serve();
        }catch (Exception e){

        }
    }

    @Override
    public RpcReponse<?> handlerRequest(RpcRequest request) {
        RpcReponse ret = (RpcReponse<?>)node.call(8080, request.getMethodName(), request.getParameters());

        return ret;
    }
}
