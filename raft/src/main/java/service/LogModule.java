package service;

import common.entiy.LogEntry;

public interface LogModule {
    void write(LogEntry logEntry);
    LogEntry read(long index);

    void removeOnStartIndex(long startIndex);

    LogEntry getLast();

    long getLastTerm();

    long getLastIndex();
}
