package org.velog.api.common.config.logtrace.v2_autoaopcreator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.velog.api.common.config.logtrace.v2_autoaopcreator.advisor_aspect.LogTraceAspect;
import org.velog.api.common.logtrace.LogTrace;

@Configuration
public class AutoAopCreatorConfig {

    @Bean
    public LogTraceAspect logTraceAspect(LogTrace logTrace){
        return new LogTraceAspect(logTrace);
    }
}
