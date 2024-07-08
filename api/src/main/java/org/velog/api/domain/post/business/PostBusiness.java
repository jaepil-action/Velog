package org.velog.api.domain.post.business;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.velog.api.common.annotation.Business;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.post.controller.model.*;
import org.velog.api.domain.post.controller.model.PostDetailResponse;
import org.velog.api.domain.post.controller.model.PostsAdminPageResponse;
import org.velog.api.domain.post.controller.model.PostsDetailPageResponse;
import org.velog.api.domain.post.controller.model.PostsPageResponse;
import org.velog.api.domain.post.converter.PostConverter;
import org.velog.api.domain.post.service.PostService;
import org.velog.api.domain.session.ifs.AuthorizationServiceIfs;
import org.velog.api.domain.user.model.User;
import org.velog.db.post.PostEntity;

import java.util.Optional;

@Business
@RequiredArgsConstructor
/***
 * 요구사항
 * - 임시글 작성
 * - 임시글 조회,삭제,수정 가능
 * - 공개 일때 이미지 등록가능
 * - 공개 일때 글의 제목,내용 일부 보여짐
 * - 공개 일때 시리즈 추가 가능
 * - 비공개 일때 비공개 표시
 */
public class PostBusiness {

    private final PostService postService;
    private final PostConverter postConverter;

    public PostDetailResponse createPost(
            User user,
            PostRequest postRequest
    ){
        PostEntity postEntity = postService.register(user.getUserId(), postRequest);

        return Optional.ofNullable(postEntity)
                .map(postConverter::toDetailResponse)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    public PostDetailResponse getPost(
            Long postId
    ){
        return Optional.ofNullable(postId)
                .map(postService::getPostWithAuthorById)//
                .map(postConverter::toDetailResponse)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    public void deletePost(
            User user,
            Long postId
    ){
        postService.delete(user.getUserId(), postId);
    }

    public void editPost(
            User user,
            Long postId,
            PostRequest postRequest
    ){
        postService.edit(user.getUserId(), postId, postRequest);
    }

    public void editTag(
            User user,
            Long postId,
            TagDto tagDto
    ){
        postService.editTag(user.getUserId(), postId, tagDto);
    }

    public void editSeries(
            User user,
            Long postId,
            SeriesDto seriesDto
    ){
        postService.editSeries(user.getUserId(), postId, seriesDto);
    }

    public Integer commentCountByPost(
            Long postId
    ){
        return postService.commentCount(postId);
    }

    public PostsAdminPageResponse getPostsByAdmin(
            int offset,
            int limit
    ){
        PageRequest pageRequest = PageRequest.of(offset, limit);
        Page<PostEntity> postsPage = postService.findAdminPosts(pageRequest);
        return postConverter.toAdminPostsResponse(postsPage);
    }

    public PostsPageResponse getPostsByStatus(
            int offset,
            int limit
    ){
        PageRequest pageRequest = PageRequest.of(offset, limit);
        Page<PostEntity> postsPage = postService.findPostsByStatus(pageRequest);
        return postConverter.toPostsResponse(postsPage);
    }


    public PostsDetailPageResponse getDetailPosts(
            int offset,
            int limit,
            String sortCond
    ){
        PageRequest pageRequest = PageRequest.of(offset, limit);

        if(sortCond.contains("popular")){
            Page<PostEntity> postsPage = postService.findPostsOrderByLike(pageRequest);
            return postConverter.toDetailPostsResponse(postsPage);
        }

        Page<PostEntity> postsPage = postService.findPostsOrderByDate(pageRequest);
        return postConverter.toDetailPostsResponse(postsPage);
    }
}
