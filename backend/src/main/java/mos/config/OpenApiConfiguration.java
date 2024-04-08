package mos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    public static final Info MOS_BACKEND_API_INFO = new Info()
            .title("MOS Backend API")
            .description("MOS 프로젝트 백엔드 API 명세 문서입니다.")
            .version("v0.0.1");

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI().info(MOS_BACKEND_API_INFO);
    }
}
