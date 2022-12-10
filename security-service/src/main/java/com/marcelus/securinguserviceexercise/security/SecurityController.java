package com.marcelus.securinguserviceexercise.security;

import com.marcelus.securinguserviceexercise.security.models.AuthReq;
import com.marcelus.securinguserviceexercise.security.models.AuthResp;
import com.marcelus.securinguserviceexercise.security.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SecurityController {

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
    public ResponseEntity<AuthResp> createAuth(@RequestBody AuthReq authReq) throws Exception {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authReq.getUsername(), authReq.getPassword())
            );
            final UserDetails userDetails = inMemoryUserDetailsManager.loadUserByUsername(authReq.getUsername());

            final String jwt = jwtUtil.generateToken(userDetails);

            return ResponseEntity.ok(new AuthResp(jwt));
        }catch (Exception e){
            throw new Exception("invalid credentials");
        }
    }


}
