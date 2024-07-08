package org.velog.api.domain.like.business;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.velog.api.common.annotation.Business;
import org.velog.api.domain.like.service.LikeService;
import org.velog.api.domain.session.ifs.AuthorizationServiceIfs;
import org.velog.api.domain.user.model.User;

@Business
@RequiredArgsConstructor
public class LikeBusiness {

    private final LikeService likeService;

    public void addLike(
            User user,
            Long postId
    ){
        likeService.addLikeAnotherPost(user.getUserId(), postId);
    }

    public void cancelLike(
            User user,
            Long postId
    ){
        likeService.cancelLikePost(user.getUserId(), postId);
    }

    public int getLikeCount(
            Long postId
    ){
        return likeService.findLikeCount(postId);
    }
}
