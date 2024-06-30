package org.velog.db.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.velog.db.user.UserEntity;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    @Query("select p from PostEntity p" +
            " join fetch p.blogEntity b" +
            " join fetch p.tagEntity t" +
            " join fetch p.seriesEntity s" +
            " where p.id = :postId")
    Optional<PostEntity> findPostById(@Param("postId") Long postId);

}
