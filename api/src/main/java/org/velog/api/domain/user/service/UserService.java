package org.velog.api.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.error.UserErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.db.blog.BlogEntityRepository;
import org.velog.db.comment.CommentEntity;
import org.velog.db.comment.CommentRepository;
import org.velog.db.follow.FollowEntityRepository;
import org.velog.db.like.LikeEntityRepository;
import org.velog.db.role.Admin;
import org.velog.db.role.RoleEntity;
import org.velog.db.role.UserRoleEntity;
import org.velog.db.role.UserRoleRepository;
import org.velog.db.user.UserEntity;
import org.velog.db.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final FollowEntityRepository followEntityRepository;
    private final LikeEntityRepository likeEntityRepository;
    private final CommentRepository commentRepository;
    private final BlogEntityRepository blogEntityRepository;

    public UserEntity register(UserEntity userEntity){

        return Optional.ofNullable(userEntity)
                .map(it -> {
                    checkDuplicationLoginIdAndEmail(userEntity.getLoginId(), userEntity.getEmail());
                    return userRepository.save(userEntity);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "User Entity null"));
    }

    public void deleteUser(Long userId, String password) {
        UserEntity deleteUser = getUserWithThrow(userId);
        if(deleteUser.getPassword().equals(password)){
            deleteUserBefore(userId);
            userRepository.delete(deleteUser);
        }else{
            throw new ApiException(ErrorCode.BAD_REQUEST, "비밀번호가 틀렸습니다.");
        }
    }

    public void editEmail(
            Long userId,
            String email
    ){
        UserEntity userEntity = getUserWithThrow(userId);
        userEntity.changeEmail(email);
    }

    public boolean checkUserRole(
            Long userId
    ){
/*        if(userRoleRepository.findFirstByUserEntity_Id(userId).isPresent()){
            UserRoleEntity userRoleEntity = userRoleRepository.findFirstByUserEntity_Id(userId).get();
            RoleEntity roleEntity = userRoleEntity.getRoleEntity();
            return roleEntity.getAdmin().equals(Admin.ROLE_ADMIN);
        }else{
            return false;
        }*/
        /***
         * TODO 위에 코드 아래로 리팩터링 (공부필요)
         */
        return userRoleRepository.findByUserEntity_Id(userId)
                .map(UserRoleEntity::getRoleEntity)
                .map(RoleEntity::getAdmin)
                .map(Admin.ROLE_ADMIN::equals)
                .orElse(false);
    }

    public void checkDuplicationLoginIdAndEmail(
        String loginId,
        String email
    ){

        if(userRepository.findFirstByEmail(email).isPresent() &&
                userRepository.findFirstByLoginId(loginId).isPresent()){
            throw new ApiException(ErrorCode.BAD_REQUEST, "Email and LonginId is already taken");
        }

        if(userRepository.findFirstByLoginId(loginId).isPresent()){ //
            throw new ApiException(ErrorCode.BAD_REQUEST, "LogInId is already taken");
        }

        if(userRepository.findFirstByEmail(email).isPresent()){
            throw new ApiException(ErrorCode.BAD_REQUEST, "Email is already taken");
        }
    }

    public boolean checkDuplicationEmail(
            String email
    ){
        return userRepository.findFirstByEmail(email).isPresent();
    }
    public boolean checkDuplicationLoginId(
            String loginId
    ){
        return userRepository.findFirstByLoginId(loginId).isPresent(); //
    }

    public UserEntity getUserWithThrow(
            String loginId,
            String password
    ){
        return userRepository.findUserByLoginInfo(
                loginId,
                password
        ).orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }

    public UserEntity getUserWithThrow(
            Long userId
    ){
        return userRepository.findUserById(
                userId
        ).orElseThrow(()-> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }

    public UserEntity getUserWithThrow(
            String loginId
    ){
        return userRepository.findFirstByLoginId(
                loginId
        ).orElseThrow(()-> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }

    private void deleteUserBefore(Long userId){
        blogEntityRepository.findByUserEntity_Id(userId).ifPresent(blogEntityRepository::delete);
        commentRepository.findByUserEntity_Id(userId).forEach(CommentEntity::changeUserEntityNull);
        likeEntityRepository.deleteAll(likeEntityRepository.findAllByUserEntity_Id(userId));
        followEntityRepository.deleteAll(followEntityRepository.findAllByFollower_Id(userId));
        followEntityRepository.deleteAll(followEntityRepository.findAllByFollowee_Id(userId));
    }
}
