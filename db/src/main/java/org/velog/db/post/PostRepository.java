package org.velog.db.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.velog.db.user.UserEntity;

import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

}
