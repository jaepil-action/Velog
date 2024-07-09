package org.velog.api.domain.like.business;

import lombok.RequiredArgsConstructor;
import org.velog.api.common.annotation.Business;
import org.velog.api.domain.like.service.LikeService;
import org.velog.api.domain.user.model.UserDto;

@Business
@RequiredArgsConstructor
public class LikeBusiness {

    private final LikeService likeService;

    public void addLike(
            UserDto userDto,
            Long postId
    ){
        likeService.addLikeAnotherPost(userDto.getUserId(), postId);
    }

    public void cancelLike(
            UserDto userDto,
            Long postId
    ){
        likeService.cancelLikePost(userDto.getUserId(), postId);
    }

    public int getLikeCount(
            Long postId
    ){
        return likeService.findLikeCount(postId);
    }
}
