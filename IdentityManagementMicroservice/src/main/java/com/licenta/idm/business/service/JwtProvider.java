package com.licenta.idm.business.service;

import com.licenta.idm.User;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class JwtProvider {

    private final JwtConsumer jwtConsumer;
    private final RsaJsonWebKey rsaJsonWebKey;

    private final List<String> blacklist;

    public JwtProvider() throws JoseException {
        rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
        jwtConsumer = new JwtConsumerBuilder()
                .setRequireExpirationTime()
                .setAllowedClockSkewInSeconds(30)
                .setRequireSubject()
                .setExpectedIssuer("Issuer")
                .setExpectedAudience("Audience")
                .setVerificationKey(rsaJsonWebKey.getKey())
                .setJwsAlgorithmConstraints(AlgorithmConstraints.ConstraintType.PERMIT, AlgorithmIdentifiers.RSA_USING_SHA256)
                .build();
        blacklist = new ArrayList<>();
    }

    public String generateJwt(User user) throws JoseException {
        rsaJsonWebKey.setKeyId("k1");

        JwtClaims claims = new JwtClaims();
        claims.setIssuer("Issuer");
        claims.setAudience("Audience");
        claims.setExpirationTimeMinutesInTheFuture(60);
        claims.setGeneratedJwtId();
        claims.setIssuedAtToNow();
        claims.setNotBeforeMinutesInThePast(2);
        claims.setSubject(String.valueOf(user.getId()));
        claims.setStringClaim("roleId", String.valueOf(user.getUserRole().getRole().getId()));
        claims.setStringClaim("companyId", String.valueOf(user.getUserRole().getCompanyId()));

        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setKey(rsaJsonWebKey.getPrivateKey());
        jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

        String jwt = jws.getCompactSerialization();

        return jwt;
    }

    public JwtClaims validate(String jwt) {
        if (blacklist.contains(jwt)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        try {
            return jwtConsumer.processToClaims(jwt);
        } catch (InvalidJwtException e) {
            if (e.hasExpired()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    public void invalidate(String jwt) {
        blacklist.add(jwt);
    }

    public boolean isAuthorized(String jwt, Long userId) throws MalformedClaimException {
        JwtClaims claims = validate(jwt);

        return hasAdminRights(claims) || isOwner(claims, userId);
    }

    public boolean hasAdminRights(String jwt) throws MalformedClaimException {
        JwtClaims claims = validate(jwt);

        return hasAdminRights(claims);
    }

    private boolean hasAdminRights(JwtClaims claims) throws MalformedClaimException {
        int adminRoleId = 4;
        int roleId = Integer.parseInt(claims.getStringClaimValue("roleId"));

        return roleId == adminRoleId;
    }

    private boolean isOwner(JwtClaims claims, Long userId) throws MalformedClaimException {
        return Objects.equals(claims.getSubject(), String.valueOf(userId));
    }

    @Scheduled(fixedRate = 600000)
    private void deleteExpiredTokens() {
        for(String token: blacklist) {
            try {
                jwtConsumer.processToClaims(token);
            } catch (InvalidJwtException e) {
                if (e.hasExpired()) {
                    blacklist.remove(token);
                }
            }
        }
    }

    public boolean hasManagerRights(String token) throws MalformedClaimException {
        JwtClaims claims = validate(token);

        return hasManagerRights(claims);
    }

    private boolean hasManagerRights(JwtClaims claims) throws MalformedClaimException {
        int managerRoleId = 3;
        int roleId = Integer.parseInt(claims.getStringClaimValue("roleId"));

        return roleId == managerRoleId;
    }
}
