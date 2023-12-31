<<<<<<< HEAD:raft/src/main/java/common/entiy/PeerSet.java
package common.entiy;
=======
package commons.entiy;
>>>>>>> 2ce3f661f751c2e08c8130ea1af42c0a00f8f04f:raft/src/main/java/commons/entiy/PeerSet.java

import java.io.Serializable;
import java.util.*;

public class PeerSet implements Serializable {
    private List<Peer> peerList = new ArrayList<>();
    private volatile Peer leader;
    /** final */
    private volatile Peer self;

    private PeerSet() {}

    public static PeerSet getInstance() {
        return PeerSetLazyHolder.INSTANCE;
    }

    private static class PeerSetLazyHolder{
        private static final PeerSet INSTANCE = new PeerSet();
    }

    public void setSelf(Peer peer) {
        self = peer;
    }

    public Peer getSelf() {
        return self;
    }

    public void addPeer(Peer peer) {
        peerList.add(peer);
    }

    public void removePeer(Peer peer) {
        peerList.remove(peer);
    }

    public List<Peer> getPeers() {
        return peerList;
    }

    public List<Peer> getPeersWithOutSelf() {
        List<Peer> tempList = new ArrayList<>(peerList);
        tempList.remove(self);
        return tempList;
    }

    public Peer getLeader() {
        return leader;
    }

    public void setLeader(Peer peer) {
        leader = peer;
    }

    @Override
    public String toString() {
        return "PeerSet{" +
                "list=" + peerList +
                ", leader=" + leader +
                ", self=" + self +
                '}';
    }
}
