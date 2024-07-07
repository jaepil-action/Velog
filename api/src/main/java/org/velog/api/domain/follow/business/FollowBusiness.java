package org.velog.api.domain.follow.business;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.velog.api.common.annotation.Business;
import org.velog.api.domain.follow.controller.model.FollowResponse;
import org.velog.api.domain.follow.converter.FollowConverter;
import org.velog.api.domain.follow.service.FollowService;
import org.velog.api.domain.session.ifs.CookieServiceIfs;
import org.velog.api.domain.user.service.UserService;
import org.velog.db.follow.FollowEntity;
import org.velog.db.user.UserEntity;

import java.util.List;

@Business
@RequiredArgsConstructor
public class FollowBusiness {

    private final FollowService followService;
    private final FollowConverter followConverter;
    private final CookieServiceIfs cookieService;
    private final UserService userService;

    public FollowEntity follow(
            HttpServletRequest request,
            String followerLoginId
    ){
        Long followeeUserId = cookieService.validateRoleUserGetId(request);
        return followService.userFollow(followerLoginId, followeeUserId);
    }

    public void unFollow(
            HttpServletRequest request,
            String followerLoginId
    ){
        Long followeeUserId = cookieService.validateRoleUserGetId(request);
        followService.userUnFollow(followerLoginId, followeeUserId);
    }

    public List<FollowResponse> getMyFollowerDetails(
            HttpServletRequest request
    ){
        Long userId = cookieService.validateRoleUserGetId(request);
        String loginId = userService.getUserWithThrow(userId).getLoginId();
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
            HttpServletRequest request
    ){
        Long userId = cookieService.validateRoleUserGetId(request);
        String loginId = userService.getUserWithThrow(userId).getLoginId();
        return followService.getFollowCount(loginId);
    }
}
