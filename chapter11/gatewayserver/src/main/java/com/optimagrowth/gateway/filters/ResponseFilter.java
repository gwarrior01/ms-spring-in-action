package com.optimagrowth.gateway.filters;

import io.micrometer.tracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class ResponseFilter {

    @Bean
    public GlobalFilter postGlobalFilter(Tracer tracer) {
        return (exchange, chain) -> chain.filter(exchange).then(Mono.fromRunnable(() -> {
            var traceId = tracer.currentSpan().context().traceId();
            var spanId = tracer.currentSpan().context().spanId();
            log.debug("Adding the correlation id to the outbound headers. {}", traceId);
            exchange.getResponse().getHeaders().add("X-B3-TraceId", traceId);
            exchange.getResponse().getHeaders().add("X-B3-SpanId", spanId);
            log.info("Completing outgoing request for {}. correlation id = {}",
                    exchange.getRequest().getURI(),
                    traceId
            );
        }));
    }
}
