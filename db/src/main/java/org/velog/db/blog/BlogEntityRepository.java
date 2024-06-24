package org.velog.db.blog;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlogEntityRepository extends JpaRepository<BlogEntity, Long> {
    Optional<BlogEntity> findFirstByUserEntity_LoginId(String loginId);

    Optional<BlogEntity> findFirstByUserEntity_Id(Long userId);
}
