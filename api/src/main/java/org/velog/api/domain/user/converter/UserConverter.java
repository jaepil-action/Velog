package org.velog.api.domain.user.converter;

import lombok.RequiredArgsConstructor;
import org.velog.api.common.annotation.Converter;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.user.controller.model.UserRegisterRequest;
import org.velog.api.domain.user.controller.model.UserResponse;
import org.velog.db.user.UserEntity;

import java.util.Optional;

@RequiredArgsConstructor
@Converter
public class UserConverter {

    public UserEntity toEntity(UserRegisterRequest request){
        return Optional.ofNullable(request)
                .map(it -> {
                    // to entity
                    return UserEntity.builder()
                            .loginId(request.getLoginId())
                            .name(request.getName())
                            .email(request.getEmail())
                            .password(request.getPassword())
                            .build();
                })
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT, "UserRegisterRequest Null"));
    }

    public UserResponse toResponse(UserEntity userEntity){
        return Optional.ofNullable(userEntity)
                .map(it -> {
                    return UserResponse.builder()
                            .id(userEntity.getId())
                            .loginId(userEntity.getLoginId())
                            .name(userEntity.getName())
                            .email(userEntity.getEmail())
                            .registrationDate(userEntity.getRegistrationDate())
                            .build();
                })
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT, "UserRegisterRequest Null"));
    }
}
