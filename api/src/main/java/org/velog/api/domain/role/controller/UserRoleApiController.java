package org.velog.api.domain.role.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.velog.api.common.api.Api;
import org.velog.api.domain.post.controller.model.PostsAdminPageResponse;
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

    @Operation(summary = "어드민 종류 등록 API", description = "ROLE_USER / ROLE_ADMIN 등록")
    @PostMapping("/role")
    public Api<RoleDto> registerAdmin(
        @Valid
        @RequestBody RoleDto roleRequest,
        HttpServletRequest request
    ){
        RoleDto roleDto = userRoleBusiness.roleRegister(roleRequest, request);
        return Api.OK(roleDto);
    }

    @Operation(summary = "어드민 권한 부여 API", description = "관리자만 권한 부여 가능")
    @PutMapping("/edit")
    public Api<UserRoleResponse> registerUserRole(
            @Valid
            @RequestBody Api<UserRoleRegisterRequest> userRoleRegister,
            HttpServletRequest request
    ){
        UserRoleResponse userRoleResponse = userRoleBusiness.userRoleRegister(userRoleRegister.getBody(), request);
        return Api.OK(userRoleResponse);
    }

    @Operation(summary = "관리자 권한 모든 Post 조회 API", description = "관리자만 조회 가능")
    @GetMapping("/postsPage")
    public ResponseEntity<Api<PostsAdminPageResponse>> findAllPostsPageByAdmin(
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        PostsAdminPageResponse postResponseList = userRoleBusiness.findAllPostsPageByAdmin(request, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK(postResponseList));
    }

    @Operation(summary = "관리자 권한 Post 삭제 API", description = "삭제할 post_id 입력")
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Api<String>> deletePostByAdmin(
            HttpServletRequest request,
            @Parameter(description = "관리자 권한으로 삭제 할 Post ID", required = true, example = "1")
            @PathVariable Long postId
    ){
        userRoleBusiness.deletePostByAdmin(request, postId);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK("글 삭제가 완료 되었습니다."));
    }
}
