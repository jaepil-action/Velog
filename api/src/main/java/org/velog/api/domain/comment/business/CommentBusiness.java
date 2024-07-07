package org.velog.api.domain.comment.business;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.velog.api.common.annotation.Business;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.comment.controller.model.CommentRegisterRequest;
import org.velog.api.domain.comment.controller.model.CommentResponse;
import org.velog.api.domain.comment.converter.CommentConverter;
import org.velog.api.domain.comment.service.CommentService;
import org.velog.api.domain.post.service.PostService;
import org.velog.api.domain.session.ifs.CookieServiceIfs;
import org.velog.api.domain.user.service.UserService;
import org.velog.db.comment.CommentEntity;
import org.velog.db.post.PostEntity;
import org.velog.db.user.UserEntity;

import java.util.Optional;

@Business
@RequiredArgsConstructor
@Slf4j
public class CommentBusiness {

    private final CommentService commentService;
    private final CommentConverter commentConverter;
    private final CookieServiceIfs cookieService;
    private final PostService postService;
    private final UserService userService;

    public CommentResponse registerCommentByPost(
            HttpServletRequest request,
            Long postId,
            CommentRegisterRequest commentRegisterRequest
    ) {
        Long userId = cookieService.validateRoleUserGetId(request);
        UserEntity commentWriter = userService.getUserWithThrow(userId); // TODO 왜 여기서 블로그엔티티 셀렉트가 나갈까? 지연로딩이지만 블로그엔티티 관련 로직은 없기때문에 내생각에는 블로그 엔티티 조회를 할 필요가 없는데,,
        log.info("UserEntity={}",commentWriter.getClass());

        PostEntity postEntity = postService.getPostWithThrow(postId);

        CommentEntity commentEntity = commentConverter.toEntity(commentWriter, postEntity, commentRegisterRequest);


        return Optional.ofNullable(commentEntity)
                .map(commentService::register)
                .map(commentConverter::toResponse)
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, "Bad Request"));
    }

    public CommentResponse registerCommentByParent(
            HttpServletRequest request,
            Long postId,
            Long parentId,
            CommentRegisterRequest commentRegisterRequest
    ) {
        Long userId = cookieService.validateRoleUserGetId(request);
        UserEntity commentWriter = userService.getUserWithThrow(userId);

        PostEntity postEntity = postService.getPostWithThrow(postId);
        CommentEntity parentComment = commentService.findById(parentId);

        CommentEntity commentEntity = commentConverter.toEntity(commentWriter, postEntity, parentComment, commentRegisterRequest);

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
        Long userId = cookieService.validateRoleUserGetId(request);
        UserEntity commentWriter = userService.getUserWithThrow(userId);
        commentService.delete(postId, commentId, commentWriter);
    }

    public void editComment(
            HttpServletRequest request,
            Long commentId,
            CommentRegisterRequest commentRegisterRequest
    ){
        Long userId = cookieService.validateRoleUserGetId(request);
        commentService.edit(userId, commentId, commentRegisterRequest);
    }
}
