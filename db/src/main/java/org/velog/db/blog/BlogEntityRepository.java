package org.velog.db.blog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BlogEntityRepository extends JpaRepository<BlogEntity, Long> {

    Optional<BlogEntity> findByUserEntity_Id(Long userId);

    @Query("select b from BlogEntity b join fetch b.userEntity u where u.loginId = :loginId")
    Optional<BlogEntity> findBlogByUserLoginId(@Param("loginId") String loginId);

    void deleteByUserEntity_Id(Long userId);
}
