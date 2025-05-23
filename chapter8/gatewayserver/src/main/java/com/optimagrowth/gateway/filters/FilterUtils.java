package com.optimagrowth.gateway.filters;

import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

public class FilterUtils {

    public static final String CORRELATION_ID = "tmx-correlation-id";
    public static final String AUTH_TOKEN = "tmx-auth-token";
    public static final String USER_ID = "tmx-user-id";
    public static final String ORG_ID = "tmx-org-id";
    public static final String PRE_FILTER_TYPE = "pre";
    public static final String POST_FILTER_TYPE = "post";
    public static final String ROUTE_FILTER_TYPE = "route";

    public static String getCorrelationId(HttpHeaders requestHeaders) {
        var headers = requestHeaders.get(CORRELATION_ID);
        if (headers != null && !headers.isEmpty()) {
            return headers.getFirst();
        }
        return null;
    }

    public static ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
        return exchange.mutate().request(
                        exchange.getRequest().mutate()
                                .header(name, value)
                                .build())
                .build();
    }

    public static ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
        return setRequestHeader(exchange, CORRELATION_ID, correlationId);
    }

}
