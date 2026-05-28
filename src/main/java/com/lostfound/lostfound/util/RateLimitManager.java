package com.lostfound.lostfound.util;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * RATE LIMITING EXPLAINED:
 * Rate limiting controls how many requests a client can make in a specific time period.
 * It prevents abuse, protects API from overload, and ensures fair usage.
 *
 * CONCEPT: Token Bucket Algorithm
 * - Imagine a bucket with tokens
 * - Each request consumes 1 token
 * - Tokens refill at a fixed rate (e.g., 100 tokens per minute)
 * - If bucket is empty, request is rejected
 *
 * EXAMPLE:
 * - 100 requests per minute limit
 * - After 60 requests in 30 seconds, user must wait before next request
 * - This prevents API from being hammered with requests
 *
 * WHY YOU NEED THIS:
 * 1. Prevents malicious attacks (DDoS protection)
 * 2. Ensures fair resource allocation among users
 * 3. Protects database from being overwhelmed
 * 4. Improves overall system stability
 */
@Component
public class RateLimitManager {

    // Thread-safe map to store one bucket per user/IP
    // Key: User identifier (IP address or user ID)
    // Value: Bucket (tracks tokens and refill rate)
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    /**
     * Creates or retrieves a rate limit bucket for a client
     * LIMIT: 100 requests per minute per client
     *
     * @param key Client identifier (IP address or user ID)
     * @return Bucket object for checking rate limit
     */

    public Bucket resolveBucket(String key) {
        return cache.computeIfAbsent(key, k -> createNewBucket());
    }

    /**
     * Creates a new token bucket with rate limit configuration
     *
     * TOKEN BUCKET CONFIG:
     * - Capacity: 100 tokens (can make max 100 requests at once)
     * - Refill: 100 tokens per 60 seconds (1 request per 0.6 seconds = ~100/min)
     *
     * @return Configured Bucket4j bucket
     */
    private Bucket createNewBucket() {
        // Define bandwidth: 100 tokens refilled every 60 seconds
        Bandwidth limit = Bandwidth.classic(
            100,                              // capacity: 100 tokens
            Refill.intervally(100, Duration.ofSeconds(60))  // refill 100 tokens every 60 seconds
        );

        return Bucket4j.builder()
            .addLimit(limit)
            .build();
    }

    /**
     * Checks if a request should be allowed based on rate limit
     * Returns true if tokens available, false if rate limit exceeded
     *
     * @param key Client identifier
     * @return true if request allowed, false if rate limit exceeded
     */
    public boolean allowRequest(String key) {
        Bucket bucket = resolveBucket(key);
        // Try to consume 1 token from bucket
        // Returns true if successful, false if no tokens available
        return bucket.tryConsume(1);
    }

    /**
     * Gets remaining tokens for a client (useful for response headers)
     * Example: X-RateLimit-Remaining: 45
     *
     * @param key Client identifier
     * @return Number of remaining tokens
     */
    public long getRemainingTokens(String key) {
        Bucket bucket = resolveBucket(key);
        return bucket.getAvailableTokens();
    }
}
