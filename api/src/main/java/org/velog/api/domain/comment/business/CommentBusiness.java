package org.velog.api.domain.comment.business;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.velog.api.common.annotation.Business;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.comment.controller.model.CommentRegisterRequest;
import org.velog.api.domain.comment.controller.model.CommentResponse;
import org.velog.api.domain.comment.converter.CommentConverter;
import org.velog.api.domain.comment.service.CommentService;
import org.velog.api.domain.post.service.PostService;
import org.velog.api.domain.session.SessionService;
import org.velog.db.comment.CommentEntity;
import org.velog.db.post.PostEntity;
import org.velog.db.user.UserEntity;

import java.util.Optional;

@Business
@RequiredArgsConstructor
public class CommentBusiness {

    private final CommentService commentService;
    private final CommentConverter commentConverter;
    private final SessionService sessionService;
    private final PostService postService;

    public CommentResponse registerCommentByPost(
            HttpServletRequest request,
            Long postId,
            CommentRegisterRequest commentRegisterRequest
    ){
        UserEntity commentWriter = sessionService.validateRoleUser(request);
        PostEntity postEntity = postService.getPostWithThrow(postId);

        CommentEntity commentEntity = commentConverter.toEntity(commentWriter, postEntity, commentRegisterRequest);

        return Optional.ofNullable(commentEntity)
                .map(commentService::register)
                .map(commentConverter::toResponse)
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, "Bad Request"));
    }

    public void deleteCommentByPost(
            HttpServletRequest request,
            Long postId,
            Long commentId
    ){
        UserEntity commentWriter = sessionService.validateRoleUser(request);
        commentService.delete(postId, commentId, commentWriter);
    }

    public void test(){

    }
}
