package org.velog.api.domain.user.business;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.velog.api.common.annotation.Business;
import org.velog.api.common.api.Api;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.user.controller.model.UserLoginRequest;
import org.velog.api.domain.user.controller.model.UserRegisterRequest;
import org.velog.api.domain.user.controller.model.UserResponse;
import org.velog.api.domain.user.converter.UserConverter;
import org.velog.api.domain.user.service.UserService;
import org.velog.db.user.UserEntity;

import java.util.Optional;

@Business
@RequiredArgsConstructor
public class UserBusiness {

    private final UserService userService;
    private final UserConverter userConverter;
    //private final TokenBusiness tokenBusiness;


    public UserResponse register(UserRegisterRequest request){

        return Optional.ofNullable(request)
                .map(userConverter::toEntity)
                .map(userService::register)
                .map(userConverter::toResponse)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "request Null"));
    }

    public UserResponse login(UserLoginRequest loginRequest, HttpServletRequest req){
        UserEntity userEntity = userService.login(loginRequest.getLoginId(), loginRequest.getPassword());
        userService.createSession(req, userEntity);
        return userConverter.toResponse(userEntity);
    }

/*    public UserResponse me(User user){
        UserEntity userEntity = userService.getUserWithThrow(user.getId());
        return userConverter.toResponse(userEntity);
    }*/
}

