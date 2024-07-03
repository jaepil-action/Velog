package org.velog.api.domain.role.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.post.service.PostService;
import org.velog.db.post.PostEntity;
import org.velog.db.role.RoleEntity;
import org.velog.db.role.RoleRepository;
import org.velog.db.role.UserRoleEntity;
import org.velog.db.role.UserRoleRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserRoleService {

    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PostService postService;


    public RoleEntity roleRegister(
            RoleEntity roleEntity
    ){
        return Optional.ofNullable(roleEntity)
                .map(it -> {
                    return roleRepository.save(roleEntity); ///
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "RoleEntity null"));
    }

    public UserRoleEntity userRoleRegister(
            UserRoleEntity userRoleEntity
    ){
        return Optional.ofNullable(userRoleEntity)
                .map(it -> {
                    userRoleEntity.setRegistrationDate();
                    return userRoleRepository.save(userRoleEntity);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "RoleEntity null"));
    }
    public void deletePostByAdmin(Long postId){
        postService.deletePostById(postId);
    }
}
