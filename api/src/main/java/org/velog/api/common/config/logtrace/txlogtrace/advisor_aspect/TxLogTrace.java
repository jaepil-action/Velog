package org.velog.api.common.config.logtrace.txlogtrace.advisor_aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.velog.api.utils.logtrace.LogTrace;
import org.velog.api.utils.logtrace.TraceStatus;
import org.velog.db.log.TxLogEntity;
import org.velog.db.log.TxLogRepository;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class TxLogTrace {

    private final LogTrace logTrace;
    private final TxLogRepository txLogRepository;
    @Pointcut("execution(* org.velog.api.domain..*Service.*(..))")
    private void allService(){}

    @Pointcut("!execution(* org.velog.api.domain..*TokenService.*(..))")
    private void excludeTokenService(){}

    @Around("allService() && excludeTokenService()")
    public Object transactionLog(ProceedingJoinPoint joinPoint) throws Throwable{

        TraceStatus status = null;
        long startTime = System.currentTimeMillis();

        try{
            log.info("[Transaction start] {}", joinPoint.getSignature());
            String message = joinPoint.getSignature().toShortString();
            status = logTrace.begin(message);

            Object result = joinPoint.proceed();

            logTrace.end(status);


            log.info("[Transaction commit] {}", joinPoint.getSignature());
            return result;

        }catch (Exception e){
            log.info("[Transaction rollback] {}", joinPoint.getSignature());
            throw e;

        }finally {
            String methodName = status.getMessage();
            long executionTime = System.currentTimeMillis() - startTime;
            log.info("long executionTime={}", executionTime);

            saveTxLog(methodName, executionTime);
        }
    }

    private void saveTxLog(String methodName, long executionTime){

        txLogRepository.findByMethodName(methodName)
                .ifPresentOrElse(txLogEntity ->{
                    checkExcessByExecutionTime(txLogEntity, executionTime);
                }, () ->{
                    TxLogEntity txLogEntity = new TxLogEntity(methodName, executionTime);
                    txLogRepository.save(txLogEntity);
                });
    }

    private void checkExcessByExecutionTime(TxLogEntity txLogEntity, long executionTime){
        if(txLogEntity.getExecutionTime() < executionTime){
            txLogEntity.changeExecutionTime(executionTime);
        }
        return;
    }
}
