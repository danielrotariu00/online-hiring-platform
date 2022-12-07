package com.licenta.jobapplicationmicroservice.business.util.errorhandler;

import com.licenta.jobapplicationmicroservice.business.util.exception.NotFoundException;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {

        return (
                httpResponse.getStatusCode().series() == CLIENT_ERROR ||
                httpResponse.getStatusCode().series() == SERVER_ERROR
        );
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse)
            throws IOException {

        if (httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
            throw new RuntimeException();
        } else if (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
            if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                String exceptionMessage = IOUtils.toString(httpResponse.getBody(), StandardCharsets.UTF_8);

                throw new NotFoundException(exceptionMessage);
            }
        }
    }
}
