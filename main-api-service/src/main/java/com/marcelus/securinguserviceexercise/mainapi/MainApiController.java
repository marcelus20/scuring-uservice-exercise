package com.marcelus.securinguserviceexercise.mainapi;

import com.marcelus.securinguserviceexercise.mainapi.models.Health;

import com.marcelus.securinguserviceexercise.security.service.client.SecurityApiClient;
import com.marcelus.securinguserviceexercise.security.service.client.models.ValidTokenAcknowledgement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;
import java.util.Optional;

@SpringBootApplication
@RestController
public class MainApiController {

    private static Logger logger = LoggerFactory.getLogger(MainApiController.class);

    @Autowired
    private SecurityApiClient securityApiClient;

    public static void main(String[] args) {
        // stub
        SpringApplication.run(MainApiController.class, args);
    }

    @GetMapping("/health")
    public ResponseEntity<?> health(@RequestHeader Map<String, String> headers){

        logger.info("Handling the health endpoint with headers.");

        ValidTokenAcknowledgement validTokenAcknowledgement = securityApiClient.validate(headers);

        logger.debug("validTokenAcknowledgement: {}", validTokenAcknowledgement);

        return Optional.ofNullable(validTokenAcknowledgement)
                .map(ValidTokenAcknowledgement::getValid)
                .map(valid->{
                    logger.debug("Token validity: {}", valid);

                    if(Boolean.TRUE.equals(valid)){
                        return ResponseEntity.ok(new Health());
                    }else{
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
                    }
                })
                .orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).body(null));
    }
}
