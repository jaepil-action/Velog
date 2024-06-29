package org.velog.api.domain.like.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.velog.api.common.api.Api;
import org.velog.api.domain.like.business.LikeBusiness;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "LikeApiController", description = "Like Api 서비스 컨트롤러")
public class LikeApiController {

    private final LikeBusiness likeBusiness;

    @Operation(summary = "Post 좋아요 누르기 API", description = "post_id 입력")
    @PostMapping("/{postId}/like")
    public ResponseEntity<Api<String>> addLike(
            HttpServletRequest request,
            @Parameter(description = "좋아요 대상 Post ID", required = true, example = "1")
            @PathVariable Long postId
    ){
        likeBusiness.addLike(request, postId);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK("좋아요가 추가 되었습니다."));
    }

    @Operation(summary = "Post 좋아요 취소 API", description = "post_id 입력")
    @DeleteMapping("/{postId}/like")
    public ResponseEntity<Api<String>> cancelLike(
            HttpServletRequest request,
            @Parameter(description = "좋아요 취소 Post ID", required = true, example = "1")
            @PathVariable Long postId
    ){
        likeBusiness.cancelLike(request, postId);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK("좋아요가 취소 되었습니다."));
    }

    @Operation(summary = "Post 좋아요 Count 조회 API", description = "post_id 입력")
    @GetMapping("/{postId}/like")
    public ResponseEntity<Api<Integer>> getLikeCount(
            @Parameter(description = "좋아요 갯수 해당 Post ID", required = true, example = "1")
            @PathVariable Long postId
    ){
        Integer count = likeBusiness.getLikeCount(postId);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK(count));
    }
}
