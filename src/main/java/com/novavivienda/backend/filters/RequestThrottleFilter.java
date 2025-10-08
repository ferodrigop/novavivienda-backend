package com.novavivienda.backend.filters;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.novavivienda.backend.exceptions.TooManyRequestsException;
import com.novavivienda.backend.utils.RequestUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Profile("test")
@Component
public class RequestThrottleFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;

    private int MAX_REQUESTS_PER_SECOND = 100; // can change to any value needed

    private final LoadingCache<String, Integer> requestCountsPerIpAddress;

    public RequestThrottleFilter(HandlerExceptionResolver handlerExceptionResolver) {
        super();
        this.handlerExceptionResolver = handlerExceptionResolver;
        requestCountsPerIpAddress = Caffeine.newBuilder().
                expireAfterWrite(1, TimeUnit.SECONDS).build(key -> 0);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String clientIpAddress = RequestUtils.getClientIP(request);
        if (isMaximumRequestsPerSecondExceeded(clientIpAddress)) {
            handlerExceptionResolver.resolveException(request, response, null, new TooManyRequestsException("Too many requests from this IP address"));
            return;
        }
        filterChain.doFilter(request, response);
    }

    private boolean isMaximumRequestsPerSecondExceeded(String clientIpAddress) {
        Integer requests = 0;
        requests = requestCountsPerIpAddress.get(clientIpAddress);
        if (requests != null) {
            if (requests > MAX_REQUESTS_PER_SECOND) {
                requestCountsPerIpAddress.asMap().remove(clientIpAddress);
                requestCountsPerIpAddress.put(clientIpAddress, requests);
                return true;
            }
        } else {
            requests = 0;
        }
        requests++;
        requestCountsPerIpAddress.put(clientIpAddress, requests);
        return false;
    }
}
