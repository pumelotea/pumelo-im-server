package io.pumelo.utils;

import io.jsonwebtoken.Claims;
import io.pumelo.utils.jwt.JwtUtils;

import java.time.Instant;
import java.util.Date;


public class IdealTokenUtils {


    public static String getAccessToken(String auth){
        String authType = auth.substring(0, 6).toLowerCase();
        if (authType.compareTo("bearer") == 0) {
            return auth.substring(7);
        }
        return null;
    }

    public static String getSubject(String stringKey,String auth){
        String accessToken = getAccessToken(auth);
        String subject = null;
        try {
            Claims claims = JwtUtils.parseJWT(stringKey,accessToken);
            subject = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subject;
    }

    public static String getJwtId(String stringKey,String auth){
        String accessToken = getAccessToken(auth);
        String jwtId = null;
        try {
            Claims claims = JwtUtils.parseJWT(stringKey,accessToken);
            jwtId = claims.getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jwtId;
    }

    public static boolean isExpired(String stringKey,String auth){
        String accessToken = getAccessToken(auth);
        try {
            Claims claims = JwtUtils.parseJWT(stringKey,accessToken);
            Date date = claims.getExpiration();
            return Instant.now().isAfter(date.toInstant());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}
