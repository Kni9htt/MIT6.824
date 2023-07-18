package impl;

import commons.entiy.AentryParam;
import commons.entiy.AentryResult;
import commons.entiy.RvoteParam;
import commons.entiy.RvoteResult;
import service.Consensus;
import service.Node;

public class ConsensusImpl implements Consensus {
    public final NodeImpl node;
    public ConsensusImpl(NodeImpl node) {
        this.node = node;
    }

    @Override
    public RvoteResult requestVote(RvoteParam Param) {
        return null;
    }

    @Override
    public AentryResult appendEntries(AentryParam Param) {
        return null;
    }
}
