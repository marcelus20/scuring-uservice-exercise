package com.marcelus.securinguserviceexercise.security.service.client;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcelus.securinguserviceexercise.security.service.client.models.ValidTokenAcknowledgement;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

public class SecurityApiClient {

    public ValidTokenAcknowledgement validate(Map<String, String> headers){
        try {
            HttpResponse httpResponse = Request.Post("http://localhost:22222/validate")
                    .setHeader("Authorization", headers.get("authorization"))
                    .setHeader("Content-Type", "application/json")
                    .execute().returnResponse();

            final int statusCode = httpResponse.getStatusLine().getStatusCode();
            final String contentAsString = EntityUtils.toString(httpResponse.getEntity());

            if(statusCode < 300 && statusCode >= 200){
                return new ObjectMapper().readValue(contentAsString, ValidTokenAcknowledgement.class);
            }else{
                return null;
            }
        } catch (IOException e) {
            return null;
        }
    }
}
