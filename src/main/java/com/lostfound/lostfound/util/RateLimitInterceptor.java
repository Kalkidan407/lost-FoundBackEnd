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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String clientId = getClientIdentifier(request);
        if (rateLimitManager.allowRequest(clientId)) {
          
            long remaining = rateLimitManager.getRemainingTokens(clientId);
            response.addHeader("X-RateLimit-Limit", "100");         
            response.addHeader("X-RateLimit-Remaining", String.valueOf(remaining));  
            response.addHeader("X-RateLimit-Reset", String.valueOf(System.currentTimeMillis() / 1000 + 60)); 
            return true;
        }
        response.setStatus(429);  
        response.addHeader("X-RateLimit-Limit", "100");
        response.addHeader("X-RateLimit-Remaining", "0");
        response.addHeader("X-RateLimit-Reset", String.valueOf(System.currentTimeMillis() / 1000 + 60));

        response.getWriter().write("{\"error\": \"Rate limit exceeded. Maximum 100 requests per minute. Please try again later.\"}");
        response.setContentType("application/json");
        return false;
    }

  
    private String getClientIdentifier(HttpServletRequest request) {
        
        String forwardedFor = request.getHeader("X-Forwarded-For");
        if (forwardedFor != null && !forwardedFor.isEmpty()) {
            return forwardedFor.split(",")[0].trim();
        }

        String realIp = request.getHeader("X-Real-IP");
        if (realIp != null && !realIp.isEmpty()) {
            return realIp;
        }

        return request.getRemoteAddr();
    }
}
