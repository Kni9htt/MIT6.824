package common.entiy;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class LogEntry implements Serializable, Comparable{
    private long term;
    private long index;
    private Command command;

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            return -1;
        }
        if (this.getIndex() > ((LogEntry) o).getIndex()) {
            return 1;
        }
        return -1;
    }
}
