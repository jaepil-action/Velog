package org.velog.api.domain.like.business;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.velog.api.common.annotation.Business;
import org.velog.api.domain.like.service.LikeService;
import org.velog.api.domain.session.SessionService;
import org.velog.db.like.LikeEntity;

@Business
@RequiredArgsConstructor
public class LikeBusiness {

    private final LikeService likeService;
    private final SessionService sessionService;

    public void addLike(
            HttpServletRequest request,
            Long postId
    ){
        Long userId = sessionService.validateRoleUserId(request);
        likeService.addLikeAnotherPost(userId, postId);
    }

    public void cancelLike(
            HttpServletRequest request,
            Long postId
    ){
        Long userId = sessionService.validateRoleUserId(request);
        likeService.cancelLikePost(userId, postId);
    }

    public int getLikeCount(
            Long postId
    ){
        return likeService.getLikeCount(postId);
    }
}
