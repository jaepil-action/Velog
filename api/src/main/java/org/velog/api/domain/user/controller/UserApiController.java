package org.velog.api.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.velog.api.common.annotation.UserSession;
import org.velog.api.common.api.Api;
import org.velog.api.domain.user.business.UserBusiness;
import org.velog.api.domain.user.controller.model.UserResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "UserController", description = "사용자 서비스 컨트롤러")
public class UserApiController {

/*    @Operation(summary = "회원 수정 API", description = "USER_ID 입력")
    @PutMapping("{id}")
    public Api<UserResponse> editUser(
        @PathVariable Long id,
        @RequestBody User
    )*/

}
