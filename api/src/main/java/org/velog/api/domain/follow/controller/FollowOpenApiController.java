package org.velog.api.domain.follow.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.velog.api.common.annotation.Login;
import org.velog.api.common.api.Api;
import org.velog.api.domain.follow.business.FollowBusiness;
import org.velog.api.domain.follow.controller.model.FollowResponse;
import org.velog.api.domain.user.model.User;

import java.util.List;

@RestController
@RequestMapping("/open-api/users")
@RequiredArgsConstructor
@Tag(name = "FollowOpenApiController", description = "Follow OpenApi 서비스 컨트롤러")
public class FollowOpenApiController {

    private final FollowBusiness followBusiness;


    @Operation(summary = "유저 Follower 목록 조회 API", description = "LoginId 입력")
    @GetMapping("/{loginId}/follow/details")
    public ResponseEntity<Api<List<FollowResponse>>> getAnotherFollowerDetail(
            @PathVariable String loginId
    ){
        List<FollowResponse> followResponseList = followBusiness.getAnotherUserFollowerDetails(loginId);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK(followResponseList));
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
