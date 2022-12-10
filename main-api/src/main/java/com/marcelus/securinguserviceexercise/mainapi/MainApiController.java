package com.marcelus.securinguserviceexercise.mainapi;

import com.marcelus.securinguserviceexercise.mainapi.models.Health;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MainApiController {

    public static void main(String[] args) {
        // stub
        SpringApplication.run(MainApiController.class, args);
    }

    @GetMapping("health")
    public Health health() {
        return new Health();
    }
}
