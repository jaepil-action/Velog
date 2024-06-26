package org.velog.api.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
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

import java.util.List;

@Service
@Transactional
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

        // 아무것도 입력을 안했을시 null 반환 -> TODO 시리즈, 태그 생성 후 추후 등록가능
        SeriesEntity seriesEntity = getSeriesEntity(postRegisterRequest);
        TagEntity tagEntity = getTagEntity(postRegisterRequest);

        if(postRegisterRequest.getExcerpt().isBlank()){
            postRegisterRequest.setExcerpt(null);
        }

        PostEntity postEntity = postConverter.toEntity(
                blogEntity,
                tagEntity,
                seriesEntity,
                postRegisterRequest
        );

        postEntity.setRegistrationDate();

        return postRepository.save(postEntity);
    }

    private TagEntity getTagEntity(PostRegisterRequest postRegisterRequest) {
        TagEntity tagEntity = null;
        if(!postRegisterRequest.getTagId().isBlank())
        {
            tagEntity = tagService.getTagWithThrow(
                    Long.parseLong(postRegisterRequest.getTagId()));
        }
        return tagEntity;
    }

    private SeriesEntity getSeriesEntity(PostRegisterRequest postRegisterRequest) {
        SeriesEntity seriesEntity = null;
        if(!postRegisterRequest.getSeriesId().isBlank())
        {
            seriesEntity = seriesService.getSeriesWithThrow(
                    Long.parseLong(postRegisterRequest.getSeriesId()));
        }
        return seriesEntity;
    }

    @Transactional(readOnly = true)
    public PostEntity getPostWithThrow(Long postId){
        return postRepository.findById(
                postId
        ).orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "Post가 존재 하지 않습니다"));
    }

    public void deletePostById(
            Long postsId
    ){
        PostEntity postEntity = getPostWithThrow(postsId);
        postRepository.delete(postEntity);
    }

    public List<PostEntity> findAllPosts(){ return postRepository.findAll(); }

}
