package com.optimagrowth.license.service;

import com.optimagrowth.license.model.Organization;
import com.optimagrowth.license.service.client.OrganizationDiscoveryClient;
import com.optimagrowth.license.service.client.OrganizationFeignClient;
import com.optimagrowth.license.service.client.OrganizationRestTemplateClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrganizationService {

    private final OrganizationFeignClient organizationFeignClient;
    private final OrganizationRestTemplateClient organizationRestClient;
    private final OrganizationDiscoveryClient organizationDiscoveryClient;

    @Cacheable(value = "orgs", key = "#organizationId")
    public Organization retrieveOrganizationInfo(String organizationId, String clientType) {
        return switch (clientType) {
            case "feign" -> {
                log.info("I am using the feign client");
                yield organizationFeignClient.getOrganization(organizationId);
            }
            case "rest" -> {
                log.info("I am using the rest client");
                yield organizationRestClient.getOrganization(organizationId);
            }
            case "discovery" -> {
                log.info("I am using the discovery client");
                yield organizationDiscoveryClient.getOrganization(organizationId);
            }
            default -> organizationRestClient.getOrganization(organizationId);
        };
    }
	
}
