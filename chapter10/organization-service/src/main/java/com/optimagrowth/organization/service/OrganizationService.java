package com.optimagrowth.organization.service;

import com.optimagrowth.organization.events.source.Source;
import com.optimagrowth.organization.model.Organization;
import com.optimagrowth.organization.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository repository;
    private final Source source;

    public Organization findById(String organizationId) {
        var organization = repository.findById(organizationId);
        source.publishOrganizationChange("GET", organizationId);
        return organization.orElse(null);
    }

    public Organization create(Organization organization) {
        organization.setId(UUID.randomUUID().toString());
        var newOrganization = repository.save(organization);
        source.publishOrganizationChange("SAVE", newOrganization.getId());
        return newOrganization;
    }

    public void update(Organization organization) {
        repository.save(organization);
        source.publishOrganizationChange("UPDATE", organization.getId());
    }

    public void delete(Organization organization) {
        repository.deleteById(organization.getId());
        source.publishOrganizationChange("DELETE", organization.getId());
    }
}