package com.optimagrowth.license.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ActuatorHealthTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testHealthStatusAndComponents() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))

                .andExpect(jsonPath("$.components.diskSpace.status").value("UP"))
                .andExpect(jsonPath("$.components.diskSpace.details.total").exists())
                .andExpect(jsonPath("$.components.diskSpace.details.free").exists())
                .andExpect(jsonPath("$.components.diskSpace.details.threshold").exists())

                .andExpect(jsonPath("$.components.ping.status").value("UP"));
    }
}