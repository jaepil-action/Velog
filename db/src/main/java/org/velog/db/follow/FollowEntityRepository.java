package org.velog.db.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowEntityRepository extends JpaRepository<FollowEntity, Long> {


    //@Query("select f from FollowEntity f join fetch f.follower fw join fetch f.followee fe where fe.id = :userId")
    //List<FollowEntity> findAllByFolloweeId(@Param("userId") Long userId);
    List<FollowEntity> findAllByFollowee_Id(Long userId);

    //@Query("select f from FollowEntity f join fetch f.follower fw where fw.id = :userId")
    //List<FollowEntity> findAllByFollowerId(@Param("userId") Long userId);
    List<FollowEntity> findAllByFollower_Id(Long userId);

    @Query("select f from FollowEntity f where f.follower.id = :followerId and f.followee.id = :followeeId")
    Optional<FollowEntity> findByFollowerAndFollowee(
            @Param("followerId") Long followerId,
            @Param("followeeId") Long followeeId
    );
}
