package org.velog.api.domain.tag.converter;

import org.velog.api.common.annotation.Converter;
import org.velog.api.domain.tag.controller.model.TagRegisterRequest;
import org.velog.api.domain.tag.controller.model.TagResponse;
import org.velog.db.blog.BlogEntity;
import org.velog.db.tag.TagEntity;

@Converter
public class TagConverter {

    public TagEntity toEntity(
            BlogEntity blogEntity,
            TagRegisterRequest tagRegisterRequest
    ){
        TagEntity tagEntity = TagEntity.builder()
                .title(tagRegisterRequest.getTitle())
                .build();
        tagEntity.addBlogEntity(blogEntity);

        return tagEntity;
    }

    public TagResponse toResponse(TagEntity tagEntity) {

        return TagResponse.builder()
                .blogId(tagEntity.getBlogEntity().getId())
                .title(tagEntity.getTitle())
                .count(tagEntity.getCount())
                .build();
    }
}
