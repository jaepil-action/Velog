package org.velog.db.blog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BlogEntityRepository extends JpaRepository<BlogEntity, Long> {

    @Query("select b from BlogEntity b where b.userEntity.loginId = :loginId")
    Optional<BlogEntity> findBlogByUserLoginId(String loginId);


    Optional<BlogEntity> findFirstByUserEntity_LoginId(String loginId);

    Optional<BlogEntity> findFirstByUserEntity_Id(Long userId);

    List<BlogEntity> findAllByUserEntity_Id(Long userId);

    void deleteByUserEntity_Id(Long userId);
}
