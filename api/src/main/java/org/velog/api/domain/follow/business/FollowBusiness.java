package org.velog.api.domain.follow.business;

import lombok.RequiredArgsConstructor;
import org.velog.api.common.annotation.Business;
import org.velog.api.domain.follow.controller.model.FollowResponse;
import org.velog.api.domain.follow.controller.model.FollowResponses;
import org.velog.api.domain.follow.converter.FollowConverter;
import org.velog.api.domain.follow.service.FollowService;
import org.velog.api.domain.user.model.UserDto;
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
            UserDto userDto,
            String followerLoginId
    ){
        Long followeeUserId = userDto.getUserId();
        return followService.userFollow(followerLoginId, followeeUserId);
    }

    public void unFollow(
            UserDto userDto,
            String followerLoginId
    ){
        Long followeeUserId = userDto.getUserId();
        followService.userUnFollow(followerLoginId, followeeUserId);
    }

    public List<FollowResponse> getMyFollowerDetails(
            UserDto userDto
    ){
        String loginId = userService.getUserWithThrow(userDto.getUserId()).getLoginId();
        return getFollowerDetails(loginId);
    }

    public FollowResponses getAnotherUserFollowerDetails(
            String targetLoginId
    ){
        List<FollowResponse> responses = getFollowerDetails(targetLoginId);
        return followConverter.toResponses(responses);
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
            UserDto userDto
    ){
        String loginId = userService.getUserWithThrow(userDto.getUserId()).getLoginId();
        return followService.getFollowCount(loginId);
    }
}
