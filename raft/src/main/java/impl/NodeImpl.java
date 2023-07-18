package impl;

import service.Consensus;
import service.Node;
import commons.entiy.*;
import util.LogUtil;

public class NodeImpl implements Node{

    volatile long currentTerm = 0;

    Consensus consensus;

    void init() {
        consensus = new ConsensusImpl(this);
    }
    @Override
    public RvoteResult handlerRequestVote(RvoteParam param) {
        LogUtil.log("handlerRequestVote will be voted, param is", param);
        return consensus.requestVote(param);
    }

    @Override
    public AentryResult handlerAppendEntries(AentryParam param) {
        return null;
    }

    @Override
    public ClientKVAck handlerClientRequest(ClientKVReq request) {
        return null;
    }
}
