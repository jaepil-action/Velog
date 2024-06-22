package org.velog.api.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.velog.api.common.api.Api;
import org.velog.api.domain.user.business.UserBusiness;
import org.velog.api.domain.user.controller.model.DuplicationResponse;
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
    public ResponseEntity<Api<UserResponse>> register(
            @Valid
            @RequestBody Api<UserRegisterRequest> request
    ){
        UserResponse response = userBusiness.register(request.getBody());
        return ResponseEntity.status(HttpStatus.CREATED).body(Api.CREATED(response));
    }

    @Operation(summary = "회원 로그인 API", description = "ID, 패스워드 입력")
    @PostMapping("/login")
    public ResponseEntity<Api<UserResponse>> login(
            @Valid
            @RequestBody Api<UserLoginRequest> userLoginRequest,
            HttpServletRequest request
    ){
        UserResponse response = userBusiness.login(userLoginRequest.getBody(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Api.OK(response));
    }

    @Operation(summary = "회원 Email 중복여부 API", description = "Email 입력")
    @PostMapping("/check-email")
    public ResponseEntity<Api<DuplicationResponse>> checkDuplicationEmail(
            @Valid
            @RequestParam String email
    ){
        DuplicationResponse response = userBusiness.checkDuplicateEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK(response));
    }

    @Operation(summary = "회원 LoginId 중복여부 API", description = "LoginID 입력")
    @PostMapping("/check-loginId")
    public ResponseEntity<Api<DuplicationResponse>> checkDuplicationLoginId(
            @Valid
            @RequestParam String loginId
    ){
        DuplicationResponse response = userBusiness.checkDuplicateLoginId(loginId);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK(response));
    }

}
