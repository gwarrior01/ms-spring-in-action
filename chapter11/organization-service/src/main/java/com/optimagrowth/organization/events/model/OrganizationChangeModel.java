package com.optimagrowth.organization.events.model;

import com.optimagrowth.organization.model.Organization;
import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor
public class OrganizationChangeModel {
	private String type;
	private String action;
	private String organizationId;
	private String correlationId;
	private String name;
	private String contactName;
	private String contactEmail;
	private String contactPhone;

	public OrganizationChangeModel(String type, String action, String correlationId, Organization organization) {
		this.type = type;
		this.action = action;
		this.correlationId = correlationId;
		this.name = organization.getName();
		this.contactName = organization.getContactName();
		this.contactEmail = organization.getContactEmail();
		this.contactPhone = organization.getContactPhone();
		this.organizationId = organization.getId();
	}
}
