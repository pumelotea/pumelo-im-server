package io.pumelo.utils;

import io.jsonwebtoken.Claims;
import io.pumelo.utils.jwt.JwtUtils;


public class IdealTokenUtils {


    public static String getAceessToken(String auth){
        String authType = auth.substring(0, 6).toLowerCase();
        if (authType.compareTo("bearer") == 0) {
            return auth.substring(7, auth.length());
        }
        return null;
    }

    public static String getSubject(String stringKey,String auth){
        String aceessToken = getAceessToken(auth);
        String subject = null;
        try {
            Claims claims = JwtUtils.parseJWT(stringKey,aceessToken);
            subject = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subject;
    }

    public static String getJwtId(String stringKey,String auth){
        String aceessToken = getAceessToken(auth);
        String jwtId = null;
        try {
            Claims claims = JwtUtils.parseJWT(stringKey,aceessToken);
            jwtId = claims.getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jwtId;
    }

}
