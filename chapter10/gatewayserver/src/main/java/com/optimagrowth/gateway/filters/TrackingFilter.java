package com.optimagrowth.gateway.filters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

import java.util.UUID;

@Order(1)
@Component
@RequiredArgsConstructor
@Slf4j
public class TrackingFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var requestHeaders = exchange.getRequest().getHeaders();
        if (isCorrelationIdPresent(requestHeaders)) {
            log.debug("tmx-correlation-id found in tracking filter: {}", FilterUtils.getCorrelationId(requestHeaders));
        } else {
            var correlationID = generateCorrelationId();
            exchange = FilterUtils.setCorrelationId(exchange, correlationID);
            log.info("tmx-correlation-id generated in tracking filter: {}", correlationID);
        }

        return chain.filter(exchange);
    }


    private boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
        return FilterUtils.getCorrelationId(requestHeaders) != null;
    }

    private String generateCorrelationId() {
        return UUID.randomUUID().toString();
    }

}