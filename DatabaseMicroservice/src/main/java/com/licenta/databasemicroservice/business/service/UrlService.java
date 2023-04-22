package com.licenta.databasemicroservice.business.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

@Service
public class UrlService {
    @Value("${scheme}")
    private String scheme;

    @Value("${server.port}")
    private int port;

    public String getBaseUrl() throws IOException {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("google.com", 80));
            return scheme + "://" + socket.getLocalAddress().getHostAddress() + ":" + port;
        }
    }
}
