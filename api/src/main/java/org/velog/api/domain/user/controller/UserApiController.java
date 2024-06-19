package org.velog.api.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.velog.api.common.api.Api;
import org.velog.api.domain.user.business.UserBusiness;
import org.velog.api.domain.user.controller.model.EmailDto;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "UserController", description = "사용자 서비스 컨트롤러")
public class UserApiController {

    private final UserBusiness userBusiness;

    @Operation(summary = "회원 Email 수정 API", description = "수정할 Email 입력")
    @PutMapping("{userid}")
    public Api<String> editUser(
            HttpSession session,
            @Valid
            @RequestBody Api<EmailDto> editEmail
    ){
        userBusiness.editUser(session, editEmail.getBody());
        return Api.OK("이메일 수정 성공");
    }

    @Operation(summary = "회원 탈퇴 API", description = "패스워드 입력")
    @DeleteMapping("{userId}")
    public Api<String> deleteUser(
            HttpSession session,
            @RequestParam("password") String password
    ){
        userBusiness.deleteUser(password, session);
        return Api.OK("회원 탈퇴 성공");
    }

    @Operation(summary = "로그아웃 API", description = "로그아웃")
    @PostMapping("/logout")
    public Api<String> logout(
            HttpSession session
    ){
        userBusiness.logout(session);
        return Api.OK("로그아웃 성공");
    }
}
