package cn.gst.util;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    private static String secretKey = "5467";

    private static Long ttl = 3600000*12L;

    public static String createJWT(String id){
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setSubject("rda")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ttl))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compressWith(CompressionCodecs.DEFLATE);

        return builder.compact();
    }
}
