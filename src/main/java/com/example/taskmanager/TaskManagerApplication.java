package com.example.taskmanager;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TaskManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskManagerApplication.class, args);
    }

    @Bean
    public OpenAPI myOpenAPI() {
        Info info = new Info()
                .title("Task manager api")
                .version("1.0")
                .description("This API provides endpoints for task manager application.");
        return new OpenAPI()
                .info(info);
    }
}
