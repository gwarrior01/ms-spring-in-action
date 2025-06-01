package com.optimagrowth.organization.controller;

import com.optimagrowth.organization.utils.UserContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.optimagrowth.organization.model.Organization;
import com.optimagrowth.organization.service.OrganizationService;

@RestController
@RequestMapping(value = "v1/organization")
@RequiredArgsConstructor
@Slf4j
public class OrganizationController {

    private final OrganizationService service;

    @GetMapping(value = "/{organizationId}")
    public ResponseEntity<Organization> getOrganization(@PathVariable("organizationId") String organizationId) {
        log.info("OrganizationController Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
        return ResponseEntity.ok(service.findById(organizationId));
    }

    @PutMapping(value = "/{organizationId}")
    public void updateOrganization(@PathVariable("organizationId") String id, @RequestBody Organization organization) {
        service.update(organization);
    }

    @PostMapping
    public ResponseEntity<Organization> saveOrganization(@RequestBody Organization organization) {
        return ResponseEntity.ok(service.create(organization));
    }

    @DeleteMapping(value = "/{organizationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization(@PathVariable("id") String id, @RequestBody Organization organization) {
        service.delete(organization);
    }

}