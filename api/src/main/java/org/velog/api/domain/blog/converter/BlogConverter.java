package org.velog.api.domain.blog.converter;

import org.velog.api.common.annotation.Converter;
import org.velog.api.domain.blog.controller.model.BlogRegisterRequest;
import org.velog.api.domain.blog.controller.model.BlogResponse;
import org.velog.db.blog.BlogEntity;
import org.velog.db.user.UserEntity;


@Converter
public class BlogConverter {

    public BlogEntity toEntity(
            BlogRegisterRequest request,
            UserEntity userEntity
    ){
        BlogEntity blogEntity = BlogEntity.builder()
                .blogTitle(request.getBlogTitle())
                .introduction(request.getIntroduction())
                .build();
        blogEntity.addUserEntity(userEntity);

        return blogEntity;
    }

    public BlogResponse toResponse(
            BlogEntity blogEntity
    ){
        return BlogResponse.builder()
                .blogTitle(blogEntity.getBlogTitle())
                .introduction(blogEntity.getIntroduction())
                .postEntityList(blogEntity.getPostEntityList())
                .seriesEntityList(blogEntity.getSeriesEntityList())
                .tagEntityList(blogEntity.getTagEntityList())
                .build();
    }

}
