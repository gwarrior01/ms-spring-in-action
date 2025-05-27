package com.optimagrowth.license.config;

import feign.Logger;
import feign.Request;
import feign.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.security.OAuth2AccessTokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class FeignConfig {

    @Bean
    public Logger logger() {
        return new FeignLogger();
    }

    @Bean
    public OAuth2AuthorizedClientManager oAuth2ClientManager(ClientRegistrationRepository clientRegistrationRepository,
                                                             OAuth2AuthorizedClientService oAuth2AuthorizedClientService) {
        var manager = new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                clientRegistrationRepository,
                oAuth2AuthorizedClientService
        );
        var provider = OAuth2AuthorizedClientProviderBuilder.builder()
                .clientCredentials()
                .build();
        manager.setAuthorizedClientProvider(provider);
        return manager;
    }

    @Bean
    public OAuth2AccessTokenInterceptor feignOauthInterceptor(OAuth2AuthorizedClientManager manager) {
        return new OAuth2AccessTokenInterceptor("license", manager);
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