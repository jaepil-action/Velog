package org.velog.api.domain.follow.business;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.velog.api.common.annotation.Business;
import org.velog.api.domain.follow.controller.FollowController;
import org.velog.api.domain.follow.controller.model.FollowResponse;
import org.velog.api.domain.follow.converter.FollowConverter;
import org.velog.api.domain.follow.service.FollowService;
import org.velog.api.domain.session.SessionService;
import org.velog.api.domain.user.controller.model.UserResponse;
import org.velog.api.domain.user.converter.UserConverter;
import org.velog.db.follow.FollowEntity;
import org.velog.db.user.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

@Business
@RequiredArgsConstructor
public class FollowBusiness {

    private final FollowService followService;
    private final SessionService sessionService;
    private final FollowConverter followConverter;

    public FollowEntity follow(
            HttpServletRequest request,
            String followerLoginId
    ){
        Long followeeUserId = sessionService.validateRoleUserId(request);
        return followService.userFollow(followerLoginId, followeeUserId);
    }

    public void unFollow(
            HttpServletRequest request,
            String followerLoginId
    ){
        Long followeeUserId = sessionService.validateRoleUserId(request);
        followService.userUnFollow(followerLoginId, followeeUserId);
    }

    public List<FollowResponse> getMyFollowerDetails(
            HttpServletRequest request
    ){
        String myLoginId = sessionService.validateRoleUserLoginId(request);
        return getFollowerDetails(myLoginId);
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
            HttpServletRequest request
    ){
        String myLoginId = sessionService.validateRoleUserLoginId(request);
        return followService.getFollowCount(myLoginId);
    }
}
