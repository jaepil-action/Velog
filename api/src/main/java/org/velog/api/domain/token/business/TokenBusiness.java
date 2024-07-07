package org.velog.api.domain.token.business;

import lombok.RequiredArgsConstructor;
import org.velog.api.common.annotation.Business;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.token.controller.model.TokenResponse;
import org.velog.api.domain.token.converter.TokenConverter;
import org.velog.api.domain.token.model.TokenDto;
import org.velog.api.domain.token.service.TokenService;
import org.velog.db.user.UserEntity;

import java.util.Optional;

@Business
@RequiredArgsConstructor
public class TokenBusiness {

    private final TokenService tokenService;
    private final TokenConverter tokenConverter;

    public TokenResponse issueToken(UserEntity userEntity){

        return Optional.ofNullable(userEntity)
                .map(ue -> {
                    return userEntity.getId();
                })
                .map(id -> {
                    TokenDto accessToken = tokenService.issueAccessToken(id);
                    TokenDto refreshToken = tokenService.issueRefreshToken(id);
                    return tokenConverter.toResponse(accessToken, refreshToken);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    public Long validationToken(String accessToken){
        return tokenService.validationToken(accessToken);
    }
}
