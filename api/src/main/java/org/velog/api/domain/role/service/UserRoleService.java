package org.velog.api.domain.role.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.db.role.RoleEntity;
import org.velog.db.role.RoleRepository;
import org.velog.db.role.UserRoleEntity;
import org.velog.db.role.UserRoleRepository;
import org.velog.db.user.UserEntity;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;


    public RoleEntity roleRegister(RoleEntity roleEntity) {
        return Optional.ofNullable(roleEntity)
                .map(it -> {
                    return roleRepository.save(roleEntity); ///
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "RoleEntity null"));
    }

    public UserRoleEntity userRoleRegister(UserRoleEntity userRoleEntity) {
        return Optional.ofNullable(userRoleEntity)
                .map(it -> {
                    userRoleEntity.setAuthorizationDate();
                    return userRoleRepository.save(userRoleEntity);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "RoleEntity null"));
    }
}
