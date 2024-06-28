package org.velog.db.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowEntityRepository extends JpaRepository<FollowEntity, Long> {

    List<FollowEntity> findAllByFollowee_Id(Long userId);

    List<FollowEntity> findAllByFollower_Id(Long userId);

    Optional<FollowEntity> findFirstByFollower_IdAndFollowee_Id(Long followerId, Long followeeId);

    @Query("select f from FollowEntity f where f.follower.id = :followerId and f.followee.id = :followeeId")
    Optional<FollowEntity> findByFollowerAndFollowee(
            @Param("followerId") Long followerId,
            @Param("followeeId") Long followeeId
    );
}
