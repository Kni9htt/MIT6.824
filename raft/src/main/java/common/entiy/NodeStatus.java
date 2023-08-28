<<<<<<< HEAD:raft/src/main/java/common/entiy/NodeStatus.java
package common.entiy;
=======
package commons.entiy;
>>>>>>> 2ce3f661f751c2e08c8130ea1af42c0a00f8f04f:raft/src/main/java/commons/entiy/NodeStatus.java

import lombok.Getter;

/**
 *  节点状态接口
 *  保存节点可用状态
 */
public interface NodeStatus {

    int FOLLOWER = 0;
    int CANDIDATE = 1;
    int LEADER = 2;

    @Getter
    enum Enum {
        FOLLOWER(0), CANDIDATE(1), LEADER(2);

        Enum(int code) {
            this.code = code;
        }

        final int code;

        public static Enum value(int i) {
            for (Enum value : Enum.values()) {
                if (value.code == i) {
                    return value;
                }
            }
            return null;
        }

    }

}