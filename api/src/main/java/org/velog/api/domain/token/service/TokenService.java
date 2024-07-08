package org.velog.api.domain.token.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.token.ifs.TokenHelperIfs;
import org.velog.api.domain.token.model.TokenDto;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TokenService {
    public static final String USER_ID = "userId";

    private final TokenHelperIfs tokenHelperIfs;

    public TokenDto issueAccessToken(Long userId){
        HashMap<String, Object> data = new HashMap<>();
        data.put(USER_ID, userId);
        return tokenHelperIfs.issueAccessToken(data);
    }

    public TokenDto issueRefreshToken(Long userId){
        HashMap<String, Object> data = new HashMap<>();
        data.put(USER_ID, userId);
        return tokenHelperIfs.issueRefreshToken(data);
    }

    public Long validationToken(String token){
        Map<String, Object> map = tokenHelperIfs.validationTokenWithThrow(token);
        Object userId = map.get(USER_ID);

        Objects.requireNonNull(userId, () -> {throw new ApiException(ErrorCode.NULL_POINT);});

        return Long.parseLong(userId.toString());
    }
}
