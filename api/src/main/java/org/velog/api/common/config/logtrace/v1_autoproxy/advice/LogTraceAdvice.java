package org.velog.api.common.config.logtrace.v1_autoproxy.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.velog.api.utils.logtrace.LogTrace;
import org.velog.api.utils.logtrace.TraceStatus;

import java.lang.reflect.Method;

public class LogTraceAdvice implements MethodInterceptor {


    private final LogTrace logTrace;

    public LogTraceAdvice(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        TraceStatus status = null;

        try{
            Method method = invocation.getMethod();
            String message = method.getDeclaringClass().getSimpleName() + "." + method.getName() + "()";
            status = logTrace.begin(message);
            //
            Object result = invocation.proceed();
            logTrace.end(status);
            return result;
        }catch (Exception e){
            logTrace.exception(status, e);
            throw e;
        }
    }
}
