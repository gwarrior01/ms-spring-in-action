package com.optimagrowth.configserver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("native")
class LicenseEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testDefault() throws Exception {
        mockMvc.perform(get("/licensing-service/default"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name").value("licensing-service"))
                .andExpect(jsonPath("$.profiles[0]").value("default"))
                .andExpect(jsonPath("$.propertySources[0].name").value("classpath:/config/licensing-service.properties"))
                .andExpect(jsonPath("$.propertySources[0].source['example.property']").value("I AM THE DEFAULT"))
                .andExpect(jsonPath("$.propertySources[0].source['spring.jpa.hibernate.ddl-auto']").value("none"))
                .andExpect(jsonPath("$.propertySources[0].source['spring.jpa.database']").value("POSTGRESQL"))
                .andExpect(jsonPath("$.propertySources[0].source['spring.datasource.platform']").value("postgres"))
                .andExpect(jsonPath("$.propertySources[0].source['spring.jpa.show-sql']").value("true"))
                .andExpect(jsonPath("$.propertySources[0].source['spring.jpa.hibernate.naming-strategy']").value("org.hibernate.cfg.ImprovedNamingStrategy"))
                .andExpect(jsonPath("$.propertySources[0].source['spring.jpa.properties.hibernate.dialect']").value("org.hibernate.dialect.PostgreSQLDialect"))
                .andExpect(jsonPath("$.propertySources[0].source['spring.database.driverClassName']").value("org.postgresql.Driver"))
                .andExpect(jsonPath("$.propertySources[0].source['spring.datasource.testWhileIdle']").value("true"))
                .andExpect(jsonPath("$.propertySources[0].source['spring.datasource.validationQuery']").value("SELECT 1"))
                .andExpect(jsonPath("$.propertySources[0].source['management.endpoints.web.exposure.include']").value("*"))
                .andExpect(jsonPath("$.propertySources[0].source['management.endpoints.enabled-by-default']").value("true"));
    }

//    @Test
//    void testDev() throws Exception {
//        mockMvc.perform(get("/licensing-service/dev"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json"))
//
//                // Проверка верхнего уровня
//                .andExpect(jsonPath("$.name").value("licensing-service"))
//                .andExpect(jsonPath("$.profiles[0]").value("dev"))
//                .andExpect(jsonPath("$.label").isEmpty())
//                .andExpect(jsonPath("$.version").isEmpty())
//                .andExpect(jsonPath("$.state").isEmpty())
//
//                // Проверка первого propertySource
//                .andExpect(jsonPath("$.propertySources[0].name").value("classpath:/config/licensing-service-dev.properties"))
//                .andExpect(jsonPath("$.propertySources[0].source['example.property']").value("I AM DEV"))
//                .andExpect(jsonPath("$.propertySources[0].source['spring.datasource.url']").value("jdbc:postgresql://localhost:5432/tech"))
//                .andExpect(jsonPath("$.propertySources[0].source['spring.datasource.username']").value("postgres"))
//                .andExpect(jsonPath("$.propertySources[0].source['spring.datasource.password']").value("postgres"))
//
//                // Проверка второго propertySource
//                .andExpect(jsonPath("$.propertySources[1].name").value("classpath:/config/licensing-service.properties"))
//                .andExpect(jsonPath("$.propertySources[1].source['example.property']").value("I AM THE DEFAULT"))
//                .andExpect(jsonPath("$.propertySources[1].source['spring.jpa.hibernate.ddl-auto']").value("none"))
//                .andExpect(jsonPath("$.propertySources[1].source['spring.jpa.database']").value("POSTGRESQL"))
//                .andExpect(jsonPath("$.propertySources[1].source['spring.datasource.platform']").value("postgres"))
//                .andExpect(jsonPath("$.propertySources[1].source['spring.jpa.show-sql']").value("true"))
//                .andExpect(jsonPath("$.propertySources[1].source['spring.jpa.hibernate.naming-strategy']").value("org.hibernate.cfg.ImprovedNamingStrategy"))
//                .andExpect(jsonPath("$.propertySources[1].source['spring.jpa.properties.hibernate.dialect']").value("org.hibernate.dialect.PostgreSQLDialect"))
//                .andExpect(jsonPath("$.propertySources[1].source['spring.database.driverClassName']").value("org.postgresql.Driver"))
//                .andExpect(jsonPath("$.propertySources[1].source['spring.datasource.testWhileIdle']").value("true"))
//                .andExpect(jsonPath("$.propertySources[1].source['spring.datasource.validationQuery']").value("SELECT 1"))
//                .andExpect(jsonPath("$.propertySources[1].source['management.endpoints.web.exposure.include']").value("*"))
//                .andExpect(jsonPath("$.propertySources[1].source['management.endpoints.enabled-by-default']").value("true"));
//    }

    @Test
    void testProd() throws Exception {
        mockMvc.perform(get("/licensing-service/prod"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))

                // Проверка основных полей
                .andExpect(jsonPath("$.name").value("licensing-service"))
                .andExpect(jsonPath("$.profiles[0]").value("prod"))
                .andExpect(jsonPath("$.label").isEmpty())
                .andExpect(jsonPath("$.version").isEmpty())
                .andExpect(jsonPath("$.state").isEmpty())

                // Проверка первого propertySource (PROD)
                .andExpect(jsonPath("$.propertySources[0].name").value("classpath:/config/licensing-service-prod.properties"))
                .andExpect(jsonPath("$.propertySources[0].source['example.property']").value("I AM PROD"))
                .andExpect(jsonPath("$.propertySources[0].source['spring.datasource.url']").value("jdbc:postgresql://localhost:5432/tech"))
                .andExpect(jsonPath("$.propertySources[0].source['spring.datasource.username']").value("postgres"))
                .andExpect(jsonPath("$.propertySources[0].source['spring.datasource.password']").value("postgres"))

                // Проверка второго propertySource (DEFAULT)
                .andExpect(jsonPath("$.propertySources[1].name").value("classpath:/config/licensing-service.properties"))
                .andExpect(jsonPath("$.propertySources[1].source['example.property']").value("I AM THE DEFAULT"))
                .andExpect(jsonPath("$.propertySources[1].source['spring.jpa.hibernate.ddl-auto']").value("none"))
                .andExpect(jsonPath("$.propertySources[1].source['spring.jpa.database']").value("POSTGRESQL"))
                .andExpect(jsonPath("$.propertySources[1].source['spring.datasource.platform']").value("postgres"))
                .andExpect(jsonPath("$.propertySources[1].source['spring.jpa.show-sql']").value("true"))
                .andExpect(jsonPath("$.propertySources[1].source['spring.jpa.hibernate.naming-strategy']").value("org.hibernate.cfg.ImprovedNamingStrategy"))
                .andExpect(jsonPath("$.propertySources[1].source['spring.jpa.properties.hibernate.dialect']").value("org.hibernate.dialect.PostgreSQLDialect"))
                .andExpect(jsonPath("$.propertySources[1].source['spring.database.driverClassName']").value("org.postgresql.Driver"))
                .andExpect(jsonPath("$.propertySources[1].source['spring.datasource.testWhileIdle']").value("true"))
                .andExpect(jsonPath("$.propertySources[1].source['spring.datasource.validationQuery']").value("SELECT 1"))
                .andExpect(jsonPath("$.propertySources[1].source['management.endpoints.web.exposure.include']").value("*"))
                .andExpect(jsonPath("$.propertySources[1].source['management.endpoints.enabled-by-default']").value("true"));
    }

    @Test
    void testNewService() throws Exception {
        mockMvc.perform(get("/new-service/default"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))

                // Проверка основных полей
                .andExpect(jsonPath("$.name").value("new-service"))
                .andExpect(jsonPath("$.profiles[0]").value("default"))
                .andExpect(jsonPath("$.label").isEmpty())
                .andExpect(jsonPath("$.version").isEmpty())
                .andExpect(jsonPath("$.state").isEmpty())

                // Проверка, что propertySources — пустой массив
                .andExpect(jsonPath("$.propertySources").isArray())
                .andExpect(jsonPath("$.propertySources").isEmpty());
    }

    @Test
    void testNoStand() throws Exception {
        mockMvc.perform(get("/licensing-service"))
                .andExpect(status().isNotFound());
    }
}