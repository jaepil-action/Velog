package org.velog.db.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findByUserEntity_Id(Long userId);

    @Query("select c from CommentEntity c where c.postEntity.id = :postId and c.id = :commentId")
    Optional<CommentEntity> findByCommentAndPost(
            @Param("postId") Long postId,
            @Param("commentId") Long id
    );
}
