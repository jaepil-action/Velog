package org.velog.api.domain.role.converter;

import lombok.RequiredArgsConstructor;
import org.velog.api.common.annotation.Converter;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.role.controller.model.RoleDto;
import org.velog.api.domain.role.controller.model.UserRoleRegisterRequest;
import org.velog.api.domain.role.controller.model.UserRoleResponse;
import org.velog.db.role.*;
import org.velog.db.user.UserEntity;
import org.velog.db.user.UserRepository;

import java.util.Optional;

@Converter
@RequiredArgsConstructor
public class UserRoleConverter {

    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
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

        UserRoleEntity userRoleEntity = userRoleRepository.findByUserEntity_Id(request.getUserId())
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT, "UserRoleRegisterRequest Null"));

        RoleEntity roleEntity = roleRepository.findById(request.getRoleId())
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT, "UserRoleRegisterRequest Null"));

        userRoleEntity.editUserRole(roleEntity);

        return userRoleEntity;
    }

    public UserRoleResponse toUserRoleResponse(UserRoleEntity userRoleEntity) {
        return Optional.ofNullable(userRoleEntity)
                .map(it -> {
                    return UserRoleResponse.builder()
                            .userRoleId(userRoleEntity.getId())
                            .userEmail(userRoleEntity.getUserEntity().getEmail())
                            .admin(userRoleEntity.getRoleEntity().getAdmin())
                            .authorizationDate(userRoleEntity.getRegistrationDate())
                            .build();
                })
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT, "UserRoleEntity Null"));
    }

    public UserRoleEntity toUserRoleDefaultEntity(UserEntity request){

        RoleEntity roleEntity = roleRepository.findByAdmin(Admin.ROLE_USER) // 고정 디폴트값
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "RoleEntity not found for ROLE_USER"));

        UserEntity userEntity = userRepository.findById(request.getId())
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT, "UserRoleRegisterRequest Null"));

        return UserRoleEntity.builder()
                .roleEntity(roleEntity)
                .userEntity(userEntity)
                .build();
    }

    public UserEntity toUserEntity(UserRoleEntity userRoleEntity) {
        return userRoleEntity.getUserEntity();
    }
}
