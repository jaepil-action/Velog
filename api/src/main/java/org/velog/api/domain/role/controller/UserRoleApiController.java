package org.velog.api.domain.role.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.velog.api.common.api.Api;
import org.velog.api.domain.role.business.UserRoleBusiness;
import org.velog.api.domain.role.controller.model.RoleDto;
import org.velog.api.domain.role.controller.model.UserRoleRegisterRequest;
import org.velog.api.domain.role.controller.model.UserRoleResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins")
@Tag(name = "AdminController", description = "어드민 서비스 컨트롤롤러")
public class UserRoleApiController {

    private final UserRoleBusiness userRoleBusiness;

    @Operation(summary = "어드민 종류 등록 API",
            description = "ROLE_USER / ROLE_ADMIN 등록")
    @PostMapping("/role")
    public Api<RoleDto> registerAdmin(
        @Valid
        @RequestBody RoleDto roleRequest,
        HttpServletRequest request
    ){
        RoleDto roleDto = userRoleBusiness.RoleRegister(roleRequest, request);
        return Api.OK(roleDto);
    }

    @Operation(summary = "어드민 권한 부여 API",
            description = "관리자만 권한 부여 가능")
    @PutMapping("edit")
    public Api<UserRoleResponse> registerUserRole(
            @Valid
            @RequestBody Api<UserRoleRegisterRequest> userRoleRegister,
            HttpServletRequest request
    ){
        UserRoleResponse userRoleResponse = userRoleBusiness.UserRoleRegister(userRoleRegister.getBody(), request);
        return Api.OK(userRoleResponse);
    }
}
