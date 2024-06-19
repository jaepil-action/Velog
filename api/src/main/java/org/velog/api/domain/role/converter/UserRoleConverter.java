package org.velog.api.domain.role.converter;

import lombok.RequiredArgsConstructor;
import org.velog.api.common.annotation.Converter;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.role.controller.model.RoleDto;
import org.velog.api.domain.role.controller.model.UserRoleRegisterRequest;
import org.velog.api.domain.role.controller.model.UserRoleResponse;
import org.velog.db.role.RoleEntity;
import org.velog.db.role.RoleRepository;
import org.velog.db.role.UserRoleEntity;
import org.velog.db.user.UserEntity;
import org.velog.db.user.UserRepository;

import java.util.Optional;

@Converter
@RequiredArgsConstructor
public class UserRoleConverter {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public RoleEntity toRoleEntity(RoleDto request){
        return Optional.ofNullable(request)
                .map(it -> {
                    // to entity
                    return RoleEntity.builder()
                            .admin(request.getAdmin())
                            .build();
                })
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT, "RoleRegisterDto Null"));
    }

    public RoleDto toRoleDto(RoleEntity roleEntity){
        return Optional.ofNullable(roleEntity)
                .map(it -> {
                    return RoleDto.builder()
                            .admin(roleEntity.getAdmin())
                            .build();
                })
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT, "RoleEntity Null"));
    }

    public UserRoleEntity toUserRoleEntity(UserRoleRegisterRequest request){

        RoleEntity roleEntity = roleRepository.findById(request.getRoleId())
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT, "UserRoleRegisterRequest Null"));

        UserEntity userEntity = userRepository.findById(request.getUserId())
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT, "UserRoleRegisterRequest Null"));

        return UserRoleEntity.builder()
                .userEntity(userEntity)
                .roleEntity(roleEntity)
                .build();
    }

    public UserRoleResponse toUserRoleResponse(UserRoleEntity userRoleEntity) {
        return Optional.ofNullable(userRoleEntity)
                .map(it -> {
                    return UserRoleResponse.builder()
                            .userRoleId(userRoleEntity.getId())
                            .userId(userRoleEntity.getUserEntity().getId())
                            .userEmail(userRoleEntity.getUserEntity().getEmail())
                            .adminId(userRoleEntity.getRoleEntity().getId())
                            .admin(userRoleEntity.getRoleEntity().getAdmin())
                            .build();
                })
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT, "UserRoleEntity Null"));
    }
}
