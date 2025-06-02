package com.optimagrowth.license.events.handler;

import com.optimagrowth.license.events.model.OrganizationChangeModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class OrganizationChangeHandler {

    @Bean
    public Consumer<OrganizationChangeModel> inboundOrgChanges() {
        return organization -> {
            log.info("Received a message of type {}", organization.getType());

            switch (organization.getAction()) {
                case "GET" -> log.info("Received a GET event for organization id {}", organization.getOrganizationId());
                case "SAVE" -> log.info("Received a SAVE event for organization id {}", organization.getOrganizationId());
                case "UPDATE" -> log.info("Received an UPDATE event for organization id {}", organization.getOrganizationId());
                case "DELETE" -> log.info("Received a DELETE event for organization id {}", organization.getOrganizationId());
                default -> log.error("Received an UNKNOWN event of type {}", organization.getType());
            }
        };
    }
}