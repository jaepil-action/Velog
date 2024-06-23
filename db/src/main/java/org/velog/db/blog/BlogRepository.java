package org.velog.db.blog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.velog.db.user.UserEntity;

import java.util.List;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<BlogEntity, Long> {
    Optional<BlogEntity> findFirstByUserEntity_LoginId(String loginId);

    List<BlogEntity> findAllByOrderByRegistrationDate();

    List<BlogEntity> findAllByOrderByPostEntities_likeCount();

}
