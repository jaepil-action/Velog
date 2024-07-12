package org.velog.api.common.config.logtrace.v1_autoproxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.velog.api.utils.logtrace.LogTrace;
import org.velog.api.common.config.logtrace.v1_autoproxy.advice.LogTraceAdvice;

@Slf4j
//@Configuration
public class AutoProxyCreatorConfig {
    /***
     * implementation 'org.springframework.boot:spring-boot-starter-aop'
     * 위 스프링 부트 설정으로 'AnnotationAwareAspectJAutoProxyCreator' 라는
     * 빈 후처리기<자동 프록시 생성기>를 스프링빈에 자동으로 등록된다.
     * 등록된 빈후처리기는 스프링빈에 등록된 Advisor를 자동으로 찾아서 프록시가
     * 필요한 곳에 자동으로 프록시를 적용해준다.
     * Advisor에는 pointCut이 포함되어있기 때문에 어떤 스프링 빈에 적용 해야 알지 알 수있다.
     */
    @Bean
    public Advisor advisor(LogTrace logTrace){

        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* org.velog.api.domain..*(..)) && !execution(* hello.proxy.app..noLog(..))");

        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        //advisor = pointcut + advice
        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
