package org.velog.api.domain.comment.converter;

import org.velog.api.common.annotation.Converter;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.comment.controller.model.CommentRegisterRequest;
import org.velog.api.domain.comment.controller.model.CommentResponse;
import org.velog.db.comment.CommentEntity;
import org.velog.db.post.PostEntity;
import org.velog.db.user.UserEntity;

import java.util.Optional;

@Converter
public class CommentConverter {

    public CommentEntity toEntity(
            UserEntity userEntity,
            PostEntity postEntity,
            CommentRegisterRequest commentRegisterRequest
    ){
        return CommentEntity.builder()
                .userEntity(userEntity)
                .postEntity(postEntity)
                .contents(commentRegisterRequest.getContents())
                .build();
    }

    public CommentEntity toEntity(
            UserEntity userEntity,
            PostEntity postEntity,
            CommentEntity parentComment,
            CommentRegisterRequest commentRegisterRequest
    ){
        CommentEntity commentEntity = CommentEntity.builder()
                .userEntity(userEntity)
                .contents(commentRegisterRequest.getContents())
                .build();
        commentEntity.addParentComment(parentComment);
        commentEntity.addPostEntity(postEntity);

        return commentEntity;
    }

    public CommentResponse toResponse(
            CommentEntity commentEntity
    ){
        return Optional.ofNullable(commentEntity)
                .map(ce -> {
                    CommentResponse response = CommentResponse.builder()
                            .commentWriter(commentEntity.getUserEntity().getLoginId())
                            .contents(ce.getContents())
                            .writeDateTime(ce.getRegistrationDate())
                            .build();

                    if(commentEntity.getParentComment() != null){
                        response.setParentCommentId(commentEntity.getParentComment().getId());
                    }else{
                        response.setParentCommentId(null);
                    }

                    return response;
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "CommentEntity Null"));
    }
}
