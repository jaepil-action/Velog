package org.velog.db.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TagRepository extends JpaRepository<TagEntity, Long> {

    @Query("select t from TagEntity t join fetch t.blogEntity b where t.id = :tagId")
    Optional<TagEntity> findTagWithBlogById(@Param("tagId") Long tagId);

}
