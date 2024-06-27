package org.velog.api.domain.follow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.user.service.UserService;
import org.velog.db.follow.FollowEntity;
import org.velog.db.follow.FollowEntityRepository;
import org.velog.db.user.UserEntity;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowService {

    private final FollowEntityRepository followEntityRepository;
    private final UserService userService;

    public FollowEntity userFollow(
        String followerLoginId,
        Long followeeUserId
    ){
        Long followerUserId = userService.getUserWithThrow(followerLoginId).getId();
        List<FollowEntity> followEntityList = followEntityRepository.findAllByFollowee_Id(followeeUserId);

        // 이미 팔로우를 한 유저를 팔로우 하는경우 예외
        if(checkMyFollow(followEntityList, followerUserId)){
            throw new ApiException(ErrorCode.BAD_REQUEST, "이미 팔로우를 한 사용자 입니다.");
        }

        // 자기자신 팔로우 불가능
        if(Objects.equals(followeeUserId, followerUserId)){
            throw new ApiException(ErrorCode.BAD_REQUEST, "본인 팔로우는 불가 입니다.");
        }

        return register(followerUserId, followeeUserId);
    }

    public void userUnFollow(
            String followerLoginId,
            Long followeeUserId
    ){
        Long followerUserId = userService.getUserWithThrow(followerLoginId).getId();
        List<FollowEntity> followEntityList = followEntityRepository.findAllByFollowee_Id(followeeUserId);

        // 본인이 팔로우하지 않은 유저를 언팔로우 하면 예외발생
        if(!checkMyFollow(followEntityList, followerUserId)){
            throw new ApiException(ErrorCode.BAD_REQUEST, "팔로우를 하지 않은 유저 입니다.");
        }

        delete(followerUserId, followeeUserId);
    }

    public FollowEntity register(
            Long followerUserId,
            Long followeeUserId
    ){
        UserEntity follower = userService.getUserWithThrow(followerUserId);
        UserEntity followee = userService.getUserWithThrow(followeeUserId);

        FollowEntity followEntity = FollowEntity.builder()
                .follower(follower)
                .followee(followee)
                .build();
        followEntity.addRegistrationDate();

        return followEntityRepository.save(followEntity);
    }

    public void delete(
            Long followerUserId,
            Long followeeUserId
    ){
        FollowEntity followEntity = followEntityRepository.findFirstByFollower_IdAndFollowee_Id(followerUserId, followeeUserId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "FollowEntity Null"));

        followEntityRepository.delete(followEntity);
    }

    // 로그인한 유저가 팔로우한 유저 목록 체크
    private boolean checkMyFollow(
            List<FollowEntity> followEntityList,
            Long followerUserId
    ){
        return followEntityList.stream()
                .anyMatch(followEntity -> Objects.equals(followEntity.getFollower().getId(), followerUserId));
    }

    @Transactional(readOnly = true)
    public List<UserEntity> getFollowerDetails(
            String loginId
    ){
        Long userId = userService.getUserWithThrow(loginId).getId();
        List<FollowEntity> followEntityList = followEntityRepository.findAllByFollower_Id(userId);

        return followEntityList.stream()
                .map(FollowEntity::getFollowee)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public int getFollowCount(
            String loginId
    ){
        Long targetUserId = userService.getUserWithThrow(loginId).getId();
        return followEntityRepository.findAllByFollower_Id(targetUserId).size();
    }
}
