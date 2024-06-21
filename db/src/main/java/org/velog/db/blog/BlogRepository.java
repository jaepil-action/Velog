package org.velog.db.blog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.velog.db.user.UserEntity;

import java.util.Optional;

public interface BlogRepository extends JpaRepository<BlogEntity, Long> {


}
