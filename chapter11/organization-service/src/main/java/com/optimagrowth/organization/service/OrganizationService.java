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
        var organization = repository.findById(organizationId).orElse(null);
        source.publishOrganizationChange("GET", organization);
        return organization;
    }

    public Organization create(Organization organization) {
        organization.setId(UUID.randomUUID().toString());
        var newOrganization = repository.save(organization);
        source.publishOrganizationChange("SAVE", newOrganization);
        return newOrganization;
    }

    public void update(Organization organization) {
        var saved = repository.save(organization);
        source.publishOrganizationChange("UPDATE", saved);
    }

    public void delete(Organization organization) {
        repository.deleteById(organization.getId());
        source.publishOrganizationChange("DELETE", organization);
    }
}