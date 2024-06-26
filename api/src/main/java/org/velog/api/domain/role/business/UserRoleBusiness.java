package org.velog.api.domain.role.business;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.velog.api.common.annotation.Business;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.role.controller.model.RoleDto;
import org.velog.api.domain.role.controller.model.UserRoleRegisterRequest;
import org.velog.api.domain.role.controller.model.UserRoleResponse;
import org.velog.api.domain.role.converter.UserRoleConverter;
import org.velog.api.domain.role.service.UserRoleService;
import org.velog.api.domain.session.SessionService;
import org.velog.db.user.UserEntity;

import java.util.Optional;

@Business
@RequiredArgsConstructor
public class UserRoleBusiness {

    private final UserRoleConverter converter;
    private final UserRoleService service;
    private final SessionService sessionService;

    public RoleDto RoleRegister(RoleDto roleDto, HttpServletRequest request) {

        sessionService.validateRoleAdmin(request);

        return Optional.ofNullable(roleDto)
                .map(converter::toRoleEntity)
                .map(service::roleRegister)
                .map(converter::toRoleDto)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "RoleDto request Null"));
    }

    public UserRoleResponse UserRoleRegister(UserRoleRegisterRequest userRoleRegister, HttpServletRequest request) {

        sessionService.validateRoleAdmin(request);

        return Optional.ofNullable(userRoleRegister)
                .map(converter::toUserRoleEntity)
                .map(service::userRoleRegister)
                .map(converter::toUserRoleResponse)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserRoleRegisterRequest request Null"));
    }

    public UserEntity UserRoleDefaultRegister(UserEntity userEntity) {

        return Optional.ofNullable(userEntity)
                .map(converter::toUserRoleDefaultEntity)
                .map(service::userRoleRegister)
                .map(converter::toUserEntity)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserRoleRegisterRequest request Null"));
    }
}
