package org.velog.api.common.config.logtrace.v2_autoaopcreator.advisor_aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.velog.api.common.logtrace.LogTrace;
import org.velog.api.common.logtrace.TraceStatus;

@Slf4j
@Aspect
/**
 *  애노테이션 기반 프록시를 적용 할 때 @Aspect를 사용해서
 *  Advisor를 편리하게 생성 할 수 있다.
 *  포인트컷 @Around + Advice 선언한 메서드
 *  ProceedingJoinPoint 실제호출대상, 전달인자, 어떤겍체와 메서드가 호출되어있는지 정보 포함
 *
 *  V1에서 공부한 'AnnotationAwareAspectJAutoProxyCreator'는 @Aspect를 Advisor로 변환해서
 *  저장 하는 기능도 있다. 'AnnotationAware'의 의미
 */
@RequiredArgsConstructor
public class LogTraceAspect {

    private final LogTrace logTrace;

    @Around("execution(* org.velog.api.domain..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{

        TraceStatus status = null;

        try{
            String message = joinPoint.getSignature().toShortString();
            status = logTrace.begin(message);

            Object result = joinPoint.proceed(); // target 호출

            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
