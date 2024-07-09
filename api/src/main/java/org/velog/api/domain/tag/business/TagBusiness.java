package org.velog.api.domain.tag.business;

import lombok.RequiredArgsConstructor;
import org.velog.api.common.annotation.Business;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.blog.business.BlogBusiness;
import org.velog.api.domain.tag.controller.model.TagRequest;
import org.velog.api.domain.tag.controller.model.TagResponse;
import org.velog.api.domain.tag.converter.TagConverter;
import org.velog.api.domain.tag.service.TagService;
import org.velog.api.domain.user.model.UserDto;
import org.velog.db.blog.BlogEntity;
import org.velog.db.tag.TagEntity;

import java.util.List;
import java.util.Optional;

@Business
@RequiredArgsConstructor
public class TagBusiness {

    private final TagService tagService;
    private final TagConverter tagConverter;
    private final BlogBusiness blogBusiness;

    public TagResponse register(
            UserDto userDto,
            TagRequest tagRequest
    ){
        BlogEntity blogEntity = blogBusiness.getBlogByIdWithThrow(userDto.getBlogId());
        TagEntity tagEntity = tagConverter.toEntity(blogEntity, tagRequest);

        return Optional.ofNullable(tagEntity)
                .map(tagService::register)
                .map(tagConverter::toResponse)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    public void edit(
            UserDto userDto,
            Long tagId,
            TagRequest tagRequest
    ){
        BlogEntity blogEntity = blogBusiness.getBlogByIdWithThrow(userDto.getBlogId());
        tagService.edit(blogEntity, tagId, tagRequest);
    }

    public void delete(
            UserDto userDto,
            Long tagId
    ){
        BlogEntity blogEntity = blogBusiness.getBlogByIdWithThrow(userDto.getBlogId());
        tagService.delete(blogEntity, tagId);
    }

    public List<TagResponse> retrieveAllTag(
            Long blogId
    ){
        BlogEntity blogEntity = blogBusiness.getBlogByIdWithThrow(blogId);
        List<TagEntity> tagEntityList = blogEntity.getTagEntityList();

        return tagEntityList.stream()
                .map(tagConverter::toResponse)
                .toList();
    }
}
