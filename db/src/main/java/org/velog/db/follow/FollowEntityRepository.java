package org.velog.db.follow;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowEntityRepository extends JpaRepository<FollowEntity, Long> {

    List<FollowEntity> findAllByFollowee_Id(Long userId);

    List<FollowEntity> findAllByFollower_Id(Long userId);

    Optional<FollowEntity> findFirstByFollower_IdAndFollowee_Id(Long followerId, Long followeeId);
}
