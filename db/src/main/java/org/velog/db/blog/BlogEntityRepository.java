package org.velog.db.blog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BlogEntityRepository extends JpaRepository<BlogEntity, Long> {
    //Optional<BlogEntity> findFirstByUserEntity_Id(Long userId);
    @Query("select b from BlogEntity b join fetch b.userEntity u" +
            " where u.id = :userId")
    Optional<BlogEntity> findBlogByUserId(@Param("userId") Long userId);

    void deleteByUserEntity_Id(Long userId);
}
