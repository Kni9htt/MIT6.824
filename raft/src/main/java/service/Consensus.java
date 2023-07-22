package service;

import common.entiy.AentryParam;
import common.entiy.AentryResult;
import common.entiy.RvoteParam;
import common.entiy.RvoteResult;

public interface Consensus {
    RvoteResult requestVote(RvoteParam Param);

    AentryResult appendEntries(AentryParam Param);
}
