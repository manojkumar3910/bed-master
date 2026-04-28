package com.bedmaster.inventory.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI bedMasterOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("BedMaster Phase‑1 Backend APIs")
                        .description("Facility, Unit, Room, Bed Inventory and Flow APIs")
                        .version("1.0.0"));
    }
}
