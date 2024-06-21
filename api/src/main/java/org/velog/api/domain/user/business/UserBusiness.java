package org.velog.api.domain.user.business;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.velog.api.common.annotation.Business;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.role.business.UserRoleBusiness;
import org.velog.api.domain.session.SessionService;
import org.velog.api.domain.user.controller.model.*;
import org.velog.api.domain.user.converter.UserConverter;
import org.velog.api.domain.user.service.UserService;
import org.velog.db.user.UserEntity;

import java.util.Optional;

import static org.velog.api.domain.session.SessionService.LOGIN_USER_ID;

@Business
@RequiredArgsConstructor
@Transactional
public class UserBusiness {

    private final UserService userService;
    private final UserConverter userConverter;
    private final SessionService sessionService;
    private final UserRoleBusiness userRoleBusiness;


    public UserResponse register(UserRegisterRequest registerParam){

        return Optional.ofNullable(registerParam)
                .map(userConverter::toEntity)
                .map(userService::register)
                .map(userRoleBusiness::UserRoleDefaultRegister)
                .map(userConverter::toResponse)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "request Null"));
    }

    @Transactional(readOnly = true)
    public UserResponse login(UserLoginRequest loginRequest, HttpServletRequest req){

        UserEntity userEntity = userService.login(loginRequest.getLoginId(), loginRequest.getPassword());

        if(userService.checkUserRole(userEntity.getId())){
            sessionService.createAdminSession(req, userEntity);
        }else{
            sessionService.createUserSession(req, userEntity);
        }

        return userConverter.toResponse(userEntity);
    }

    public void logout(HttpSession session){
        sessionService.deleteSession(session);
    }

    public void editUser(HttpSession session, EmailDto emailDto){
        Long userId = getUserId(session);
        UserEditRequest editRequest = userConverter.toEditRequest(userId, emailDto);
        userService.editEmail(editRequest);
    }

    public void deleteUser(String password, HttpSession session){

        Long userId = getUserId(session);
        userService.deleteUser(userId, password);
        sessionService.deleteSession(session);
    }

    private static Long getUserId(HttpSession session) {
        return (Long) session.getAttribute(LOGIN_USER_ID);
    }
}

