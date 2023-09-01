package rpc;


import rpc.common.RpcReponse;
import rpc.common.RpcRequest;


/**
 * @author knight
 */
public interface RpcService {
    /**
     * TODO
     * @param request
     * @return: RpcReponse<?>
     */
    RpcReponse<?> handlerRequest(RpcRequest request);
}
