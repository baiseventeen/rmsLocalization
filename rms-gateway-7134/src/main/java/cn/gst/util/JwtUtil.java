package cn.gst.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtUtil {

    private static String secretKey = "5467";

    public static Claims parseJWT(String jwt){
        return Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }
}
