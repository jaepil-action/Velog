package org.velog.db.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    Optional<CommentEntity> findFirstByPostEntity_IdAndUserEntity_IdAndId(Long postId, Long userId, Long commentId);
    Optional<CommentEntity> findFirstByPostEntity_IdAndId(Long postId, Long commentId);


    @Query("select c from CommentEntity c where c.postEntity.id = :postId and c.id = :commentId")
    Optional<CommentEntity> findByCommentAndPost(
            @Param("postId") Long postId,
            @Param("commentId") Long id
    );
}
