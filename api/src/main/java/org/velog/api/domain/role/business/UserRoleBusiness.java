package org.velog.api.domain.role.business;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.velog.api.common.annotation.Business;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.post.business.PostBusiness;
import org.velog.api.domain.post.controller.model.PostsAdminPageResponse;
import org.velog.api.domain.post.converter.PostConverter;
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

    private final UserRoleService userRoleService;
    private final UserRoleConverter userRoleConverter;
    private final PostBusiness postBusiness;
    private final PostConverter postConverter;
    private final SessionService sessionService;

    // 관리자 권한으로 admin 등급 추가 가능
    public RoleDto RoleRegister(RoleDto roleDto, HttpServletRequest request) {

        sessionService.validateRoleAdmin(request);

        return Optional.ofNullable(roleDto)
                .map(userRoleConverter::toRoleEntity)
                .map(userRoleService::roleRegister)
                .map(userRoleConverter::toRoleDto)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "RoleDto request Null"));
    }

    // 관리자 권한으로 유저의 권한 변경
    public UserRoleResponse UserRoleRegister(UserRoleRegisterRequest userRoleRegister, HttpServletRequest request) {

        sessionService.validateRoleAdmin(request);

        return Optional.ofNullable(userRoleRegister)
                .map(userRoleConverter::toUserRoleEntity)
                .map(userRoleService::userRoleRegister)
                .map(userRoleConverter::toUserRoleResponse)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserRoleRegisterRequest request Null"));
    }


    // 일반 사용자는 자동으로 디폴트 권한
    public UserEntity UserRoleDefaultRegister(UserEntity userEntity) {

        return Optional.ofNullable(userEntity)
                .map(userRoleConverter::toUserRoleDefaultEntity)
                .map(userRoleService::userRoleRegister)
                .map(userRoleConverter::toUserEntity)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserRoleRegisterRequest request Null"));
    }

    public PostsAdminPageResponse findAllPostsPageByAdmin(
            HttpServletRequest request,
            int offset,
            int limit
    ) {
        sessionService.validateRoleAdmin(request);
        return postBusiness.getPostsByAdmin(offset, limit);
    }

    public void deletePostByAdmin(
            HttpServletRequest request,
            Long postId
    ){
        sessionService.validateRoleAdmin(request);
        userRoleService.deletePostByAdmin(postId);
    }
}
