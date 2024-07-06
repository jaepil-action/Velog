package org.velog.api.domain.token.helper;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.velog.api.common.error.TokenErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.token.ifs.TokenHelperIfs;
import org.velog.api.domain.token.model.TokenDto;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenHelper implements TokenHelperIfs {

    @Value("${token.secret.key}")
    private String secretKey;

    @Value("${token.access-token.plus-hour}")
    private Long accessTokenPlusHour;

    @Value("${token.refresh-token.plus-hour}")
    private Long refreshTokenPlusHour;

    @Override
    public TokenDto issueAccessToken(Map<String, Object> data) {

        LocalDateTime expiredTime = LocalDateTime.now().plusHours(accessTokenPlusHour);
        Date expiredAt = Date.from(expiredTime.atZone(
                        ZoneId.systemDefault())
                        .toInstant());

        Algorithm algo = Algorithm.HMAC256(secretKey.getBytes(StandardCharsets.UTF_8));
        String jwtToken = JWT.create()
                .withPayload(data)
                .withExpiresAt(expiredAt)
                .sign(algo);

        return TokenDto.builder()
                .token(jwtToken)
                .expiredAt(expiredTime)
                .build();
    }

    @Override
    public TokenDto issueRefreshToken(Map<String, Object> data) {

        LocalDateTime refreshTime = LocalDateTime.now().plusHours(refreshTokenPlusHour);

        Date refreshAt = Date.from(refreshTime.atZone(
                ZoneId.systemDefault()).toInstant());

        Algorithm algo = Algorithm.HMAC256(secretKey.getBytes(StandardCharsets.UTF_8));

        String jwtToken = JWT.create()
                .withPayload(data)
                .withExpiresAt(refreshAt)
                .sign(algo);

        return TokenDto.builder()
                .token(jwtToken)
                .expiredAt(refreshTime)
                .build();
    }

    @Override
    public Map<String, Object> validationTokenWithThrow(String token) {

        Algorithm algo = Algorithm.HMAC256(secretKey.getBytes(StandardCharsets.UTF_8));

        JWTVerifier verifier = JWT.require(algo).build();

        try{
            DecodedJWT decodedJWT = verifier.verify(token);
            Map<String, Object> claims = new HashMap<>();
            decodedJWT.getClaims().forEach((k, v) ->
                    claims.put(k, v.as(Object.class)));
            return claims;
        }catch (Exception e){
            if(e instanceof JWTVerificationException){
                throw new ApiException(TokenErrorCode.INVALID_TOKEN, e);
            } else if(e instanceof TokenExpiredException){
                throw new ApiException(TokenErrorCode.EXPIRED_TOKEN, e);
            } else{
                throw new ApiException(TokenErrorCode.EXPIRED_TOKEN, e);
            }
        }
    }
}
