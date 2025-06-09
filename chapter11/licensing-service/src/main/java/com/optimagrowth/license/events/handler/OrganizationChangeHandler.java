package com.optimagrowth.license.events.handler;

import com.optimagrowth.license.events.model.OrganizationChangeModel;
import com.optimagrowth.license.model.Organization;
import com.optimagrowth.license.service.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class OrganizationChangeHandler {

    private final Cache cache;

    @Bean
    public Consumer<OrganizationChangeModel> inboundOrgChanges() {
        return org -> {
            log.info("Received a message of type {}", org.getType());

            switch (org.getAction()) {
                case "GET" -> log.info("Received a GET event for organization id {}", org.getOrganizationId());

                case "SAVE" -> {
                    cache.updateOrg(org.getOrganizationId(), mapOrganization(org));
                    log.info("Received a SAVE event for organization id {}", org.getOrganizationId());
                }
                case "UPDATE" -> {
                    cache.updateOrg(org.getOrganizationId(), mapOrganization(org));
                    log.info("Received an UPDATE event for organization id {}", org.getOrganizationId());
                }
                case "DELETE" -> {
                    cache.evictOrg(org.getOrganizationId());
                    log.info("Received a DELETE event for organization id {}", org.getOrganizationId());
                }
                default -> log.error("Received an UNKNOWN event of type {}", org.getType());
            }
        };
    }

    private Organization mapOrganization(OrganizationChangeModel org) {
        var event = new Organization();
        event.setId(org.getOrganizationId());
        event.setContactEmail(org.getContactEmail());
        event.setContactName(org.getContactName());
        event.setName(org.getName());
        event.setContactPhone(org.getContactPhone());
        return event;
    }
}