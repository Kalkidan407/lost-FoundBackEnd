package com.lostfound.lostfound.config;

import com.lostfound.lostfound.util.RateLimitInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WEB MVC CONFIGURATION EXPLAINED:
 * This configuration registers the rate limit interceptor globally
 * It ensures rate limiting is applied to ALL API endpoints
 *
 * WHAT IT DOES:
 * 1. Registers RateLimitInterceptor to intercept all requests
 * 2. Excludes certain paths if needed (e.g., health checks, metrics)
 * 3. Applies rate limiting BEFORE requests reach controllers
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final RateLimitInterceptor rateLimitInterceptor;

    public WebConfig(RateLimitInterceptor rateLimitInterceptor) {
        this.rateLimitInterceptor = rateLimitInterceptor;
    }

    /**
     * Register interceptors to apply rate limiting globally
     *
     * @param registry Interceptor registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(rateLimitInterceptor)
            // Apply to all API paths
            .addPathPatterns("/api/**")
            // Optionally exclude health checks and metrics from rate limiting
            .excludePathPatterns(
                "/health",
                "/metrics",
                "/swagger-ui.html",
                "/v3/api-docs/**",
                "/swagger-ui/**"
            );
    }
}
