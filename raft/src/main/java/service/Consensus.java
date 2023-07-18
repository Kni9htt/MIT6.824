package service;

import commons.entiy.AentryParam;
import commons.entiy.AentryResult;
import commons.entiy.RvoteParam;
import commons.entiy.RvoteResult;

public interface Consensus {
    RvoteResult requestVote(RvoteParam Param);

    AentryResult appendEntries(AentryParam Param);
}
