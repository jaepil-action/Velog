package org.velog.api.domain.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.tag.controller.model.TagRequest;
import org.velog.db.blog.BlogEntity;
import org.velog.db.tag.TagEntity;
import org.velog.db.tag.TagRepository;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TagService {

    private final TagRepository tagRepository;

    public TagEntity register(
            TagEntity tagEntity
    ){
        return Optional.ofNullable(tagEntity)
                .map(it ->{
                    it.setCountZero();
                    return tagRepository.save(it);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "Tag Entity Null"));
    }

    public void edit(
            BlogEntity blogEntity,
            Long tagId,
            TagRequest tagRequest
    ){
        TagEntity tagEntity = checkMyTag(blogEntity, tagId);
        tagEntity.changeTagTitle(tagRequest.getTitle());
    }

    public void delete(
            BlogEntity blogEntity,
            Long tagId
    ){
        checkMyTag(blogEntity, tagId);
        tagRepository.deleteById(tagId);
    }

    private TagEntity checkMyTag(BlogEntity blogEntity, Long tagId) {
        TagEntity tagEntity = getTagWithBlogAndThrow(tagId);
        if(!Objects.equals(tagEntity.getBlogEntity().getId(), blogEntity.getId())){
            throw new ApiException(ErrorCode.BAD_REQUEST, "본인의 태그가 아닙니다.");
        }
        return tagEntity;
    }

    public TagEntity getTagWithThrow(
            Long tagId
    ){
        return tagRepository.findById(tagId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND));
    }

    public TagEntity getTagWithBlogAndThrow(
            Long tagId
    ){
        return tagRepository.findTagWithBlogById(tagId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND));
    }
}
