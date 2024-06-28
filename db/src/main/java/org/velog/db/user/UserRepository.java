package org.velog.db.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("select u from UserEntity  u where u.id = :id")
    Optional<UserEntity> findUserById(@Param("id") Long userId);
    Optional<UserEntity> findFirstByIdOrderByIdDesc(Long userId);


    @Query("select u from UserEntity u where u.loginId = :loginId and u.password = :password")
    Optional<UserEntity> findUserByLoginInfo(
            @Param("loginId") String loginId,
            @Param("password") String password
    );
    Optional<UserEntity> findFirstByLoginIdAndPasswordOrderByIdDesc(
            String loginId, String password
    );

    Optional<UserEntity> findUserByLoginId(String loginId);
    Optional<UserEntity> findFirstByLoginId(String loginId);
    Optional<UserEntity> findFirstByEmail(String email);

}
