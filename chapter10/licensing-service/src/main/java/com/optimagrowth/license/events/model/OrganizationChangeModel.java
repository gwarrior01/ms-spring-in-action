package com.optimagrowth.license.events.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class OrganizationChangeModel {
    private String type;
    private String action;
    private String organizationId;
    private String correlationId;
    private String name;
    private String contactName;
    private String contactEmail;
    private String contactPhone;
}