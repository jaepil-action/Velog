package org.velog.api.domain.tag.business;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.velog.api.common.annotation.Business;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.blog.service.BlogService;
import org.velog.api.domain.session.ifs.CookieServiceIfs;
import org.velog.api.domain.tag.controller.model.TagRequest;
import org.velog.api.domain.tag.controller.model.TagResponse;
import org.velog.api.domain.tag.converter.TagConverter;
import org.velog.api.domain.tag.service.TagService;
import org.velog.db.blog.BlogEntity;
import org.velog.db.tag.TagEntity;

import java.util.List;
import java.util.Optional;

@Business
@RequiredArgsConstructor
public class TagBusiness {

    private final TagService tagService;
    private final TagConverter tagConverter;
    private final CookieServiceIfs cookieService;
    private final BlogService blogService;

    public TagResponse register(
            HttpServletRequest request,
            TagRequest tagRequest
    ){
        BlogEntity blogEntity = getBlogByRequest(request);
        TagEntity tagEntity = tagConverter.toEntity(blogEntity, tagRequest);

        return Optional.ofNullable(tagEntity)
                .map(tagService::register)
                .map(tagConverter::toResponse)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    public void edit(
            HttpServletRequest request,
            Long tagId,
            TagRequest tagRequest
    ){
        BlogEntity blogEntity = getBlogByRequest(request);

        tagService.edit(blogEntity, tagId, tagRequest);
    }

    public void delete(
            HttpServletRequest request,
            Long tagId
    ){
        BlogEntity blogEntity = getBlogByRequest(request);

        tagService.delete(blogEntity, tagId);
    }

    private BlogEntity getBlogByRequest(HttpServletRequest request) {
        Long userId = cookieService.validateRoleUserGetId(request);
        return blogService.getBlogByUserIdWithThrow(userId);
    }
    public List<TagResponse> retrieveAllTag(
            Long blogId
    ){
        BlogEntity blogEntity = blogService.getBlogByIdWithThrow(blogId);
        List<TagEntity> tagEntityList = blogEntity.getTagEntityList();

        return tagEntityList.stream()
                .map(tagConverter::toResponse)
                .toList();
    }
}
