package org.velog.api.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.blog.service.BlogService;
import org.velog.api.domain.post.controller.model.PostRegisterRequest;
import org.velog.api.domain.post.controller.model.SeriesDto;
import org.velog.api.domain.post.controller.model.TagDto;
import org.velog.api.domain.post.converter.PostConverter;
import org.velog.api.domain.series.service.SeriesService;
import org.velog.api.domain.tag.service.TagService;
import org.velog.db.blog.BlogEntity;
import org.velog.db.post.PostEntity;
import org.velog.db.post.PostRepository;
import org.velog.db.series.SeriesEntity;
import org.velog.db.tag.TagEntity;
import org.velog.db.user.UserEntity;

import java.util.List;
import java.util.Objects;

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

        // 아무것도 입력을 안했을시 null 반환 -> 추후 등록가능
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
            tagEntity.addTagCount();
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
        postEntity.getTagEntity().minusTagCount();
        postRepository.delete(postEntity);
    }

    public List<PostEntity> findAllPosts(){ return postRepository.findAll(); }

    public void editTag(
            Long userId,
            Long postId,
            TagDto tagDto
    ){
        PostEntity postEntity = getPostWithThrow(postId);
        UserEntity userEntity = postEntity.getBlogEntity().getUserEntity();

        if(!Objects.equals(userEntity.getId(), userId)){
            throw new ApiException(ErrorCode.BAD_REQUEST, "자신의 Post가 아닙니다");
        }

        if(tagDto.getTagId() != null){
            TagEntity editTag = tagService.getTagWithThrow(tagDto.getTagId());
            postEntity.changeTagEntity(editTag);
        }else{
            postEntity.changeTagEntity(null);
        }
    }

    public void editSeries(
            Long userId,
            Long postId,
            SeriesDto seriesDto
    ){
        PostEntity postEntity = getPostWithThrow(postId);
        UserEntity userEntity = postEntity.getBlogEntity().getUserEntity();

        if(!Objects.equals(userEntity.getId(), userId)){
            throw new ApiException(ErrorCode.BAD_REQUEST, "자신의 Post가 아닙니다");
        }

        if(seriesDto.getSeriesId() != null){
            SeriesEntity editSeries = seriesService.getSeriesWithThrow(seriesDto.getSeriesId());
            postEntity.changeSeriesEntity(editSeries);
        }else{
            postEntity.changeSeriesEntity(null);
        }
    }

    public Integer commentCount(
            Long postId
    ){
        PostEntity postEntity = getPostWithThrow(postId);
        return postEntity.getCommentEntityList().size();
    }
}
