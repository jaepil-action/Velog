package org.velog.api.common.config.logtrace.v2_autoaopcreator_aspect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.velog.api.common.config.logtrace.v2_autoaopcreator_aspect.advisor_aspect.LogTraceAspect;
import org.velog.api.utils.logtrace.LogTrace;

//@Configuration
public class AutoAopCreatorAspectConfig {

    @Bean
    public LogTraceAspect logTraceAspect(LogTrace logTrace){
        return new LogTraceAspect(logTrace);
    }
}
