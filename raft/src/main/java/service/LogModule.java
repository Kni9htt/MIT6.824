package service;

import commons.entiy.LogEntry;

public interface LogModule {
    void write(LogEntry logEntry);
    LogEntry read(long index);

    void removeOnStartIndex(long startIndex);

    LogEntry getLast();

    long getLastIndex();
}
