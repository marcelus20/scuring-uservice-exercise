package com.marcelus.securinguserviceexercise.security.service;

import com.marcelus.securinguserviceexercise.security.service.client.models.AuthReq;
import com.marcelus.securinguserviceexercise.security.service.client.models.AuthResp;
import com.marcelus.securinguserviceexercise.security.service.client.models.AuthToken;
import com.marcelus.securinguserviceexercise.security.service.client.models.ValidTokenAcknowledgement;
import com.marcelus.securinguserviceexercise.security.service.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SpringBootApplication
@RestController
public class SecurityController {

    private static Logger logger = LoggerFactory.getLogger(SecurityController.class);

    public static final String SERVICE_NAME = "security-service";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public static void main(String[] args) {
        SpringApplication.run(SecurityController.class, args);
    }

    @PostMapping(value="/auth", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResp> createAuth(@RequestBody AuthReq authReq) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authReq.getUsername(), authReq.getPassword())
            );
            final UserDetails userDetails = inMemoryUserDetailsManager.loadUserByUsername(authReq.getUsername());

            final String jwt = jwtUtil.generateToken(userDetails);

            logger.info("Successful authentication.");
            return ResponseEntity.ok(new AuthResp(jwt));
        }catch (Exception e){
            logger.error("Error while creating the authentication. Error content: {}", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping(value="/validate")
    public ResponseEntity<ValidTokenAcknowledgement> validate(@RequestHeader Map<String, String> headers){
        // If Request gets here, it's because the token is valid, so send the acknowledgement!
        logger.info("Successful validation of token. Returning the validTokenAcknowledgement instance.");
        return ResponseEntity.ok(new ValidTokenAcknowledgement(new AuthToken(headers.get("authorization"))));
    }


}
