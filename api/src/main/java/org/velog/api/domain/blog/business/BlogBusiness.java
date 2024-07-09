package org.velog.api.domain.blog.business;

import lombok.RequiredArgsConstructor;
import org.velog.api.common.annotation.Business;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.blog.controller.model.BlogDetailResponse;
import org.velog.api.domain.blog.controller.model.BlogRegisterRequest;
import org.velog.api.domain.blog.controller.model.BlogResponse;
import org.velog.api.domain.blog.converter.BlogConverter;
import org.velog.api.domain.blog.service.BlogService;
import org.velog.api.domain.user.model.UserDto;
import org.velog.api.domain.user.service.UserService;
import org.velog.db.blog.BlogEntity;
import org.velog.db.user.UserEntity;

import java.util.Optional;

@Business
@RequiredArgsConstructor
public class BlogBusiness {

    private final BlogService blogService;
    private final BlogConverter blogConverter;
    private final UserService userService;

    public BlogResponse register(
            BlogRegisterRequest blogRegisterRequest,
            UserDto userDto
    ){
        UserEntity userEntity = userService.getUserWithThrow(userDto.getUserId());
        BlogEntity blogEntity = blogConverter.toEntity(blogRegisterRequest, userEntity);

        return Optional.ofNullable(blogEntity)
                .map(blogService::register)
                .map(blogConverter::toResponse)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    public BlogEntity getBlogByIdWithThrow(Long blogId){
        return blogService.getBlogByIdWithThrow(blogId);
    }

    public void delete(
            UserDto userDto
    ){
        blogService.deleteBlogByUserId(userDto.getUserId());
    }

    public BlogDetailResponse retrieveBlogByLoginId(String loginId) {

        BlogEntity blogEntity = blogService.getBlogByLoginId(loginId);

        return Optional.ofNullable(blogEntity)
                .map(blogConverter::toDetailResponse)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }
}