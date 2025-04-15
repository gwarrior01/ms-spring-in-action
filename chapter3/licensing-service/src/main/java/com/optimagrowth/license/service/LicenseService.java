package com.optimagrowth.license.service;

import com.optimagrowth.license.model.License;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class LicenseService {

    private final MessageSource messages;

    public License getLicense(String licenseId, String organizationId) {
        return License.builder()
                .id(new Random().nextInt(1000))
                .licenseId(licenseId)
                .organizationId(organizationId)
                .description("Software product")
                .productName("Ostock")
                .licenseType("full")
                .build();
    }

    public String createLicense(License license, String organizationId, Locale locale) {
        if (license == null) {
            return null;
        }
        license.setOrganizationId(organizationId);
        return String.format(messages.getMessage("license.create.message", null, locale), license.toString());
    }

    public String updateLicense(License license, String organizationId) {
        if (license == null) {
            return null;
        }
        license.setOrganizationId(organizationId);
        return String.format(messages.getMessage("license.update.message", null, null), license);
    }

    public String deleteLicense(String licenseId, String organizationId) {
        return String.format(messages.getMessage("license.delete.message", null, null), licenseId, organizationId);
    }
}
