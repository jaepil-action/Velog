package org.velog.api.domain.follow.converter;

import org.velog.api.common.annotation.Converter;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.follow.controller.model.FollowResponse;
import org.velog.api.domain.follow.controller.model.FollowResponses;
import org.velog.db.user.UserEntity;

import java.util.List;
import java.util.Optional;

@Converter
public class FollowConverter {

    public FollowResponse toResponse(UserEntity userEntity){

        return Optional.ofNullable(userEntity)
                .map(it -> {
                    return FollowResponse.builder()
                            .name(it.getName())
                            .loginId(it.getLoginId())
                            .profileImage(null) // TODO
                            .build();
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserEntity Null"));
    }

    public FollowResponses toResponses(List<FollowResponse> followResponseList){

        return Optional.ofNullable(followResponseList)
                .map(it -> {
                    return FollowResponses.builder()
                            .followResponseList(followResponseList)
                            .build();
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserEntity Null"));
    }

}
