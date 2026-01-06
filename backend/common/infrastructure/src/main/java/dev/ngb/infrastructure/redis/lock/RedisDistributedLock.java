package dev.ngb.infrastructure.redis.lock;

import dev.ngb.application.port.DistributedLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component("redisLock")
@RequiredArgsConstructor
@Slf4j
public class RedisDistributedLock implements DistributedLock {

    private final RedissonClient redissonClient;
    private final ThreadLocal<Map<String, RLock>> locksHolder = ThreadLocal.withInitial(ConcurrentHashMap::new);

    private static final String LOCK_PREFIX = "l:";

    private String buildLockKey(String key) {
        return LOCK_PREFIX + key;
    }

    @Override
    public boolean tryAcquire(String key, Duration wait, Duration lease) {
        String realKey = buildLockKey(key);
        RLock lock = redissonClient.getLock(realKey);

        try {
            boolean acquired = lock.tryLock(wait.toMillis(), lease.toMillis(), TimeUnit.MILLISECONDS);
            if (acquired) {
                locksHolder.get().put(realKey, lock);
            }
            return acquired;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Thread interrupted while trying to acquire lock for key: {}", key);
            return false;
        } catch (Exception e) {
            log.error("Error while trying to acquire lock for key: {}", key, e);
            return false;
        }
    }

    @Override
    public void unlock(String key) {
        String realKey = buildLockKey(key);
        Map<String, RLock> threadLocks = locksHolder.get();
        RLock lock = threadLocks.get(realKey);

        if (lock != null && lock.isHeldByCurrentThread()) {
            try {
                lock.unlock();
            } catch (IllegalMonitorStateException e) {
                log.warn("Attempted to unlock a lock not held by current thread for key: {}", key, e);
            } finally {
                threadLocks.remove(realKey);
                if (threadLocks.isEmpty()) {
                    locksHolder.remove();
                }
            }
        } else {
            log.warn("No lock held by current thread for key: {}", key);
        }
    }
}

