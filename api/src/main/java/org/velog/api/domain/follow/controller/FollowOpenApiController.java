package org.velog.api.domain.follow.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.velog.api.common.api.Api;
import org.velog.api.domain.follow.business.FollowBusiness;
import org.velog.api.domain.follow.controller.model.FollowResponses;
import org.velog.api.utils.hateoas.HateoasTemplate;
import org.velog.api.utils.hateoas.target.mine.FollowHateoasLink;

@RestController
@RequestMapping("/open-api/users")
@RequiredArgsConstructor
@Tag(name = "FollowOpenApiController", description = "Follow OpenApi 서비스 컨트롤러")
public class FollowOpenApiController {

    private final FollowBusiness followBusiness;
    private final HateoasTemplate hateoasTemplate;
    private final FollowHateoasLink followHateoasLink;

    @Operation(summary = "유저 Follower 목록 조회 API", description = "LoginId 입력")
    @GetMapping("/{loginId}/follow/details")
    public ResponseEntity<Api<EntityModel<FollowResponses>>> getAnotherFollowerDetail(
            @PathVariable String loginId,
            HttpServletRequest request
    ){
        //List<FollowResponse> followResponseList = followBusiness.getAnotherUserFollowerDetails(loginId);
        FollowResponses followResponseList = followBusiness.getAnotherUserFollowerDetails(loginId);

        EntityModel<FollowResponses> resource = EntityModel.of(followResponseList);
        hateoasTemplate.addCommonLinks(resource, request, followHateoasLink);

        return ResponseEntity.status(HttpStatus.OK).body(Api.OK(resource));
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
