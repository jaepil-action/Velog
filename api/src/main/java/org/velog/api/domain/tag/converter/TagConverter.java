package org.velog.api.domain.tag.converter;

import org.velog.api.common.annotation.Converter;
import org.velog.api.domain.tag.controller.model.TagRequest;
import org.velog.api.domain.tag.controller.model.TagResponse;
import org.velog.api.domain.tag.controller.model.TagResponses;
import org.velog.db.blog.BlogEntity;
import org.velog.db.tag.TagEntity;

import java.util.List;

@Converter
public class TagConverter {

    public TagEntity toEntity(
            BlogEntity blogEntity,
            TagRequest tagRequest
    ){
        TagEntity tagEntity = TagEntity.builder()
                .title(tagRequest.getTitle())
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

    public TagResponses toResponses(List<TagResponse> tagResponseList){

        return TagResponses.builder()
                .tagResponseList(tagResponseList)
                .build();
    }
}
