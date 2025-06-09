package com.optimagrowth.license.service;

import com.optimagrowth.license.model.Organization;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;

@Component
public class Cache {

    @CacheEvict(value = "orgs", key = "#organizationId")
    public void evictOrg(String organizationId) {
        // удаляет из кэша
    }

    @CachePut(value = "orgs", key = "#organizationId")
    public Organization updateOrg(String organizationId, Organization organization) {
        return organization;
    }
}