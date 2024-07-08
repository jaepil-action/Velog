package org.velog.api.domain.user.business;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.velog.api.common.annotation.Business;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.role.business.UserRoleBusiness;
import org.velog.api.domain.session.ifs.AuthorizationServiceIfs;
import org.velog.api.domain.user.controller.model.*;
import org.velog.api.domain.user.converter.UserConverter;
import org.velog.api.domain.user.service.UserService;
import org.velog.db.user.UserEntity;

import java.util.Optional;

@Business
@RequiredArgsConstructor
public class UserBusiness {

    private final UserService userService;
    private final UserConverter userConverter;
    private final AuthorizationServiceIfs authorizationService;
    private final UserRoleBusiness userRoleBusiness;


    public UserResponse register(UserRegisterRequest registerParam){

        return Optional.ofNullable(registerParam)
                .map(userConverter::toEntity)
                .map(userService::register)
                .map(userRoleBusiness::UserRoleDefaultRegister)
                .map(userConverter::toResponse)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "request Null"));
    }

    public UserResponse login(UserLoginRequest loginRequest, HttpServletRequest req, HttpServletResponse rep){

        UserEntity userEntity = userService.getUserWithThrow(loginRequest.getLoginId(), loginRequest.getPassword());

        if(userService.checkUserRole(userEntity.getId())){
            authorizationService.createAdminCookie(req, rep, userEntity);
        }else{
            authorizationService.createUserCookie(req, rep, userEntity);
        }

        return userConverter.toResponse(userEntity);
    }

    public void logout(
            HttpServletRequest request,
            HttpServletResponse response
    ){
        authorizationService.expiredCookie(request, response);
    }

    public void editUser(
            HttpServletRequest request,
            EmailDto emailDto
    ){
        Long userId = authorizationService.validateRoleUserGetId(request);
        UserEditRequest editRequest = userConverter.toEditRequest(userId, emailDto);
        userService.editEmail(editRequest);
    }

    public void deleteUser(
            String password,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        Long userId = authorizationService.validateRoleUserGetId(request);
        userService.deleteUser(userId, password);
        authorizationService.expiredCookie(request, response);
    }

    public DuplicationResponse checkDuplicateEmail(String email){
        // 중복이라면
        if(userService.checkDuplicationEmail(email)){
            return new DuplicationResponse("중복");
        }else{
            return new DuplicationResponse("중복X");
        }
    }

    public DuplicationResponse checkDuplicateLoginId(String loginId){
        // 중복이라면
        if(userService.checkDuplicationLoginId(loginId)){
            return new DuplicationResponse("중복");
        }else{
            return new DuplicationResponse("중복X");
        }
    }
}

