package org.velog.api.domain.follow.business;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.velog.api.common.annotation.Business;
import org.velog.api.domain.follow.controller.model.FollowResponse;
import org.velog.api.domain.follow.converter.FollowConverter;
import org.velog.api.domain.follow.service.FollowService;
import org.velog.api.domain.session.ifs.AuthorizationServiceIfs;
import org.velog.api.domain.user.model.User;
import org.velog.api.domain.user.service.UserService;
import org.velog.db.follow.FollowEntity;
import org.velog.db.user.UserEntity;

import java.util.List;

@Business
@RequiredArgsConstructor
public class FollowBusiness {

    private final FollowService followService;
    private final FollowConverter followConverter;
    private final UserService userService;

    public FollowEntity follow(
            User user,
            String followerLoginId
    ){
        Long followeeUserId = user.getUserId();
        return followService.userFollow(followerLoginId, followeeUserId);
    }

    public void unFollow(
            User user,
            String followerLoginId
    ){
        Long followeeUserId = user.getUserId();
        followService.userUnFollow(followerLoginId, followeeUserId);
    }

    public List<FollowResponse> getMyFollowerDetails(
            User user
    ){
        String loginId = userService.getUserWithThrow(user.getUserId()).getLoginId();
        return getFollowerDetails(loginId);
    }

    public List<FollowResponse> getAnotherUserFollowerDetails(
            String targetLoginId
    ){
        return getFollowerDetails(targetLoginId);
    }

    private List<FollowResponse> getFollowerDetails(
            String loginId
    ){
        List<UserEntity> followerDetails = followService.getFollowerDetails(loginId);

        return followerDetails.stream()
                .map(followConverter::toResponse)
                .toList();
    }

    public Integer getAnotherUserFollowCount(
        String targetLoginId
    ){
        return followService.getFollowCount(targetLoginId);
    }

    public Integer getMyFollowCount(
            User user
    ){
        String loginId = userService.getUserWithThrow(user.getUserId()).getLoginId();
        return followService.getFollowCount(loginId);
    }
}
