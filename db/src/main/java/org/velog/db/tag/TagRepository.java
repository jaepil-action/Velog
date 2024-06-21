package org.velog.db.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.velog.db.user.UserEntity;

import java.util.Optional;

public interface TagRepository extends JpaRepository<TagEntity, Long> {

}
