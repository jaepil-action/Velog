package org.velog.db.series;

import org.springframework.data.jpa.repository.JpaRepository;
import org.velog.db.user.UserEntity;

import java.util.Optional;

public interface SeriesRepository extends JpaRepository<SeriesEntity, Long> {

}
