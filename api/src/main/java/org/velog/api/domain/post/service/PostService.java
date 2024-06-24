package org.velog.api.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.velog.api.domain.blog.service.BlogService;
import org.velog.api.domain.post.controller.model.PostRegisterRequest;
import org.velog.api.domain.post.converter.PostConverter;
import org.velog.api.domain.series.service.SeriesService;
import org.velog.api.domain.tag.service.TagService;
import org.velog.db.blog.BlogEntity;
import org.velog.db.post.PostEntity;
import org.velog.db.post.PostRepository;
import org.velog.db.series.SeriesEntity;
import org.velog.db.tag.TagEntity;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostConverter postConverter;
    private final BlogService blogService;
    private final SeriesService seriesService;
    private final TagService tagService;

    public PostEntity register(
            Long userId,
            PostRegisterRequest postRegisterRequest
    ){
        BlogEntity blogEntity = blogService.getBlogByUserIdWithThrow(userId);

        SeriesEntity seriesEntity = null;
        if(postRegisterRequest.getSeriesId() != null &&
                !postRegisterRequest.getSeriesId().isBlank())
        {
            seriesEntity = seriesService.getSeriesWithThrow(
                    Long.parseLong(postRegisterRequest.getSeriesId()));
        }

        TagEntity tagEntity = null;
        if(postRegisterRequest.getTagId() != null &&
                !postRegisterRequest.getTagId().isBlank())
        {
            tagEntity = tagService.getTagWithThrow(
                    Long.parseLong(postRegisterRequest.getTagId()));
        }

        if(postRegisterRequest.getExcerpt().isBlank()){
            postRegisterRequest.setExcerpt(null);
        }

        PostEntity postEntity = postConverter.toEntity(
                blogEntity,
                tagEntity,
                seriesEntity,
                postRegisterRequest
        );

        postEntity.setLikeCountZero();
        postEntity.setRegistrationDate();

        return postRepository.save(postEntity);
    }
}
