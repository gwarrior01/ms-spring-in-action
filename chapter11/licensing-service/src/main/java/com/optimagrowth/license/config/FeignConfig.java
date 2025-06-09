package com.optimagrowth.license.config;

import feign.Logger;
import feign.Request;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class FeignConfig {

    @Bean
    public Logger logger() {
        return new FeignLogger();
    }

}

@Slf4j
class FeignLogger extends Logger {

    protected void logRequest(String configKey, Logger.Level logLevel, Request request) {
        super.logRequest(configKey, logLevel, request);
    }

    protected Response logAndRebufferResponse(String configKey, Logger.Level logLevel, Response response, long elapsedTime) throws IOException {
        return super.logAndRebufferResponse(configKey, logLevel, response, elapsedTime);
    }

    protected void log(String configKey, String format, Object... args) {
        log.info(String.format(methodTag(configKey) + format, args));
    }
}