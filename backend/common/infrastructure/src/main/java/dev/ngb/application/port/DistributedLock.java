package dev.ngb.application.port;

import java.time.Duration;

public interface DistributedLock {
    boolean tryAcquire(String key, Duration wait, Duration lease);

    void unlock(String key);
}
