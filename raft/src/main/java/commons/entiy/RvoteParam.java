package commons.entiy;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Data
public class RvoteParam {
    long term;
    String  candidateId;
    String serverId;
    long lastLogIndex;
    long lastLogTerm;
}
