package com.licenta.databasemicroservice.business.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UrlService {
    @Value("${scheme}")
    private String scheme;

    @Value("${host}")
    private String host;

    @Value("${server.port}")
    private int port;

    public String getBaseUrl() {
        return scheme + "://" + host + ":" + port;
    }
}
