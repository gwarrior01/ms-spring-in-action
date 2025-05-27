package com.optimagrowth.organization.service;

import com.optimagrowth.organization.model.Organization;
import com.optimagrowth.organization.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository repository;

    @PreAuthorize("hasAuthority('SCOPE_read:organization')")
    public Organization findById(String organizationId) {
        return repository.findById(organizationId).orElse(null);
    }

    @PreAuthorize("hasAuthority('SCOPE_write:organization')")
    public Organization create(Organization organization) {
        organization.setId(UUID.randomUUID().toString());
        return repository.save(organization);
    }

    public void update(Organization organization) {
        repository.save(organization);
    }

    public void delete(Organization organization) {
        repository.deleteById(organization.getId());
    }
}