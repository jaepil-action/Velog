package org.velog.api.domain.post.business;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.velog.api.common.annotation.Business;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.blog.service.BlogService;
import org.velog.api.domain.post.controller.model.PostRegisterRequest;
import org.velog.api.domain.post.controller.model.PostResponse;
import org.velog.api.domain.post.converter.PostConverter;
import org.velog.api.domain.post.service.PostService;
import org.velog.api.domain.session.SessionService;
import org.velog.db.blog.BlogEntity;
import org.velog.db.post.PostEntity;

import java.util.Optional;

@Business
@RequiredArgsConstructor
public class PostBusiness {

    private final PostService postService;
    private final PostConverter postConverter;
    private final SessionService sessionService;

    public PostResponse register(
            HttpServletRequest request,
            PostRegisterRequest postRegisterRequest
    ){
        Long userId = sessionService.validateRoleUserId(request);
        PostEntity postEntity = postService.register(userId, postRegisterRequest);

        return Optional.ofNullable(postEntity)
                .map(postConverter::toResponse)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }
}
