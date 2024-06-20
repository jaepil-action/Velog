package org.velog.api.domain.role.business;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.velog.api.common.annotation.Business;
import org.velog.api.common.api.Api;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.role.controller.model.RoleDto;
import org.velog.api.domain.role.controller.model.UserRoleRegisterRequest;
import org.velog.api.domain.role.controller.model.UserRoleResponse;
import org.velog.api.domain.role.converter.UserRoleConverter;
import org.velog.api.domain.role.service.UserRoleService;
import org.velog.api.domain.user.converter.UserConverter;
import org.velog.api.domain.user.service.UserService;
import org.velog.db.role.RoleEntity;
import org.velog.db.role.UserRoleEntity;
import org.velog.db.user.UserEntity;

import java.util.Optional;

import static org.velog.api.domain.session.SessionService.LOGIN_ADMIN;

@Business
@RequiredArgsConstructor
public class UserRoleBusiness {

    private final UserRoleConverter converter;
    private final UserRoleService service;

    public RoleDto RoleRegister(RoleDto roleDto, HttpServletRequest request){

        checkRoleAdmin(request);

        return Optional.ofNullable(roleDto)
                .map(converter::toRoleEntity)
                .map(service::roleRegister)
                .map(converter::toRoleDto)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "RoleDto request Null"));
    }

    public UserRoleResponse UserRoleRegister(UserRoleRegisterRequest userRoleRegister, HttpServletRequest request){

        checkRoleAdmin(request);

        return Optional.ofNullable(userRoleRegister)
                .map(converter::toUserRoleEntity)
                .map(service::userRoleRegister)
                .map(converter::toUserRoleResponse)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserRoleRegisterRequest request Null"));
    }

    public UserEntity UserRoleDefaultRegister(UserEntity userEntity){

        return Optional.ofNullable(userEntity)
                .map(converter::toUserRoleDefaultEntity)
                .map(service::userRoleRegister)
                .map(converter::toUserEntity)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserRoleRegisterRequest request Null"));
    }

    private static void checkRoleAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session.getAttribute(LOGIN_ADMIN) == null){
            throw new ApiException(ErrorCode.BAD_REQUEST, "관리자 권한이 없습니다");
        }
    }
}
