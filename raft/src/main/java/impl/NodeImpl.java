package impl;

import common.entiy.*;
import current.RaftThreadPool;
import lombok.Getter;
import lombok.Setter;
import service.Consensus;
import service.LogModule;
import service.Node;
import util.LogUtil;
import util.LongConvert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 节点实现类
 * 用于处理节点请求
 */
@Getter
@Setter
public class NodeImpl implements Node{
    /** 选举时间间隔基数 */
    public volatile long electionTime = 15 * 1000;
    /** 上一次选举时间 */
    public volatile long preElectionTime = 0;

    public PeerSet peerSet;

    public volatile int status = NodeStatus.FOLLOWER;
    volatile long currentTerm = 0;
    volatile String votedFor;
    LogModule logModule;

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


    /**
     * 1. 在转变成候选人后就立即开始选举过程
     * 自增当前的任期号（currentTerm）
     * 给自己投票
     * 重置选举超时计时器
     * 发送请求投票的 RPC 给其他所有服务器
     * 2. 如果接收到大多数服务器的选票，那么就变成领导人
     * 3. 如果接收到来自新的领导人的附加日志 RPC，转变成跟随者
     * 4. 如果选举过程超时，再次发起一轮选举
     */
    class ElectionTask implements Runnable{

        @Override
        public void run() {
            // 当前节点已经是LEADER
            if (status == NodeStatus.LEADER)
                return;

            // 到达过期时间退出线程
            long current = System.currentTimeMillis();
            electionTime = electionTime + ThreadLocalRandom.current().nextInt(50);
            if (current - preElectionTime > electionTime)
                return;
            status = NodeStatus.CANDIDATE;

            preElectionTime = System.currentTimeMillis() + ThreadLocalRandom.current().nextInt(200) + 150;

            // 周期++并投票给自己
            currentTerm += 1;
            votedFor = peerSet.getSelf().getAddr();

            // 获取别的服务器节点
            List<Peer> onterPeerList = peerSet.getPeersWithOutSelf();
            List<Future<RvoteResult>> futureList = new ArrayList<>();

            // 线程池：远程投票
            for (Peer peer : onterPeerList) {
                futureList.add(RaftThreadPool.submit(() -> {
                    // 获取最后一个logEntry
                    long lastTerm = 0L;
                    LogEntry last = logModule.getLast();
                    if (last != null) {
                        lastTerm = last.getTerm();
                    }

                    // 设置投票请求参数
                    RvoteParam param = RvoteParam.builder()
                            .term(currentTerm)
                            .candidateId(peerSet.getSelf().getAddr())
                            .lastLogTerm(lastTerm)
                            .lastLogIndex(LongConvert.convert(last.getIndex()))
                            .build();


                    return null;
                }));
            }
        }
    }
}
