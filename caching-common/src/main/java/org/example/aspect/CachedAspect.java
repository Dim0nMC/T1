package org.example.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.annotation.Cached;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
@EnableScheduling
public class CachedAspect {

    private static class CacheEntry {
        Object value;
        LocalDateTime expiryTime;
        CacheEntry(Object value, LocalDateTime expiryTime) {
            this.value = value;
            this.expiryTime = expiryTime;
        }
    }

    private final Map<Object, CacheEntry> cache = new ConcurrentHashMap<>();

    @Value("${app.cache.default-ttl-seconds}")
    private long defaultTtlSeconds;

    @Around("@annotation(cachedAnnotation)")
    public Object cacheMethod(ProceedingJoinPoint joinPoint, Cached cachedAnnotation) throws Throwable {

        Object[] args = joinPoint.getArgs();
        Object key;

        if (args.length == 1) {
            key = args[0];
        } else {
            key = java.util.Arrays.hashCode(args);
        }

        CacheEntry cachedEntry = cache.get(key);
        if (cachedEntry != null && LocalDateTime.now().isBefore(cachedEntry.expiryTime)) {
            return cachedEntry.value;
        }

        Object result = joinPoint.proceed();
        cache.put(key, new CacheEntry(result, LocalDateTime.now().plus(defaultTtlSeconds, ChronoUnit.SECONDS)));

        return result;
    }

    @Scheduled(fixedRate = 30000)
    public void cleanupExpiredEntries() {
        LocalDateTime now = LocalDateTime.now();
        Iterator<Map.Entry<Object, CacheEntry>> iterator = cache.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Object, CacheEntry> entry = iterator.next();
            if (now.isAfter(entry.getValue().expiryTime)) {
                iterator.remove();
            }
        }
    }
}
