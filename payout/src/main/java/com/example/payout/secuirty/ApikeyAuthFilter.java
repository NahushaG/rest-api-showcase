package com.example.payout.secuirty;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ApikeyAuthFilter implements Filter {
    private static final String API_KEY_HEADER = "X-API-KEY";
    private static final String VALID_API_KEY = "my-secret-key";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI();
        if (path.startsWith("/swagger-ui/")
                || path.startsWith("/v3/api-docs/")
                || path.startsWith("/swagger-resources")
                || path.startsWith("/swagger-resources/webjars/")) {
            chain.doFilter(request, response);
            return;
        }

        // Enforce API key on /api/*
        if (path.startsWith("/api/")) {
            String apiKey = httpRequest.getHeader(API_KEY_HEADER);
            if (!VALID_API_KEY.equals(apiKey)) {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                httpResponse.getWriter().write("Unauthorized - Invalid API key");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
