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
import org.velog.api.common.api.Api;
import org.velog.api.domain.post.business.PostBusiness;
import org.velog.api.domain.post.controller.model.*;
import org.velog.api.domain.post.controller.model.PostDetailResponse;
import org.velog.api.domain.post.controller.model.PostsDetailPageResponse;
import org.velog.api.domain.post.controller.model.PostsPageResponse;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Tag(name = "PostApiController", description = "Post Api 서비스 컨트롤러")
public class PostApiController {

    private final PostBusiness postBusiness;

    @Operation(summary = "Post 생성 API", description = "사용자블로그의 Post 생성")
    @PostMapping("")
    public ResponseEntity<Api<PostDetailResponse>> createPost(
            HttpServletRequest request,
            @Valid @RequestBody PostRequest postRequest
    ){
        PostDetailResponse response = postBusiness.createPost(request, postRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(Api.CREATED(response));
    }

    @Operation(summary = "Post 단건 조회 API", description = "PostId 입력")
    @GetMapping("{postId}")
    public ResponseEntity<Api<PostDetailResponse>> retrievePost(
            @Parameter(description = "조회할 Post ID", required = true, example = "1")
            @PathVariable Long postId
    ){
        PostDetailResponse postResponse = postBusiness.getPost(postId);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK(postResponse));
    }

    @Operation(summary = "모든 Post 상세 조회 API", description = "최신순, 인기순 조회 가능 (비공개 조회불가)")
    @GetMapping("")
    public ResponseEntity<Api<PostsDetailPageResponse>> retrieveAllPost(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "latest") String sortCond
    ){
        PostsDetailPageResponse postResponseList = postBusiness.getDetailPosts(page, size, sortCond);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK(postResponseList));
    }

    @Operation(summary = "모든 Post 임시 조회 API", description = "공개,임시 상태 조회 가능 (비공개 조회불가)")
    @GetMapping("/drafts")
    public ResponseEntity<Api<PostsPageResponse>> retrieveAllPostWithDraft(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        PostsPageResponse postResponseList = postBusiness.getPostsByStatus(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK(postResponseList));
    }

    @Operation(summary = "Post 삭제 API", description = "PostId 입력")
    @DeleteMapping("{postId}")
    public ResponseEntity<Api<String>> deletePost(
            HttpServletRequest request,
            @Parameter(description = "삭제할 Post ID", required = true, example = "1")
            @PathVariable Long postId
    ){
        postBusiness.deletePost(request, postId);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK("Post 삭제 성공"));
    }

    @Operation(summary = "Post 수정 API", description = "PostId 입력")
    @PutMapping("{postId}")
    public ResponseEntity<Api<String>> editPost(
            HttpServletRequest request,
            @Parameter(description = "수정할 Post ID", required = true, example = "1")
            @PathVariable Long postId,
            @Valid @RequestBody PostRequest postRequest
    ){
        postBusiness.editPost(request, postId, postRequest);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK("Post 변경 완료 되었습니다."));
    }

    @Operation(summary = "Post Tag 변경 API", description = "PostId, TagId 입력")
    @PutMapping("/{postId}/tag")
    public ResponseEntity<Api<String>> editTag(
            HttpServletRequest request,
            @Parameter(description = "태그 변경할 Post ID", required = true, example = "1")
            @PathVariable Long postId,
            @Valid @RequestBody TagDto tagDto
    ){
        postBusiness.editTag(request, postId, tagDto);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK("Tag 변경 완료 되었습니다."));
    }

    @Operation(summary = "Post Series 변경 API", description = "PostId, SeriesId 입력")
    @PutMapping("/{postId}/series")
    public ResponseEntity<Api<String>> editSeries(
            HttpServletRequest request,
            @Parameter(description = "시리즈 변경할 Post ID", required = true, example = "1")
            @PathVariable Long postId,
            @Valid @RequestBody SeriesDto seriesDto
    ){
        postBusiness.editSeries(request, postId, seriesDto);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK("Series 변경 완료 되었습니다."));
    }

    @Operation(summary = "Post 댓글 갯수 조회 API", description = "PostId 입력")
    @GetMapping("/{postId}/commentCount")
    public ResponseEntity<Api<Integer>> editSeries(
            @Parameter(description = "댓글 갯수 해당 Post ID", required = true, example = "1")
            @PathVariable Long postId
    ){
        Integer count = postBusiness.commentCountByPost(postId);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK(count));
    }
}
