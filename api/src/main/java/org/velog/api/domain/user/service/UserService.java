package org.velog.api.domain.user.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.error.UserErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.user.controller.model.UserEditRequest;
import org.velog.db.role.Admin;
import org.velog.db.role.RoleEntity;
import org.velog.db.role.UserRoleEntity;
import org.velog.db.role.UserRoleRepository;
import org.velog.db.user.UserEntity;
import org.velog.db.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public UserEntity register(UserEntity userEntity){
        return Optional.ofNullable(userEntity)
                .map(it -> {
                    userEntity.setRegistrationDate();
                    return userRepository.save(userEntity);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "User Entity null"));
    }

    public void deleteUser(Long userId, String password) {
        UserEntity deleteUser = getUserWithThrow(userId);
        if(deleteUser.getPassword().equals(password)){
            userRepository.delete(deleteUser);
        }else{
            throw new ApiException(ErrorCode.BAD_REQUEST, "비밀번호가 틀렸습니다.");
        }
    }

    public void editEmail(UserEditRequest param){
        Optional.ofNullable(param)
                .ifPresentOrElse(it -> {
                    UserEntity user = getUserWithThrow(it.getId());
                    user.changeEmail(param.getEmail());
                    userRepository.save(user);
                }, () -> {
                    throw new ApiException(ErrorCode.NULL_POINT, "User Entity null");
                });
    }

    public boolean checkUserRole(
            Long userId
    ){
        if(userRoleRepository.findFirstByUserEntity_Id(userId).isPresent()){
            UserRoleEntity userRoleEntity = userRoleRepository.findFirstByUserEntity_Id(userId).get();
            RoleEntity roleEntity = userRoleEntity.getRoleEntity();
            return roleEntity.getAdmin().equals(Admin.ROLE_ADMIN);
        }else{
            return false;
        }
    }

    public UserEntity login(
            String loginId,
            String password
    ){
        return getUserWithThrow(loginId, password);
    }

    public UserEntity getUserWithThrow(
            String loginId,
            String password
    ){
        return userRepository.findFirstByLoginIdAndPasswordOrderByIdDesc(
                loginId,
                password
        ).orElseThrow(()-> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }

    public UserEntity getUserWithThrow(
            Long userId
    ){
        return userRepository.findFirstByIdOrderByIdDesc(
                userId
        ).orElseThrow(()-> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }
}
