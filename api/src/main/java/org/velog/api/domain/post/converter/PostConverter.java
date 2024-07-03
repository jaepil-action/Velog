package org.velog.api.domain.post.converter;

import org.springframework.data.domain.Page;
import org.velog.api.common.annotation.Converter;
import org.velog.api.domain.post.controller.model.*;
import org.velog.db.blog.BlogEntity;
import org.velog.db.post.PostEntity;
import org.velog.db.series.SeriesEntity;
import org.velog.db.tag.TagEntity;
import org.velog.db.user.UserEntity;

import java.util.List;

@Converter
public class PostConverter {

    public PostEntity toEntity(
            BlogEntity blogEntity,
            TagEntity tagEntity,
            SeriesEntity seriesEntity,
            PostRequest postRequest
    ){
        PostEntity postEntity = PostEntity.builder()
                .postStatus(postRequest.getPostStatus())
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .excerpt(postRequest.getExcerpt())
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
        return PostResponse.builder()
                .postId(postEntity.getId())
                .title(postEntity.getTitle())
                .postStatus(postEntity.getPostStatus())
                .createdAt(postEntity.getRegistrationDate())
                .build();
    }

    public PostDetailResponse toDetailResponse(
            PostEntity postEntity
    ){
        PostDetailResponse postResponse = PostDetailResponse.builder()
                .postId(postEntity.getId())
                .title(postEntity.getTitle())
                .excerpt(postEntity.getExcerpt())
                .likeCount(postEntity.getLikeEntityList().size())
                .build();

        UserEntity userEntity = postEntity.getBlogEntity().getUserEntity();
        postResponse.setAuthor(new AuthorDto(userEntity.getName(), userEntity.getProfileImage()));

/*        List<LikeEntity> likeEntityList = postEntity.getLikeEntityList();
        for (LikeEntity likeEntity : likeEntityList) {
            Long id = likeEntity.getId();
            System.out.println("id = " + id);
        }*/

        return postResponse;
    }

    public PostAdminResponse toAdminResponse(
            PostEntity postEntity
    ){
        PostAdminResponse postResponse = PostAdminResponse.builder()
                .postId(postEntity.getId())
                .title(postEntity.getTitle())
                .postStatus(postEntity.getPostStatus())
                .createdAt(postEntity.getRegistrationDate())
                .build();

        UserEntity userEntity = postEntity.getBlogEntity().getUserEntity();
        postResponse.setAuthor(new AuthorDto(userEntity.getName(), userEntity.getProfileImage()));

        return postResponse;
    }

    public PostsPageResponse toPostsResponse(
            Page<PostEntity> postEntity
    ){
        PostsPageResponse postsPageResponse = PostsPageResponse.builder()
                .totalElements(postEntity.getTotalElements())
                .totalPages(postEntity.getTotalPages())
                .offset((int)postEntity.getPageable().getOffset())
                .limit(postEntity.getPageable().getPageSize())
                .build();

        List<PostEntity> postEntityList = postEntity.getContent();
        List<PostResponse> contents = postEntityList.stream()
                .map(this::toResponse)
                .toList();
        postsPageResponse.setContents(contents);

        return postsPageResponse;
    }

    public PostsAdminPageResponse toAdminPostsResponse(
            Page<PostEntity> postEntity
    ){
        PostsAdminPageResponse postsAdminPageResponse = PostsAdminPageResponse.builder()
                .totalElements(postEntity.getTotalElements())
                .totalPages(postEntity.getTotalPages())
                .offset((int)postEntity.getPageable().getOffset())
                .limit(postEntity.getPageable().getPageSize())
                .build();

        List<PostEntity> postEntityList = postEntity.getContent();
        List<PostAdminResponse> contents = postEntityList.stream()
                .map(this::toAdminResponse)
                .toList();
        postsAdminPageResponse.setContents(contents);

        return postsAdminPageResponse;
    }

    public PostsDetailPageResponse toDetailPostsResponse(
            Page<PostEntity> postEntity
    ){
        PostsDetailPageResponse postsDetailPageResponse = PostsDetailPageResponse.builder()
                .totalElements(postEntity.getTotalElements())
                .totalPages(postEntity.getTotalPages())
                .offset((int)postEntity.getPageable().getOffset())
                .limit(postEntity.getPageable().getPageSize())
                .build();

        List<PostEntity> postEntityList = postEntity.getContent();
        List<PostDetailResponse> contents = postEntityList.stream()
                .map(this::toDetailResponse)
                .toList();
        postsDetailPageResponse.setContents(contents);

        return postsDetailPageResponse;
    }
}
