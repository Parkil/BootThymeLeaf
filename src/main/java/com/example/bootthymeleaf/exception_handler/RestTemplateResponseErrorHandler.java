package com.example.bootthymeleaf.exception_handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Slf4j
@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return (response.getStatusCode().series() == CLIENT_ERROR
            || response.getStatusCode().series() == SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if(response.getStatusCode().series() == SERVER_ERROR) {
            log.error("[RestTemplate] Rest API returned server error, errorCode : {}, errorMessage : {}",
                    response.getStatusCode(), response.getStatusText());
        } else if(response.getStatusCode().series() == CLIENT_ERROR) {
            log.error("[RestTemplate] Rest API returned client error, errorCode : {}, errorMessage : {}",
                    response.getStatusCode(), response.getStatusText());
        }
    }
}
