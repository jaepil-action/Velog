package org.velog.api.domain.post.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.velog.api.common.annotation.Login;
import org.velog.api.common.api.Api;
import org.velog.api.domain.post.business.PostBusiness;
import org.velog.api.domain.post.controller.model.*;
import org.velog.api.domain.post.controller.model.PostDetailResponse;
import org.velog.api.domain.post.controller.model.PostsDetailPageResponse;
import org.velog.api.domain.post.controller.model.PostsPageResponse;
import org.velog.api.domain.user.model.User;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Tag(name = "PostApiController", description = "Post Api 서비스 컨트롤러")
public class PostApiController {

    private final PostBusiness postBusiness;

    @Operation(summary = "Post 생성 API", description = "사용자블로그의 Post 생성")
    @PostMapping("")
    public ResponseEntity<Api<PostDetailResponse>> createPost(
            @Parameter(hidden = true) @Login User user,
            @Valid @RequestBody PostRequest postRequest
    ){
        PostDetailResponse response = postBusiness.createPost(user, postRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(Api.CREATED(response));
    }


    @Operation(summary = "Post 삭제 API", description = "PostId 입력")
    @DeleteMapping("{postId}")
    public ResponseEntity<Api<String>> deletePost(
            @Parameter(hidden = true) @Login User user,
            @Parameter(description = "삭제할 Post ID", required = true, example = "1")
            @PathVariable Long postId
    ){
        postBusiness.deletePost(user, postId);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK("Post 삭제 성공"));
    }

    @Operation(summary = "Post 수정 API", description = "PostId 입력")
    @PutMapping("{postId}")
    public ResponseEntity<Api<String>> editPost(
            @Parameter(hidden = true) @Login User user,
            @Parameter(description = "수정할 Post ID", required = true, example = "1")
            @PathVariable Long postId,
            @Valid @RequestBody PostRequest postRequest
    ){
        postBusiness.editPost(user, postId, postRequest);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK("Post 변경 완료 되었습니다."));
    }

    @Operation(summary = "Post Tag 변경 API", description = "PostId, TagId 입력")
    @PutMapping("/{postId}/tag")
    public ResponseEntity<Api<String>> editTag(
            @Parameter(hidden = true) @Login User user,
            @Parameter(description = "태그 변경할 Post ID", required = true, example = "1")
            @PathVariable Long postId,
            @Valid @RequestBody TagDto tagDto
    ){
        postBusiness.editTag(user, postId, tagDto);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK("Tag 변경 완료 되었습니다."));
    }

    @Operation(summary = "Post Series 변경 API", description = "PostId, SeriesId 입력")
    @PutMapping("/{postId}/series")
    public ResponseEntity<Api<String>> editSeries(
            @Parameter(hidden = true) @Login User user,
            @Parameter(description = "시리즈 변경할 Post ID", required = true, example = "1")
            @PathVariable Long postId,
            @Valid @RequestBody SeriesDto seriesDto
    ){
        postBusiness.editSeries(user, postId, seriesDto);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK("Series 변경 완료 되었습니다."));
    }

}
