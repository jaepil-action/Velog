package org.velog.db.role;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

    @EntityGraph(attributePaths = "roleEntity")
    Optional<UserRoleEntity> findFirstByUserEntity_Id(Long userId);
}
