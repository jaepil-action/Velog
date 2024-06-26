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


/*        // 이미 좋아요를 누른 post라면 예외 발생
        List<LikeEntity> likeEntityList = likeEntityRepository.findAllByUserEntity_Id(userId);
        for (LikeEntity likeEntity : likeEntityList) {
            if(Objects.equals(likeEntity.getPostEntity().getId(), postId)){
                throw new ApiException(ErrorCode.BAD_REQUEST, "사용자가 이미 좋아요를 누른 Post 입니다.");
            }
        }

        // 좋아요를 한번도 안누른 사용자라면 무조건 ok
        // 좋아요 테이블에 userId를 통해 나온 결과가 0 이거나 아예 블로그를 생성하지 않은 사용자
        if(likeEntityList.isEmpty() || userEntity.getBlogEntity() == null){
            return register(postEntity, userEntity);
        }

        // 자신이 작성한 post 좋아요 누르면 예외 발생
        if(Objects.equals(userEntity.getBlogEntity().getId(),
                postEntity.getBlogEntity().getId())){
            throw new ApiException(ErrorCode.BAD_REQUEST, "자신의 Post는 '좋아요'를 누를 수 없습니다.");
        }*/

        validateUserCanLike(postEntity, userEntity);
        // 정상 좋아요
        return register(postEntity, userEntity);
    }
    public LikeEntity register(PostEntity postEntity, UserEntity userEntity){


        LikeEntity likeEntity = LikeEntity.builder()
                .userEntity(userEntity)
                .build();

        likeEntity.addRegistrationDate();
        likeEntity.addPostEntity(postEntity);
        return likeEntityRepository.save(likeEntity);
    }


    private void validateUserCanLike(PostEntity postEntity, UserEntity userEntity){

        List<LikeEntity> likeEntityList = likeEntityRepository.findAllByUserEntity_Id(userEntity.getId());
        if (hasAlreadyLikedPost(likeEntityList, postEntity)) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "사용자가 이미 좋아요를 누른 Post 입니다.");
        }

        if (userEntity.getBlogEntity() == null) {
            return;
        }

        if (isLikingOwnPost(userEntity, postEntity)) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "자신의 Post는 '좋아요'를 누를 수 없습니다.");
        }
    }

    private boolean hasAlreadyLikedPost(List<LikeEntity> likeEntityList, PostEntity postEntity) {
        return likeEntityList.stream()
                .anyMatch(likeEntity -> Objects.equals(likeEntity.getPostEntity().getId(), postEntity.getId()));
    }

    private boolean isLikingOwnPost(UserEntity userEntity, PostEntity postEntity) {
        return Objects.equals(userEntity.getBlogEntity().getId(), postEntity.getBlogEntity().getId());
    }

    public void cancelLikePost(
            Long userId,
            Long postId
    ) {

        PostEntity postEntity = postService.getPostWithThrow(postId);
        UserEntity userEntity = userService.getUserWithThrow(userId);

        List<LikeEntity> likeEntityList = likeEntityRepository.findAllByUserEntity_Id(userEntity.getId());
        if (!hasAlreadyLikedPost(likeEntityList, postEntity)) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "'좋아요'를 누르지 않은 post 입니다.");
        }

        LikeEntity likeEntity = likeEntityRepository.findFirstByUserEntity_IdAndPostEntity_Id(userId, postId)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "Like Entity Null"));

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
