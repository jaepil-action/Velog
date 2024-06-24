package org.velog.api.domain.tag.business;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.velog.api.common.annotation.Business;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.blog.service.BlogService;
import org.velog.api.domain.session.SessionService;
import org.velog.api.domain.tag.controller.model.TagRegisterRequest;
import org.velog.api.domain.tag.controller.model.TagResponse;
import org.velog.api.domain.tag.converter.TagConverter;
import org.velog.api.domain.tag.service.TagService;
import org.velog.db.blog.BlogEntity;
import org.velog.db.tag.TagEntity;

import java.util.Optional;

@Business
@RequiredArgsConstructor
public class TagBusiness {

    private final TagService tagService;
    private final TagConverter tagConverter;
    private final SessionService sessionService;
    private final BlogService blogService;

    public TagResponse register(
            HttpServletRequest request,
            TagRegisterRequest tagRegisterRequest
    ){
        Long userId = sessionService.validateRoleUserId(request);
        BlogEntity blogEntity = blogService.getBlogByUserIdWithThrow(userId);
        TagEntity tagEntity = tagConverter.toEntity(blogEntity, tagRegisterRequest);

        return Optional.ofNullable(tagEntity)
                .map(tagService::register)
                .map(tagConverter::toResponse)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }
}
