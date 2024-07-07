package org.velog.api.domain.like.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.post.service.PostService;
import org.velog.api.domain.user.service.UserService;
import org.velog.db.like.LikeEntity;
import org.velog.db.like.LikeEntityRepository;
import org.velog.db.post.PostEntity;
import org.velog.db.user.UserEntity;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final LikeEntityRepository likeEntityRepository;
    private final PostService postService;
    private final UserService userService;

    public LikeEntity addLikeAnotherPost(
            Long userId,
            Long postId
    ){

        PostEntity postEntity = postService.getPostWithThrow(postId);
        UserEntity userEntity = userService.getUserWithThrow(userId);


        validateUserCanLike(postEntity, userEntity);
        // 정상 좋아요
        return register(postEntity, userEntity);
    }
    public LikeEntity register(PostEntity postEntity, UserEntity userEntity){


        LikeEntity likeEntity = LikeEntity.builder()
                .userEntity(userEntity)
                .build();

        likeEntity.addPostEntity(postEntity);
        return likeEntityRepository.save(likeEntity);
    }


    private void validateUserCanLike(PostEntity postEntity, UserEntity userEntity){

        if(likeEntityRepository.findFirstByUserEntity_IdAndPostEntity_Id(
                userEntity.getId(), postEntity.getId()).isPresent()){
            throw new ApiException(ErrorCode.BAD_REQUEST, "사용자가 이미 좋아요를 누른 Post 입니다.");
        }


        if (isLikingOwnPost(userEntity, postEntity)) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "자신의 Post는 '좋아요'를 누를 수 없습니다.");
        }
    }

    private boolean isLikingOwnPost(UserEntity userEntity, PostEntity postEntity) {

        if (userEntity.getBlogEntity() == null) {
            return false;
        }

        return Objects.equals(userEntity.getBlogEntity().getId(), postEntity.getBlogEntity().getId());
    }

    public void cancelLikePost(
            Long userId,
            Long postId
    ) {
        LikeEntity likeEntity = likeEntityRepository.findFirstByUserEntity_IdAndPostEntity_Id(userId, postId)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "'좋아요'를 누르지 않은 post 입니다."));

        likeEntityRepository.delete(likeEntity);
    }

    @Transactional(readOnly = true)
    public int findLikeCount(
            Long postId
    ){
        List<LikeEntity> likeEntityList = likeEntityRepository.findAllByPostEntity_Id(postId);
        return likeEntityList.size();
    }
}
