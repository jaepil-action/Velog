package org.velog.api.domain.blog.converter;

import org.velog.api.common.annotation.Converter;
import org.velog.api.domain.blog.controller.model.*;
import org.velog.db.blog.BlogEntity;
import org.velog.db.series.SeriesEntity;
import org.velog.db.user.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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

    public BlogDetailResponse toDetailResponse(
            BlogEntity blogEntity
    ){
        List<TagDto> tagsDto = blogEntity.getTagEntityList().stream()
                .map(t -> new TagDto(t.getTitle(), t.getCount()))
                .toList();

        List<PostDto> postsDto = blogEntity.getPostEntityList().stream()
                .map(p -> {
                    String seriesTitle = Optional.ofNullable(p.getSeriesEntity())
                            .map(SeriesEntity::getTitle)
                            .orElse(null);
                    return new PostDto(p.getId(), p.getTitle(), p.getExcerpt(), p.getRegistrationDate(), seriesTitle);
                })
                .toList();

        /* TODO 공부필요
             List<PostDto> postsDto = blogEntity.getPostEntityList().stream()
                .map(p -> new PostDto(p.getId(), p.getTitle(), p.getExcerpt(),
                    p.getRegistrationDate(), p.getSeriesEntity().getTitle()))
                    .toList();
        *   */

/*        List<PostOfSeriesDto> postOfSeries = blogEntity.getPostEntityList().stream()
                .map(p -> new PostOfSeriesDto(p.getId(), p.getTitle()))
                .toList();

        List<SeriesDto> seriesDto = blogEntity.getSeriesEntityList().stream()
                .map(s -> new SeriesDto(s.getId(), s.getTitle(), postOfSeries))
                .toList();*/

        List<SeriesDto> seriesDto = blogEntity.getSeriesEntityList().stream()
                .map(s -> {
                    List<PostOfSeriesDto> postOfSeries = s.getPostEntityList().stream()
                            .map(p -> new PostOfSeriesDto(p.getId(), p.getTitle()))
                            .toList();
                    return new SeriesDto(s.getId(), s.getTitle(), postOfSeries);
                })
                .toList();

        return BlogDetailResponse.builder()
                .tags(tagsDto)
                .posts(postsDto)
                .series(seriesDto)
                .introduction(blogEntity.getIntroduction())
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
