package org.velog.api.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.blog.service.BlogService;
import org.velog.api.domain.post.controller.model.PostRequest;
import org.velog.api.domain.post.controller.model.SeriesDto;
import org.velog.api.domain.post.controller.model.TagDto;
import org.velog.api.domain.post.converter.PostConverter;
import org.velog.api.domain.series.service.SeriesService;
import org.velog.api.domain.tag.service.TagService;
import org.velog.db.blog.BlogEntity;
import org.velog.db.post.PostEntity;
import org.velog.db.post.PostRepository;
import org.velog.db.post.enums.PostStatus;
import org.velog.db.series.SeriesEntity;
import org.velog.db.tag.TagEntity;
import org.velog.db.user.UserEntity;

import java.util.Arrays;
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
            PostRequest postRequest
    ){
        BlogEntity blogEntity = blogService.getBlogByUserIdWithThrow(userId);

        checkPostStatusAndSeries(postRequest);

        // 아무것도 입력을 안했을시 null 반환 -> 추후 등록가능
        SeriesEntity seriesEntity = getSeriesEntity(postRequest, blogEntity.getId());
        TagEntity tagEntity = getTagEntity(postRequest, blogEntity.getId());

        if(postRequest.getExcerpt().isBlank()){
            postRequest.setExcerpt(null);
        }

        PostEntity postEntity = postConverter.toEntity(
                blogEntity,
                tagEntity,
                seriesEntity,
                postRequest
        );

        return postRepository.save(postEntity);
    }

    public void edit(
            Long userId,
            Long postId,
            PostRequest postRequest
    ){
        PostEntity postEntity = getPostWithTagAndSeries(postId); //
        BlogEntity blogEntity = postEntity.getBlogEntity();

        checkPostByBlogEntity(userId, blogEntity);
        checkPostStatusAndSeries(postRequest);

        // 아무것도 입력을 안했을시 null 반환 -> 추후 등록가능
        SeriesEntity seriesEntity = getSeriesEntity(postRequest, blogEntity.getId());
        TagEntity tagEntity = getTagEntity(postRequest, blogEntity.getId());

        if(postRequest.getExcerpt().isBlank()){
            postRequest.setExcerpt(null);
        }

        postEntity.changePost(
                postRequest.getPostStatus(),
                postRequest.getTitle(),
                postRequest.getContent(),
                tagEntity,
                seriesEntity,
                postRequest.getExcerpt()
        );
    }

    private static void checkPostByBlogEntity(Long userId, BlogEntity blogEntity) {
        if(!Objects.equals(blogEntity.getUserEntity().getId(), userId)){
            throw new ApiException(ErrorCode.BAD_REQUEST, "자신의 Post가 아닙니다.");
        }
    }

    public void delete(
            Long userId,
            Long postId
    ){
        PostEntity postEntity = getPostWithTagAndSeries(postId);
        if(postEntity.getTagEntity() != null){
            postEntity.getTagEntity().minusTagCount();
        }

        BlogEntity blogEntity = postEntity.getBlogEntity();
        checkPostByBlogEntity(userId,blogEntity);

        postRepository.delete(postEntity);
    }

    private static void checkPostStatusAndSeries(PostRequest postRequest) {
        if (Objects.equals(postRequest.getPostStatus(), PostStatus.TEMPORARY) &&
                !postRequest.getSeriesId().isBlank())
        {
            throw new ApiException(ErrorCode.BAD_REQUEST, "임시글에 시리즈를 추가 할 수 없습니다.");
        }
    }

    public void deletePostById(
            Long postsId
    ){
        PostEntity postEntity = getPostWithTagAndSeries(postsId);
        postEntity.getTagEntity().minusTagCount();
        postRepository.delete(postEntity);
    }

    private TagEntity getTagEntity(
            PostRequest postRequest,
            Long blogId
    ){
        TagEntity tagEntity = null;
        if(!postRequest.getTagId().isBlank())
        {
            tagEntity = tagService.getTagWithThrow(
                    Long.parseLong(postRequest.getTagId()));
            if(!Objects.equals(tagEntity.getBlogEntity().getId(), blogId)){
                throw new ApiException(ErrorCode.BAD_REQUEST, "해당 블로그의 태그가 아닙니다.");
            }
            tagEntity.addTagCount();
        }
        return tagEntity;
    }

    private SeriesEntity getSeriesEntity(
            PostRequest postRequest,
            Long blogId
    ){
        SeriesEntity seriesEntity = null;
        if(!postRequest.getSeriesId().isBlank())
        {
            seriesEntity = seriesService.getSeriesWithThrow(
                    Long.parseLong(postRequest.getSeriesId()));

            if(!Objects.equals(seriesEntity.getBlogEntity().getId(), blogId)){
                throw new ApiException(ErrorCode.BAD_REQUEST, "해당 블로그의 시리즈가 아닙니다.");
            }
        }
        return seriesEntity;
    }

    @Transactional(readOnly = true)
    public PostEntity getPostWithTagAndSeries(Long postId){
        return postRepository.findPostWithTagAndSeries(
                postId
        ).orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "Post가 존재 하지 않습니다"));
    }

    @Transactional(readOnly = true)
    public PostEntity getPostWithThrow(Long postId){
        return postRepository.findById(
                postId
        ).orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "Post가 존재 하지 않습니다"));
    }

    @Transactional(readOnly = true)
    public PostEntity getPostWithAuthorById(Long postId){
        return postRepository.findPostWithAuthorById(
                postId
        ).orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "Post 존재 하지 않습니다"));
    }

    public Page<PostEntity> findPostsByStatus(PageRequest pageRequest){ // TODO 공부필요 IN SQL
        List<PostStatus> statuses = Arrays.asList(PostStatus.PUBLIC, PostStatus.TEMPORARY);
        return postRepository.findByPostStatusIn(pageRequest, statuses);
    }

    public Page<PostEntity> findAdminPosts(PageRequest pageRequest){
        return postRepository.findPosts(pageRequest);
    }

    public Page<PostEntity> findPostsOrderByLike(PageRequest pageRequest){
        return postRepository.findPostsOrderByLikeCount(pageRequest, PostStatus.PUBLIC);
    }

    public Page<PostEntity> findPostsOrderByDate(PageRequest pageRequest){
        return postRepository.findPostsOrderByDate(pageRequest, PostStatus.PUBLIC);
    }

    public void editTag(
            Long userId,
            Long postId,
            TagDto tagDto
    ){
        PostEntity postEntity = getPostWithTagAndSeries(postId);
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
        PostEntity postEntity = getPostWithTagAndSeries(postId);
        UserEntity userEntity = postEntity.getBlogEntity().getUserEntity();

        if(!Objects.equals(userEntity.getId(), userId) ||
                Objects.equals(postEntity.getPostStatus(), PostStatus.TEMPORARY))
        {
            throw new ApiException(ErrorCode.BAD_REQUEST, "Post 수정 권한이 없습니다.");
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
