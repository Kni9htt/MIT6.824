package rpc;

import rpc.common.RpcReponse;
import rpc.common.RpcRequest;

public interface RpcService {
    RpcReponse<?> handlerRequest(RpcRequest request);
}
