package org.velog.api.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.velog.api.common.api.Api;
import org.velog.api.domain.user.business.UserBusiness;
import org.velog.api.domain.user.controller.model.UserLoginRequest;
import org.velog.api.domain.user.controller.model.UserRegisterRequest;
import org.velog.api.domain.user.controller.model.UserResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/users")
@Tag(name = "OpenController", description = "개방된 서비스 컨트롤롤러")
public class UserOpenApiController {

    private final UserBusiness userBusiness;

    @Operation(summary = "회원 등록 API", description = "ID, 패스워드, 이름, 이메일 입력")
    @PostMapping("/register")
    public Api<UserResponse> register(
            @Valid
            @RequestBody Api<UserRegisterRequest> request
    ){
        UserResponse response = userBusiness.register(request.getBody());
        return Api.OK(response);
    }

    @Operation(summary = "회원 로그인 API", description = "ID, 패스워드 입력")
    @PostMapping("/login")
    public Api<UserResponse> login(
            @Valid
            @RequestBody Api<UserLoginRequest> request,
            HttpServletRequest req
    ){
        UserResponse response = userBusiness.login(request.getBody(), req);
        return Api.OK(response);
    }


}
