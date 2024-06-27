package org.velog.api.domain.follow.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.velog.api.common.api.Api;
import org.velog.api.domain.follow.business.FollowBusiness;
import org.velog.api.domain.follow.controller.model.FollowResponse;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "FollowApiController", description = "Follow Api 서비스 컨트롤러")
public class FollowApiController {

    private final FollowBusiness followBusiness;

    @Operation(summary = "User Follow API", description = "Follow 대상 user_id 입력")
    @PostMapping("/{loginId}/follow")
    public ResponseEntity<Api<String>> userFollow(
            HttpServletRequest request,
            @PathVariable String loginId
    ){
        followBusiness.follow(request, loginId);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK("팔로우가 완료 되었습니다."));
    }

    @Operation(summary = "User UnFollow API", description = "UnFollow 대상 user_id 입력")
    @DeleteMapping("/{loginId}/follow")
    public ResponseEntity<Api<String>> userUnFollow(
            HttpServletRequest request,
            @PathVariable String loginId
    ){
        followBusiness.unFollow(request, loginId);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK("언팔로우가 완료 되었습니다."));
    }

    @Operation(summary = "My Follower 목록 조회 API", description = "")
    @GetMapping("/my/follow/details")
    public ResponseEntity<Api<List<FollowResponse>>> getMyFollowerDetail(
            HttpServletRequest request
    ){
        List<FollowResponse> followResponseList = followBusiness.getMyFollowerDetails(request);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK(followResponseList));
    }

    @Operation(summary = "유저 Follower 목록 조회 API", description = "LoginId 입력")
    @GetMapping("/{loginId}/follow/details")
    public ResponseEntity<Api<List<FollowResponse>>> getAnotherFollowerDetail(
            @PathVariable String loginId
    ){
        List<FollowResponse> followResponseList = followBusiness.getAnotherUserFollowerDetails(loginId);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK(followResponseList));
    }

    @Operation(summary = "My Follow Count 조회 API", description = "")
    @GetMapping("/my/follow/count")
    public ResponseEntity<Api<Integer>> getMyFollowCount(
            HttpServletRequest request
    ){
        Integer count = followBusiness.getMyFollowCount(request);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK(count));
    }

    @Operation(summary = "다른 유저 Follow Count 조회 API", description = "Follow Count 대상 login_id 입력")
    @GetMapping("/{loginId}/follow/count")
    public ResponseEntity<Api<Integer>> getAnotherFollowCount(
            @PathVariable String loginId
    ){
        Integer count = followBusiness.getAnotherUserFollowCount(loginId);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK(count));
    }
}
