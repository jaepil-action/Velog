package org.velog.api.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.comment.controller.model.CommentRegisterRequest;
import org.velog.db.comment.CommentEntity;
import org.velog.db.comment.CommentRepository;
import org.velog.db.user.UserEntity;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentEntity register(
        CommentEntity commentEntity
    ){
        return Optional.ofNullable(commentEntity)
                .map(ce -> {
                    ce.addRegistrationDate();
                    return commentRepository.save(ce);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "CommentEntity Null"));
    }

    public void delete(
            Long postId,
            Long commentId,
            UserEntity writer
    ){
        CommentEntity commentEntity = commentRepository.findByCommentAndPost(postId, commentId)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "CommentEntity Null"));

        if(!Objects.equals(commentEntity.getUserEntity(), writer)){
            throw new ApiException(ErrorCode.BAD_REQUEST, "자신의 댓글이 아닙니다.");
        }

        commentRepository.delete(commentEntity);
    }

    public void edit(
            Long userId,
            Long commentId,
            CommentRegisterRequest editComment
    ){
        CommentEntity commentEntity = findById(commentId);
        if(!Objects.equals(commentEntity.getUserEntity().getId(), userId)){
            throw new ApiException(ErrorCode.BAD_REQUEST, "자신의 댓글이 아닙니다.");
        }

        commentEntity.editComment(editComment.getContents());
    }



    public CommentEntity findById(Long commentId){
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "CommentEntity Null"));
    }
}
