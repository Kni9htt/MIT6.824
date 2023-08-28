package impl;

import common.entiy.*;
import io.netty.util.internal.StringUtil;
import service.Consensus;

import java.util.concurrent.locks.*;

public class ConsensusImpl implements Consensus {
    public final NodeImpl node;

    public final Lock voteLock = new ReentrantLock();

    public ConsensusImpl(NodeImpl node) {
        this.node = node;
    }

    /**
     * 请求投票 RPC
     *
     * 接收者实现：
     *      如果term < currentTerm返回 false （5.2 节）
     *      如果 votedFor 为空或者就是 candidateId，并且候选人的日志至少和自己一样新，那么就投票给他（5.2 节，5.4 节）
     */
    @Override
    public RvoteResult requestVote(RvoteParam param) {
        // 尝试获取锁，进行节点状态的修改
        try{
            RvoteResult.Builder builder = RvoteResult.newBuilder();
            // 锁获取失败，表明有多个vote请求
            if (!voteLock.tryLock()) {
                return builder.setTerm(node.getCurrentTerm()).setVoteGranted(false).build();
            }
            // 若请求投票的candidate的term小于自己，拒绝投票
            if (param.getLastLogTerm() < node.getCurrentTerm()) {
                return builder.setTerm(node.getCurrentTerm()).setVoteGranted(false).build();
            }
            // 为了保证一致性，确保candidate的log的term和index大于自己
            if (StringUtil.isNullOrEmpty(node.getVotedFor()) || node.getVotedFor().equals(param.getCandidateId())) {
                if (node.getLogModule().getLast() != null) {
                    if (node.getLogModule().getLastTerm() > param.getLastLogTerm()) {
                        return RvoteResult.fail();
                    }
                    if (node.getLogModule().getLastIndex() > param.getLastLogIndex()) {
                        return RvoteResult.fail();
                    }
                }

                node.setStatus(NodeStatus.FOLLOWER);
                node.setCurrentTerm(param.getTerm());
                node.setVotedFor(param.getCandidateId());
                return builder.setTerm(node.getCurrentTerm()).setVoteGranted(true).build();
            }

            return builder.setTerm(node.getCurrentTerm()).setVoteGranted(false).build();
        }finally {
            voteLock.unlock();
        }
    }

    @Override
    public AentryResult appendEntries(AentryParam Param) {
        return null;
    }
}
