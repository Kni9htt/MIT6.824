<<<<<<< HEAD:raft/src/main/java/common/entiy/Peer.java
package common.entiy;
=======
package commons.entiy;
>>>>>>> 2ce3f661f751c2e08c8130ea1af42c0a00f8f04f:raft/src/main/java/commons/entiy/Peer.java

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Peer {
    private final String addr;
    public Peer(String addr) {
        this.addr = addr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Peer peer = (Peer)o;
        return Objects.equals(addr, peer.addr);
    }
    @Override
    public int hashCode() {
        return Objects.hash(addr);
    }

    @Override
    public String toString() {
        return "Peer{" +
                "addr='" + addr + '\'' +
                '}';
    }
}
