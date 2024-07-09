package org.velog.api.domain.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.velog.api.common.annotation.Login;
import org.velog.api.common.api.Api;
import org.velog.api.domain.comment.business.CommentBusiness;
import org.velog.api.domain.comment.controller.model.CommentRegisterRequest;
import org.velog.api.domain.comment.controller.model.CommentResponse;
import org.velog.api.domain.user.model.UserDto;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Tag(name = "CommentApiController", description = "댓글 Api 컨트롤러")
public class CommentApiController {

    private final CommentBusiness commentBusiness;

    @Operation(summary = "댓글 생성 API", description = "PostID 입력, 댓글생성")
    @PostMapping("/{postId}/comments")
    public ResponseEntity<Api<CommentResponse>> createComments(
            @Parameter(hidden = true) @Login UserDto userDto,
            @Parameter(description = "댓글을 입력할 Post ID", required = true, example = "1")
            @PathVariable Long postId,
            @Valid @RequestBody CommentRegisterRequest commentRegisterRequest
    ){
        CommentResponse commentResponse = commentBusiness.registerCommentByPost(userDto, postId, commentRegisterRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(Api.CREATED(commentResponse));
    }

    @Operation(summary = "대댓글 생성 API", description = "PostID, 부모댓글ID 입력")
    @PostMapping("/{postId}/{parentId}/comments")
    public ResponseEntity<Api<CommentResponse>> createChildComments(
            @Parameter(hidden = true) @Login UserDto userDto,
            @Parameter(description = "댓글을 입력할 Post ID", required = true, example = "1")
            @PathVariable Long postId,
            @Parameter(description = "대댓글을 입력할 댓글 ID", required = true, example = "1")
            @PathVariable Long parentId,
            @Valid @RequestBody CommentRegisterRequest commentRegisterRequest
    ){
        CommentResponse commentResponse = commentBusiness.registerCommentByParent(userDto, postId, parentId, commentRegisterRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(Api.CREATED(commentResponse));
    }

    @Operation(summary = "댓글 삭제 API", description = "PostID 입력")
    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<Api<String>> deleteComments(
            @Parameter(hidden = true) @Login UserDto userDto,
            @Parameter(description = "댓글을 삭제할 Post ID", required = true, example = "1")
            @PathVariable Long postId,
            @Parameter(description = "삭제할 댓글의 ID", required = true, example = "1")
            @PathVariable Long commentId
    ){
        commentBusiness.deleteCommentByPost(userDto, postId, commentId);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK("댓글 삭제가 완료 되었습니다."));
    }

    @Operation(summary = "댓글 수정 API", description = "PostID 입력")
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<Api<String>> editComments(
            @Parameter(hidden = true) @Login UserDto userDto,
            @Parameter(description = "수정할 댓글의 ID", required = true, example = "1")
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRegisterRequest commentRegisterRequest
    ){
        commentBusiness.editComment(userDto, commentId, commentRegisterRequest);
        return ResponseEntity.status(HttpStatus.OK).body(Api.OK("댓글 수정이 완료 되었습니다."));
    }
}
