package com.marcelus.securinguserviceexercise.mainapi;

import com.marcelus.securinguserviceexercise.mainapi.models.Health;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@SpringBootApplication
@RestController
public class MainApiController {

    public static void main(String[] args) {
        // stub
        SpringApplication.run(MainApiController.class, args);
    }

    @GetMapping("/health")
    public ResponseEntity<?> health(@RequestHeader Map<String, String> headers){

        try {
            HttpResponse response = Request.Post("http://localhost:22222/validate")
                    .setHeader("Authorization", headers.get("authorization"))
                    .setHeader("Content-Type", "application/json")
                    .execute().returnResponse();

            if(response.getStatusLine().getStatusCode()< 300 && response.getStatusLine().getStatusCode() >= 200){
                return ResponseEntity.ok(new Health());
            }else{
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
