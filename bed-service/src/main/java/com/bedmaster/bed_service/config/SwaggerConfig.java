package com.bedmaster.bed_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI bedServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Bed Service API")
                        .description("""
                                Hospital Bed Management System

                                Handles:
                                - Bed Assignments: Assign patients to available beds and track occupancy
                                - Patient Transfers: Request, approve, and complete patient transfers between beds/units
                                - Patient Discharges: Discharge patients and free beds for new admissions
                                - Bed Status: Monitor real-time bed availability and occupancy
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("BedMaster Support")
                                .email("support@bedmaster.com"))
                        .license(new License()
                                .name("Apache 2.0")));
    }
}
