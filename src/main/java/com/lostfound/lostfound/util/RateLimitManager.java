package com.lostfound.lostfound.util;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class RateLimitManager {
 
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();
    public Bucket resolveBucket(String key) {
        return cache.computeIfAbsent(key, k -> createNewBucket());
    }

 
    private Bucket createNewBucket() {
        Bandwidth limit = Bandwidth.classic(
            100,                             
            Refill.intervally(100, Duration.ofSeconds(60))  
        );

        return Bucket4j.builder()
            .addLimit(limit)
            .build();
    }


    public boolean allowRequest(String key) {
        Bucket bucket = resolveBucket(key);
        return bucket.tryConsume(1);
    }

    
    public long getRemainingTokens(String key) {
        Bucket bucket = resolveBucket(key);
        return bucket.getAvailableTokens();
    }
}
