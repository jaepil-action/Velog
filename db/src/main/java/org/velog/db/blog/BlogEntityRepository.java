package org.velog.db.blog;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlogEntityRepository extends JpaRepository<BlogEntity, Long> {
    List<BlogEntity> findAllByUserEntity_LoginId(String loginId);
}
