package service;

import common.entiy.*;
import commons.entiy.*;

public interface Node {
    RvoteResult handlerRequestVote(RvoteParam param);

    AentryResult handlerAppendEntries(AentryParam param);

    ClientKVAck handlerClientRequest(ClientKVReq request);
}
