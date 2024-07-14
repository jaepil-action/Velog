package org.velog.db.user;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @EntityGraph(attributePaths = "blogEntity")
    @Query("select u from UserEntity  u where u.id = :id")
    Optional<UserEntity> findUserById(@Param("id") Long userId);


    @Query("select u from UserEntity u left join fetch u.blogEntity b" +
            " where u.loginId = :loginId and u.password = :password")
    Optional<UserEntity> findUserByLoginInfo(
            @Param("loginId") String loginId,
            @Param("password") String password
    );

    @EntityGraph(attributePaths = "blogEntity")
    Optional<UserEntity> findFirstByLoginId(String loginId);
    @EntityGraph(attributePaths = "blogEntity")
    Optional<UserEntity> findFirstByEmail(String email);

}
