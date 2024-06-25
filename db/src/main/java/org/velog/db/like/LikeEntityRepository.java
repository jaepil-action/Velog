package org.velog.db.like;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeEntityRepository extends JpaRepository<LikeEntity, Long> {

    List<LikeEntity> findAllByUserEntity_Id(Long userId);
    List<LikeEntity> findAllByPostEntity_Id(Long postId);

    Optional<LikeEntity> findFirstByUserEntity_IdAndPostEntity_Id(Long userId, Long postId);
}
