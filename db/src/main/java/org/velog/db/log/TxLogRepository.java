package org.velog.db.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface TxLogRepository extends JpaRepository<TxLogEntity, Long> {


    @Query("select t from TxLogEntity t" +
            " where t.executionTime < :executionTime" +
            " and t.methodName = :methodName")
    Optional<TxLogEntity> findByMethodNameLess(
            @Param("executionTime") long executionTime,
            @Param("methodName") String methodName
    );

    Optional<TxLogEntity> findByMethodName(String methodName);
}
