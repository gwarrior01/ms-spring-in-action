package com.optimagrowth.license.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.optimagrowth.license.model.License;
import com.optimagrowth.license.service.LicenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LicenseController.class)
class LicenseControllerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private LicenseService licenseService;
    private License license;

    @BeforeEach
    void setup() {
        license = License.builder()
                .licenseId("123")
                .organizationId("org1")
                .productName("Test Product")
                .build();
    }

    @Test
    void testGetLicense() throws Exception {
        when(licenseService.getLicense("123", "org1")).thenReturn(license);

        mockMvc.perform(get("/v1/organization/org1/license/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licenseId").value("123"))
                .andExpect(jsonPath("$.organizationId").value("org1"))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.createLicense.href").value("http://localhost/v1/organization/org1/license"))
                .andExpect(jsonPath("$._links.updateLicense.href").value("http://localhost/v1/organization/org1/license"))
                .andExpect(jsonPath("$._links.deleteLicense.href").value("http://localhost/v1/organization/org1/license/123"));
    }

    @Test
    void testCreateLicense() throws Exception {
        when(licenseService.createLicense(any(License.class), eq("org1"), any(Locale.class)))
                .thenReturn("License created");

        mockMvc.perform(post("/v1/organization/org1/license")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Accept-Language", "en")
                        .content(MAPPER.writeValueAsString(license)))
                .andExpect(status().isOk())
                .andExpect(content().string("License created"));
    }

    @Test
    void testCreateLicenseWithSpanishLocale() throws Exception {
        when(licenseService.createLicense(any(License.class), eq("org1"), any(Locale.class)))
                .thenReturn("Licencia creada");

        mockMvc.perform(post("/v1/organization/org1/license")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Accept-Language", "es")
                        .content(MAPPER.writeValueAsString(license)))
                .andExpect(status().isOk())
                .andExpect(content().string("Licencia creada"));
    }

    @Test
    void testUpdateLicense() throws Exception {
        when(licenseService.updateLicense(any(License.class), eq("org1")))
                .thenReturn("License updated");

        mockMvc.perform(put("/v1/organization/org1/license")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(license)))
                .andExpect(status().isOk())
                .andExpect(content().string("License updated"));
    }

    @Test
    void testDeleteLicense() throws Exception {
        when(licenseService.deleteLicense("123", "org1")).thenReturn("License deleted");

        mockMvc.perform(delete("/v1/organization/org1/license/123"))
                .andExpect(status().isOk())
                .andExpect(content().string("License deleted"));
    }
}