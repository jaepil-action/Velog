package org.velog.api.domain.blog.converter;

import org.velog.api.common.annotation.Converter;
import org.velog.api.domain.blog.controller.model.BlogRegisterRequest;
import org.velog.api.domain.blog.controller.model.BlogRegisterWithUserRequest;
import org.velog.api.domain.blog.controller.model.BlogResponse;
import org.velog.db.blog.BlogEntity;
import org.velog.db.user.UserEntity;

import java.util.Optional;

@Converter
public class BlogConverter {

    public BlogEntity toEntity(
            BlogRegisterRequest request,
            UserEntity userEntity
    ){
        return BlogEntity.builder()
                .userEntity(userEntity)
                .blogTitle(request.getBlogTitle())
                .introduction(request.getIntroduction())
                .build();
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
