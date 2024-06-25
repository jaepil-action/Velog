package org.velog.api.domain.post.converter;

import jakarta.servlet.http.HttpServletRequest;
import org.velog.api.common.annotation.Converter;
import org.velog.api.domain.post.controller.model.PostRegisterRequest;
import org.velog.api.domain.post.controller.model.PostResponse;
import org.velog.db.blog.BlogEntity;
import org.velog.db.post.PostEntity;
import org.velog.db.series.SeriesEntity;
import org.velog.db.tag.TagEntity;

@Converter
public class PostConverter {

    public PostEntity toEntity(
            BlogEntity blogEntity,
            TagEntity tagEntity,
            SeriesEntity seriesEntity,
            PostRegisterRequest postRegisterRequest
    ){
        PostEntity postEntity = PostEntity.builder()
                .title(postRegisterRequest.getTitle())
                .content(postRegisterRequest.getContent())
                .excerpt(postRegisterRequest.getExcerpt())
                .build();

        postEntity.addBlogEntity(blogEntity);
        if (seriesEntity != null) {
            postEntity.addSeriesEntity(seriesEntity);
        }

        if (tagEntity != null) {
            postEntity.addTagEntity(tagEntity);
        }

        return postEntity;
    }

    public PostResponse toResponse(
            PostEntity postEntity
    ){
        PostResponse postResponse = PostResponse.builder()
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .excerpt(postEntity.getExcerpt())
                .blogTitle(postEntity.getBlogEntity().getBlogTitle())
                .build();

        if(postEntity.getTagEntity() != null){
            postResponse.setTagTitle(postEntity.getTagEntity().getTitle());
        }
        if(postEntity.getSeriesEntity() != null){
            postResponse.setSeriesTitle(postEntity.getSeriesEntity().getTitle());
        }

        return postResponse;
    }
}
