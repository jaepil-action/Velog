package org.velog.api.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
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
        CommentEntity commentEntity = commentRepository.findFirstByPostEntity_IdAndId(postId, commentId)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "CommentEntity Null"));

        if(!Objects.equals(commentEntity.getUserEntity(), writer)){
            throw new ApiException(ErrorCode.BAD_REQUEST, "userId가 작성한 댓글이 아닙니다.");
        }

        commentRepository.delete(commentEntity);
    }

    public CommentEntity findById(Long commentId){
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "CommentEntity Null"));
    }
}
