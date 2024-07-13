package org.velog.db.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LogRepository extends JpaRepository<LogEntity, Long> {
    Optional<LogEntity> findByMethodName(String methodName);
}
