package com.lostfound.lostfound.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private final RateLimitManager rateLimitManager;

    public RateLimitInterceptor(RateLimitManager rateLimitManager) {
        this.rateLimitManager = rateLimitManager;
    }

    /**
     * Pre-processing: Check rate limit BEFORE request reaches controller
     * This method is called before each request
     *
     * @param request HTTP request object
     * @param response HTTP response object
     * @param handler Controller handler
     * @return true = continue to controller, false = stop here
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Get client identifier: IP address or user ID from headers
        // Using IP address: If client is behind proxy, use X-Forwarded-For header
        String clientId = getClientIdentifier(request);

        // Check if client has tokens available
        if (rateLimitManager.allowRequest(clientId)) {
            // Request allowed! Add rate limit info to response headers
            // Client can use these headers to adjust their request rate
            long remaining = rateLimitManager.getRemainingTokens(clientId);
            response.addHeader("X-RateLimit-Limit", "100");           // Max requests per minute
            response.addHeader("X-RateLimit-Remaining", String.valueOf(remaining));  // Requests left
            response.addHeader("X-RateLimit-Reset", String.valueOf(System.currentTimeMillis() / 1000 + 60)); // Reset time

            // Continue to controller
            return true;
        }

        // Rate limit exceeded!
        // Return 429 Too Many Requests error
        response.setStatus(429);  // HTTP 429 = Too Many Requests
        response.addHeader("X-RateLimit-Limit", "100");
        response.addHeader("X-RateLimit-Remaining", "0");
        response.addHeader("X-RateLimit-Reset", String.valueOf(System.currentTimeMillis() / 1000 + 60));

        // Write error message to response body
        response.getWriter().write("{\"error\": \"Rate limit exceeded. Maximum 100 requests per minute. Please try again later.\"}");
        response.setContentType("application/json");

        // Stop here, don't continue to controller
        return false;
    }

    /**
     * Extracts client identifier from request
     * Used to track rate limit per client
     *
     * PRIORITY:
     * 1. X-Forwarded-For header (if behind proxy)
     * 2. X-Real-IP header (alternative proxy header)
     * 3. RemoteAddr (direct client IP)
     *
     * @param request HTTP request
     * @return Client identifier
     */
    private String getClientIdentifier(HttpServletRequest request) {
        // Check for X-Forwarded-For header (common when behind load balancer)
        String forwardedFor = request.getHeader("X-Forwarded-For");
        if (forwardedFor != null && !forwardedFor.isEmpty()) {
            // X-Forwarded-For can contain multiple IPs, get the first one
            return forwardedFor.split(",")[0].trim();
        }

        // Check for X-Real-IP header (alternative)
        String realIp = request.getHeader("X-Real-IP");
        if (realIp != null && !realIp.isEmpty()) {
            return realIp;
        }

        // Fall back to direct client IP
        return request.getRemoteAddr();
    }
}
