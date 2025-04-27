package com.optimagrowth.license.service;

import com.optimagrowth.license.model.License;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class LicenseServiceTest {

    private LicenseService licenseService;
    private MessageSource messageSource;

    @BeforeEach
    void setup() {
        messageSource = mock(MessageSource.class);
        licenseService = new LicenseService(messageSource);
    }

    @Test
    void getLicense_shouldReturnPopulatedLicense() {
        var license = licenseService.getLicense("L123", "ORG456");

        assertThat(license).isNotNull();
        assertThat(license.getLicenseId()).isEqualTo("L123");
        assertThat(license.getOrganizationId()).isEqualTo("ORG456");
        assertThat(license.getProductName()).isEqualTo("Ostock");
        assertThat(license.getLicenseType()).isEqualTo("full");
    }

    @Test
    void createLicense_shouldReturnFormattedMessage() {
        var license = License.builder().licenseId("L999").build();

        when(messageSource.getMessage(eq("license.create.message"), any(), eq(Locale.ENGLISH)))
                .thenReturn("Created license: %s");

        String response = licenseService.createLicense(license, "ORG1", Locale.ENGLISH);

        assertThat(response).startsWith("Created license:");
        verify(messageSource).getMessage(eq("license.create.message"), any(), eq(Locale.ENGLISH));
    }

    @Test
    void updateLicense_shouldReturnFormattedMessage() {
        var license = License.builder().licenseId("L888").build();

        when(messageSource.getMessage(eq("license.update.message"), any(), isNull()))
                .thenReturn("Updated license: %s");

        String response = licenseService.updateLicense(license, "ORG2");

        assertThat(response).startsWith("Updated license:");
        verify(messageSource).getMessage(eq("license.update.message"), any(), isNull());
    }

    @Test
    void deleteLicense_shouldReturnFormattedMessage() {
        when(messageSource.getMessage(eq("license.delete.message"), any(), isNull()))
                .thenReturn("Deleted license %s for org %s");

        String response = licenseService.deleteLicense("L777", "ORG3");

        assertThat(response).isEqualTo("Deleted license L777 for org ORG3");
        verify(messageSource).getMessage(eq("license.delete.message"), any(), isNull());
    }
}