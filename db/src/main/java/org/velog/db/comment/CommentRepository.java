package org.velog.db.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    Optional<CommentEntity> findFirstByPostEntity_IdAndUserEntity_IdAndId(Long postId, Long userId, Long commentId);
    Optional<CommentEntity> findFirstByPostEntity_IdAndId(Long postId, Long commentId);
}
