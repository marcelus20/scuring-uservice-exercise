package com.marcelus.securinguserviceexercise.mainapi.beans;

import com.marcelus.securinguserviceexercise.security.service.client.SecurityApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
public class MainApiBeans {

    @Bean
    public SecurityApiClient securityApiClient(){
        return new SecurityApiClient();
    }
}
