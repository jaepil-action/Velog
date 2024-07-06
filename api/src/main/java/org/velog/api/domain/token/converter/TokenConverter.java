package org.velog.api.domain.token.converter;

import org.velog.api.common.annotation.Converter;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.token.controller.model.TokenResponse;
import org.velog.api.domain.token.model.TokenDto;

import java.util.Objects;
import java.util.Optional;

@Converter
public class TokenConverter {


    public TokenResponse toResponse(
            TokenDto accessToken,
            TokenDto refreshToke
    ){
        Objects.requireNonNull(accessToken, () -> {throw new ApiException(ErrorCode.NULL_POINT);});
        Objects.requireNonNull(refreshToke, () -> {throw new ApiException(ErrorCode.NULL_POINT);});


        return TokenResponse.builder()
                .accessToken(accessToken.getToken())
                .accessTokenExpiredAt(accessToken.getExpiredAt())
                .refreshToken(refreshToke.getToken())
                .refreshTokenExpiredAt(refreshToke.getExpiredAt())
                .build();
    }

}
