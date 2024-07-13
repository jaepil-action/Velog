package org.velog.api.common.config.logtrace.txlogtrace.advisor_aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.velog.api.domain.log.service.LogService;
import org.velog.api.utils.logtrace.LogTrace;
import org.velog.api.utils.logtrace.TraceStatus;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class TxLogTrace {

    private final LogTrace logTrace;
    private final LogService logService;
    @Pointcut("execution(* org.velog.api.domain..*Service.*(..))")
    private void allService(){}

    @Pointcut("!execution(* org.velog.api.domain..*TokenService.*(..)) && !execution(* org.velog.api.domain..*LogService.*(..))")
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

            logService.saveTxLog(methodName, executionTime);
        }
    }
}
