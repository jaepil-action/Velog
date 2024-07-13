package org.velog.api.domain.log.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.velog.db.log.LogEntity;
import org.velog.db.log.LogRepository;

@RequiredArgsConstructor
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class LogService {

    private final LogRepository logRepository;

    public void saveTxLog(String methodName, long executionTime){

        logRepository.findByMethodName(methodName)
                .ifPresentOrElse(logEntity ->{
                    checkExcessByExecutionTime(logEntity, executionTime);
                }, () ->{
                    LogEntity logENtity = new LogEntity(methodName, executionTime);
                    logRepository.save(logENtity);
                });
    }

    private void checkExcessByExecutionTime(LogEntity logENtity, long executionTime){
        if(logENtity.getExecutionTime() < executionTime){
            logENtity.changeExecutionTime(executionTime);
        }
        return;
    }
}
