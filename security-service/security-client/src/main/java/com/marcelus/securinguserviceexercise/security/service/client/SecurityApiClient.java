package com.marcelus.securinguserviceexercise.security.service.client;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcelus.securinguserviceexercise.security.service.client.models.ValidTokenAcknowledgement;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class SecurityApiClient {

    private static Logger logger = LoggerFactory.getLogger(SecurityApiClient.class);

    public ValidTokenAcknowledgement validate(Map<String, String> headers){
        try {
            HttpResponse httpResponse = Request.Post("http://localhost:22222/validate")
                    .setHeader("Authorization", headers.get("authorization"))
                    .setHeader("Content-Type", "application/json")
                    .execute().returnResponse();

            final int statusCode = httpResponse.getStatusLine().getStatusCode();
            final String contentAsString = EntityUtils.toString(httpResponse.getEntity());

            logger.info("Response successfully generated with status code {} and content {}", statusCode, contentAsString);

            if(statusCode < 300 && statusCode >= 200){
                return new ObjectMapper().readValue(contentAsString, ValidTokenAcknowledgement.class);
            }else{
                logger.info("Returning null validTokenAcknowledgement due to status code {}",statusCode);
                return null;
            }
        } catch (IOException e) {
            logger.error("Error while executing the SecurityApiClient.validate method. Error content: {}", e);
            return null;
        }
    }
}
