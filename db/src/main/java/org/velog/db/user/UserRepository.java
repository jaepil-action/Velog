package org.velog.db.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findFirstByIdOrderByIdDesc(Long userId);

    Optional<UserEntity> findFirstByLoginIdAndPasswordOrderByIdDesc(
            String loginId, String password
    );
}
